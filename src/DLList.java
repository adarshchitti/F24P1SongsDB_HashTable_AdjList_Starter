import java.util.Iterator;
/*
 * @param <T>
 */
public class DLList<T> implements Iterable<T> 
{
    private static class Node<T> 
    {
        private T data;
        private Node<T> next;
        private Node<T> prev;

        public Node(T data, Node<T> next,Node<T> prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
        public Node(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }


        public void setData(T value) {
            this.data = value;
        }


        public Node<T> getNext() {
            return this.next;
        }


        public void setNext(Node<T> next) {
            this.next = next;
        }
        public Node<T> getPrev() {
            return this.prev;
        }


        public void setPrev(Node<T> prev) {
            this.prev = prev;
        }
    }
    private Node<T> head;
    private Node<T> tail;
    private int size;
    
    public DLList()
    {
        head = new DLList.Node<T>(null);     
        tail = new DLList.Node<T>(null);
        head.setNext(tail);
        tail.setPrev(head);
        size = 0;
    }
    public boolean isEmpty()
    {
        return size==0;
    }
    public int size()
    {
        return size;
    }
    public void clear()
    {
        head = new DLList.Node<T>(null);     
        tail = new DLList.Node<T>(null);
        head.setNext(tail);
        tail.setPrev(head);
        size = 0;
    }
    public void add(T obj)
    {
        if(obj == null )
        {
            throw new IllegalArgumentException("Cannot add null " + "objects to a list");
        }
        if()
        Node<T> nodeAfter;
    }

    @Override
    public Iterator<T> iterator() {
        // TODO Auto-generated method stub
        return null;
    }
    
    
    
    
    
    

}
