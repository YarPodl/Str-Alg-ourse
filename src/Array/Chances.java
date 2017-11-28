package Array;

import java.util.Random;

public class Chances {

    /*
    private int values[];
    private long sums[];
    private int maxNumber;
    public Chances(int maxNumber) {
        this.maxNumber = maxNumber;
        Random random = new Random();
        values = new int[maxNumber];
        sums = new long[maxNumber];
        sums[0] = 0;
        for (int i = 0; i < values.length; i++) {
            values[i] = random.nextInt(1000000);  // <= Long.MAX_VALUE / Integer.MAX_VALUE
            sums[i] += values[i];
        }
    }
    */

    public short values[];
    public int sums[];
    public short maxNumber;
    public Chances(short maxNumber) {
        this.maxNumber = maxNumber;
        Random random = new Random();
        values = new short[maxNumber];
        sums = new int[maxNumber];
        values[0] = (short) random.nextInt(30000);  // <= Integer.MAX_VALUE / Short.MAX_VALUE
        sums[0] = values[0];
        for (int i = 1; i < values.length; i++) {
            values[i] = (short) random.nextInt(30000);  // <= Integer.MAX_VALUE / Short.MAX_VALUE
            sums[i] += sums[i - 1] + values[i];
        }
    }

    public int nextNumber(){
        Random random = new Random();
        int v = random.nextInt(sums[sums.length - 1]);
        return search(v);
    }

    private short search(int v){
        int r = sums.length - 1;
        int l = 0;
        int i = 0;
        while (l < r){
            i = (l + r) >> 1;
            if (v <= sums[i]){
                r = i;
            }
            else {
                l = i + 1;
            }
        }
        return (short) r;
    }

}
