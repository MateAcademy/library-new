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

//    @Bean
//    public CommandLineRunner commandLineRunner2(MyHttpPeople validators) {
//        return args -> {

//            List<PersonResponse> peopleResponses = validators.geAllPeople();
//            System.out.println(peopleResponses);

//            PersonResponse peopleById = validators.getPeopleById(1);
//            System.out.println(peopleById);
//        };
//    }

//    @Bean
//    public CommandLineRunner commandLineRunner2(MyHttpClient client) {
//        return args -> {
//            // Calling a method GET without parameters
//            System.out.println("Response get without parameters: " + client.getHelloMessage(null, null));
//
//            // Calling a method GET with parameters
//            System.out.println("Response get with parameters: " +client.getHelloMessage("John", 30));

//            // Calling a method POST without parameters
//            System.out.println("Response post without parameters: " + client.postHelloMessage(null, null));
//
//            // Calling a method POST with parameters
//            System.out.println("Response post with parameters: " + client.postHelloMessage("Serhii", 21));

//            System.out.println("Response get with pathVariable and parameters: " + client.getDescriptionMessage("1L", "Serhii", 30));

//            client.postHelloMessageWithBody(new UserRequest("test", 20));
//
//            try {
//                System.out.println("Response: " + client.getSlowMessage());
//            } catch (WebClientRequestException e) {
//                System.err.println("Timeout Exception: " + e);
//            }
//        };
//    }
}





