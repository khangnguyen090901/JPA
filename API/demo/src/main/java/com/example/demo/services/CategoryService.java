package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Category;
import com.example.demo.repository.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository repo;

    public List<Category> getAllCate() {
        return repo.findAll();
    }

    public Category createCate(Category cate) {
        return repo.save(cate);
    }

    public Category editCate(int id, Category newCate) {
        Optional<Category> oldCate = repo.findById(id);
        Category updateCate = oldCate.get();
        updateCate.setName(newCate.getName());
        updateCate.setDescription(newCate.getDescription());
        return repo.save(updateCate);
    }

    public boolean findById(Integer id) {
        return repo.findById(id).isPresent();
    }
}
