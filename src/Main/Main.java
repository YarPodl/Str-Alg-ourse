package Main;
import Array.Chances;
import Array.Array;

public class Main {

    final static int count = 10000;
    final static short maxNumber = 10000;

    public static void main(String[] args) {
        //testChances();
        testArray();

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

    private static void testChances(){
        Chances chances = new Chances(maxNumber);
        short[] t = new short[maxNumber];
        for (int i = 0; i < count; i++) {
            t[chances.nextNumber()]++;
        }
        double temp;
        for (int i = 0; i < t.length; i++) {
            temp = (double) chances.values[i] / t[i];
            System.out.printf("%4d %7d %7d %7f%n", i, t[i], chances.values[i], temp);
        }
    }
}
