package Array;

public class ArrayChange implements arraySortingItself{
    public short[] values;
    private int shift = 1000;
    private int countCmp = 0;
    private int num = 0;
    private int step = 1;
    private int term = 100000;
    private int prevDelta = Integer.MAX_VALUE;



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
        /*if ((prevDelta - delta < 0) && (shift != 1)){
            shift /= 2;
        }*/
        prevDelta = delta;
        return delta;
    }


    public void setShift(int shift) {
        this.shift = shift;
    }


    public ArrayChange(short[] initial, int shift) {
        values = initial.clone();
        this.shift = shift;
    }
    public ArrayChange(short[] initial) {
        values = initial.clone();
    }


    public short search(short key){
        num++;
        if (num == term){
            if (shift > 7){
                step++;
                shift /= 7;
                shift *= 4;
                term += step * 100000;
            }
            else if (shift > 1){
                step++;
                shift--;
                term += step * 100000;
            } else
                term = 0;
        }
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
        countCmp = i;
        if (i >= values.length){
            return -1;      // если элемент не найден
        }
        return values[i];   // если найденый элемент - близко к началу
    }

}
