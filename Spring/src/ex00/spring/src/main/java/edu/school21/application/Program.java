package edu.school21.application;

import edu.school21.preprocessor.PreProcessor;
import edu.school21.preprocessor.PreProcessorToUpperImpl;
import edu.school21.renderer.Renderer;
import edu.school21.renderer.RendererErrImpl;
import edu.school21.printer.Printer;
import edu.school21.printer.PrinterWithPrefixImpl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Program {
    public static void main(String[] args) {
		System.out.println("Standartd way:");
		PreProcessor preProcessor = new PreProcessorToUpperImpl();
		Renderer renderer = new RendererErrImpl(preProcessor);
		PrinterWithPrefixImpl printer = new PrinterWithPrefixImpl(renderer);
		printer.setPrefix("Prefix ");
		printer.print("Hello!");
		
		System.out.println("Spring beans way:");
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
		
        Printer printerWithPrefix = context.getBean("printerWithPrefix", Printer.class);
        printerWithPrefix.print("Hello, Spring!");

        printerWithPrefix = context.getBean("printerWithPrefixErr", Printer.class);
        printerWithPrefix.print("Hello, Spring! Err");

        Printer datePrinter = context.getBean("printerWithDateTime", Printer.class);
        datePrinter.print("Hello, Spring!");

        datePrinter = context.getBean("printerWithDateTimeErr", Printer.class);
        datePrinter.print("Hello, Spring! Err");
    }
}