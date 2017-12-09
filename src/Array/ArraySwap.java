package Array;

public class ArraySwap implements arraySortingItself{
    public short[] values;
    private int shift = 1;


    public int getLength(){
        return values.length;
    }

    public int getDelta(Chances chances){
        int delta = 0;
        for (int i = 0; i < values.length; i++) {
            delta += Math.abs(i - chances.ideal[values[i]]);
        }
        /*
        for (int i = 0; i < chances.maxNumber; i++) {
            int j;
            j = 0;
            while (values[j] != i) {
                j++;
            }
            delta += Math.abs(j - chances.ideal[i]);
        }
        */
        return delta;
    }


    public void setShift(int shift) {
        this.shift = shift;
    }


    public ArraySwap(short[] initial, int shift) {
        values = initial;
        this.shift = shift;
    }
    public ArraySwap(short[] initial) {
        values = initial;
    }


    public short search(short key){
        int i = 0;
        try {
            while (key != values[i]){
                ++i;
            }
            short temp = values[i - shift];
            values[i - shift] = values[i];
            values[i] = temp;
        }
        catch (Exception ignored){}
        if (i >= values.length){
            return -1;      // если элемент не найден
        }
        return values[i];   // если найденый элемент - близко к началу
    }
    public long testSearch(short key){
        long time = System.nanoTime();
        int i = 0;
        try {
            while (key != values[i]){
                ++i;
            }
            short temp = values[i - 1];
            values[i - 1] = values[i];
            values[i] = temp;
            return values[i];
        }
        catch (Exception e){
        }
        finally {
            return System.nanoTime() - time;
        }
    }
}
