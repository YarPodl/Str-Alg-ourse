package Array;

public class Array implements arraySortingItself {
    private arraySortingItself array;
    private int prevDelta = Integer.MAX_VALUE;
    private int shift;
    private boolean sost = true;


    public Array(short[] initial, int shift){
        this.shift = shift;
        array = new ArrayInsertInBeg(initial);
    }

    @Override
    public short search(short key) {
        return array.search(key);
    }

    @Override
    public int getLength() {
        return array.getLength();
    }

    @Override
    public short[] getData() {
        return array.getData();
    }

    @Override
    public int getDelta(Chances chances) {
        int delta = array.getDelta(chances);
        if ((sost) && (prevDelta - delta < 0)){
            array = new ArraySwap(array.getData(), shift);
            sost = false;
        }
        prevDelta = delta;
        return delta;
    }
}
