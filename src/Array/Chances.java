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

    public short values[];  // хранит вероятнсти
    public short[] ideal;          // идеальные места для цифр
    public int sums[];      // интервалы для вероятности
    public short maxNumber; // максимально допустимое число
    public Chances(short maxNumber) {
        this.maxNumber = maxNumber;
        ideal = new short[maxNumber];
        for (short i = 0; i < maxNumber; i++) {
            ideal[i] = i;
        }



        Random random = new Random();
        values = new short[maxNumber];
        sums = new int[maxNumber];
        values[0] = (short) random.nextInt(32000);  // <= Integer.MAX_VALUE / Short.MAX_VALUE
        sums[0] = values[0];
        for (int i = 1; i < values.length; i++) {
            values[i] = (short) random.nextInt(32000);  // <= Integer.MAX_VALUE / Short.MAX_VALUE
            sums[i] += sums[i - 1] + values[i];
        }
        createIdeal();
    }

    public int getDelta(short[] massive){
        int delta = 0;
        for (int i = 0; i < maxNumber; i++) {
            int j;
            j = 0;
            while (massive[j] != i) {
                j++;
            }
            delta += Math.abs(j - ideal[i]);
        }
        return delta;
    }

    public short nextNumber(){
        Random random = new Random();
        int v = random.nextInt(sums[sums.length - 1]);
        return search(v);
    }

    private short search(int v){
        int r = sums.length - 1;
        int l = 0;
        int i;
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

    private void createIdeal() {
        short[][] massive = new short[][]
                values.clone();
        int j = massive.length - 1;
        while (j > 0) {
            int M = massive[0];
            int k = 0;
            int i = 1;
            while (i <= j) {
                if (M > massive[i]) {
                    M = massive[i];
                    k = i;
                }
                i++;
            }
            short tmp = massive[j];
            massive[j] = massive[k];
            massive[k] = tmp;
            ideal[tmp] = (short) j;
            j--;
        }


    }
}
