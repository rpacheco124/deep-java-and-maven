import java.lang.reflect.*;
import java.util.Arrays;

public class ReflectionInspector {

    private static String DEFAULT_CLASS_NAME = "java.lang.String";

    public static void main(String[] args) throws ClassNotFoundException {
        String className = getClassName(args);

        Class<?> clazz = Class.forName(className);

        printClassName(clazz);
        
        printConstructors(clazz);

        printFields(clazz);
        
        printMethods(clazz);
    }

    private static String getClassName(String[] args) {
        String className = "";
        if (args == null || args.length == 0) {
            System.out.println("**No class provided, set default class [" + DEFAULT_CLASS_NAME + "] **");
            return DEFAULT_CLASS_NAME;
        } else {
            return args[0];
        }
    }

    private static void printClassName(Class<?> clazz) {
        printSeparator();
        System.out.println("Class: " + clazz.getName());
    }

    private static void printConstructors(Class<?> clazz) {
        Constructor[] constructors = clazz.getConstructors();
        if (constructors.length == 0) return;

        printSeparator();
        System.out.println("Constructors: ");
        for (Constructor constructor : constructors) {
            System.out.println("- " + constructor.getName() + "(" + Arrays.stream(constructor.getParameters()).map(p -> p.getType()+" "+p.getName()).toList().toString() + ")");
        }
    }

    private static void printFields(Class<?> clazz) {
        Field[] fields = clazz.getFields();
        if (fields.length == 0) return;

        printSeparator();
        System.out.println("Fields: ");
        for (Field field : fields) {
            System.out.println("- " + field.getType() + " " + field.getName());
        }
    }

    private static void printMethods(Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        if (methods.length == 0) return;

        printSeparator();
        System.out.println("Methods: ");
        for (Method method : methods) {
            System.out.println("- " + method.getName() + "(" + Arrays.stream(method.getParameters()).map(p -> p.getType()+" "+p.getName()).toList().toString() + ")");
        }
    }

    private static void printSeparator() {
        System.out.println("==========================");
    }

}