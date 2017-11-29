package Main;
import Array.Chances;
import Array.Array;

import java.util.Arrays;

public class Main {

    final static int count = 1000000;
    final static short maxNumber = 10000;

    public static void main(String[] args) {
        //testChances();
        //testArray();
        testArray2();

    }

    private static void testArray() {
        Chances chances = new Chances(maxNumber);
        short[] t = new short[maxNumber];
        for (short i = 0; i < maxNumber; i++) {
            t[i] = i;
        }
        Array array = new Array(t);
        for (int i = 0; i < 100; i++) {
            long temp = System.nanoTime();
            for (int j = 0; j < count; j++) {
                array.search(chances.nextNumber());
            }
            System.out.println((System.nanoTime() - temp)/count);
        }

    }

    private static void testArray2() {
        Chances chances = new Chances(maxNumber);
        short[] t = new short[maxNumber];
        for (short i = 0; i < maxNumber; i++) {
            t[i] = i;
        }
        Array array = new Array(t);


        for (int i = 0; i < 100000000; i++) {

            array.search(chances.nextNumber());

        }
        short[][] s = new short[2][maxNumber];
        s[0] = chances.values;
        for (short l = 0; l < maxNumber; l++) {
            s[1][l] = l;
        }
        sort(s);
        int delta = 0;
        for (int i = 0; i < maxNumber; i++) {
            int j, k;
            for (j = 0; array.values[j] != i; j++) {

            }

            for (k = 0; s[1][k] != i; k++) {

            }
            delta += Math.abs(j-k);
        }
        System.out.println(delta);


    }

    private static void sort(short[][] massive) {
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
    }

    private static void testChances(){
        Chances chances = new Chances(maxNumber);
        short[] t = new short[maxNumber];
        for (int i = 0; i < chances.sums[chances.sums.length-1]; i++) {
            t[chances.nextNumber()]++;
        }
        double temp;
        for (int i = 0; i < t.length; i++) {
            temp = chances.values[i] - t[i];
            System.out.printf("%4d %7d %7d %7f%n", i, t[i], chances.values[i], temp);
        }
    }
}
