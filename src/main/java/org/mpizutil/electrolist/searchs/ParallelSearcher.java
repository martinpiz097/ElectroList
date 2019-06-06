/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpizutil.electrolist.searchs;

import java.util.function.Predicate;
import org.mpizutil.electrolist.structure.ElectroList;

/**
 *
 * @author martin
 */
public class ParallelSearcher {
    
    public static <T> ElectroList<T> search(ElectroList<T> list, Predicate<? super T> condition){
        ElectroList<T> listaResultados = new ElectroList<>();
        TSearcher<T> searcher1 = new TSearcher<>(list, listaResultados, condition, false);
        TSearcher<T> searcher2 = new TSearcher<>(list, listaResultados, condition, true);
        
        while (!searcher1.isFinished() && !searcher2.isFinished()) {}
    
        return listaResultados;
    } 
}
