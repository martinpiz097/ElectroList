/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.electroList.structure;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *
 * @author martin
 * @param <T> Tipo de dato con los que el StreamSupport trabajará.
 */
public interface StreamSupport<T>{
    /**
     * Devuelve true si al menos uno de los elementos cumple
     * con el Predicate entregado; false en caso contrario.
     * @param predicate Función predicate con la condición dada.
     * @return Valor booleano según el resultado.
     */
    boolean anyMatch(Predicate<? super T> predicate);
    
    /**
     * Devuelve true si todos los elementos cumplen
     * con el Predicate entregado; false en caso contrario.
     * @param predicate Función predicate con la condición dada.
     * @return Valor booleano según el resultado.
     */
    boolean allMatch(Predicate<? super T> predicate);
    
    /**
     * Filtra secuencialmente la colección de acuerdo a la condición del Predicate.
     * @param predicate Función Predicate con la condición a evaluar.
     * @return Stream con los elementos filtrados.
     */
    Stream<T> filter(Predicate<? super T> predicate);
    
    /**
     * Filtra paralelamente (tarea multihilo) la colección de acuerdo a la condición del Predicate.
     * @param predicate Función Predicate con la condición a evaluar.
     * @return Stream con los elementos filtrados
     */
    Stream<T> parallelFilter(Predicate<? super T> predicate);
    
}
