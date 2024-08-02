package com.github.martinpiz097.testing;///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.martin.electroList.test;
//
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import java.util.ArrayDeque;
//import java.util.Scanner;
//import java.util.concurrent.Executors;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.function.Predicate;
//import java.util.stream.Stream;
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
//    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//        
//        ElectroList<String> lista = new ElectroList<>();
//        for (int i = 0; i < 10; i++)
//            lista.add("element"+i);
//        
//        System.out.println("Tamaño: "+lista.getSizeInMemory());
//        
//        
//        System.exit(0);
//        
//        execFindAnyTest();
//        execTestClear();
//        String MSG_1 = "streamFilter";
//        String MSG_2 = "filterSolo";
//        String MSG_3 = "streamAnyMatch";
//        String MSG_4 = "anyMatchSolo";
//        String MSG_5 = "streamAllMatch";
//        String MSG_6 = "allMatchSolo";
//        String MSG_7 = "for search";
//        String MSG_8 = "foreach search";
//        String MSG_9 = "findFirst";
//        String MSG_10 = "findLast";
//        String MSG_11 = "findAny";
//        
//        ElectroList<String> list = new ElectroList<>("miElectroList");
//
//        System.out.print("Cantidad de elementos: ");
//        int lim = new Scanner(System.in).nextInt();
//
//        System.out.print("Filtro: ");
//        String filtro = new Scanner(System.in).nextLine();
//        for (int i = 0; i < lim; i++) {
//            list.add("elemento"+i);
//        }
//
//        System.out.println("Cantidad de elementos de la lista: "+list.size());
//        System.out.println("-----------------------------------");
//        System.out.println("-----------------------------------");
//        
//        Stream<String> stream = list.stream();
//        start();
//        stream.filter(s->s.equals(filtro)).forEach(a->{});
//        finish(MSG_1);
//        System.out.println("Cantidad de elementos de la lista: "+list.size());
//        
//        start();
//        list.filter(s->s.equals(filtro)).forEach(a->{});
//        finish(MSG_2);
//        System.out.println("Cantidad de elementos de la lista: "+list.size());
//        
//        start();
//        Predicate<String> p1 = s->s.equals(filtro);
//        ElectroList<String> search = list.parallelSearch(p1);
//        finish("parallelSearch");
//        System.out.println("Cantidad de elementos de la lista: "+list.size());
//        
//        start();
//        Predicate<String> p2 = s->s.equals(filtro);
//        list.parallelFilter(p2);
//        finish("parallelFilter");
//        System.out.println("Cantidad de elementos de la lista: "+list.size());
//        
//        stream = list.stream();
//        start();
//        list.stream().anyMatch(s->s.equals(filtro));
//        finish(MSG_3);
//        System.out.println("Cantidad de elementos de la lista: "+list.size());
//        
//        start();
//        list.anyMatch(s->s.equals(filtro));
//        finish(MSG_4);
//        System.out.println("Cantidad de elementos de la lista: "+list.size());
//        
//        stream = list.stream();
//        start();
//        list.stream().allMatch(s->s.equals(filtro));
//        finish(MSG_5);
//        System.out.println("Cantidad de elementos de la lista: "+list.size());
//        
//        start();
//        list.allMatch(s->s.equals(filtro));
//        finish(MSG_6);
//        System.out.println("Cantidad de elementos de la lista: "+list.size());
//        
//        start();
//        for (String string : list)
//            if (string.equals(search)) {break;}
//        finish(MSG_8);
//        System.out.println("Cantidad de elementos de la lista: "+list.size());
//        
//        start();
//        list.findFirst(s->s.equals(search));
//        finish(MSG_9);
//        System.out.println("Cantidad de elementos de la lista: "+list.size());
//        
//        start();
//        list.findLast(s->s.equals(search));
//        finish(MSG_10);
//        System.out.println("Cantidad de elementos de la lista: "+list.size());
//        
//        start();
//        list.findAny(s->s.equals(search));
//        finish(MSG_11);
//        System.out.println("Cantidad de elementos de la lista: "+list.size());
//        
//        System.out.println("-----------------------------------");
//        System.out.println("-----------------------------------");
//        
//        System.out.println("Elementos encontrados: "+search.size());
//    }
//
//    private static void execMultithreadTest() throws InterruptedException{
//        ElectroList<String> list = new ElectroList<>("miElectroList");
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
//    }
//    
//    private static void execGCTest() throws InterruptedException{
//        ElectroList<String> list = new ElectroList<>("miElectroList");
//        list.add("xd");
//        
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
//    }
//    
//    private static void execAddAllTest(){
//        String[] array = new String[10];
//        ElectroList<String> electro = new ElectroList<>();
//        for (int i = 0; i < 10; i++)
//            array[i] = "elementoArray"+i;
//        
//        electro.addAll(array);
//        
//        for (String string : electro) {
//            System.out.println(string);
//        }
//        
//        System.exit(0);
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
//    private static void execTestClear() throws InterruptedException {
//        ElectroList<String> list = new ElectroList<>();
//        
//        System.out.println("Antes de agregar elementos");
//        Thread.sleep(20000);
//        for (int i = 0; i < 1000000; i++) {
//            list.add("elemento de la lista"+i);
//        }
//
//        System.out.println("Elementos agregados");
//        Thread.sleep(20000);
//        list.clear();
//        System.out.println("Limpieza");
//        Thread.sleep(20000);
//        System.exit(0);
//    }
//
//    private static void execFindAnyTest() {
//        ElectroList<String> list;
//        list = new ElectroList<>();
//        
//        for (int i = 0; i < 100000; i++) {
//            list.add("elemento"+i);
//        }
//        
//        start();
//        System.out.println(list.findAny(s->s.contains("99")));
//        finish("findAny");
//        
//        System.exit(0);
//    }
//    
//}