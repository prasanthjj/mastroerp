package com.erp.mastro.service;

import com.erp.mastro.entities.*;
import com.erp.mastro.repository.ProductRepository;
import com.erp.mastro.repository.SubCategoryRepository;
import com.erp.mastro.service.interfaces.ProductService;
import com.erp.mastro.service.interfaces.SubcategoryService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    public Set<Product> addProducts() {

        Set<Product> productSet = new HashSet<>();
        Catalog catalog = new Catalog(3L, "A ","b");
        Category category= new Category(1L,"a1","a2","a3","a4",catalog);
        SubCategory subCategory=new SubCategory(1L,"a11","a22",category);
        HSN hsn = new HSN(3L,new Date(),"abcde","a2","H3","SH3","hname3","gstname2",new Date(),0.12,0.12,0.06,0.06);
        Set<ProductImages> productImages1=new HashSet<>();
        Set<ProductImages> productImages2=new HashSet<>();
        Set<ProductUOM> productUOMSet1=new HashSet<>();
        Set<ProductUOM> productUOMSet2=new HashSet<>();
        Stream<Product> stream = Stream.of(new Product(1L,"a11","a22","a33","a44","a55","a66","a77",subCategory,hsn,"a88","a99",productImages1,productUOMSet1),
        new Product(2L,"b11","b22","b33","b44","b55","b66","b77",subCategory,hsn,"b88","b99",productImages2,productUOMSet2));
        productSet=stream.collect(Collectors.toSet());
        return  productSet;

    }

    public Product addProduct() {

        Catalog catalog = new Catalog(3L, "A ","b");
        Category category= new Category(1L,"a1","a2","a3","a4",catalog);
        SubCategory subCategory=new SubCategory(1L,"a11","a22",category);
        HSN hsn = new HSN(3L,new Date(),"abcde","a2","H3","SH3","hname3","gstname2",new Date(),0.12,0.12,0.06,0.06);
        Set<ProductImages> productImages1=new HashSet<>();
        Set<ProductUOM> productUOMSet1=new HashSet<>();
        Product product=new Product(1L,"a11","a22","a33","a44","a55","a66","a77",subCategory,hsn,"a88","a99",productImages1,productUOMSet1);
        return product;

    }

    @Test
    public void testGetProductsSizeEqual() {

        when(productRepository.findAll()).thenReturn(addProducts());
        Assert.assertEquals(2,productService.getAllProducts().size());

    }

    @Test
    public void testGetProductsSizeNotEqual() {

        when(productRepository.findAll()).thenReturn(addProducts());
        Assert.assertNotEquals(1,productService.getAllProducts().size());

    }

    @Test
    public void testGetById() {

        when(productRepository.findById(1L)).thenReturn(Optional.of(addProduct()));
        Assert.assertEquals(addProduct().getId(),productService.getProductById(addProduct().getId()).getId());

    }

    @Test
    public void testSaveProduct()
    {
        Catalog catalog = new Catalog(3L, "A ","b");
        Category category= new Category(1L,"a1","a2","a3","a4",catalog);
        SubCategory subCategory=new SubCategory(1L,"a11","a22",category);
        HSN hsn = new HSN(3L,new Date(),"abcde","a2","H3","SH3","hname3","gstname2",new Date(),0.12,0.12,0.06,0.06);
        Set<ProductImages> productImages1=new HashSet<>();
        Set<ProductUOM> productUOMSet1=new HashSet<>();
        Product product=new Product(1L,"a11","a22","a33","a44","a55","a66","a77",subCategory,hsn,"a88","a99",productImages1,productUOMSet1);
        productService.saveOrUpdateProduct(product);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testDeleteProduct() {

        productService.deleteProduct(addProduct().getId());
        verify(productRepository,times(1)).deleteById(addProduct().getId());

    }

    @Test
    public void testProductValidationSucess() {

        when(productRepository.findById(addProduct().getId())).thenReturn(Optional.of(addProduct()));
        Assert.assertEquals("a11",productService.getProductById(addProduct().getId()).getProductName());
        Assert.assertEquals("a22",productService.getProductById(addProduct().getId()).getItemCode());
        Assert.assertEquals("a33",productService.getProductById(addProduct().getId()).getDimension());
        Assert.assertEquals("a44",productService.getProductById(addProduct().getId()).getColour());
        Assert.assertEquals("a55",productService.getProductById(addProduct().getId()).getGuarantee());
        Assert.assertEquals("a66",productService.getProductById(addProduct().getId()).getWarranty());
        Assert.assertEquals("a77",productService.getProductById(addProduct().getId()).getPropertySize());
        Assert.assertEquals("a88",productService.getProductById(addProduct().getId()).getBaseUOM());
        Assert.assertEquals("a99",productService.getProductById(addProduct().getId()).getBaseQuantity());
        Assert.assertEquals(addProduct().getSubCategory().getId(),productService.getProductById(addProduct().getId()).getSubCategory().getId());
        Assert.assertEquals(addProduct().getHsn().getId(),productService.getProductById(addProduct().getId()).getHsn().getId());
        Assert.assertEquals(addProduct().getProductImages().size(),productService.getProductById(addProduct().getId()).getProductImages().size());
        Assert.assertEquals(addProduct().getProductUOMSet().size(),productService.getProductById(addProduct().getId()).getProductUOMSet().size());
    }

    @Test
    public void testSubCategoryProductsSizeEqual() {

        Catalog catalog = new Catalog(3L, "A ","b");
        Category category= new Category(1L,"a1","a2","a3","a4",catalog);
        SubCategory subCategory=new SubCategory(1L,"a11","a22",category);
        Set<Product> productSet=new HashSet<>();
        productSet=addProducts();
        subCategory.setProductSet(productSet);
        Assert.assertEquals(subCategory.getProductSet().size(),productService.getSubCategoryProducts( subCategory).size());

    }

    @Test
    public void testSaveorUpdateProductParties()
    {

        Product product= addProduct();
        Set<Party> partySet=new HashSet<>();
        Party party= new Party(1L,"a1","a2","a3","a4","a5","a6","a7",new Date(),"a9","a10","a11","a12","a13");
        partySet.add(party);
        productService.saveOrUpdateProductPartys(product,partySet);
        verify(productRepository, times(1)).save(product);

    }
}
