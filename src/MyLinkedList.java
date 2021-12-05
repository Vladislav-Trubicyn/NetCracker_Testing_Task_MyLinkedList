import java.util.Iterator;

public interface MyLinkedList<E>
{
    int size();

    boolean isEmpty();

    boolean contains(Object o);

    Iterator<E> myIterator();

    boolean add(E e);

    boolean remove(E e);

    boolean equals(Object o);

    E get(int index);

    E set(int index, E element);

    void add(int index, E element);

    E remove(int index);

    int indexOf(Object o);

    public String toString();

}
