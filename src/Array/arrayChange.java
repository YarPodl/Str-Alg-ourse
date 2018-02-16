package Array;

public class arrayChange implements arraySortingItself{
    public short[] values;
    private int shift = 1000;
    private int prevDelta = Integer.MAX_VALUE;


    public int getLength(){
        return values.length;
    }

    @Override
    public short[] getData() {
        return values.clone();
    }

    public int getDelta(Chances chances){
        int delta = 0;
        for (int i = 0; i < values.length; i++) {
            delta += Math.abs(i - chances.ideal[values[i]]);
        }
        if ((prevDelta - delta < 0) && (shift != 1)){
            shift /= 2;
        }
        prevDelta = delta;
        return delta;
    }


    public void setShift(int shift) {
        this.shift = shift;
    }


    public arrayChange(short[] initial, int shift) {
        values = initial.clone();
        this.shift = shift;
    }
    public arrayChange(short[] initial) {
        values = initial.clone();
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
