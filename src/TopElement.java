/**
 * Created by pavlo on 12.03.17.
 */
public class TopElement  implements  Comparable{
    private String value;
    private int count;

    /**
     *
     * @param value phrase
     * @param count the number of meeting this phrase in the big file
     */
    public TopElement(String value, int count) {
        this.value = value;
        this.count = count;
    }

    /**
     *
     * @return the phrase
     */
    public String getValue() {
        return value;
    }

    /**
     *
     * @param value save new value to phrase
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     *
     * @return the number of meething this phrase in the file
     */
    public int getCount() {
        return count;
    }

    /**
     *
     * @param count set the number of meething
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Compare current object to incoming TopElement object
     * @param o
     * @return 1 if this object is "grather" than incomed, -1 if "lower" and 0 if those object is equals
     */
    @Override
    public int compareTo(Object o) {
        if(o == null) {
            return 1;
        }
        TopElement elem = (TopElement) o;
        if(this.getCount() < elem.getCount())
            return -1;
        else if(this.getCount() > elem.getCount())
            return 1;
        else
            return 0;
    }

    @Override
    public String toString() {
        return this.value + " ||||| " + this.count;
    }
}
