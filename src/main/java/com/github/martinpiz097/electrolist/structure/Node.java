/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.martinpiz097.electrolist.structure;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author martin
 */
public class Node<E> implements Cloneable, Comparable<E>, Serializable {
    public transient Node<E> prev;
    public E data;
    public transient Node<E> next;

    Node(E data) {
        this(null, data, null);
    }

    Node(Node<E> prev, E data, Node<E> next) {
        this.prev = prev;
        this.data = data;
        this.next = next;
    }
    
    boolean hasNext(){
        return next != null;
    }

    boolean hasPrevious(){
        return prev != null;
    }
    
    int getDequeCount(){
        int counter = 0;
        Node<E> n = next;
        while (n != null) {            
            counter++;
            n = n.next;
        }
        return counter;
    }
    
    /**
     * Enlaza los nodos siguiente y anterior a éste verificando que estos existan, 
     * es decir:
     * next.prev = this && prev.next = this;
     * 
     */
    void connect(){
        connectNext();
        connectPrevious();
    }
    
    void connectNext(){
        if (hasNext())
            next.prev = this;
    }
    
    void connectPrevious(){
        if(hasPrevious())
            prev.next = this;
    }
    
    void setPrevious(Node<E> prev){
        this.prev = prev;
    }
    
    void setNext(Node<E> next){
        this.next = next;
    }
    
    void unlinkPrevious(){
        prev = null;
    }
    
    void unlinkNext(){
        next = null;
    }
    
    private void unlinkAll(){
        unlinkPrevious();
        unlinkNext();
    }
    
    void skip(){
        if(hasPrevious())
            prev.next = next;
        if(hasNext())
            next.prev = prev;
        unlink();
    }
    
    void unlink(){
        unlinkAll();
        data = null;
    }

    void kill() {
        try {
            unlink();
            finalize();
        } catch (Throwable ex) {
            Logger.getLogger(Node.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void print(){
        System.out.print(data);
        if (hasNext()) {
            System.out.print("->");
            next.print();
        }
    }

    boolean equals(Node<E> another){
        return hashCode() == another.hashCode();
    }
    
    @Override
    protected Node<E> clone() throws CloneNotSupportedException{
        return new Node<>(prev, data, next);
    }
    
    @Override
    public int compareTo(E o) {
        final int hashData = data.hashCode();
        final int hashO = o.hashCode();
        if (hashData>=hashO)
            if (hashData>hashO)
                return -1;
            
            else
                return 0;
        
        else return 1;
    }

    @Override
    public String toString() {
        return data.toString();
    }
    
}
