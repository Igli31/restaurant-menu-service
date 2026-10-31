package com.restaurant.menu.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Data public class CategoryResponse {

    private Long id;
    private String name;
}
