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

    public short values[];  // хранит вероятности
    public short[] ideal;   // идеальные места для цифр
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
        values[0] = (short) random.nextInt(Short.MAX_VALUE - 5);  // <= Integer.MAX_VALUE / Short.MAX_VALUE
        sums[0] = values[0];
        for (int i = 1; i < values.length; i++) {
            values[i] = (short) random.nextInt(Short.MAX_VALUE - 5);  // <= Integer.MAX_VALUE / Short.MAX_VALUE
            sums[i] += sums[i - 1] + values[i];
        }
        createIdeal();
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
        short[][] massive = new short[2][maxNumber];
        massive[0] = values.clone();
        for (short i = 0; i < maxNumber; i++) {
            massive[1][i] = i;
        }
        int j = massive[0].length - 1;
        while (j > 0) {
            int M = massive[0][0];
            int k = 0;
            int i = 1;
            while (i <= j) {
                if (M > massive[0][i]) {
                    M = massive[0][i];
                    k = i;
                }
                i++;
            }
            short tmp = massive[0][j];
            massive[0][j] = massive[0][k];
            massive[0][k] = tmp;
            tmp = massive[1][j];
            massive[1][j] = massive[1][k];
            massive[1][k] = tmp;
            j--;
        }

        for (short i = 0; i < maxNumber; i++) {
            ideal[massive[1][i]] = i;
        }
    }
}
