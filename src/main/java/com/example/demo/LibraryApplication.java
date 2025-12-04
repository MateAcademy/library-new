package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(LibraryApplication.class, args);
    }


//    @Bean
//    public CommandLineRunner helloCommandLineRunner(MyHttpHello myHttpHello) {
//        return args -> {
//            myHttpHello.getHelloMessage("Serhii", 10);
//        };
//    }

}





