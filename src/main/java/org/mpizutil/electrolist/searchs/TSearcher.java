/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpizutil.electrolist.searchs;

import org.mpizutil.electrolist.structure.ElectroList;
import org.mpizutil.electrolist.structure.Node;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author martin
 */
public class TSearcher<T> extends Thread{
    private ElectroList<T> listToSearch;
    private ElectroList<T> listResults;
    private Predicate<? super T> condition;
    private boolean isDescending;
    private Method getNodeMethod;
    
    public TSearcher(ElectroList<T> listToSearch, ElectroList<T> listResults, 
            Predicate<? super T> condition) {
        this(listToSearch, listResults, condition, false);
    }
    
    public TSearcher(ElectroList<T> listToSearch, ElectroList<T> listResults, 
            Predicate<? super T> condition, boolean isDescending) {
        this.listToSearch = listToSearch;
        this.listResults = listResults;
        this.condition = condition;
        this.isDescending = isDescending;
        Method[] listMethods = ElectroList.class.getDeclaredMethods();
        
        for (Method method : listMethods) {
            if (method.getName().equals("getNode")) {
                getNodeMethod = method;
                getNodeMethod.setAccessible(true);
                break;
            }
        }
        start();
        setName("@"+hashCode()+"TSearcherFor"+listToSearch.getName());
        listMethods = null;
    }
    
    public boolean isFinished(){
        return getState() == State.TERMINATED;
    }

    public boolean hasResults(){
        return !listResults.isEmpty();
    }
    
    public T getFirstOcurrence(){
        return listResults.getFirst();
    }
    
    private Node<T> getNode(int index){
        try {
            return (Node<T>) getNodeMethod.invoke(listToSearch, index);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(TSearcher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public void run() {
        int listSize = listToSearch.size();
        int middle = listSize >> 1;
        
        // Acceso a campos dejarlo con reflection.
        if (isDescending) {
            Node<T> node = getNode(listSize-1);
            
            for(int i = 0; i > middle; i++){
                if (condition.test(node.data))
                    listResults.add(node.data);
                node = node.prev;
            }
        }
        else{
            Node<T> node = getNode(0);
            
            for(int i = 0; i < middle; i++){
                if (condition.test(node.data))
                    listResults.add(node.data);
                node = node.next;
            }
        }
    }
    
}
