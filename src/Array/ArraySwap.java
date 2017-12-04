package Array;

public class ArraySwap {
    public short[] values;

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
            return values[i];
        }
        catch (Exception e){
            if (i < 0){
                return values[i];   // если найденый элемент - первый
            }
            return -1;  // если элемент не найден
        }
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
