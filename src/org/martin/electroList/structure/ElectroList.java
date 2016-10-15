/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.electroList.structure;

import java.io.Serializable;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 *
 * @author martin
 */
public class ElectroList<E> implements List<E>, Deque<E>, Cloneable, Serializable{
    private Node<E> first;
    private Node<E> last;
    private String name;
    private int size;

    public ElectroList() {
        String objCode = "@"+Integer.toHexString(hashCode());
        this.name = "ElectroList"+objCode;
        objCode = null;
    }

    public ElectroList(String name) {
        this.name = name;
    }
    
    private void checkIndex(int index){
        if(index >= size || index < 0)
            throw new IndexOutOfBoundsException(index+"");
    }

    private Node<E> getNode(int index){
        if(index == 0)
            return first;
        else if (index == size-1)
            return last;
        
        Node<E> aux = first;
        for (int i = 0; i < index; i++) {
            aux = aux.next;
        }
        return aux;
    }
    
    private void unlinkNode(Node<E> node, int index){
        if (size == 1)
            first = last = null;
        else if (index == 0) {
            first = node.next;
            first.unlinkPrevious();
        }
        else if (index == size-1) {
            last = node.prev;
            last.unlinkNext();
        }
        else{
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }
    
    private void unlinkNode(Node<E> node){
        /*
        if (size == 1) {
            first = last = null;
        }
        else if (node.equals(first)) {
            first = first.next;
            first.unlinkPrevious();
        }
        else if (node.equals(last)) {
            last = last.prev;
            last.unlinkNext();
        }
        */
        
        if (size == 1)
            first = last = null;
        
        else if (node.prev != null)
            node.prev.next = node.next;
        
        else if(node.next != null)
            node.next.prev = node.prev;
    }

    /**
     * Devuelve el nombre de esta lista, si no se le ha asignado ninguno, 
     * el método devolverá un nombre por defecto.
     * @return Nombre de esta lista.
     */
    public String getName() {
        return name;
    }

    /**
     * Cambia el nombre de la lista al dado como parámetro.
     * @param name Nombre nuevo de la lista a asignar.
     */
    public void setName(String name) {
        this.name = name;
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
        if(isEmpty())
            return false;
        Node<E> aux = first;
        
        for (int i = 0; i < size; i++) {
            if (aux.data.equals(o))
                return true;
            aux = aux.next;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new ElectroIterator<>(this);
    }

    @Override
    public Object[] toArray() {
        E[] array = (E[]) new Object[size];
    
        Node<E> aux = first;
        for (int i = 0; i < size; i++) {
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

    @Override
    public boolean add(E e) {
        Objects.requireNonNull(e);
        if(isEmpty())
            first = last = new Node<>(e);
        
        else{
            Node<E> aux = first;
            while (aux.hasNext())
                aux = aux.next;
                
            aux.next = new Node<>(aux, e, null);
            last = aux.next;
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        Objects.requireNonNull(o);
        if(isEmpty())
            return false;
        else{
            boolean removed = false;
            Node<E> aux = first;
        
            for (int i = 0; i < size; i++) {
                if (aux.data.equals(o)) {
                    unlinkNode(aux, i);
                    return true;
                }
                aux = aux.next;
            }
            
            return removed;
        }
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
        if (isEmpty()) {
            for (E e : c)
                add(e);
        }
        else{
            Node<E> aux = last;
            
            for (E e : c) {
                aux.next = new Node<>(aux, e, null);
                aux = aux.next;
                size++;
            }
            last = aux;
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if(true)
            throw new UnsupportedOperationException("todavia no esta lista");
        if (index == 0)
            return addAll(c);
        else{
            // falta esa funcion para no dejar la embarrada.
            //leaveSpace();
            Node<E> aux = getNode(index);
            
            for (E e : c) {
                aux.next = new Node<>(aux, e, null);
                aux = aux.next;
                size++;
            }
            last = aux;
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if(isEmpty() || c.isEmpty())
            return false;
        Node<E> aux = first;
        for (int i = 0; i < size; i++) {
            for (Object object : c) {
                if (aux.data.equals(object))
                    unlinkNode(aux);
            }
            aux = aux.next;
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if(isEmpty() || c.isEmpty())
            return false;
        Node<E> aux = first;
        for (int i = 0; i < size; i++) {
            for (Object object : c) {
                if (!aux.data.equals(object))
                    unlinkNode(aux);
            }
            aux = aux.next;
        }
        return true;
    }

    @Override
    public void clear() {
        if(isEmpty())
            return;
        first = last = null;
        size = 0;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return getNode(index).data;
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
        if (isEmpty() && index == 0) {
            add(element);
        }
        else{
            checkIndex(index);
            final Node<E> node = getNode(index); // Obtengo nodo en el índice establecido
            // el nuevo nodo tendra la posicion del actual
            final Node<E> newNode = new Node<>(node.prev, element, node); 

            // si el nodo nuevo tiene un nodo previo, este apuntará al nuevo, de lo
            // contrario significa que equivale al primer nodo.
            if(newNode.hasPrevious())
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
        
        if(index == 0)
            return removeFirst();
        else if (index == size-1)
            return removeLast();
        else{
            Node<E> node = getNode(index);
            E element = node.data;
            unlinkNode(node, index);
        
            node = null;
            size--;
            return element;
        }
    }

    @Override
    public int indexOf(Object o) {
        if(isEmpty())
            return -1;
        
        Node<E> aux = first;
        for (int i = 0; i < size; i++) {
            if (aux.data.equals(o))
                return i;
            aux = aux.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        for (int i = 0; i < toIndex; i++) {
            subList.add(aux.data);
            aux = aux.next;
        }
        return subList;
    }

    @Override
    public void addFirst(E e) {
        add(0, e);
    }

    @Override
    public void addLast(E e) {
        add(e);
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
        if(isEmpty())
            throw new NullPointerException();
        E data = first.data;
        
        unlinkNode(first);
        size--;
        return data;
    }

    @Override
    public E removeLast() {
        if(isEmpty())
            throw new NullPointerException();
        E data = last.data;
        unlinkNode(last);
        size--;
        return data;
    }

    @Override
    public E pollFirst() {
        if(isEmpty())
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
        if(isEmpty())
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
        if(isEmpty())
            return null;
        return getFirst();
    }

    @Override
    public E peekLast() {
        if(isEmpty())
            return null;
        return getLast();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        for (int i = 0; i < size; i++) {
            sBuilder.append(get(i));
            if (i < size-1)
                sBuilder.append(", ");
        }
        sBuilder.append(']');
        strList = sBuilder.toString();
        sBuilder = null;
        return strList;
    }
    
    private static class ElectroIterator<E> implements Iterator<E>{
        private ElectroList<E> list;
        private Node<E> current;
        boolean isDescending;

        public ElectroIterator(ElectroList<E> list) {
            this(list, false);
        }

        public ElectroIterator(ElectroList<E> list, boolean isDescending) {
            this.list = list;
            current = isDescending ? list.getNode(list.size-1) : list.getNode(0);
            this.isDescending = isDescending;
        }

        @Override
        public void remove() {
            if (isDescending) {
                current = current.prev;
                list.unlinkNode(current.next);
            }
            else{
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
}
