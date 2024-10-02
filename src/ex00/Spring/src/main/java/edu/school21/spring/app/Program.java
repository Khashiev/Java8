package edu.school21.spring.app;

import edu.school21.spring.printer.Printer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Program {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        Printer printer = context.getBean("printerWithPrefixStandardUpper", Printer.class);

        printer.print("Hello!");
    }
}
