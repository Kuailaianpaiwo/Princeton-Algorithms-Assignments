/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Node<Item> first;
    private Node<Item> last;
    private int size;


    private class Node<Item>
    {
        Item value;
        Node<Item> next;
    }

    public RandomizedQueue()
    {
        first=new Node<>();
        last=first;
    }                 // construct an empty randomized queue
    public boolean isEmpty()
    {
        return size==0;
    }                 // is the randomized queue empty?
    public int size()
    {
        return size;
    }                        // return the number of items on the randomized queue
    public void enqueue(Item item)
    {
        if (item==null) throw new IllegalArgumentException();
        if (first==null) {
            first=new Node<>();
            last=first;
        }
        if (size==0) first.value=item;
        else {
        Node<Item> newNode = new Node<>();
        newNode.value=item;
        last.next=newNode;
        last=last.next;
        }
       size++;
    }           // add the item
    public Item dequeue()
    {
        if (isEmpty()) throw new NoSuchElementException();
        int place = StdRandom.uniform(0,size);
        int index = place;
        Item result;
        if (place==0) {
            result= first.value;
            first=first.next;
        }
        else {
            Node<Item> node = first;
            while (place!=1)
            {
                place--;
                node=node.next;
            }
            result = node.next.value;
            if (index==size-1)
            {
                last=node;
                last.next=null;
            }else {
                node.next=node.next.next;
            }

        }
        size--;

        return result;

    }                    // remove and return a random item
    public Item sample()
    {
        if (isEmpty()) throw new NoSuchElementException();
        int place = StdRandom.uniform(0,size);
        Item result;
        if (place==0) {
            result= first.value;
        }
        else {
            Node<Item> node = first;
            while (place!=1)
            {
                place--;
                node=node.next;
            }
            result = node.next.value;
        }

        return result;
    }                     // return a random item (but do not remove it)
    public Iterator<Item> iterator()
    {
        return new ListIteraror<>();
    }         // return an independent iterator over items in random order

    private class ListIteraror<Item> implements Iterator
    {
        private Node<Item> current = (Node<Item>) first;
        private int times;
        private int[] table = new int[size];


        @Override
        public boolean hasNext() {
            return times != size;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int place = StdRandom.uniform(0,size);
            while (table[place]!=0)
            {
                place=(place+1)%size;
            }
            table[place] = 1;
            Node<Item> node = (Node<Item>)first;
            while (place!=0)
            {
             node = node.next;
             place--;
            }
            times++;
            return node.value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    public static void main(String[] args)
    {
        RandomizedQueue<Integer> r = new RandomizedQueue<>();
        r.enqueue(1);
        r.enqueue(2);
        r.enqueue(3);
         r.enqueue(4);
       r.enqueue(5);

        r.dequeue();
        r.dequeue();
        r.dequeue();
        r.dequeue();
        //r.dequeue();
         System.out.println(r.sample());
       // System.out.println(r.sample());
       //  Iterator<Integer> iterator = r.iterator();
       //  while (iterator.hasNext())
       //  {
       //      System.out.println(iterator.next());
       //  }
    }   // unit testing (optional)
}
