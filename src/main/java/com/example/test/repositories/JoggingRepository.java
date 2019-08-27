package com.example.test.repositories;

import com.example.test.entities.Jogging;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoggingRepository extends CrudRepository<Jogging, Long> {
}
