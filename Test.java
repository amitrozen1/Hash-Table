import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Test {
    private static class HashIsI extends OAHashTable {
        public HashIsI(int m) {
            super(m);
        }

        @Override
        public int Hash(long x, int i) {
            return i;
        }
    }

    private static class RealHashTable implements IHashTable {
        private int m;
        private HashMap<Long, HashTableElement> real;

        public RealHashTable(int m, long p) {
            real = new HashMap<Long, HashTableElement>();
            this.m = m;
        }

        public void Insert(HashTableElement hte) throws TableIsFullException, KeyAlreadyExistsException {
            if (real.get(hte.GetKey()) != null)
                throw new KeyAlreadyExistsException(hte);

            real.put(hte.GetKey(), hte);
            if (real.size() > m) throw new TableIsFullException(hte);
        }

        public void Delete(long key) throws KeyDoesntExistException {
            if (real.get(key) == null)
                throw new KeyDoesntExistException(key);

            real.remove(key);
        }

        public HashTableElement Find(long key) {
            return real.get(key);
        }
    }

    private static void testFillup() {
        // tests the insert function besides the hash function by using a hash function which
        // goes through the array linearly i.e. like a linked list
        System.out.println("test: fillup");
        IHashTable ht = new HashIsI(3);
        try {
            ht.Insert(new HashTableElement(2, 1));
            ht.Insert(new HashTableElement(6, 2));
            ht.Insert(new HashTableElement(3, 3));
        } catch (Exception e) {
            System.out.println("FAILED: " + e + " during 3 first insertions");
            return;
        }

        // tests the part in insert which throws an error when there's no more room. if no error is
        // thrown or a different error is thrown the test fails
        try {
            ht.Insert(new HashTableElement(4, 3));
            System.out.println("FAILED: No exception during in third insertion");
            return;
        } catch (IHashTable.TableIsFullException e) {
            return;
        } catch (Exception e) {
            System.out.println("FAILED: " + e + " during last insertion");
            return;
        }
    }

    // an array of every type of hashtables
    private static List<IHashTable> allHashTables(int m, long p) {
        ArrayList<IHashTable> result = new ArrayList<IHashTable>();
        result.add(new HashIsI(m)); // 0
        result.add(new LPHashTable(m, p)); // 1
        result.add(new QPHashTable(m, p));  // 2
        result.add(new AQPHashTable(m, p)); // 3
        result.add(new DoubleHashTable(m, p)); // 4
        return result;
    }

    private static boolean testRandomStress(IHashTable ht) {

        // toggle for printing descriptions of each stage in the test
        boolean VERBOSE = true;

        Random rng = new Random();
        RealHashTable rht = new RealHashTable(15, 97);

        // performs random operations on real hash table and the given hash table(ht)
        for (int i = 0; i < 2000; i++) {
            // choose random operation - insert delete or find
            int randomOperation = rng.nextInt(3);
            long key = rng.nextInt(20);

            // if opersation is insert
            if (randomOperation == 0) {
                if (VERBOSE) System.out.println("insert " + key);
                // insert
                HashTableElement hte = new HashTableElement(key, 1 + rng.nextInt(10000));
                String e1 = "", e2 = "";
                boolean full = false;
                try {
                    ht.Insert(hte);
                } catch (IHashTable.TableIsFullException e) {
                    full = true;
                } catch (Exception e) {
                    e1 = e.getClass().getName();
                }
                if (!full) {
                    // don't insert if full so as to have a consistent state
                    try {
                        rht.Insert(hte);
                    } catch (Exception e) {
                        e2 = e.getClass().getName();
                    }
                }
                if (e1 != e2) { System.out.println("FAILED: insert, e1 != e2: " + e1 + ", " + e2); return false; }
            } else if (randomOperation == 1) {
                if (VERBOSE) System.out.println("         delete " + key);
                // delete
                String e1 = "", e2 = "";
                try {
                    ht.Delete(key);
                } catch (Exception e) {
                    e1 = e.getClass().getName();
                }
                try {
                    rht.Delete(key);
                } catch (Exception e) {
                    e2 = e.getClass().getName();
                }
                if (e1 != e2) { System.out.println("FAILED: delete, e1 != e2: " + e1 + ", " + e2); return false; }
            } else if (randomOperation == 2) {
                if (VERBOSE) System.out.println("                  find " + key);
                // find
                HashTableElement v1 = null, v2 = null;
                String e1 = "", e2 = "";
                try {
                    v1 = ht.Find(key);
                } catch (Exception e) {
                    e1 = e.getClass().getName();
                }
                try {
                    v2 = rht.Find(key);
                } catch (Exception e) {
                    e2 = e.getClass().getName();
                }
                if (e1 != e2) { System.out.println("FAILED: find, e1 != e2: " + e1 + ", " + e2); return false; }
                if (v2 != null && (v1.GetKey() != v2.GetKey() || v1.GetValue() != v2.GetValue())) { System.out.println("FAILED: find, v1 != v2"); return false; }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        testFillup();
        System.out.println("test: random stress");
        for (int i = 0; i < 100; i++) {
            List<IHashTable> hashTables = allHashTables(15, 97);
            for (IHashTable ht : hashTables) {
                if (!testRandomStress(ht)) {
                    System.out.println("in test case " + ht.getClass().getName());
                    break;
                }
            }
        }
    }
}
