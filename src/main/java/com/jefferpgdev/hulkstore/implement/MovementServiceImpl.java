package com.jefferpgdev.hulkstore.implement;

import com.jefferpgdev.hulkstore.exception.InvalidDataException;
import com.jefferpgdev.hulkstore.model.Movement;
import com.jefferpgdev.hulkstore.repository.MovementRepository;
import com.jefferpgdev.hulkstore.service.MovementService;
import com.jefferpgdev.hulkstore.utils.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovementServiceImpl implements MovementService {

    @Autowired private AppUtil appUtil;
    @Autowired private MovementRepository movementRepository;

    @Override
    public Iterable<Movement> getMovements() {
        return movementRepository.findAll();
    }

    @Override
    public List<Movement> getMovementsByProduct(Integer product) {
        appUtil.validateParameter(product, "product is required.");
        return movementRepository.findAllByProduct(product);
    }

    @Override
    public Movement getMovement(Integer id) {
        appUtil.validateParameter(id, "id is required.");
        Optional<Movement> optionalMovement = movementRepository.findById(id);
        return optionalMovement.orElseGet(Movement::new);
    }

    @Override
    public Movement saveMovement(Movement movement) {
        appUtil.validateParameter(movement.getCreatedat(), "date is required.");
        appUtil.validateParameter(movement.getUsername(), "userName is required.");
        appUtil.validateParameter(movement.getType(), "type (IN/OUT) is required.");
        appUtil.validateParameter(movement.getProduct(), "product is required.");
        appUtil.validateParameter(movement.getQuantity(), "quantity is required.");
        appUtil.validateParameter(movement.getPrice(), "price is required.");
        appUtil.validateParameter(movement.getStatus(), "status is required.");
        if (!movement.getType().name().equals("IN") && !movement.getType().name().equals("OUT"))
            throw new InvalidDataException("Movement type not valid must be IN/OUT");
        return movementRepository.save(movement);
    }

    @Override
    public Movement updateMovement(Movement movement) {
        appUtil.validateParameter(movement.getId(), "id is required.");
        appUtil.validateParameter(movement.getCreatedat(), "date is required.");
        appUtil.validateParameter(movement.getUsername(), "userName is required.");
        appUtil.validateParameter(movement.getType(), "type (IN/OUT) is required.");
        appUtil.validateParameter(movement.getProduct(), "product is required.");
        appUtil.validateParameter(movement.getQuantity(), "quantity is required.");
        appUtil.validateParameter(movement.getPrice(), "price is required.");
        appUtil.validateParameter(movement.getStatus(), "status is required.");
        if (!movement.getType().name().equals("IN") && !movement.getType().name().equals("OUT"))
            throw new InvalidDataException("Movement type not valid must be IN/OUT");
        return movementRepository.save(movement);
    }

}
