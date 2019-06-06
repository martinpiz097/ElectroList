/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mpizutil.electrolist.test;

import org.mpizutil.electrolist.structure.ElectroList;

/**
 *
 * @author martin
 */
public class ThreadMachine {
    private final ElectroList<Thread> threads;

    public ThreadMachine() {
        threads = new ElectroList<>();
    }
    
    public void addThread(Runnable r){
        Thread t = new Thread(r);
        threads.add(t);
        threads.getLast().start();
    }
    
    public void stopAll(){
        while(!threads.isEmpty()){
            try {
                threads.poll().stop();
            } catch (Exception e) {
            }
        }
    }
    
}
