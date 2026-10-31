package com.restaurant.menu.repository;

import com.restaurant.menu.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish,Long> {
    boolean existsByCategoryId(Long categoryId);
}
