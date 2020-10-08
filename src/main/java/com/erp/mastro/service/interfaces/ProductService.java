package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.Product;
import com.erp.mastro.entities.ProductPartyRateRelation;
import com.erp.mastro.entities.SubCategory;
import com.erp.mastro.exception.FileStoreException;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.ProductRequestModel;

import java.io.File;
import java.util.List;
import java.util.Set;

public interface ProductService {

    List<Product> getAllProducts();

    Set<Product> getSubCategoryProducts(SubCategory subCategory);

    Product getProductById(Long id);

    Product saveOrUpdateProduct(ProductRequestModel productRequestModel,String value) throws ModelNotFoundException, FileStoreException;

    void deleteProduct(Long id);

    void enableOrDisableProduct(Long id);

    void productUploasS3(final File folder, String sproductId);

    void deleteProductImage(Long id, String fileName);

    void addPartyToProduct(Long productId, Long partyId);

    void saveOrUpdateItemParty(String[] productPartyRateIds, String[] rates, String[] discounts, String[] creditDays, String[] allowedPriceUpper, String[] allowedDevLower);

    List<ProductPartyRateRelation> getAllProductPartyRateRelation();

    void saveOrUpdatePartyItems(String[] productPartyRateIds, String[] rates, String[] remarks);
}
