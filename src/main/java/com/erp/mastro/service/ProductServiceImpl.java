package com.erp.mastro.service;


import com.erp.mastro.entities.*;
import com.erp.mastro.model.request.ProductRequestModel;
import com.erp.mastro.repository.HSNRepository;
import com.erp.mastro.repository.ProductRepository;
import com.erp.mastro.repository.SubCategoryRepository;
import com.erp.mastro.repository.UOMRepository;
import com.erp.mastro.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private HSNRepository hsnRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private UOMRepository uomRepository;

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<Product>();
        productRepository.findAll().forEach(product -> productList.add(product));
        return productList;
    }

    public Set<Product> getSubCategoryProducts(SubCategory subCategory) {
        Set<Product> productSetSet = new HashSet<>();
        productSetSet = subCategory.getProductSet();
        return productSetSet;
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).get();
    }

    @Transactional(rollbackOn = {Exception.class})
    public void saveOrUpdateProduct(ProductRequestModel productRequestModel) {
        if (productRequestModel.getId() == null) {
            Product product = new Product();
            SubCategory subCategory = subCategoryRepository.findById(productRequestModel.getSubCategoryId()).get();
            product.setItemCode(productRequestModel.getItemCode());
            product.setProductName(subCategory.getSubCategoryName() + productRequestModel.getColour() + productRequestModel.getDimension() + productRequestModel.getBaseUOM());
            product.setDimension(productRequestModel.getDimension());
            product.setColour(productRequestModel.getColour());
            product.setWarranty(productRequestModel.getWarranty());
            product.setGuarantee(productRequestModel.getGuarantee());
            product.setPropertySize(productRequestModel.getPropertySize());
            product.setBaseUOM(productRequestModel.getBaseUOM());
            product.setBaseQuantity(productRequestModel.getBaseQuantity());
            Set<ProductUOM> productUOM = saveOrUpdateProductUOM(productRequestModel, product);
            product.setProductUOMSet(productUOM);
            HSN hsn = hsnRepository.findById(productRequestModel.getHsnId()).get();
            product.setHsn(hsn);
            product.setSubCategory(subCategory);
            subCategory.getProductSet().add(product);
            subCategoryRepository.save(subCategory);
        } else {
            Product product = getProductById(productRequestModel.getId());
            product.setItemCode(productRequestModel.getItemCode());
            product.setProductName(productRequestModel.getColour() + productRequestModel.getDimension());
            product.setDimension(productRequestModel.getDimension());
            product.setColour(productRequestModel.getColour());
            product.setWarranty(productRequestModel.getWarranty());
            product.setGuarantee(productRequestModel.getGuarantee());
            product.setPropertySize(productRequestModel.getPropertySize());
            product.setBaseUOM(productRequestModel.getBaseUOM());
            product.setBaseQuantity(productRequestModel.getBaseQuantity());
            HSN hsn = hsnRepository.findById(productRequestModel.getHsnId()).get();
            product.setHsn(hsn);
            productRepository.save(product);
        }
    }

    @Transactional(rollbackOn = {Exception.class})
    public Set<ProductUOM> saveOrUpdateProductUOM(ProductRequestModel productRequestModel, Product product) {
        Set<ProductUOM> productUOMSet = new HashSet<>();
        product.getProductUOMSet().clear();
        if (productRequestModel.getProductUOMModelList().isEmpty() == false) {
            productRequestModel.getProductUOMModelList().parallelStream()
                    .forEach(x -> {
                        ProductUOM productUOM = new ProductUOM();
                        productUOM.setTransactionType(x.getTransactionType());
                        productUOM.setConvertionFactor(x.getConvertionFactor());
                        productUOM.setUom(uomRepository.findById(x.getUomId()).get());
                        productUOM.setProduct(product);
                        productUOMSet.add(productUOM);
                    });

        }
        return productUOMSet;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void saveOrUpdateProductPartys(Product product, Set<Party> parties) {
        product.setParties(parties);
        productRepository.save(product);
    }

    @Transactional(rollbackOn = {Exception.class})
    public void deleteProductDetails(Long id) {

        Product product = getProductById(id);
        product.setProductDeleteStatus(1);
        productRepository.save(product);

    }
}
