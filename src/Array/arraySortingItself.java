package Array;

public interface arraySortingItself {
    short search(short key);
    int getDelta(Chances chances);
    int getLength();
    class createArray{
        public static arraySortingItself create(short[] t, int index){
            switch (index){
                case 0: return new ArraySwap(t);
                case 1: return new ArrayInsert(t);
                default: return new ArraySwap(t);
            }
        }
    }

}
