
public class Calculator {

    public static void main(String[] args) {
        int num1 = 10;
        int num2 = 40;
        System.out.println("Init calculator -> sum " + num1 + " + " + num2);
        int result = sum(num1, num2);
        System.out.println("Result is: " + result);
    }

    public static int sum(int a, int b) {
        return a + b;
    }

}