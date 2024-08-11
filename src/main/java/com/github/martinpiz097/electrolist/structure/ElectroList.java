/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.martinpiz097.electrolist.structure;

import com.github.martinpiz097.electrolist.search.TSearcherManager;

//import java.io.Serial;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author martin
 */
public class ElectroList<E> extends AbstractSequentialList<E>
        implements Deque<E>, Cloneable, Serializable, Streamable<E> {
    private transient Node<E> first;
    private transient Node<E> last;
    private final String name;
    private transient int size;

    private static final long serialVersionUID = 80031400030041L;

    public static <E> void sort(List<E> list, Comparator<E> c) {
        list.sort(c);
    }

    public ElectroList() {
        this.name = getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }

    public ElectroList(Collection<? extends E> col) {
        this.name = null;
        addAll(col);
    }

    public ElectroList(E[] array) {
        this.name = null;
        addAll(array);
    }

    public ElectroList(String name) {
        this.name = name;
    }

    @Serial
    private void writeObject(java.io.ObjectOutputStream out)
            throws java.io.IOException {
        // Este método escribe todos los atributos que no son transient por defecto.
        // Por esta razon todo lo demas es transient para hacer escrituras manuales.
        out.defaultWriteObject();

        out.writeInt(size);

        for (Node<E> x = first; x != null; x = x.next)
            out.writeObject(x.data);
    }

    @Serial
    private void readObject(java.io.ObjectInputStream in)
            throws java.io.IOException, ClassNotFoundException {
        in.defaultReadObject();

        int size = in.readInt();

        for (int i = 0; i < size; i++)
            linkLast((E) in.readObject());
    }

    private void checkIndex(int index) {
        if (index >= size || index < 0)
            throw new IndexOutOfBoundsException(index + "");
    }

    private Node<E> getNode(int index) {
        Node<E> aux;
        if (index < (size >> 1)) {
            aux = first;
            for (int i = 0; i < index; i++)
                aux = aux.next;
        } else {
            aux = last;
            for (int i = size - 1; i > index; i--)
                aux = aux.prev;
        }
        return aux;
    }

    private void unlinkNode(Node<E> node) {
        if (size == 1) {
            first.data = null;
            first = last = null;
        } else {
            node.skip();
            node = null;
        }

    }

    private void linkLast(E e) {
        final Node<E> newNode = new Node<>(last, e, null);
        last = newNode;

        if (!newNode.hasPrevious())
            first = newNode;
        else
            newNode.prev.next = newNode;

        size++;
    }

    private void linkFirst(E e) {
        final Node<E> f = first;
        final Node<E> newNode = new Node<>(null, e, f);
        first = newNode;
        if (f == null)
            last = newNode;
        else
            f.prev = newNode;
        size++;
    }

    private void linkBefore(E e, Node<E> node) {
        final Node<E> newNode = new Node<>(node.prev, e, node);
        newNode.connect();
    }

    private void linkAfter(E e, Node<E> node) {
        final Node<E> newNode = new Node<>(node, e, node.next);
        newNode.connect();
    }

    /**
     * Devuelve el nombre de esta lista, si no se le ha asignado ninguno,
     * el método devolverá un nombre por defecto.
     *
     * @return Nombre de esta lista.
     */
    public String getName() {
        return name;
    }

    public void collectTo(List<E> list) {
        Node<E> f = first;
        while (f != null) {
            list.add(f.data);
            f = f.next;
        }
    }

    public List<E> collect() {
        List<E> list = new ElectroList<>();
        collectTo(list);
        return list;
    }

    public void drainTo(List<E> list) {
        collectTo(list);
        clear();
    }

    public List<E> drain() {
        List<E> listCollect = collect();
        clear();
        return listCollect;
    }


    @Override
    public boolean anyMatch(Predicate<? super E> predicate) {
        Node<E> aux;

        for (aux = first; aux != null; aux = aux.next)
            if (predicate.test(aux.data))
                return true;

        return false;
    }

    @Override
    public boolean allMatch(Predicate<? super E> predicate) {
        Node<E> aux;

        for (aux = first; aux != null; aux = aux.next)
            if (!predicate.test(aux.data))
                return false;

        return true;
    }

    @Override
    public Stream<E> filter(Predicate<? super E> predicate) {
        List<E> filterList = new ElectroList<>();
        Node<E> aux;

        for (aux = first; aux != null; aux = aux.next)
            if (predicate.test(aux.data))
                filterList.add(aux.data);

        return filterList.stream();
    }

    @Override
    public E findFirst(Predicate<? super E> predicate) {
        for (Node<E> f = first; f != null; f = f.next)
            if (predicate.test(f.data))
                return f.data;
        return null;
    }

    @Override
    public E findLast(Predicate<? super E> predicate) {
        for (Node<E> l = last; l != null; l = l.prev)
            if (predicate.test(l.data))
                return l.data;
        return null;
    }

    @Override
    public E findAny(Predicate<? super E> predicate) {
        return new TSearcherManager<>(this, predicate).getFirstOcurrence();
    }

    @Override
    public Stream<E> parallelFilter(Predicate<? super E> predicate) {
        final TSearcherManager<E> searchsManager = new TSearcherManager<>(this, predicate);

        return searchsManager.getStreamResults();
    }

    @Override
    public List<E> parallelSearch(Predicate<? super E> predicate) {
        final TSearcherManager<E> searchsManager = new TSearcherManager<>(this, predicate);
        return searchsManager.getResults();
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        Node<E> aux;
        for (aux = first; aux != null; aux = aux.next)
            action.accept(aux.data);
    }

    @Override
    public E removeFirst(Predicate<? super E> predicate) {
        E data;
        for (Node<E> f = first; f != null; f = f.next)
            if (predicate.test(f.data)) {
                data = f.data;
                unlinkNode(f);
                return data;
            }
        return null;
    }

    @Override
    public E removeLast(Predicate<? super E> predicate) {
        E data;
        for (Node<E> l = last; l != null; l = l.prev)
            if (predicate.test(l.data)) {
                data = l.data;
                unlinkNode(l);
                return data;
            }
        return null;
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        Node<E> aux = first;
        Node<E> next;
        boolean removed = false;

        int listSize = size;
        for (int i = 0; i < listSize; i++) {
            next = aux.next;
            if (filter.test(aux.data)) {
                unlinkNode(aux);
                removed = true;
                size--;
            }
            aux = next;
        }
        return removed;
    }

    @Override
    public boolean retainIf(Predicate<? super E> filter) {
        Node<E> aux = first;
        Node<E> next;
        boolean retained = false;

        int listSize = size;
        for (int i = 0; i < listSize; i++) {
            next = aux.next;
            if (!filter.test(aux.data)) {
                unlinkNode(aux);
                retained = true;
                size--;
            }
            aux = next;
        }
        return retained;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public Iterator<E> iterator() {
        return new ElectroIterator<>(this);
    }

    @Override
    public E[] toArray() {
        E[] array = (E[]) new Object[size];
        Node<E> aux = first;
        int listSize = size;

        for (int i = 0; i < listSize; i++) {
            array[i] = aux.data;
            aux = aux.next;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        a = (T[]) toArray();
        return a;
    }

    public void copyTo(E[] array) {
        if (array.length != size)
            array = (E[]) new Object[size];

        int i = 0;
        for (Node<E> f = first; f != null; f = f.next, i++)
            array[i] = f.data;
    }

    @Override
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Node<E> aux;

        for (aux = first; aux != null; aux = aux.next)
            if (aux.data.equals(o)) {
                unlinkNode(aux);
                size--;
                return true;
            }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean containsAll = false;

        for (Object object : c)
            if (!(containsAll = contains(object)))
                return containsAll;
        return containsAll;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        // Corregir problemas con algunas coleciones cuando se crea el toArray
        // al parecer falla cuando se crean copias de una colecion tipo ElectroList
        // ya que el array queda vacio.
        return addAll(0, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        checkIndex(index);
        Node<E> aux = getNode(index);
        final Node<E> next = aux.next;

        for (E e : c) {
            aux.next = new Node<>(aux, e, null);
            aux = aux.next;
        }
        aux.next = next;
        if (aux.hasNext())
            aux.next.connect();

        return true;
    }

    public void addAll(E[] array) {
        addAll(array, 0);
    }

    public void addAll(E[] array, int index) {
        if (index >= array.length || index < 0)
            throw new IndexOutOfBoundsException(index + "");

        final int arrayLen = array.length;
        final int elementCount = arrayLen - index;
        add(array[index]);
        Node<E> l = last;
        Node<E> newNode;

        for (int i = index + 1; i < arrayLen; i++) {
            newNode = new Node<>(l, array[i], null);
            newNode.connectPrevious();
            l = newNode;
        }
        size += elementCount;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (isEmpty() || c.isEmpty())
            return false;
        Node<E> aux = first;
        for (int i = 0; i < size; i++) {
            for (Object object : c) {
                if (aux.data.equals(object)) {
                    unlinkNode(aux);
                    size--;
                }
            }
            aux = aux.next;
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (isEmpty() || c.isEmpty())
            return false;
        Node<E> aux = first;
        for (int i = 0; i < size; i++) {
            for (Object object : c) {
                if (!aux.data.equals(object)) {
                    unlinkNode(aux);
                    size--;
                }
            }
            aux = aux.next;
        }
        return true;
    }

    @Override
    public void clear() {
        first = last = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return getNode(index).data;
    }

    public List<E> get(int start, int end) {
        checkIndex(start);
        checkIndex(end);
        List<E> listGet = new ElectroList<>();

        Node<E> aux = getNode(start);
        for (int i = start; i < end; i++) {
            listGet.add(aux.data);
            aux = aux.next;
        }
        return listGet;
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);
        final Node<E> node = getNode(index);
        E data = node.data;
        node.data = element;

        return data;
    }

    @Override
    public void add(int index, E element) {
        if (index == 0)
            linkFirst(element);
        else {
            checkIndex(index);
            final Node<E> node = getNode(index); // Obtengo nodo en el índice establecido
            // el nuevo nodo tendra la posicion del actual
            final Node<E> newNode = new Node<>(node.prev, element, node);

            // si el nodo nuevo tiene un nodo previo, este apuntará al nuevo, de lo
            // contrario significa que equivale al primer nodo.
            if (newNode.hasPrevious())
                newNode.prev.next = newNode;
            else
                first = newNode;

            // el nodo anterior al obtenido corresponde al nodo nuevo.
            node.prev = newNode;
        }
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        Node<E> node = getNode(index);
        E element = node.data;
        unlinkNode(node);

        size--;
        return element;
    }

    @Override
    public int indexOf(Object o) {
        Node<E> aux;
        int index = 0;
        for (aux = first; aux != null; aux = aux.next) {
            if (aux.data.equals(o))
                return index;
            index++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Node<E> aux;
        int index = size - 1;
        for (aux = last; aux != null; aux = aux.prev) {
            if (aux.data.equals(o))
                return index;
            index--;
        }

        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        checkIndex(fromIndex);
        checkIndex(toIndex);
        List<E> subList = new ElectroList<>();

        Node<E> aux = getNode(fromIndex);
        for (int i = fromIndex; i < toIndex; i++) {
            subList.add(aux.data);
            aux = aux.next;
        }
        return subList;
    }

    @Override
    public void addFirst(E e) {
        linkFirst(e);
    }

    @Override
    public void addLast(E e) {
        linkLast(e);
    }

    @Override
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }

    @Override
    public E removeFirst() {
        if (isEmpty())
            throw new NullPointerException();
        E data = first.data;

        unlinkNode(first);
        size--;
        return data;
    }

    @Override
    public E removeLast() {
        if (isEmpty())
            throw new NullPointerException();
        E data = last.data;
        unlinkNode(last);
        size--;
        return data;
    }

    @Override
    public E pollFirst() {
        if (isEmpty())
            return null;
        return removeFirst();
    }

    @Override
    public E pollLast() {
        if (isEmpty())
            return null;
        return removeLast();
    }

    @Override
    public E getFirst() {
        if (isEmpty())
            throw new NullPointerException();
        return first.data;
    }

    @Override
    public E getLast() {
        if (isEmpty())
            throw new NullPointerException();
        return last.data;
    }

    @Override
    public E peekFirst() {
        if (isEmpty())
            return null;
        return first.data;
    }

    @Override
    public E peekLast() {
        if (isEmpty())
            return null;
        return last.data;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return remove(o);
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        Node<E> aux;

        for (aux = last; aux != null; aux = aux.prev) {
            if (aux.data.equals(o)) {
                unlinkNode(aux);
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean offer(E e) {
        add(e);
        return true;
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    public void cut(int index) {
        if (index == size) {
            clear();
            return;
        }
        Node<E> begin = getNode(index);
        begin.setPrevious(null);
        first = begin;
        if (begin == last) {
            last = begin;
            begin.setNext(null);
        }
        size -= index;
    }

    // solo start es inclusivo
    public void removeAt(int start, int end) {
        if (size == 0)
            return;
        int siz = end - start;
        if (start > end || siz <= 0 || (start < 0 || end < 0)
                || end > size)
            throw new IndexOutOfBoundsException();

        Node<E> begin = getNode(start);
        Node<E> en = getNode(end);

        if (size == 1) {
            first = last = null;
            size = 0;
        } else if (begin == first || en == last) {
            if (begin == first && en == last) {
                en.prev = null;
                first = last = en;
                size = 1;
            } else if (begin == first) {
                first = en;
                en.setPrevious(null);
            } else {
                last = en;
                last.setNext(null);
                last.setPrevious(begin.prev);
                begin.prev.setNext(last);
            }
        } else {
            begin.prev.setNext(en);
            en.setPrevious(begin.prev);
        }

        size -= siz;
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    @Override
    public void push(E e) {
        add(0, e);
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new ElectroIterator<>(this, true);
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();
        String strList = null;

        sBuilder.append(name);
        sBuilder.append(' ');
        sBuilder.append('[');
        Node<E> aux;
        for (aux = first; aux != null; aux = aux.next) {
            sBuilder.append(aux.data);
            if (aux.next != null)
                sBuilder.append(", ");
        }
        sBuilder.append(']');
        strList = sBuilder.toString();
        sBuilder = null;
        return strList;
    }

    @Override
    public boolean equals(Object another) {
        return this == another;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.first);
        hash = 97 * hash + Objects.hashCode(this.last);
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + this.size;
        return hash;
    }

    public void println() {
        forEach(System.out::println);
    }

    public boolean equals(List<E> anotherList) {
        return this == anotherList;
    }

    private static class ElectroIterator<E> implements Iterator<E> {
        private final ElectroList<E> list;
        private Node<E> current;
        boolean isDescending;

        private static final long serialVersionUID = 122000300400L;

        public ElectroIterator(ElectroList<E> list) {
            this(list, false);
        }

        public ElectroIterator(ElectroList<E> list, boolean isDescending) {
            this.list = list;
            current = isDescending ? list.getNode(list.size - 1) : list.getNode(0);
            this.isDescending = isDescending;
        }

        @Override
        public void remove() {
            if (isDescending) {
                current = current.prev;
                list.unlinkNode(current.next);
            } else {
                current = current.next;
                list.unlinkNode(current.prev);
            }
            list.size--;
        }


        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            E data = current.data;
            current = isDescending ? current.prev : current.next;
            return data;
        }

    }

    private static class ElectroListIterator<E> implements ListIterator<E> {
        private final ElectroList<E> list;
        private int index;
        private static final long serialVersionUID = 122000300500L;

        public ElectroListIterator(ElectroList<E> list) {
            this.list = list;
            index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < list.size;
        }

        @Override
        public E next() {
            return list.get(index++);
        }

        @Override
        public boolean hasPrevious() {
            return index > 0;
        }

        @Override
        public E previous() {
            return list.get(--index);
        }

        @Override
        public int nextIndex() {
            return index;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            list.remove(index);
        }

        @Override
        public void set(E e) {
            list.set(index, e);
        }

        @Override
        public void add(E e) {
            list.add(e);
        }

    }

}
