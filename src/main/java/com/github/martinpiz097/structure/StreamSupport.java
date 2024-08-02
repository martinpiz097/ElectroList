/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.martinpiz097.structure;

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
     * Filtra secuencialmente la colección de acuerdo a la condición del Predicate
     * y devuelve el primer elemento que cumpla con la condicion solicitada.
     * @param predicate Función Predicate con la condición a evaluar.
     * @return Objeto que cumple con la condición solicitada, en caso de estar 
     * vacía la coleccion o de no encontrar un resultado será devuelto null.
     */
    T findFirst(Predicate<? super T> predicate);
    
    /**
     * Filtra secuencialmente la colección de acuerdo a la condición del Predicate
     * y devuelve el último elemento que cumpla con la condicion solicitada.
     * @param predicate Función Predicate con la condición a evaluar.
     * @return Objeto que cumple con la condición solicitada, en caso de estar 
     * vacía la coleccion o de no encontrar un resultado será devuelto null.
     */
    T findLast(Predicate<? super T> predicate);
    
    /**
     * Filtra paralelamente (multihilo) la colección de acuerdo a la condición del Predicate
     * y devuelve el primer elemento encontrado que cumpla con la condicion solicitada.
     * @param predicate Función Predicate con la condición a evaluar.
     * @return Objeto que cumple con la condición solicitada, en caso de estar 
     * vacía la coleccion o de no encontrar un resultado será devuelto null.
     */
    T findAny(Predicate<? super T> predicate);
    
    /**
     * Filtra paralelamente (tarea multihilo) la colección de acuerdo a la condición del Predicate.
     * @param predicate Función Predicate con la condición a evaluar.
     * @return Stream con los elementos filtrados.
     */
    Stream<T> parallelFilter(Predicate<? super T> predicate);
    
    /**
     * Filtra paralelamente (tarea multihilo) la colección de acuerdo a la condición del Predicate.
     * @param predicate Función Predicate con la condición a evaluar.
     * @return Lista con los elementos filtrados.
     */
    ElectroList<T> parallelSearch(Predicate<? super T> predicate);

    /**
     * Elimina y devuelve el primer elemento de la colección que cumpla con la 
     * condición del Predicate.
     * @param predicate Función Predicate con la condición a evaluar.
     * @return Elemento eliminado dada la condición del Predicate.
     */
    
    public T removeFirst(Predicate<? super T> predicate);
    
    /**
     * Elimina y devuelve el último elemento de la colección que cumpla con la 
     * condición del Predicate.
     * @param predicate Función Predicate con la condición a evaluar.
     * @return Elemento eliminado dada la condición del Predicate.
     */
    public T removeLast(Predicate<? super T> predicate);
    
    /**
     * Retiene solo los elementos de la lista que cumplen con la condición del Predicate, 
     * los demás son eliminados.
     * @param predicate Función Predicate con la condición a evaluar.
     * @return true si se hizo efectiva la retención; false en cualquier otro caso.
     */
    boolean retainIf(Predicate<? super T> predicate);
    
}
