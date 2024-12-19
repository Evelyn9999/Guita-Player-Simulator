package deque;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<T>{
    private T[] items;
    private int len = 8;
    private int size;
    private int front;
    private int back;

    public ArrayDeque61B(){
        items = (T[]) new Object[len];
        size = 0;
        front = 4; // Points to the first element
        back = 5; // Points to the next available position for adding at the back
    }

    private void resizing(int newLen){
        T[] newItems = (T[]) new Object[newLen];
        for(int i = 0; i < size; i++){
            newItems[i] = get(i);
        }
        items = newItems;
        len = newLen;
        front = newLen - 1;  // Updates the front pointer.
        back = size; // Updates the back pointer.
    }

    @Override
    public void addFirst(T x) {
        if(size == len){
            int newLen = len * 2;
            resizing(newLen);
        }
        items[front] = x;
        front = Math.floorMod(front - 1, len);
        size++;
    }

    @Override
    public void addLast(T x) {
        if(size == len){
            int newLen = len * 2;
            resizing(newLen);
        }
        items[back] = x;
        back = Math.floorMod(back + 1, len);
        size++;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();

        if(isEmpty()){
            return returnList;
        }

        int curr = Math.floorMod(front + 1, len);
        for(int i = 0; i < size; i++){
            returnList.add(items[curr]);
            curr = Math.floorMod(curr + 1, len);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if(size == 0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if(size == 0){
            return null;
        }
        front = Math.floorMod(front + 1, len); // Combine 2 steps. Step1: get removeItemIndex; Step2: update it to front directly
        T removeItem = items[front];
        items[front] = null;
        size--;
        if(size >= 16 && size <= len / 4){
            int newLen = len / 2;
            resizing(newLen);
        }
        return removeItem;
    }

    @Override
    public T removeLast() {
        if(size == 0){
            return null;
        }
        back = Math.floorMod(back - 1, len);
        T removeItem = items[back];
        items[back] = null;
        size--;
        if(size >= 16 && size <= len / 4){
            int newLen = len / 2;
            resizing(newLen);
        }
        return removeItem;
    }

    @Override
    public T get(int index) {
        if(index < 0 || index >= size){
            return null;
        }
        int acIndex = Math.floorMod(front + 1 + index, len);
        return items[acIndex];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }


    /**Override Iterator method. */
    private class ArrayListIterator implements Iterator<T> {
        private int wizPoz;

        public ArrayListIterator(){

            wizPoz = 0;
        }

        @Override
        public boolean hasNext() {
            return wizPoz < size;
        }

        @Override
        public T next() {
            int actulIndex = Math.floorMod((front + 1 + wizPoz), len);
            T returnItem = (T) items[actulIndex];
            wizPoz++;
            return returnItem;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

    @Override
    public boolean equals(Object other){
        if(other == this){
            return true;
        }

        if(other instanceof ArrayDeque61B<?> oad){
            if(oad.size != this.size){
                return false;
            }

            for(int i = 0; i < this.size; i++){
                if(!oad.get(i).equals(this.get(i))){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

//    @Override
//    public String toString(){
//        return toList().toString();
//    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("[");
        for(T item : this){
            sb.append(item);
            sb.append(",").append(" ");
        }
        if(size > 0){
            sb.setLength(sb.length() - 2); // Remove trailing ", "
        }
        sb.append("]");
        return sb.toString();
    }
}

