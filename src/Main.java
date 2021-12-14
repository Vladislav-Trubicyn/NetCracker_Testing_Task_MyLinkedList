import java.sql.SQLOutput;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Main
{
    public static void main(String[] args)
    {
        MyLinkedListImpl<String> myString = new MyLinkedListImpl<>();

        System.out.println("Список пуст: " + myString.isEmpty() + " (метод isEmpty())");
        System.out.println("Задаем значения ... (метод add('A'))");
        myString.add("А");
        myString.add("Б");
        myString.add("В");

        System.out.println("Список пуст: " + myString.isEmpty() + " (метод isEmpty())");
        System.out.println("Выводим список ... (метод toString())");
        System.out.println(myString.toString());

        System.out.println("Получим элемент по его идентификатору/номеру (метод get(int index = 1))");
        System.out.println(myString.get(1));

        System.out.println("Зададим новое значение элементу по идентификатору/номеру (метод set(int index = 1, 'D'))");
        myString.set(1,"D");

        System.out.println("Выводим список ...");
        System.out.println(myString);

        System.out.println("Количество элементов списка " + myString.size() + " (метод size())");

        System.out.println("Присутствует ли элемент со значением D в списке: " + myString.contains("D") + " (метод contains(object o = 'D'))");
        System.out.println("Присутствует ли элемент со значением Dы в списке: " + myString.contains("Dы") + " (метод contains(object o = 'Dы'))");

        System.out.println("Удалим D из списка по значению (метод remove(Object o = 'D'))");
        myString.remove("D");

        System.out.println("Выводим список ...");
        System.out.println(myString);

        System.out.println("Удалим Б из списка по идентификатору/номеру (метод remove(int index = 1))");
        myString.remove(1);

        System.out.println("Выводим список ...");
        System.out.println(myString);

        System.out.println("Вставим элемент со значение Б первым по списку (метод add(int index = 1, 'Б'))");
        myString.add(1, "Б");

        System.out.println("Выводим список ...");
        System.out.println(myString);

        System.out.println("Вставим элемент со значение А вторым по списку (метод add(int index = 2, 'А'))");
        myString.add(2, "А");

        System.out.println("Проверка на совпадение элементов (метод equals())");
        System.out.println("Равен ли элемент А единице: " + myString.get(3).equals(1));

        System.out.println("Выводим список ...");
        System.out.println(myString);

    }

    public static class MyLinkedListImpl<E> implements MyLinkedList<E>
    {
        private transient int size;
        private transient Element<E> first;
        private transient Element<E> last;

        public MyLinkedListImpl()
        {
            this.first = null;
            this.last = null;
            this.size = 0;
        }

        @Override
        public int size()
        {
            return size;
        }

        @Override
        public boolean isEmpty()
        {
            return size() == 0;
        }

        @Override
        public boolean contains(Object o)
        {
            return indexOf(o) >= 1;
        }

        @Override
        public boolean add(E e)
        {
            Element<E> prev = last;

            if (isEmpty() == false)
            {
                last = new Element<E>(e, null, prev);
                prev.next = last;
            }
            else
            {
                last = new Element<E>(e, null, prev);
                first = last;
            }

            size++;
            return true;
        }

        @Override
        public boolean remove(E o)
        {
            boolean result = false;

            if (isEmpty() == false && contains(o) == true)
            {
                Element<E> prev = first;
                Element<E> next = first;

                Iterator<E> iter = myIterator();

                while (iter.hasNext())
                {
                    if (next.item.equals(o))
                    {
                        if (size == 1)
                        {
                            first = null;
                            last = null;
                        }
                        else if (next.equals(first))
                        {
                            first = first.next;
                        }
                        else if (next.equals(last))
                        {
                            last = prev;
                            last.next = null;
                        }
                        else
                        {
                            prev.next = next.next;
                        }
                        size--;
                        result = true;
                        break;
                    }

                    prev = next;
                    next = prev.next;

                }

            }

            return result;
        }

        @Override
        public E get(int index)
        {
            Element<E> prev = first;
            Element<E> next = first;
            E element = null;

            int count = 0;
            Iterator<E> iter = myIterator();

            while (iter.hasNext())
            {
                if(index > 0)
                {
                    if(count == index)
                    {
                        element = prev.item;
                        break;
                    }

                    prev = next;
                    next = prev.next;

                    count++;
                }
                else
                {
                    break;
                }

            }
            return element;
        }

        @Override
        public E set(int index, E element)
        {
            Element<E> x = node(index);
            E oldVal = x.item;
            x.item = element;
            return oldVal;
        }

        @Override
        public void add(int index, E element)
        {
            if(index > size)
            {
                index = size;
            }

            if(index == size)
            {
                add(element);
            }
            else
            {
                Element<E> succ = node(index);
                Element<E> prev = succ.prev;
                Element<E> newNode = new Element<E>(element, succ, prev);

                succ.prev = newNode;
                if (prev == null)
                {
                    first = newNode;
                }
                else
                {
                    prev.next = newNode;
                }
                size++;

            }
        }

        @Override
        public E remove(int index)
        {
            remove(get(index));
            return null;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MyLinkedListImpl<E> that = (MyLinkedListImpl<E>) o;
            return size == that.size &&
                    first.equals(that.first) &&
                    last.equals(that.last);
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(size, first, last);
        }

        @Override
        public int indexOf(Object o)
        {
            int count = 1;
            int result = 0;
            Iterator<E> iter = myIterator();

            while (iter.hasNext())
            {
                if(iter.next().equals(o) == true)
                {
                    result = count;
                    break;
                }
                count++;

            }

            return result;
        }

        @Override
        public Iterator<E> myIterator()
        {
            return new LinkedListIterator();
        }

        private class LinkedListIterator implements Iterator<E>
        {
            private Element<E> next = first;

            public E next()
            {
                if (!hasNext())
                {
                    throw new NoSuchElementException();
                }

                E item = next.item;
                next = next.next;
                return item;

            }

            public boolean hasNext()
            {
                return next != null;
            }

            public void remove()
            {
                throw new UnsupportedOperationException();
            }

        }

        @Override
        public String toString()
        {
            StringBuilder sb = new StringBuilder();

            if (!isEmpty())
            {
                Iterator<E> iter = myIterator();

                while (iter.hasNext())
                {
                    sb.append(iter.next());
                }
            }
            else
            {
                sb.append("[]");
            }


            return sb.toString();
        }

        private static class Element<E>
        {
            private E item;
            private MyLinkedListImpl.Element<E> next;
            private MyLinkedListImpl.Element<E> prev;

            Element( E element, MyLinkedListImpl.Element<E> next, MyLinkedListImpl.Element<E> prev)
            {
                this.item = element;
                this.next = next;
                this.prev = prev;
            }
        }

        Element<E> node(int index)
        {
            if (index < (size >> 1))
            {
                Element<E> prev = first;
                for (int i = 1; i <= index; i++)
                {
                    prev = prev.next;
                }

                return prev;
            }
            else
            {
                Element<E> succ = last;
                for (int i = size; i > index; i--)
                {
                    succ = succ.prev;
                }

                return succ;
            }

        }

    }

}
