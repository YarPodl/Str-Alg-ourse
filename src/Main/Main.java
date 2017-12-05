package Main;
import Array.Chances;
import Array.ArraySwap;

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
        ArraySwap arraySwap = new ArraySwap(t);
        for (int i = 0; i < 100; i++) {
            long temp = System.nanoTime();
            for (int j = 0; j < count; j++) {
                arraySwap.search(chances.nextNumber());
            }
            System.out.println((System.nanoTime() - temp)/count);
        }

    }

    private static void testArray2() {
        /*
            Для шага 3 порог после 100000000
         */
        Chances chances = new Chances(maxNumber);
        short[] t = new short[maxNumber];
        for (short i = 0; i < maxNumber; i++) {
            t[i] = i;
        }
        ArraySwap arraySwap = new ArraySwap(t);

        arraySwap.setShift(3);
        for (int j1 = 0; j1 < 200; j1++) {
            for (int i = 0; i < 1000000; i++) {

                arraySwap.search(chances.nextNumber());

            }

            System.out.println(chances.getDelta(arraySwap.values));
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
