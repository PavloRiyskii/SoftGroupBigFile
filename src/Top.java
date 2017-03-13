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

    public Top(File bigFile, String spliter, int topNumber) throws IOException {
        this.bigFile = bigFile;
        this.spliter = spliter;
        this.topNumber = topNumber;
        this.countFile = new File(System.getProperty("user.dir") + "/count.txt");
        countFile.delete();
        countFile.createNewFile();
    }

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
        boolean isEmpty = true;

        while((currentCountFrase = countReader.readLine()) != null ){
            String[] topElement = currentCountFrase.split("\\" + spliter);

            if(topElement[0].compareTo(currentFrase) == 0) {
                currentCountFrase = currentFrase + spliter + (Integer.parseInt(topElement[1]) + 1);
            } else if(topElement[0].compareTo(currentFrase) > 0) {
                writer.println(currentFrase + spliter + "1");
            }

            writer.println(currentCountFrase);
            isEmpty = false;
        }

        if(isEmpty) {
            writer.println(currentFrase + spliter + "1");
        }

        writer.close();
        countReader.close();

        Files.copy(temp.toPath(), this.countFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        this.countFile = new File(System.getProperty("user.dir") + "/count.txt");
        temp.delete();
    }

    private List<TopElement> generateTop() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(this.countFile));
        List<TopElement> top = new ArrayList<>();
        String elem;
        while((elem = reader.readLine()) != null) {
            String[] currentElemeString = elem.split("\\" + spliter);
            TopElement currentElem = new TopElement(currentElemeString[0], Integer.parseInt(currentElemeString[1]));
            top.add(findPlace(top, currentElem), currentElem);
        }

        return top;
    }

    private int findPlace(List<TopElement> elements, TopElement currentElement) {
        int i = 0;
        ListIterator<TopElement> iter = elements.listIterator();
        while(iter.hasNext() && i != topNumber) {
            if(currentElement.compareTo(iter.next()) == 1)
                return i;
            i++;
        }
        return i < topNumber ? 0: topNumber + 1;
    }
}
