package data;

import org.springframework.data.repository.CrudRepository;

import Taco_cloud_JPA.Order;

public interface OrderRepository
        extends CrudRepository<Order, Long> {};