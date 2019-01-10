/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first;
    private Node<Item> last;
    private int size;


    private class Node<Item>
    {
        Item value;
        Node<Item> next;
    }

    public Deque()
    {
        first=new Node<>();
        last=first;
    }                           // construct an empty deque
    public boolean isEmpty()
    {
        return size == 0;
    }                 // is the deque empty?
    public int size()
    {
        return size;
    }                        // return the number of items on the deque
    public void addFirst(Item item)
    {
        if (item == null)throw new IllegalArgumentException();
        if (size == 0)
        {
            first.value = item;
            size++;
            return;
        }
        Node<Item> tem = first;
        Node<Item> newFirst = new Node<>();
        newFirst.value = item;
        first = newFirst;
        first.next = tem;
        size++;
    }          // add the item to the front
    public void addLast(Item item)
    {
        if (item == null)throw new IllegalArgumentException();
        if (size == 0)
        {
            last.value = item;
            size++;
            return;
        }

        Node newLast = new Node();
        newLast.value = item;
        last.next = newLast;
        last = last.next;
        size++;
    }           // add the item to the end
    public Item removeFirst()
    {
        if (isEmpty()) throw new  NoSuchElementException();
       Item i = first.value;
        if (size == 1)
        {
            first.value=null;
        }
        else {
            first = first.next;
        }
        size--;
        return i;

    }                // remove and return the item from the front
    public Item removeLast()
    {
        if (isEmpty()) throw new  NoSuchElementException();
      Item i = last.value;
        if (size == 1)last.value = null;
        else {
            Node<Item> index = first;
            while (index.next.next != null)
            {
                index = index.next;
            }
            last = index;
            last.next = null;

        }
        size--;
        return i;

    }                 // remove and return the item from the end
    public Iterator<Item> iterator()
    {
        return new ListItereater();
    }         // return an iterator over items in order from front to end

    private class ListItereater implements Iterator<Item>
    {
        private Node<Item> current = first;

        @Override
        public boolean hasNext() {
            return current!=null&&current.value!=null;
        }

        @Override
        public Item next() {
          if (current == null) throw new NoSuchElementException();
            Item i = current.value;
            if (i == null) throw new NoSuchElementException();
            current = current.next;
            return i;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    public static void main(String[] args)
    {
        Deque<Integer> d = new Deque<>();

        d.addFirst(1);

        d.addFirst(2);
        d.addLast(4);
        d.addLast(5);
        d.addFirst(12);
        d.removeFirst();
        d.removeLast();
        System.out.println(d.size);
        Iterator<Integer> i = d.iterator();
        while (i.hasNext())
        {
            System.out.println(i.next());
        }


    }   // unit testing (optional)

}
