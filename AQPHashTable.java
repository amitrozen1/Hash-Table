//#username1 - argov
//#id1       - 308447382
//#name1     - Yael Argov
//#username2 - amitrosen
//#id2       - 208279489
//#name2     - Amit Rosen
import java.util.Random;

public class AQPHashTable extends OAHashTable {

    private ModHash hashFunction;

    public AQPHashTable(int m, long p) {
        super(m);
        this.hashFunction = ModHash.GetFunc(m, p);
    }

    @Override
    public int Hash(long x, int i) {
        int j = (i % 2 == 0) ? 1 : -1;
        int returnVal = (this.hashFunction.Hash(x) + j * (int)Math.pow(i, 2)) % this.maxSize;
        return (returnVal >= 0) ? returnVal : returnVal + this.maxSize;
    }
}
