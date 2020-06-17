package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.Product;
import com.erp.mastro.entities.SubCategory;

import java.util.List;
import java.util.Set;

public interface ProductService {

    List<Product> getAllProducts();

    Set<Product> getSubCategoryProducts(SubCategory subCategory);

    Product getProductById(Long id);

    void saveOrUpdateProduct(Product product);

    void deleteProduct(Long id);
}
