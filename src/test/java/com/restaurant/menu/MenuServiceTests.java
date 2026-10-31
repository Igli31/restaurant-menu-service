package com.restaurant.menu;

import com.restaurant.menu.dto.*;
import com.restaurant.menu.service.*;
import com.restaurant.menu.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {"spring.datasource.url=jdbc:h2:mem:menutest;DB_CLOSE_DELAY=-1", "spring.jpa.hibernate.ddl-auto=create-drop"})
@Transactional
class MenuServiceTests {

    @Autowired
    CategoryService categories;

    @Autowired
    DishService dishes;

    @Test
    void createsAndUpdatesDishesWithoutChangingTheirIdentity() {
        var category = new CategoryRequest();
        category.setName("Mains");
        var savedCategory = categories.create(category);
        var request = new DishRequest();
        request.setName("Pizza");
        request.setDescription("Tomato");
        request.setPrice(new BigDecimal("12.50"));
        request.setAvailable(true);
        request.setCategoryId(savedCategory.getId());
        var saved = dishes.create(request);
        assertEquals(savedCategory.getId(), saved.getCategoryId());
        assertEquals("Mains", saved.getCategoryName());
        request.setName("Margherita");
        request.setAvailable(false);
        var updated = dishes.update(saved.getId(), request);
        assertEquals(saved.getId(), updated.getId());
        assertEquals("Margherita", updated.getName());
        assertFalse(updated.isAvailable());
        assertThrows(IllegalArgumentException.class,
                () -> categories.delete(savedCategory.getId()));
        dishes.delete(saved.getId());
        categories.delete(savedCategory.getId());
        assertThrows(ResourceNotFoundException.class, () -> dishes.getById(saved.getId()));
    }

    @Test
    void rejectsUnknownCategoriesWithoutSavingDish() {
        var request = new DishRequest();
        request.setName("Soup");
        request.setPrice(new BigDecimal("6.00"));
        request.setAvailable(true);
        request.setCategoryId(999999L);
        int before = dishes.getAll().size();
        assertThrows(ResourceNotFoundException.class, () -> dishes.create(request));
        assertEquals(before, dishes.getAll().size());
    }
}
