package com.invoice.email.sender.repository;

import org.springframework.data.repository.CrudRepository;

import com.invoice.email.sender.entity.User;

public interface UserRepository extends CrudRepository<User, Long> { 

}
