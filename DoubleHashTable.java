//#username1 - argov
//#id1       - 308447382
//#name1     - Yael Argov
//#username2 - amitrosen
//#id2       - 208279489
//#name2     - Amit Rosen
import java.util.Random;

public class DoubleHashTable extends OAHashTable {

    private ModHash hashFunction1;
    private ModHash hashFunction2;

    public DoubleHashTable(int m, long p) {
        super(m);
        this.hashFunction1 = ModHash.GetFunc(m, p);
        this.hashFunction2 = ModHash.GetFunc(m - 1, p);
    }

    @Override
    public int Hash(long x, int i) {
        int returnVal = (this.hashFunction1.Hash(x) + i * (this.hashFunction2.Hash(x) + 1)) % this.maxSize;
        return (returnVal >= 0) ? returnVal : returnVal + this.maxSize;
    }

}
