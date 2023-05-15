//#username1 - argov
//#id1       - 308447382
//#name1     - Yael Argov
//#username2 - amitrosen
//#id2       - 208279489
//#name2     - Amit Rosen
public abstract class OAHashTable implements IHashTable {

    private HashTableElement [] table;
    protected int maxSize;

    public OAHashTable(int m) {
        this.table = new HashTableElement[m];
        this.maxSize = m;
    }

    @Override
    public HashTableElement Find(long key) {
        for (int i = 0; i < this.maxSize; i++ ) {
            int j = this.Hash(key, i);
            if (table[j] == null) {
                return null;
            }
            if (table[j].GetKey() == key) {
                return table[j];
            }
        }
        return null;
    }

    @Override
    public void Insert(HashTableElement hte) throws TableIsFullException,KeyAlreadyExistsException {
        long key = hte.GetKey();
        if (this.Find(key) != null) {
            throw new KeyAlreadyExistsException(hte);
        }
        for (int i = 0; i < this.maxSize; i++) {
            int j = this.Hash(key, i);
            if (table[j] == null || table[j].GetKey() == -1) {
                table[j] = hte;
                return;
            }
        }
        throw new TableIsFullException(hte);
    }

    @Override
    public void Delete(long key) throws KeyDoesntExistException {
        for (int i = 0; i < this.maxSize; i++) {
            int j = this.Hash(key, i);
            if (table[j] == null) {
                throw new KeyDoesntExistException(key);
            }
            if (table[j].GetKey() == key) {
                table[j] = new HashTableElement(-1, -1);
                return;
            }
        }
        throw new KeyDoesntExistException(key);
    }

    /**
     *
     * @param x - the key to hash
     * @param i - the index in the probing sequence
     * @return the index into the hash table to place the key x
     */
    public abstract int Hash(long x, int i);
}
