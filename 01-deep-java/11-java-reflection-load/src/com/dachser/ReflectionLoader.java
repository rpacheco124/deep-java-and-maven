package com.dachser;
import java.lang.reflect.*;

public class ReflectionLoader {
    
    public static void main(String[] args) throws Exception {
        Class<?> greetingSoutClass = Class.forName("com.dachser.greeting.GreetingSout");

        Object greetingSoutInstance = greetingSoutClass.getDeclaredConstructor().newInstance();
        
        Method executeMethod = greetingSoutClass.getMethod("personalizedGreeting", String.class);
        executeMethod.invoke(greetingSoutInstance, "Ronald :D");
    }

}