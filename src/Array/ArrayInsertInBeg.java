package Array;


public class ArrayInsertInBeg implements arraySortingItself{
    MyList values;
    private int length;

    public ArrayInsertInBeg(short[] initial) {
        values = new MyList();
        length = initial.length;
        for (short i: initial) {
            values.add(i);
        }

    }


    public int getLength(){
        return length;
    }

    public int getDelta(Chances chances){
        int delta = 0;
        int j = 0;
        MyList.element element = values.first;
        while (element != null) {

            delta += Math.abs(j - chances.ideal[element.value]);
            j++;
            element = element.next;
        }
        /*
        for (short i = 0; i < length; i++) {


            int j;
            j = 0;
            MyList.element element = values.first;
            while (element.value != i) {
                //System.out.println(Integer.toString(i)+" "+Integer.toString(j)+" "+Integer.toString(element.value));
                if (element.next==null) {

                    System.out.println(Integer.toString(i)+" "+Integer.toString(j)+" "+Integer.toString(element.value));
                }
                j++;
                element = element.next;
            }
            delta += Math.abs(j - chances.ideal[i]);
        }
        */

        return delta;
    }

    public short search(short key){
        MyList.element current = values.first;
        try {
            while (current.value != key) {
                current = current.next;
            }
            current.previous.next = current.next;
            current.next.previous = current.previous;
            values.first.previous = current;
            current.previous = null;
            current.next = values.first;
            values.first = current;
            return current.value;
        }
        catch (Exception ignored){}
        if (current == null){
            return Short.MIN_VALUE;
        }
        return current.value;

    }
}
