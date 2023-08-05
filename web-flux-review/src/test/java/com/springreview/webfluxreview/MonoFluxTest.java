package com.springreview.webfluxreview;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoFluxTest {

    @Test
    public void testMono() {
        Mono<String> helloMono = Mono.just("Hello Mono").log();
        helloMono.subscribe(System.out::println);
    }

    @Test
    public void testMonoError() {
        Mono<String> helloMono = Mono.just("Hello Mono").log().then(Mono.error(new RuntimeException("Hello Mono error")));
        helloMono.subscribe(System.out::println, e -> System.out.println(e.getMessage()));
    }

    @Test
    public void testFlux() {
        Flux<String> fruitFlux = Flux.just("Apple", "Mango", "Banana", "Orange")
                .concatWithValues("Peach")
                .log();
        fruitFlux.subscribe(System.out::println);
    }

    @Test
    public void testFluxError() {
        Flux<String> fruitFlux = Flux.just("Apple", "Mango", "Banana", "Orange")
                .concatWithValues("Peach")
                .concatWith(Flux.error(new RuntimeException("Exception occurred - on Flux")))
                .concatWithValues("Guava")
                .log();
        fruitFlux.subscribe(System.out::println, e -> System.out.println(e.getMessage()));
    }
}
