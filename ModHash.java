//#username1 - argov
//#id1       - 308447382
//#name1     - Yael Argov
//#username2 - amitrosen
//#id2       - 208279489
//#name2     - Amit Rosen
import java.util.concurrent.ThreadLocalRandom;

public class ModHash {

    private long a;
    private long b;
    private int m;
    private long p;

    public ModHash(long a, long b, int m, long p) {
        this.a = a;
        this.b = b;
        this.m = m;
        this.p = p;
    }

    public static ModHash GetFunc(int m, long p){
        long a = ThreadLocalRandom.current().nextLong(1, p);
        long b = ThreadLocalRandom.current().nextLong(0, p);
        return new ModHash(a, b, m, p);
    }
    public int Hash(long key) {
        int returnVal = (int)((a * key + b) % p) % m;
        return (returnVal >= 0) ? returnVal : returnVal + m;
    }
}
