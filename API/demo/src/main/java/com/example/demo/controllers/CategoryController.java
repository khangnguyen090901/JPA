package com.example.demo.controllers;

import java.util.List;
import com.example.demo.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.services.CategoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    CategoryService service;
    @Autowired
    CategoryRepository cateRepo;

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAlllCategories() {
        return new ResponseEntity<>(service.getAllCate(), HttpStatus.OK);
    }

    @PreAuthorize("HasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createNewCate(@RequestBody Category cate) {
        try {
            service.createCate(cate);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "created successful"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("HasRole('ADMIN')")
    @PutMapping("edit/{id}")
    public ResponseEntity<ApiResponse> editCate(@PathVariable Integer id, @RequestBody Category newCate) {
        try {
            if (!service.findById(id)) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category not found"),
                        HttpStatus.NOT_FOUND);
            } else {
                service.editCate(id, newCate);
                return new ResponseEntity<ApiResponse>(new ApiResponse(true, "update successful"), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("HasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteCate(@PathVariable Integer id) {
        try {
            cateRepo.deleteById(id);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "deleted"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
