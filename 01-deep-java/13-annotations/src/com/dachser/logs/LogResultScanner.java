package com.dachser.logs;

import com.dachser.annotations.LogResult;

import java.lang.reflect.Method;

public class LogResultScanner {

    public static void main(String[] args) throws Exception {
        Class<?> clazz = LogResultExample.class;
        Object instance = clazz.getDeclaredConstructor().newInstance();

        System.out.println("=== Scanning " + clazz.getSimpleName() + " for @LogResult ===\n");

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(LogResult.class)) {
                System.out.println("Found loggable method: " + method.getName() + "()");
                System.out.println("  -> Result: " + method.invoke(instance));
                System.out.println();
            }
        }
    }
}
