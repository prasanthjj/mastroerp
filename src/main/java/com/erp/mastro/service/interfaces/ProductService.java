package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.Party;
import com.erp.mastro.entities.Product;
import com.erp.mastro.entities.ProductUOM;
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

    Product saveOrUpdateProduct(ProductRequestModel productRequestModel) throws ModelNotFoundException, FileStoreException;

    Set<ProductUOM> saveOrUpdateProductUOM(ProductRequestModel productRequestModel, Product product);

    void deleteProduct(Long id);

    void saveOrUpdateProductPartys(Product product, Set<Party> parties);

    void enableOrDisableProduct(Long id);

    void productUploasS3(final File folder, String sproductId);

}
