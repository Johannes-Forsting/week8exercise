package com.example.week8exercise.Controller;

import com.example.week8exercise.DTO.Age;
import com.example.week8exercise.DTO.Gender;
import com.example.week8exercise.DTO.NameResponse;
import com.example.week8exercise.DTO.Nation;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class NameController {

    @RequestMapping("/name-info")
    public NameResponse getDetails(@RequestParam String name){

        Mono<Age> age  = getAge(name);
        Mono<Gender> gender  = getGender(name);
        Mono<Nation> nation  = getNation(name);

        var info = Mono.zip(age, gender, nation).map(person -> {
            NameResponse nr = new NameResponse();
            nr.setAge(person.getT1().getAge());
            nr.setAgeCount(person.getT1().getCount());

            nr.setGender(person.getT2().getGender());
            nr.setGenderPropability(person.getT2().getProbability());

            nr.setCountry(person.getT3().getCountry().get(0).getCountry_id());
            System.out.println(person.getT3().getCountry().get(0));
            nr.setCountryPropability(person.getT3().getCountry().get(0).getProbability());

            return nr;
        });
        NameResponse res = info.block();
        res.setName(name);

        return res;
    }
    @RequestMapping("/test")
    public NameResponse getDetailsTest(@RequestParam String name) {

        return null;
    }

    private Mono<Age> getAge(String name){
        Mono<Age> slowResponse = WebClient.create()
                .get()
                .uri("https://api.agify.io/?name=" + name)
                .retrieve()
                .bodyToMono(Age.class)
                .doOnError(e-> System.out.println("UUUPS : "+e.getMessage()));
        return slowResponse;
    }

    private Mono<Gender> getGender(String name){
        Mono<Gender> slowResponse = WebClient.create()
                .get()
                .uri("https://api.genderize.io/?name=" + name)
                .retrieve()
                .bodyToMono(Gender.class)
                .doOnError(e-> System.out.println("UUUPS : "+e.getMessage()));
        return slowResponse;
    }

    private Mono<Nation> getNation(String name){
        Mono<Nation> slowResponse = WebClient.create()
                .get()
                .uri("https://api.nationalize.io/?name=" + name)
                .retrieve()
                .bodyToMono(Nation.class)
                .doOnError(e-> System.out.println("UUUPS : "+e.getMessage()));
        return slowResponse;
    }

}
