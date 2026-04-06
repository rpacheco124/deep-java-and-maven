import java.lang.reflect.*;

public class ReflectionInvoker {
    
    public static void main(String[] args) throws Exception {
        // Create instance
        Class<?> clazz = Class.forName("java.lang.String");
        Object instance = "Hello, world";

        // Get and execute toUpperCase method dynamically
        Method method = clazz.getMethod("toUpperCase");
        Object result = method.invoke(instance);
        System.out.println("Result: " + result);
    }

}