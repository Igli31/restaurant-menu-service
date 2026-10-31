package com.restaurant.menu.serviceimpl;

import com.restaurant.menu.dto.*;
import com.restaurant.menu.entity.*;
import com.restaurant.menu.repository.*;
import com.restaurant.menu.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.restaurant.menu.service.CategoryService;

@Service @Transactional(readOnly=true) public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categories;
    private final DishRepository dishes;
    private final ModelMapper mapper;
    public CategoryServiceImpl(CategoryRepository categories,DishRepository dishes,ModelMapper mapper) {
        this.categories=categories;
        this.dishes=dishes;
        this.mapper=mapper;
    }
    private Category find(Long id) {
        return categories.findById(id).orElseThrow(()->new ResourceNotFoundException("Category not found: "+id));
    }
    public List<CategoryResponse> getAll() {
        return categories.findAll().stream().map(c->mapper.map(c,CategoryResponse.class)).toList();
    }
    public CategoryResponse getById(Long id) {
        return mapper.map(find(id),CategoryResponse.class);
    }
    @Transactional public CategoryResponse create(CategoryRequest request) {
        return mapper.map(categories.save(mapper.map(request,Category.class)),CategoryResponse.class);
    }
    @Transactional public CategoryResponse update(Long id,CategoryRequest request) {
        var category=find(id);
        mapper.map(request,category);
        return mapper.map(categories.save(category),CategoryResponse.class);
    }
    @Transactional public void delete(Long id) {
        var category=find(id);
        if(dishes.existsByCategoryId(id))throw new IllegalArgumentException("Remove the category's dishes first.");
        categories.delete(category);
    }
}
