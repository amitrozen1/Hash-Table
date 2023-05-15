//#username1 - argov
//#id1       - 308447382
//#name1     - Yael Argov
//#username2 - amitrosen
//#id2       - 208279489
//#name2     - Amit Rosen
public class HashTableElement{
    private long key;
    private long value;

    public HashTableElement(long key, long value) {
        this.key = key;
        this.value = value;
    }

    public long GetKey() { return this.key;}

    public long GetValue() { return this.value;}
}