package com.jefferpgdev.hulkstore.controller;

import com.jefferpgdev.hulkstore.exception.OperationException;
import com.jefferpgdev.hulkstore.implement.MovementServiceImpl;
import com.jefferpgdev.hulkstore.implement.ProductServiceImpl;
import com.jefferpgdev.hulkstore.model.Movement;
import com.jefferpgdev.hulkstore.model.Product;
import com.jefferpgdev.hulkstore.utils.AppUtil;
import com.jefferpgdev.hulkstore.utils.TypeMov;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movement/")
public class MovementController {

    @Autowired private AppUtil appUtil;
    @Autowired private MovementServiceImpl movementServiceImpl;
    @Autowired private ProductServiceImpl productServiceImpl;

    @GetMapping
    public ResponseEntity<Iterable<Movement>> getProduct() {
        return ResponseEntity.ok(movementServiceImpl.getMovements());
    }

    @GetMapping("{id}")
    public ResponseEntity<Movement> getMovement(@PathVariable Integer id) {
        return ResponseEntity.ok(movementServiceImpl.getMovement(id));
    }

    @PostMapping
    public ResponseEntity<Movement> saveMovement(@RequestBody Movement movement) {
        Product product = productServiceImpl.getProduct(movement.getProduct());

        appUtil.validateParameter(movement.getType(), "type is required.");
        appUtil.validateParameter(movement.getQuantity(), "quantity is required.");

        if (movement.getType().equals(TypeMov.OUT) && movement.getQuantity() > product.getQuantity())
            throw new OperationException("The quantity to withdraw is greater than the existing quantity");

        Movement movementSaved = movementServiceImpl.saveMovement(movement);

        updateProductInventory(movementSaved.getProduct());

        return ResponseEntity.ok(movementSaved);
    }

    @PutMapping
    public ResponseEntity<Movement> updateMovement(@RequestBody Movement movement) {
        Movement currentMovement = movementServiceImpl.getMovement(movement.getId());
        Product product = productServiceImpl.getProduct(movement.getProduct());

        appUtil.validateParameter(movement.getType(), "type is required.");
        appUtil.validateParameter(movement.getQuantity(), "quantity is required.");

        if (movement.getType().equals(TypeMov.OUT) && movement.getQuantity() > product.getQuantity() + currentMovement.getQuantity())
            throw new OperationException("The quantity to withdraw is greater than the existing quantity");

        Movement movementUpdated = movementServiceImpl.updateMovement(movement);

        updateProductInventory(movementUpdated.getProduct());

        return ResponseEntity.ok(movementUpdated);
    }

    private void updateProductInventory(Integer idProduct) {
        List<Movement> movementList = movementServiceImpl.getMovementsByProduct(idProduct);

        int quantity = 0;
        double priceByMovement = 0.0;
        for(Movement movement : movementList) {
            if (movement.getType().equals(TypeMov.IN)) {
                quantity += movement.getQuantity();
                priceByMovement += appUtil.round2(movement.getQuantity() * movement.getPrice());
            } else {
                quantity -= movement.getQuantity();
                priceByMovement -= appUtil.round2(movement.getQuantity() * movement.getPrice());
            }
        }

        double price = 0.0;
        if (quantity > 0)
            price = priceByMovement/quantity;

        productServiceImpl.updateProductPrice(idProduct, quantity, price);
    }
}
