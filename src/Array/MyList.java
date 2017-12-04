package Array;

public class MyList {


    public class element{
        short value;
        element next;
        element previous;

        public element(short value, element next, element previous) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }

    public element first;
    public element last;

    public MyList() {
        first = last = null;
    }

    public void add(short value){
        element newElement = new element(value, null, last);
        if (last == null){
            first = last = newElement;
        }
        else {
            last.next = newElement;
        }
    }
}
