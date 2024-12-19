package deque;

import net.sf.saxon.functions.Remove;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    private Node sentinel;
    private int size;

    public class Node{
        public T item;
        public Node prev;
        public Node next;
        public Node(T i, Node p, Node n){
            item = i;
            prev = p;
            next = n;
        }
    }
    public LinkedListDeque61B(){
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        Node newNode = new Node(x, sentinel, sentinel.next);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size += 1;
    }

    @Override
    public void addLast(T x) {
        Node newNode = new Node(x, sentinel.prev, sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node p = sentinel.next;
        while(p != sentinel){
            returnList.add(p.item);
            p = p.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
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

        Node p = sentinel.next;
        sentinel.next = p.next;
        p.next.prev = sentinel;

        size--;
        p.next = null; // Help garbage collection
        p.prev = null; // Help garbage collection

        return p.item;
    }

    @Override
    public T removeLast() {
        if(size == 0){
            return null;
        }

        Node p = sentinel.prev;
        sentinel.prev = p.prev;
        p.prev.next = sentinel;

        size--;
        p.next = null; // Help garbage collection
        p.prev = null; // Help garbage collection

        return p.item;
    }

    @Override
    public T get(int index) {
        if(index < 0 || index >= size){
            return null;
        }

        Node p = sentinel.next;
        while(index-- > 0){
            p = p.next;
        }
        return p.item;
    }

    @Override
    public T getRecursive(int index) {
        if(index < 0 || index >= size){
            return null;
        }
        return getRecursiveHelper(sentinel.next, index);
    }

    private T getRecursiveHelper(Node p, int index){
        if(index == 0){
            return p.item;
        }
        return getRecursiveHelper(p.next, index - 1);
    }

    /**Override itrator method. */
    private class LinkedListIterator implements Iterator<T> {
        private Node curr;

        public LinkedListIterator(){

            curr = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return curr != sentinel;
        }

        @Override
        public T next() {
            T returnItem = curr.item;
            curr = curr.next;
            return returnItem;
        }
    }
    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    @Override
    public boolean equals(Object other){
        if(other == this){
            return true;
        }

        if(other instanceof LinkedListDeque61B<?> olld){
           if(olld.size != this.size){
               return false;
           }
            for(int i = 0; i < this.size; i++){
                if(!olld.get(i).equals(this.get(i))){
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

