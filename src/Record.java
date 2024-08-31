public class Record<T> {
    String key;
    Node<T> n;

    public Record(String key, Node<T> node) {
        this.key = key;
        n = node;
    } 

}
