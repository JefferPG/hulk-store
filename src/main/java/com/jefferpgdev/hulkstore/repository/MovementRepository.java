package com.jefferpgdev.hulkstore.repository;

import com.jefferpgdev.hulkstore.model.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Integer> {

    List<Movement> findAllByProduct(int product);

}
