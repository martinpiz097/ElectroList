/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.electroList.structure;

import java.io.Serializable;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author martin
 */
public class Node<E> implements Cloneable, Serializable {
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
    
//    private void writeObject(java.io.ObjectOutputStream out) throws IOException{
//        out.defaultWriteObject();
//        out.putFields().put("data", data);
//        out.putFields().put("next", next);
//    }
//    
//    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException{
//        in.defaultReadObject();
//        data = (E) in.readFields().get("data", null);
//        next = (Node<E>) in.readFields().get("next", null);
//    }

    boolean hasNext(){
        return next != null;
    }
    
    boolean hasPrevious(){
        return prev != null;
    }
    
    /**
     * Enlaza los nodos siguiente y anterior a éste verificando que estos existan, 
     * es decir:
     * next.prev = this && prev.next = this;
     * 
     */
    void connect(){
        if (hasNext())
            next.prev = this;
        if(hasPrevious())
            prev.next = this;
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
    
    boolean equals(Node<E> another){
        return hashCode() == another.hashCode();
    }
    
    
//    @Override
//    public void writeExternal(ObjectOutput out) throws IOException {
//        out.writeObject(data);
//        out.writeObject(prev);
//        out.writeObject(next);
//    }
//
//    @Override
//    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
//        data = (E) in.readObject();
//        prev = (Node<E>) in.readObject();
//        next = (Node<E>) in.readObject();
//    }
    
}
