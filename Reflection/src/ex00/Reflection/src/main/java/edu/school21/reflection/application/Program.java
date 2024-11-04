package edu.school21.reflection.application;

import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.lang.Class;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

public class Program {
	private static Scanner scanner;
	private static Class<?> usingClass = null;
	private static Object usingObject = null;
	private static final String LINE_SEPARATOR = "---------------------";
		
    public static void main(String[] args) {
		try {
			scanner = new Scanner(System.in);
			usingClass = ChooseClass();
			PrintClassInfo();
			CreateObject();
			ChangeOneField();
			CallMethod(ChooseMethod());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		} finally {		
			scanner.close();
		}
    }
	
	private static Class<?> ChooseClass() throws Exception {
		Reflections reflections = new Reflections("edu.school21.reflection.classes", new SubTypesScanner(false));
		Set<Class<?>> allClasses = reflections.getSubTypesOf(Object.class);
		System.out.println("Classes:");
		for (Class<?> clas : allClasses) {
            System.out.println(" - " + clas.getSimpleName());
        }
        System.out.println(LINE_SEPARATOR);		
		Class<?> result = null;
		System.out.println("Enter class name:");
		while (result == null) {
			System.out.print("-> ");
			if (scanner.hasNext() == false) {
				throw new Exception("Empty input!");
			}
			String className = scanner.nextLine();		
			System.out.println(LINE_SEPARATOR);
			for (Class<?> clas : allClasses) {
				if (clas.getSimpleName().equals(className)) {
					result = clas;
				}
			}
			if (result == null) {
				System.out.println("Error! Try to choose class again!");
			}
		}
		return result;
	}
	
	private static void PrintClassInfo() {
	    System.out.println("fields:");
        Field[] allFields = usingClass.getDeclaredFields();
        for (Field field : allFields) {
            System.out.println("\t" + field.getType().getSimpleName() + " " + field.getName());
        }
        System.out.println("methods:");
        Method[] allMethods = usingClass.getDeclaredMethods();
        for (Method method : allMethods) {
			System.out.print("\t" + method.getReturnType().getSimpleName() + " " + method.getName() + "(");
			Class<?>[] parameters = method.getParameterTypes();
			for (int i = 0; i < parameters.length; i++) {
				System.out.print(parameters[i].getSimpleName());
				if (i < parameters.length - 1) {
					System.out.print(", ");
				}
			}
			System.out.println(")");
        }
        System.out.println(LINE_SEPARATOR);
	}
	
	private static void CreateObject() throws Exception {
		System.out.println("Let's create an object.");
		usingObject = usingClass.getConstructor().newInstance();
		Field[] fields = usingClass.getDeclaredFields();
		for (Field field : fields) {
            System.out.print(field.getName() + ":\n-> ");
            field.setAccessible(true);
            field.set(usingObject, getInputValue(field.getType()));
			field.setAccessible(false);
		}
		System.out.println("Object created: " + usingObject);
		System.out.println(LINE_SEPARATOR);
	}
	
	private static void ChangeOneField() throws Exception {
		System.out.print("Enter name of the field for changing:\n-> ");
		if (scanner.hasNext() == false) {
			throw new Exception("Empty input in ChangeOneField()!");
		}
		try {
			Field changeField = usingClass.getDeclaredField(scanner.nextLine());
			System.out.print("Enter " + changeField.getType().getSimpleName() + " value:\n-> ");
			changeField.setAccessible(true);
			changeField.set(usingObject, getInputValue(changeField.getType()));
			changeField.setAccessible(false);
			System.out.println("Object updated: " + usingObject);
			System.out.println(LINE_SEPARATOR);
		} catch (NoSuchFieldException e) {
			System.out.println("No such field, try again!");
			ChangeOneField();
		} catch (Exception e) {
			throw e;
		}
	}
	
	private static Method ChooseMethod() throws Exception {
		Method choosingMethod = null;
		System.out.print("Enter name of the method for call:\n-> ");
		if (scanner.hasNext() == false) {
			throw new Exception("Empty input in CallOneMethod()!");
		}
        String methodName = scanner.nextLine().trim();
		Method[] methods = usingClass.getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				choosingMethod = method;
				break;
			}
		}
		if (choosingMethod == null) {
			System.out.println("Method not found. Try again!");
			ChooseMethod();
		}
		return choosingMethod;
	}
	
	private static void CallMethod(Method usingMethod) throws Exception {
		Class<?>[] argumentsTypes = usingMethod.getParameterTypes();
		Object[] arguments = new Object[argumentsTypes.length];
		for (int i = 0; i < argumentsTypes.length; i++) {
			System.out.print("Enter " + argumentsTypes[i].getSimpleName() + " value:\n-> ");
			if (scanner.hasNext() == false) {
				throw new Exception("Empty input in CallMethod() in parameters!");
			}
			arguments[i] = getInputValue(argumentsTypes[i]);
		}
		
		Object result = usingMethod.invoke(usingObject, arguments);
        if (usingMethod.getReturnType().equals(void.class) == false) {
            System.out.println("Method returned: " + result);
        }
	}
	
	private static Object getInputValue(Object type) throws Exception {
		if (scanner.hasNext() == false) {
			throw new Exception("Empty input in getInputValue()!");
		}
        try {
            if (type.equals(String.class)) {
                return scanner.nextLine().trim();
            } else if (type.equals(Integer.class) || type.equals(int.class)) {
                return Integer.parseInt(scanner.nextLine());
			} else if (type.equals(Long.class) || type.equals(long.class)) {
                return Long.parseLong(scanner.nextLine());
            } else if (type.equals(Double.class) || type.equals(double.class)) {
                return Double.parseDouble(scanner.nextLine());
            } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
                return Boolean.parseBoolean(scanner.nextLine());
            }
        } catch (NumberFormatException e) {
			System.out.print("Invalid argument, try again!\n->");
			return getInputValue(type);
        } catch (Exception e) {
			throw e;
		}
		return null;
    }
}
