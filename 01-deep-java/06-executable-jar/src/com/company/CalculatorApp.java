package com.company;

import java.util.Scanner;

public class CalculatorApp {

    public static void main(String[] args) {
        initCalculator();
    }

    private static void initCalculator() {
        Scanner scanner = new Scanner(System.in);
        printAppMenu();
        String line = scanner.nextLine();
        System.out.println("You are selected option " + line);
    }

    private static void printAppMenu() {
        System.out.println("**** Calculator App ****");
        System.out.println("Select operation");
        System.out.println("[1] SUM (+)");
        System.out.println("[2] SUB (-)");
        System.out.println("[3] MUL (*)");
        System.out.println("[4] DIV (/)");
        System.out.println("To exit press any other key");
    }

}