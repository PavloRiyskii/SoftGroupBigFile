/**
 * Created by pavlo on 12.03.17.
 */
public class TopElement  implements  Comparable{
    private String value;
    private int count;

    public TopElement(String value, int count) {
        this.value = value;
        this.count = count;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

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
