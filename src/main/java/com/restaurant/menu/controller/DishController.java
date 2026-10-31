package com.restaurant.menu.controller;

import com.restaurant.menu.dto.*;
import com.restaurant.menu.service.DishService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.net.URI;

@RestController @RequestMapping("/api/dishes") public class DishController {
    private final DishService service;
    public DishController(DishService service) {
        this.service=service;
    }
    @GetMapping public List<DishResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}") public DishResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping public ResponseEntity<DishResponse> create(@Valid @RequestBody DishRequest request) {
        var result=service.create(request);
        return ResponseEntity.created(URI.create("/api/dishes/"+result.getId())).body(result);
    }

    @PutMapping("/{id}") public DishResponse update(@PathVariable Long id,@Valid @RequestBody DishRequest request) {
        return service.update(id,request);
    }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
