/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.martin.electroList.test;

import java.util.Iterator;
import java.util.LinkedList;
import org.martin.electroList.structure.ElectroList;

/**
 *
 * @author martin
 */
public class Main {

    public static void main(String[] args) {
        ElectroList<String> list = new ElectroList<>("miElectroList");

        for (int i = 0; i < 10; i++) {
            list.add("elemento"+i);
        }
        
        LinkedList<String> linked = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            linked.add("linked"+i);
        }
        
        list.addFirst("xd");
        //list.addAll(1, linked);
        list.remove("elemento2");
        System.out.println("Size: "+list.size());

        for (Iterator<String> it = list.descendingIterator(); it.hasNext();) {
            System.out.println(it.next());
        }
    }
}
