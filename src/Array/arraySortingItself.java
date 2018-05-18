package Array;

public interface arraySortingItself {
    short search(short key);
    int getDelta(Chances chances);
    int getLength();
    int getCountCmp();
    short[] getData();
    class createArray{
        public static arraySortingItself create(short[] t, int index, int param){
            switch (index){
                case 0: return new ArraySwap(t, param);
                case 1: return new ArrayInsertInBeg(t);
                case 2: return new ArrayComb(t, param);
                case 3: return new ArrayChange(t);
                case 4: return new ArrayCombChange(t);
                default: return new ArraySwap(t);
            }
        }
    }

}
