/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.electroList.structure;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 *
 * @author martin
 */
public class Node<E> implements Cloneable, Externalizable {
    Node<E> prev;
    E data;
    Node<E> next;

    public Node(E data) {
        this(null, data, null);
    }

    public Node(Node<E> prev, E data, Node<E> next) {
        this.prev = prev;
        this.data = data;
        this.next = next;
    }

    public boolean hasNext(){
        return next != null;
    }
    
    public boolean hasPrevious(){
        return prev != null;
    }
    
    public void setPrevious(Node<E> prev){
        this.prev = prev;
    }
    
    public void setNext(Node<E> next){
        this.next = next;
    }
    
    public void unlinkPrevious(){
        prev = null;
    }
    
    public void unlinkNext(){
        next = null;
    }

    public boolean equals(Node<E> another){
        return hashCode() == another.hashCode();
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(prev);
        out.writeObject(data);
        out.writeObject(next);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        prev = (Node<E>) in.readObject();
        data = (E) in.readObject();
        next = (Node<E>) in.readObject();
    }
    
}
