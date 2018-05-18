package Array;

public class ArrayCombChange implements arraySortingItself {
    private arraySortingItself array;
    private int prevDelta = Integer.MAX_VALUE;
    private int shift = 500;
    private boolean sost = true;


    public ArrayCombChange(short[] initial, int shift){
        this.shift = shift;
        array = new ArrayInsertInBeg(initial);
    }

    public ArrayCombChange(short[] initial){
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

    public int getCountCmp(){
        return array.getCountCmp();
    }

    @Override
    public short[] getData() {
        return array.getData();
    }

    @Override
    public int getDelta(Chances chances) {
        int delta = array.getDelta(chances);
        if ((sost) && (prevDelta - delta < 0)){
            array = new ArrayChange(array.getData(), shift);
            sost = false;
        }
        prevDelta = delta;
        return delta;
    }
}
