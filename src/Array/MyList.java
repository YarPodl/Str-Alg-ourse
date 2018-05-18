package Array;

class MyList {


    public class element{
        short value;		// значение
        element next;	    // ссылка на следующий элемент
        element previous;	// ссылка на предыдущий элемент


        public element(short value, element next, element previous) {
            this.value = value;
            this.next = next;
            this.previous = previous;
        }
    }

    public element first;	// первый элемент списка
    public element last;	// последний элемент списка


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
            last = newElement;
        }
    }
}
