package deque;

import java.util.Comparator;
public class MaxArrayDeque61B<T> extends ArrayDeque61B<T>{

    private final Comparator<T> comparator;

    public MaxArrayDeque61B(){
        super();  // Call the constructor of ArrayDeque61B

        // default comparator 1
        /*this.comparator = (o1, o2) -> {
            return Comparator.comparing(Object::toString).compare(o1, o2);
        }; */

        // default comparator 2
        this.comparator = (o1, o2) -> o1.toString().compareTo(o2.toString());
    }

    public MaxArrayDeque61B(Comparator<T> c){
        super();  // Call the constructor of ArrayDeque61B
        this.comparator = c;
    }

    public T max(){
        if(isEmpty()){
            return null;
        }
        T maxItem = get(0);
        for(T item : this){
            if(comparator.compare(item, maxItem) > 0){
                maxItem = item;
            }
        }
        return maxItem;
    }

    public T max(Comparator<T> c){
        if(isEmpty()){
            return null;
        }
        T maxItem = get(0);
        for(T item : this){
            if(c.compare(item, maxItem) > 0){
                maxItem = item;
            }
        }
        return maxItem;
    }
}
