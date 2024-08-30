// ----------------------------------------------------------

/**
     * The internal Node class that represents nodes in the linked chain.
     */
    public class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
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
    }