package com.restaurant.menu.service;

import com.restaurant.menu.dto.*;

import java.util.List;

public interface DishService {
    List<DishResponse> getAll();

    DishResponse getById(Long id);

    DishResponse create(DishRequest request);

    DishResponse update(Long id, DishRequest request);

    void delete(Long id);
}
