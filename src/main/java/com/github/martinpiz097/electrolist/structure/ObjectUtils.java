package com.github.martinpiz097.electrolist.structure;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author martin
 */
public class ObjectUtils {
    private static final String BOOLEAN = "boolean";
    private static final String BYTE = "byte";
    private static final String SHORT = "short";
    private static final String INTEGER = "int";
    private static final String LONG = "long";
    private static final String FLOAT = "float";
    private static final String DOUBLE = "double";
    
    private static final byte BOOLEAN_SIZE = 1;
    private static final byte BYTE_SIZE = Byte.SIZE / 8;
    private static final byte SHORT_SIZE = Short.SIZE / 8;
    private static final byte INTEGER_SIZE = Integer.SIZE / 8;
    private static final byte LONG_SIZE = Long.SIZE / 8;
    private static final byte FLOAT_SIZE = Float.SIZE / 8;
    private static final byte DOUBLE_SIZE = Double.SIZE / 8;
    
    private boolean isNumberClazz(Class clazz){
        return clazz.getSuperclass().equals(Number.class) || 
                (clazz.equals(float.class) || clazz.equals(double.class) || 
                clazz.equals(byte.class) || clazz.equals(short.class) || 
                clazz.equals(int.class) || clazz.equals(long.class));
    }
    
    private boolean isBool(Class clazz){
        return clazz.getSimpleName().toLowerCase().equals(BOOLEAN);
    }
    
    private int sizeOfNumber(Number number){
        String clazzName = number.getClass().getSimpleName().toLowerCase();
        switch(clazzName){
            case FLOAT:
                return FLOAT_SIZE;
            case DOUBLE:
                return DOUBLE_SIZE;
            case BYTE:
                return BYTE_SIZE;
            case SHORT:
                return SHORT_SIZE;
            case INTEGER:
                return INTEGER_SIZE;
            default:
                return LONG_SIZE;
        }
    }
    
    private int sizeOfStr(String str){
        try {
            return str == null ? 0 : str.getBytes("UTF-8").length;
        } catch (UnsupportedEncodingException ex) {
            return 0;
        }
    }
    
    public int sizeof(Object obj){
        if(obj == null)
            return 0;
        Class objClazz = obj.getClass();
        
        if (isNumberClazz(objClazz))
            return sizeOfNumber((Number) obj);
        if (String.class.equals(objClazz))
            return sizeOfStr(obj.toString());
        if(isBool(objClazz))
            return 1;
        
        int sizeof = 0;
        Field[] fields = objClazz.getDeclaredFields();
        int fieldsLen = fields.length;

        for (int i = 0; i < fieldsLen; i++) {
            try {
                fields[i].setAccessible(true);
                sizeof+=sizeof(fields[i].get(obj));
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(ObjectUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        fields = null;
        
        return sizeof;
    }
}
