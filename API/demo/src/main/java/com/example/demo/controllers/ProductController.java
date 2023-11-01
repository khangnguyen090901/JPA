package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;
import com.example.demo.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DTO.ProductCategoryDTO;
import com.example.demo.common.ApiResponse;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.services.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductService service;

    @Autowired
    ProductRepository productRepo;

    @Autowired
    CategoryRepository cateRepo;

    @GetMapping("/all")
    public ResponseEntity<List<ProductCategoryDTO>> getAllProducts() {
        List<ProductCategoryDTO> products = service.getAllProduct();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PreAuthorize("HasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductCategoryDTO product) {
        try {
            Optional<Category> cate = cateRepo.findById(product.getCategoryId());
            if (!cate.isPresent()) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category not found"),
                        HttpStatus.BAD_REQUEST);
            }
            service.createNewProduct(product, cate.get());

            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "create new product successful"),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("HasRole('ADMIN')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable Integer id, @RequestBody ProductCategoryDTO dto)
            throws Exception {
        try {
            Optional<Category> cate = cateRepo.findById(dto.getCategoryId());
            if (!cate.isPresent()) {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category not found"),
                        HttpStatus.NOT_FOUND);
            }
            service.updateProduct(dto, id);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "update product successful"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("HasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Integer id) {
        try {
            productRepo.deleteById(id);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "deleted"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, e.getMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
