/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.electroList.searchs;

import java.util.function.Predicate;
import java.util.stream.Stream;
import org.martin.electroList.structure.ElectroList;

/**
 *
 * @author martin
 */
public class TSearcherManager<T> {
    private final TSearcher<T> searcher1;
    private final TSearcher<T> searcher2;
    private final ElectroList<T> listResults;

    public TSearcherManager(ElectroList<T> listSearch, Predicate<? super T> predicate) {
        listResults = new ElectroList<>();
        searcher1 = new TSearcher<>(listSearch, listResults, predicate);
        searcher2 = new TSearcher<>(listSearch, listResults, predicate, true);
    }
    
    private void stopSearchers(){
        searcher1.interrupt();
        searcher2.interrupt();
    }
    
    public T getFirstOcurrence(){
        while (!searcher1.isFinished() || !searcher2.isFinished() && 
                listResults.isEmpty()){}
        return listResults.pollFirst();
    }
    
    public ElectroList<T> getResults(){
        while (!searcher1.isFinished() && !searcher2.isFinished()) {}
        return listResults;
    }
    
    public Stream<T> getStreamResults(){
        return getResults().stream();
    }
    
}
