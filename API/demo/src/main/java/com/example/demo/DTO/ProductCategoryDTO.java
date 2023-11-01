package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryDTO {
    private int id;
    private String name;
    private String description;
    private String image;
    private int price;
    private int categoryId;
}
