package com.example.demo.httpClient;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

// @HttpExchange – new way to call REST API,
// MyHttpPeople – interface that Spring automatically turns into an HTTP client.
@HttpExchange(url = "people")   // @HttpExchange(url = "validators") specifies the base URL.
public interface MyHttpPeople {

    // @GetExchange() – performs HTTP GET request.
    @GetExchange()
    String geAllPeople();

    // @GetExchange("{id}") allows requests with URL path variables
//    @GetExchange("{id}")
//    PersonResponse getPeopleById(@PathVariable("id") int id);

}

//    @PostExchange("helloPost")
//    String postHelloMessage(@RequestParam(required = false) String name,
//                            @RequestParam(required = false) Integer age);
//
//    @GetExchange("descriptionGet/{id}")
//    String getDescriptionMessage(@PathVariable("id") String id,   // id в самом запросе
//                                 @RequestParam(required = false) String name,  // query parameters
//                                 @RequestParam(required = false) Integer age);
//
//    @PostExchange("helloPostWithBody")
//    void postHelloMessageWithBody(@RequestBody UserRequest request);
//
//    @GetExchange("slow")
//    String getSlowMessage();

