package com.dachser;

import com.dachser.greeting.GreetingSout;

public class App {

    public static void main(String[] args) {
        GreetingSout gSout = new GreetingSout();
        if (args != null && args.length > 0) {
            gSout.personalizedGreeting(args[0]);
        } else {
            gSout.genericGreeting();
        }
    }

}