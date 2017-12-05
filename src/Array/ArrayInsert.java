package Array;

import java.util.ArrayList;

public class ArrayInsert {
    MyList values;

    public ArrayInsert(short[] initial) {
        values = new MyList();
        for (short i: initial) {
            values.add(i);
        }

    }

    public short search(short key){
        MyList.element current = values.first;
        try {
            while (current != null) {
                if (current.value == key) {
                    current.previous.next = current.next;
                    current.next.previous = current.previous;
                    values.first.previous = current;
                    current.previous = null;
                    current.next = values.first;
                    return current.value;
                }
                current = current.next;
            }
        }
        catch (Exception ignored){}
        if (current == null){
            return Short.MIN_VALUE;
        }
        return current.value;

    }
}
