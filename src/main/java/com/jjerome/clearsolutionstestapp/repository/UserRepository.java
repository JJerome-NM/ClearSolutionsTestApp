package com.jjerome.clearsolutionstestapp.repository;

import com.jjerome.clearsolutionstestapp.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    List<User> findAllByBirthdayBetween(Date from, Date to);
}
