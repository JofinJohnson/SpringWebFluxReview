package com.springreview.webfluxreview.service;

import com.springreview.webfluxreview.dao.CustomerDao;
import com.springreview.webfluxreview.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public List<Customer> loadAllCustomers() {
        long start = System.currentTimeMillis();
        List<Customer> customerList = customerDao.getCustomers();
        long end = System.currentTimeMillis();
        System.out.println("Elapsed time : " + (end - start));
        return customerList;
    }

    public Flux<Customer> loadAllCustomersStream() {
        long start = System.currentTimeMillis();
        Flux<Customer> customerList = customerDao.getCustomersStream();
        long end = System.currentTimeMillis();
        System.out.println("Elapsed time : " + (end - start));
        return customerList;
    }
}
