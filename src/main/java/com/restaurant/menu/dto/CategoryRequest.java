package com.restaurant.menu.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Data
public class CategoryRequest {

    @NotBlank
    @Size(max = 100)
    private String name;
}
