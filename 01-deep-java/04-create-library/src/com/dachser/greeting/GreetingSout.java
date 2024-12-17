package com.dachser.greeting;

import com.dachser.greeting.interfaces.Greeting;

public class GreetingSout implements Greeting {

    @Override
    public void genericGreeting() {
        System.out.println("Hello everyone");
    }

    @Override
    public void personalizedGreeting(String name) {
        System.out.println("Hello " + name);
    }

}