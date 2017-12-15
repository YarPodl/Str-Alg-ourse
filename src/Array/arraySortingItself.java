package Array;

public interface arraySortingItself {
    short search(short key);
    int getDelta(Chances chances);
    int getLength();
    class createArray{
        public static arraySortingItself create(short[] t, int index, int param){
            switch (index){
                case 0: return new ArraySwap(t, param);
                case 1: return new ArrayInsertInBeg(t);
                default: return new ArraySwap(t);
            }
        }
    }

}
