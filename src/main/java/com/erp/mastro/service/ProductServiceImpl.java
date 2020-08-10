package com.erp.mastro.service;

import com.erp.mastro.Store.MastroFileStore;
import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.config.UserDetailsServiceImpl;
import com.erp.mastro.constants.Constants;
import com.erp.mastro.entities.*;
import com.erp.mastro.exception.FileStoreException;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.ProductRequestModel;
import com.erp.mastro.repository.*;
import com.erp.mastro.service.interfaces.AssetService;
import com.erp.mastro.service.interfaces.ProductService;
import com.erp.mastro.service.interfaces.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static com.erp.mastro.Store.MastroFileStore.saveProductFile;
import static com.erp.mastro.Store.MastroFileStoreBase.getUserFolder;

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

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    S3Service s3Services;

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
    public Product saveOrUpdateProduct(ProductRequestModel productRequestModel) throws ModelNotFoundException, FileStoreException {
        Product product = new Product();
        if (productRequestModel == null) {
            throw new ModelNotFoundException("ProductRequestModel model is empty");
        } else {
            if (productRequestModel.getId() == null) {
                MastroLogUtils.info(ProductService.class, "Going to Add product  {}" + productRequestModel.toString());
                SubCategory subCategory = subCategoryRepository.findById(productRequestModel.getSubCategoryId()).get();
                product.setSubCategory(subCategory);
                product.setProductName(subCategory.getSubCategoryName() + productRequestModel.getColour() + productRequestModel.getDimension() + productRequestModel.getBaseUOM());
                product.setDimension(productRequestModel.getDimension());
                product.setColour(productRequestModel.getColour());
                product.setWarranty(productRequestModel.getWarranty());
                product.setGuarantee(productRequestModel.getGuarantee());
                product.setPropertySize(productRequestModel.getPropertySize());
                product.setUom(uomRepository.findById(productRequestModel.getBaseUOM()).get());
                product.setBaseQuantity(productRequestModel.getBaseQuantity());
                product.setBasePrice(productRequestModel.getBasePrice());
                product.setInspectionType(productRequestModel.getInspectionType());
                product.setLoadingCharge(productRequestModel.getLoadingCharge());

                Set<ProductUOM> productUOM = saveOrUpdateProductUOM(productRequestModel, product);
                product.setProductUOMSet(productUOM);
                HSN hsn = hsnRepository.findById(productRequestModel.getHsnId()).get();
                product.setHsn(hsn);
                Brand brand = brandRepository.findById(productRequestModel.getBrandId()).get();
                product.setBrand(brand);
                Map<String, byte[]> productDocs = (Map<String, byte[]>) userDetailsServiceImpl.getDataMap().get(Constants.PRODUCT);
                setProductDocs(productDocs, product);

                product = productRepository.save(product);
                if (product.getId() != null) {
                    saveProductFilesToFileDB(product.getId(), productDocs);
                    String sproductId = String.valueOf(product.getId());
                    final File folder = new File(getUserFolder() + "/" + sproductId + "/productPic/");
                    productUploasS3(folder, sproductId);
                }

            } else {
                product = getProductById(productRequestModel.getId());
                product.setProductName(productRequestModel.getColour() + productRequestModel.getDimension());
                product.setDimension(productRequestModel.getDimension());
                product.setColour(productRequestModel.getColour());
                product.setWarranty(productRequestModel.getWarranty());
                product.setGuarantee(productRequestModel.getGuarantee());
                product.setPropertySize(productRequestModel.getPropertySize());
                product.setBaseQuantity(productRequestModel.getBaseQuantity());
                HSN hsn = hsnRepository.findById(productRequestModel.getHsnId()).get();
                product.setHsn(hsn);
                productRepository.save(product);

            }
        }
        return product;
    }


    @Transactional(rollbackOn = {Exception.class})
    public Set<ProductUOM> saveOrUpdateProductUOM(ProductRequestModel productRequestModel, Product product) {

        MastroLogUtils.info(AssetService.class, "Going to save productuoms   {}" + productRequestModel.toString());
        Set<ProductUOM> productUOMSet = new HashSet<>();

        if (productRequestModel.getProductUOMModelList().isEmpty() == false) {

            productRequestModel.getProductUOMModelList().parallelStream()
                    .forEach(x -> {
                        ProductUOM productUOM;
                        if (!containsInList(x.getId(), product.getProductUOMSet().stream().filter(productdata -> (null != productdata)).map(y -> y.getId()).collect(Collectors.toList()))) {
                            productUOM = new ProductUOM();
                            productUOM.setTransactionType(x.getTransactionType());
                            productUOM.setConvertionFactor(x.getConvertionFactor());
                            if (x.getUomId() != null) {
                                productUOM.setUom(uomRepository.findById(x.getUomId()).get());
                            }
                            productUOMSet.add(productUOM);
                        } else {

                        }
                    });
        }

        removeBlankProductUoms(productUOMSet);
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

    private boolean containsInList(Long id, Collection<Long> ids) {
        return id != null
                && ids.stream().anyMatch(x -> x.equals(id));
    }

    private void removeBlankProductUoms(Set<ProductUOM> productUOMS) {
        productUOMS.removeIf(x -> x.getTransactionType() == null);
    }

    private void setProductDocs(Map<String, byte[]> productDocs, Product product) {
        if (productDocs != null && !productDocs.isEmpty()) {
            Set<ProductImages> newProductImages = new HashSet<>();
            productDocs.forEach((x, y) -> {
                if (!containsInList(x,
                        product.getProductImages().stream().map(z -> z.getFileName()).collect(Collectors.toList()))) {
                    ProductImages productImages = new ProductImages();
                    productImages.setFileName(x);
                    productImages.setProduct(product);
                    newProductImages.add(productImages);
                }
            });
            product.getProductImages().addAll(newProductImages);
        }
    }

    private boolean containsInList(String name, Collection<String> names) {
        return name != null
                && !name.isEmpty()
                && names.stream().anyMatch(x -> x.equals(name));
    }

    private void saveProductFilesToFileDB(Long productId, Map<String, byte[]> productDocs) throws FileStoreException {
        if (productDocs != null && !productDocs.isEmpty()) {
            saveProductFile(productId.toString(), productDocs, MastroFileStore.FileType.productImage);

        }
    }

    public void productUploasS3(final File folder, String sproductId) {

        for (final File fileEntry : folder.listFiles()) {
            try {
                String uploadFilePath = getUserFolder() + "/" + sproductId + "/productPic/" + fileEntry.getName();
                String ftype = "productImg";
                s3Services.uploadFile(fileEntry.getAbsoluteFile().getName(), uploadFilePath, sproductId, ftype);
            } catch (FileStoreException e) {
                e.printStackTrace();
            }
        }
    }
}
