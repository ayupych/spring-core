package org.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("org.example");

//        OperationConsoleListener consoleListener = context.getBean(OperationConsoleListener.class);
//        consoleListener.start();
//        consoleListener.listenUpdate();
//        consoleListener.endListen();




    }
}
