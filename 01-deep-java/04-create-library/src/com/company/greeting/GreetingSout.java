package com.company.greeting;

import com.company.greeting.interfaces.Greeting;

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