package com.example.demo.httpClient;

import com.example.demo.dto.PersonResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

// @HttpExchange – новый способ вызова REST API,
// MyHttpPeople – это интерфейс, который Spring автоматически превращает в HTTP-клиент.
@HttpExchange(url = "people")   // @HttpExchange(url = "validators") указывает базовый URL.
public interface MyHttpPeople {

    // @GetExchange() – выполняет HTTP GET-запрос.
    @GetExchange()
    String geAllPeople();

    // @GetExchange("{id}") позволяет делать запрос с переменными в URL
//    @GetExchange("{id}")
//    PersonResponse getPeopleById(@PathVariable("id") int id);

}

//    @PostExchange("helloPost")
//    String postHelloMessage(@RequestParam(required = false) String name,
//                            @RequestParam(required = false) Integer age);
//
//    @GetExchange("descriptionGet/{id}")
//    String getDescriptionMessage(@PathVariable("id") String id,   // id в самом запросе
//                                 @RequestParam(required = false) String name,  // параметры в запросе
//                                 @RequestParam(required = false) Integer age);
//
//    @PostExchange("helloPostWithBody")
//    void postHelloMessageWithBody(@RequestBody UserRequest request);
//
//    @GetExchange("slow")
//    String getSlowMessage();

