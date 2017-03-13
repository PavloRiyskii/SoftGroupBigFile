import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


/**
 * Created by pavlo on 12.03.17.
 */
public class Top {
    private File bigFile;
    private File countFile;
    private String spliter;
    private int topNumber;

    /**
     * I used here "|" as spliter
     * @param bigFile the file with size more than 10 GB
     * @param topNumber the number of generated top
     * @throws IOException
     */
    public Top(File bigFile,  int topNumber) throws IOException {
        this.bigFile = bigFile;
        this.spliter = "|";
        this.topNumber = topNumber;
        this.countFile = new File(System.getProperty("user.dir") + "/count.txt");
        countFile.delete();
        countFile.createNewFile();
    }

    /**
     *
     * @return the list of top frazes the size of list is choosen by you in constructor
     * @throws IOException
     */
    public List<TopElement> getTop() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(bigFile));
        String readedLine;

        while((readedLine = reader.readLine()) != null) {
            String[] frases = readedLine.split("\\" + spliter);
            for(int i = 0; i < frases.length; i++) {
                writeDate(frases[i]);
            }
        }
        return generateTop();
    }

    private void writeDate(String currentFrase) throws IOException {
        File temp = new File(System.getProperty("user.dir") + "/temp.txt");
        temp.createNewFile();

        BufferedReader countReader = new BufferedReader(new FileReader(this.countFile));
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(temp, true)));

        String currentCountFrase;
        boolean isEmpty = true, isWrited = false;

        while((currentCountFrase = countReader.readLine()) != null ){
            String[] topElement = currentCountFrase.split("\\" + spliter);

            if(topElement[0].equals(currentFrase)) {
                if (!isWrited) {
                    currentCountFrase = currentFrase + spliter + (Integer.parseInt(topElement[1]) + 1);
                    isWrited = true;
                }
            } else if(topElement[0].compareTo(currentFrase) >= 0) {
                if(!isWrited) {
                    isWrited = true;
                    writer.println(currentFrase + spliter + "1");
                }
            }

            writer.println(currentCountFrase);
            isEmpty = false;
        }

        if(isEmpty || !isWrited) {
            writer.println(currentFrase + spliter + "1");
        }

        writer.close();
        countReader.close();

        Files.copy(temp.toPath(), this.countFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        this.countFile = new File(System.getProperty("user.dir") + "/count.txt");
        temp.delete();
    }

    private List<TopElement> generateTop() throws IOException {
        List<TopElement> top = new ArrayList<>();
        String string;
        BufferedReader reader = new BufferedReader(new FileReader(countFile));
        while((string = reader.readLine()) != null) {
            String[] currentElement = string.split("\\" + spliter);
            TopElement elem = new TopElement(currentElement[0], Integer.parseInt(currentElement[1]));
            int place = findPlace(top, elem);
            if(place == topNumber) {
                continue;
            }
            top.add(place, elem);
        }
        return top.subList(0, topNumber > top.size() ? top.size() : topNumber);
    }

    private int findPlace(List<TopElement> elements, TopElement elemet) {
        ListIterator<TopElement> iter = elements.listIterator();
        int i = 0;
        while(iter.hasNext()) {
            if(elemet.compareTo(iter.next()) >= 0)
                return i;
            i++;
        }
        return i < topNumber ? 0 : topNumber;
    }

    private void cleanGarbige(List<TopElement> elements) {

    }

}
