///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.martin.electroList.test;
//
//import java.io.IOException;
//import java.util.Iterator;
//import java.util.ListIterator;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.function.Predicate;
//import java.util.stream.Stream;
//import javafx.application.Platform;
//import org.martin.electroList.searchs.ParallelSearcher;
//import org.martin.electroList.searchs.TSearcher;
//import org.martin.electroList.structure.ElectroList;
//
///**
// *
// * @author martin
// */
//public class Main {
//    
//    static long ti;
//    static int alarm = 10;
//    static final StringBuilder sBuilder = new StringBuilder();
//    //        ArrayDeque<String> deque = new ArrayDeque<>();
////        ArrayQueue<String> queue = new ArrayQueue<>(10);
////        Stack s = new Stack();
//    
//    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
//        ElectroList<String> list = new ElectroList<>("miElectroList");
//        
//        // Pruebas de rendimiento bajo tareas multihilo superadas!.
//        Runnable runAdd = new Runnable() {
//            @Override
//            public void run() {
//                for(int i = 0; i < 5; i++){
//                    System.out.println("add");
//                    list.add("elemento"+this.hashCode());
//                }
//            }
//        };
//        
//        Runnable runGet = () -> {
//            for(int i = 0; i < 5; i++){
//                System.out.println("get");
//                list.get(list.size()-1);
//            }
//        };
//        
//        Runnable runSet = new Runnable() {
//            @Override
//            public void run() {
//                for(int i = 0; i < 5; i++){
//                    System.out.println("set");
//                    list.set(list.size()-1, "xd"+hashCode());
//                }
//            }
//        };
//        
//        Runnable runDel = () -> {
//            for(int i = 0; i < 2; i++){
//                System.out.println("del");
//                list.remove(5);
//            }
//        };
//        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(100);
//        
//        for (int i = 0; i < 5; i++) {
//            executor.execute(runAdd);
//            executor.execute(runGet);
//            executor.execute(runSet);
//            executor.execute(runDel);
//        }
//        
//        System.out.println("Salio del for");
//        Thread.sleep(5000);
//        executor.shutdownNow();
//        System.out.println("Cantidad de elementos de la lista: "+list.size());
//        
//        System.exit(0);
//        for (int i = 0; i < 1000000; i++) {
//            list.add("elemento"+i);
//        }
//
//        System.out.println("Antes de sleep");
//        Thread.sleep(30000);
//        list.clear();
//
//        System.out.println("Antes de salir");
//        Thread.sleep(30000);
//        System.exit(0);
//        
//        list.addFirst("xd");
//        list.addLast("xdLast");
//        System.out.println(list.get(0));
//        System.out.println(list.getLast());
//
//        String MSG_1 = "streamFilter";
//        String MSG_2 = "filterSolo";
//        String MSG_3 = "streamAnyMatch";
//        String MSG_4 = "anyMatchSolo";
//        String MSG_5 = "streamAllMatch";
//        String MSG_6 = "allMatchSolo";
//        
//        Stream<String> stream = list.stream();
//        start();
//        stream.filter(s->s.equals("elemento99999")).forEach(a->{});
//        finish(MSG_1);
//    
//        start();
//        list.filter(s->s.equals("elemento99999")).forEach(a->{});
//        finish(MSG_2);
//        
//        start();
//        Predicate<String> p = s->s.contains("e");
//        ElectroList<String> search = ParallelSearcher.search(list, p);
//        finish("parallelSearch");
//        
//        stream = list.stream();
//        start();
//        list.stream().anyMatch(s->s.equals("elemento99999"));
//        finish(MSG_3);
//        
//        start();
//        list.anyMatch(s->s.equals("elemento99999"));
//        finish(MSG_4);
//        
//        stream = list.stream();
//        start();
//        list.stream().allMatch(s->s.equals("elemento99999"));
//        finish(MSG_5);
//        
//        start();
//        list.allMatch(s->s.equals("elemento99999"));
//        finish(MSG_6);
//
//        System.out.println("-----------------------------------");
//        System.out.println("-----------------------------------");
//        
//        System.out.println("Elementos encontrados: "+search.size());
//    }
//    
//    public static void clearStringBuilder(){
//        sBuilder.delete(0, sBuilder.length());
//    }
//    
//    public static void appendToBuilder(Object obj){
//        sBuilder.append(obj.toString());
//    }
//    
//    public static long currentTime(){
//        return System.currentTimeMillis();
//    }
//    
//    public static void start(){
//        ti = currentTime();
//    }
//    
//    public static void finish(String msg){
//        long tf = currentTime();
//        tf -= ti;
//        appendToBuilder(msg);
//        appendToBuilder(": ");
//        appendToBuilder(tf);
//        System.err.println(sBuilder.toString());
//        clearStringBuilder();
//    }
//    
//}