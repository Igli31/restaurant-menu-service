package com.restaurant.menu.serviceimpl;

import com.restaurant.menu.dto.*;
import com.restaurant.menu.entity.*;
import com.restaurant.menu.repository.*;
import com.restaurant.menu.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import com.restaurant.menu.service.DishService;

@Service @Transactional(readOnly=true) public class DishServiceImpl implements DishService {
    private final DishRepository dishes;
    private final CategoryRepository categories;
    private final ModelMapper mapper;
    public DishServiceImpl(DishRepository dishes,CategoryRepository categories,ModelMapper mapper) {
        this.dishes=dishes;
        this.categories=categories;
        this.mapper=mapper;
    }
    private Dish find(Long id) {
        return dishes.findById(id).orElseThrow(()->new ResourceNotFoundException("Dish not found: "+id));
    }
    private DishResponse response(Dish dish) {
        var dto=mapper.map(dish,DishResponse.class);
        dto.setCategoryId(dish.getCategory().getId());
        dto.setCategoryName(dish.getCategory().getName());
        return dto;
    }
    private void apply(DishRequest request,Dish dish) {
        mapper.map(request,dish);
        dish.setCategory(categories.findById(request.getCategoryId()).orElseThrow(()->new ResourceNotFoundException("Category not found: "+request.getCategoryId())));
    }
    public List<DishResponse> getAll() {
        return dishes.findAll().stream().map(this::response).toList();
    }
    public DishResponse getById(Long id) {
        return response(find(id));
    }
    @Transactional public DishResponse create(DishRequest request) {
        var dish=new Dish();
        apply(request,dish);
        return response(dishes.save(dish));
    }
    @Transactional public DishResponse update(Long id,DishRequest request) {
        var dish=find(id);
        apply(request,dish);
        return response(dishes.save(dish));
    }
    @Transactional public void delete(Long id) {
        dishes.delete(find(id));
    }
}
