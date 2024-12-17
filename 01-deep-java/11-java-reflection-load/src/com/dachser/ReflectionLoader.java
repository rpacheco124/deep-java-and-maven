package com.dachser;
import java.lang.reflect.*;

public class ReflectionLoader {
    
    public static void main(String[] args) throws Exception {
        Class<?> grettingSoutClass = Class.forName("com.dachser.greeting.GreetingSout");

        Object grettingSoutInstance = grettingSoutClass.getDeclaredConstructor().newInstance();
        
        Method executeMethod = grettingSoutClass.getMethod("personalizedGreeting", String.class);
        executeMethod.invoke(grettingSoutInstance, "Ronald :D");
    }

}