package com.restaurant.menu.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Data public class DishResponse {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean available;
    private Long categoryId;
    private String categoryName;
}
