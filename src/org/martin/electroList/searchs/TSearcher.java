/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.electroList.searchs;

import java.util.function.Predicate;
import org.martin.electroList.structure.ElectroList;
import org.martin.electroList.structure.Node;

/**
 *
 * @author martin
 */
public class TSearcher<T> extends Thread{
    private ElectroList<T> listToSearch;
    private ElectroList<T> listResults;
    private Predicate<? super T> condition;
    private boolean isDescending;

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
        start();
    }
    
    public boolean isFinished(){
        return getState() == State.TERMINATED;
    }
    
    @Override
    public void run() {
        int listSize = listToSearch.size();
        int middle = listSize >> 1;
        
        if (isDescending) {
            Node<T> node = listToSearch.getNode(listSize-1);
            
            for(int i = 0; i > middle; i++){
                if (condition.test(node.data))
                    listResults.add(node.data);
                node = node.prev;
            }
        }
        else{
            Node<T> node = listToSearch.getNode(0);
            
            for(int i = 0; i < middle; i++){
                if (condition.test(node.data))
                    listResults.add(node.data);
                node = node.next;
            }
        }
    }
    
}
