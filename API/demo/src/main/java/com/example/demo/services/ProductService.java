package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DTO.ProductCategoryDTO;
import com.example.demo.models.Category;
import com.example.demo.models.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    ProductRepository repo;

    public Product createNewProduct(ProductCategoryDTO dto, Category cate) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setImage(dto.getImage());
        product.setPrice(dto.getPrice());
        product.setCategory(cate);
        return repo.save(product);
    }

    // find all products DTO
    private ProductCategoryDTO getProductDTO(Product product) {
        ProductCategoryDTO dto = new ProductCategoryDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setImage(product.getImage());
        dto.setPrice(product.getPrice());
        dto.setCategoryId(product.getCategory().getId());
        return dto;

    }

    public List<ProductCategoryDTO> getAllProduct() {
        List<Product> allProduct = repo.findAll();
        List<ProductCategoryDTO> dto = new ArrayList<>();
        for (Product product : allProduct) {
            dto.add(getProductDTO(product));
        }
        return dto;
    }

    public void updateProduct(ProductCategoryDTO dto, Integer id) throws Exception {
        Optional<Product> data = repo.findById(id);
        if (!data.isPresent()) {
            throw new Exception("Product not found");
        }
        Product product = data.get();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setImage(dto.getImage());
        product.setPrice(dto.getPrice());
        repo.save(product);

    }
}
