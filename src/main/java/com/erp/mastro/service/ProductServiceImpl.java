package com.erp.mastro.service;

import com.erp.mastro.dao.CategoryRepository;
import com.erp.mastro.dao.ProductRepository;
import com.erp.mastro.entities.Catalog;
import com.erp.mastro.entities.Category;
import com.erp.mastro.entities.Product;
import com.erp.mastro.entities.SubCategory;
import com.erp.mastro.service.interfaces.CategoryService;
import com.erp.mastro.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts()
    {
        List<Product> productList = new ArrayList<Product>();
        productRepository.findAll().forEach(product ->productList.add(product));
        return productList;
    }

    public Set<Product> getSubCategoryProducts(SubCategory subCategory)
    {
        Set<Product> productSetSet = new HashSet<>();
        productSetSet=subCategory.getProductSet();
        return productSetSet;
    }

    public Product getProductById(Long id)
    {
        return productRepository.findById(id).get();
    }

    public void saveOrUpdateProduct(Product product)
    {
        productRepository.save(product);
    }

    public void deleteProduct(Long id)
    {
        productRepository.deleteById(id);
    }
}
