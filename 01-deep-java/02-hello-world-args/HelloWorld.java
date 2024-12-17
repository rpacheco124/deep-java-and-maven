public class HelloWorld {
    public static void main(String[] args) {
        if (args != null && args.length > 0) {
            System.out.println("Hola " + args[0]);
        } else {
            System.out.println("Hola MUNDO!");
        }
    }
}