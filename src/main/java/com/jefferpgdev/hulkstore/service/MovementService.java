package com.jefferpgdev.hulkstore.service;

import com.jefferpgdev.hulkstore.model.Movement;

public interface MovementService {

    Iterable<Movement> getMovements();

    Iterable<Movement> getMovementsByProduct(Integer product);

    Movement getMovement(Integer id);

    Movement saveMovement(Movement product);

    Movement updateMovement(Movement product);

}
