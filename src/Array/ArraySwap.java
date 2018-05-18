package Array;

public class ArraySwap implements arraySortingItself{
    public short[] values;  // массив, содержащий элементы
    private int shift = 3;  // сдвиг
    private int countCmp = 0;


    public int getLength(){
        return values.length;
    }
    public int getCountCmp(){
        return countCmp;
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
        return delta;
    }


    public void setShift(int shift) {
        this.shift = shift;
    }


    public ArraySwap(short[] initial, int shift) {
        values = initial.clone();
        this.shift = shift;
    }
    public ArraySwap(short[] initial) {
        values = initial.clone();
    }


    public short search(short key){
        int i = 0;
        try {
            while (key != values[i]){
                ++i;
            }
            countCmp = i;
            short temp = values[i - shift];
            values[i - shift] = values[i];
            values[i] = temp;
        }
        catch (Exception ignored){}
        if (i >= values.length){
            return Short.MIN_VALUE;      // если элемент не найден
        }
        return values[i];   // если найденый элемент - близко к началу
    }
}
