package Array;

public class ArraySwap implements arraySortingItself{
    public short[] values;


    public void setShift(int shift) {
        this.shift = shift;
    }

    int shift = 1;
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
