package com.springreview.webfluxreview.handler;

import com.springreview.webfluxreview.dao.CustomerDao;
import com.springreview.webfluxreview.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerHandler {

    @Autowired
    private CustomerDao customerDao;

    public Mono<ServerResponse> loadCustomers(ServerRequest serverRequest) {
        Flux<Customer> customerList = customerDao.getCustomersStream();
        return ServerResponse.ok().body(customerList, Customer.class);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        int id = Integer.parseInt(request.pathVariable("id"));
        Mono<Customer> customerMono = customerDao.getCustomersStream().filter(c -> c.getId() == id).next();// take(1).single();
        return ServerResponse.ok().body(customerMono, Customer.class);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest request) {
        Mono<Customer> customerMono = request.bodyToMono(Customer.class);
        Mono<String> stringMono = customerMono.map(dto -> dto.getId() + ":" + dto.getName());
        return ServerResponse.ok().body(stringMono, String.class);
    }
}
