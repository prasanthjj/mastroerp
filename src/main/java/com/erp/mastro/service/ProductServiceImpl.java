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
import com.erp.mastro.service.interfaces.PartyService;
import com.erp.mastro.service.interfaces.PriceListService;
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

/**
 * Product service include product related methods
 */
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
    private PartyService partyService;

    @Autowired
    private PriceListService priceListService;

    @Autowired
    private PartyPriceListRepository partyPriceListRepository;

    @Autowired
    private ProductPartyRateRelationRepository productPartyRateRelationRepository;

    @Autowired
    PartyRepository partyRepository;

    @Autowired
    S3Service s3Services;

    /**
     * Method to get all products
     *
     * @return productlist
     */
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<Product>();
        productRepository.findAll().forEach(product -> productList.add(product));
        return productList;
    }

    /**
     * Method to get sucategorys products
     *
     * @param subCategory
     * @return the productset
     */
    public Set<Product> getSubCategoryProducts(SubCategory subCategory) {
        Set<Product> productSetSet = new HashSet<>();
        productSetSet = subCategory.getProductSet();
        return productSetSet;
    }

    /**
     * Method to get a product by id
     *
     * @param id
     * @return the product
     */
    public Product getProductById(Long id) {
        Product product = new Product();
        if (id != null) {
            MastroLogUtils.info(ProductService.class, "Going to getProductBy Id : {}" + id);
            product = productRepository.findById(id).get();
        }
        return product;
    }

    /**
     * Method to save or update product
     *
     * @param productRequestModel
     * @return product
     * @throws ModelNotFoundException
     * @throws FileStoreException
     */
    @Transactional(rollbackOn = {Exception.class})
    public Product saveOrUpdateProduct(ProductRequestModel productRequestModel,String value) throws ModelNotFoundException, FileStoreException {
        Product product = new Product();
        if (productRequestModel == null) {
            throw new ModelNotFoundException("ProductRequestModel model is empty");
        } else {
            if (productRequestModel.getId() == null) {
                MastroLogUtils.info(ProductService.class, "Going to Add product  {}" + productRequestModel.toString());
                SubCategory subCategory = subCategoryRepository.findById(productRequestModel.getSubCategoryId()).get();
                product.setSubCategory(subCategory);
                product.setProductName(subCategory.getSubCategoryName() + " " + productRequestModel.getColour() + " " + productRequestModel.getDimension());
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
                product.setToleranceType(productRequestModel.getToleranceType());
                product.setEnabled(true);
                product.setToleranceType(value);
                if (product.getToleranceType().equals(Constants.FLAT_AMOUNT)) {
                    product.setAmount(productRequestModel.getAmount());
                } else {
                    product.setAmount(productRequestModel.getPercentageAmount());
                }

                Set<ProductUOM> productUOM = saveOrUpdateProductUOM(productRequestModel, product);
                product.setProductUOMSet(productUOM);
                HSN hsn = hsnRepository.findById(productRequestModel.getHsnId()).get();
                product.setHsn(hsn);
                Brand brand = brandRepository.findById(productRequestModel.getBrandId()).get();
                product.setBrand(brand);
                Map<String, byte[]> productDocs = (Map<String, byte[]>) userDetailsServiceImpl.getDataMap().get(Constants.PRODUCT);
                setProductDocs(productDocs, product);
                product.setProductCode("P-" + product.getProductName());
                product = productRepository.save(product);
                if ((product.getId() != null) && (productDocs != null)) {
                    saveProductFilesToFileDB(product.getId(), productDocs);
                    String sproductId = String.valueOf(product.getId());
                    final File folder = new File(getUserFolder() + "/" + sproductId + "/productPic/");
                    productUploasS3(folder, sproductId);
                    userDetailsServiceImpl.getDataMap().clear();
                }

            } else {
                MastroLogUtils.info(ProductService.class, "Going to edit product  {}" + productRequestModel.toString());
                product = getProductById(productRequestModel.getId());
                SubCategory subCategory = subCategoryRepository.findById(productRequestModel.getSubCategoryId()).get();
                product.setSubCategory(subCategory);
                product.setProductName(subCategory.getSubCategoryName() + " " + productRequestModel.getColour() + " " + productRequestModel.getDimension());
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
                product.setToleranceType(value);
               // product.setAmount(productRequestModel.getAmount());
                if (product.getToleranceType().equals(Constants.FLAT_AMOUNT)) {
                    product.setAmount(productRequestModel.getAmount());
                } else {
                    product.setAmount(productRequestModel.getPercentageAmount());
                }
                Set<ProductUOM> productUOM = saveOrUpdateProductUOM(productRequestModel, product);
                product.setProductUOMSet(productUOM);
                HSN hsn = hsnRepository.findById(productRequestModel.getHsnId()).get();
                product.setHsn(hsn);
                Brand brand = brandRepository.findById(productRequestModel.getBrandId()).get();
                product.setBrand(brand);
                Map<String, byte[]> productDocs = (Map<String, byte[]>) userDetailsServiceImpl.getDataMap().get(Constants.PRODUCT);
                setProductDocs(productDocs, product);
                product.setProductCode("P-" + product.getProductName());
                product = productRepository.save(product);
                if ((product.getId() != null) && (productDocs != null)) {
                    saveProductFilesToFileDB(product.getId(), productDocs);
                    String sproductId = String.valueOf(product.getId());
                    final File folder = new File(getUserFolder() + "/" + sproductId + "/productPic/");
                    productUploasS3(folder, sproductId);
                    userDetailsServiceImpl.getDataMap().clear();
                }

            }
        }
        return product;
    }

    /**
     * Method to save or update productuoms
     *
     * @param productRequestModel
     * @param product
     * @return set of productuoms
     */

    private Set<ProductUOM> saveOrUpdateProductUOM(ProductRequestModel productRequestModel, Product product) {

        MastroLogUtils.info(ProductService.class, "Going to save productuoms   {}" + productRequestModel.toString());
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
                            productUOM = product.getProductUOMSet().stream().filter(productdata -> (null != productdata)).filter(z -> z.getId().equals(x.getId())).findFirst().get();
                            productUOM.setTransactionType(x.getTransactionType());
                            productUOM.setConvertionFactor(x.getConvertionFactor());
                            if (x.getUomId() != null) {
                                productUOM.setUom(uomRepository.findById(x.getUomId()).get());
                            }
                            productUOMSet.add(productUOM);
                        }
                    });
        }

        removeBlankProductUoms(productUOMSet);
        return productUOMSet;

    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

   /* public void saveOrUpdateProductPartys(Product product, Set<Party> parties) {
        product.setParties(parties);
        productRepository.save(product);
    }*/

    /**
     * Method to enable or disable product
     *
     * @param id
     */
    @Transactional(rollbackOn = {Exception.class})
    public void enableOrDisableProduct(Long id) {

        Product product = getProductById(id);
        if (product.isEnabled()) {
            product.setEnabled(false);
        } else {
            product.setEnabled(true);
        }
        productRepository.save(product);

    }

    private boolean containsInList(Long id, Collection<Long> ids) {
        return id != null
                && ids.stream().anyMatch(x -> x.equals(id));
    }

    private void removeBlankProductUoms(Set<ProductUOM> productUOMS) {
        productUOMS.removeIf(x -> x.getTransactionType() == null);
    }

    /**
     * Method to set productimages
     *
     * @param productDocs
     * @param product
     */
    private void setProductDocs(Map<String, byte[]> productDocs, Product product) {
        if (productDocs != null && !productDocs.isEmpty()) {
            MastroLogUtils.info(ProductService.class, "Going to save productImages   {}" + productDocs);
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

    /**
     * Method to save images in folder for s3
     *
     * @param productId
     * @param productDocs
     * @throws FileStoreException
     */
    private void saveProductFilesToFileDB(Long productId, Map<String, byte[]> productDocs) throws FileStoreException {
        if (productDocs != null && !productDocs.isEmpty()) {
            MastroLogUtils.info(ProductService.class, "Going to save productImages in folder   {}" + productDocs);
            saveProductFile(productId.toString(), productDocs, MastroFileStore.FileType.productImage);

        }
    }

    /**
     * Method to save in s3
     *
     * @param folder
     * @param sproductId
     */
    public void productUploasS3(final File folder, String sproductId) {
        for (final File fileEntry : folder.listFiles()) {
            try {
                MastroLogUtils.info(ProductService.class, "Going to save productImages in s3   {}" + sproductId);
                String uploadFilePath = getUserFolder() + "/" + sproductId + "/productPic/" + fileEntry.getName();
                String ftype = "productImg";
                s3Services.uploadFile(fileEntry.getAbsoluteFile().getName(), uploadFilePath, sproductId, ftype);
            } catch (FileStoreException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Remove product image
     *
     * @param id
     * @param fileName
     */
    public void deleteProductImage(Long id, String fileName) {

        Product product = getProductById(id);
        if (product.getProductImages() != null) {
            MastroLogUtils.info(ProductService.class, "Going to remove productImages in s3   {}");
            Set<ProductImages> productImages = getProductById(id).getProductImages();
            Iterator<ProductImages> productImageSet = productImages.iterator();
            for (Iterator<ProductImages> images = productImageSet; images.hasNext(); ) {
                ProductImages productImage = images.next();
                if (productImage != null) {
                    if (productImage.getFileName().equals(fileName)) {
                        productImageSet.remove();
                        s3Services.deleteProductImage(id, fileName);
                    }
                }
            }

        }
        productRepository.save(product);

    }

    /**
     * Method to add party to product
     *
     * @param productId
     * @param partyId
     */
    @Transactional(rollbackOn = {Exception.class})
    public void addPartyToProduct(Long productId, Long partyId) {

        if (productId != null && partyId != null) {
            MastroLogUtils.info(ProductService.class, "Going to add party to party   {}");
            Set<ProductPartyRateRelation> productPartyRateRelationsSet = getAllProductPartyRateRelation().stream()
                    .filter(relationData -> (null != relationData))
                    .filter(relationData -> (productId.equals(relationData.getProduct().getId())))
                    .filter(relationData -> (partyId.equals(relationData.getParty().getId())))
                    .collect(Collectors.toSet());
            if (productPartyRateRelationsSet.size() == 0) {

                Party party = partyService.getPartyById(partyId);
                Product product = getProductById(productId);
                ProductPartyRateRelation productPartyRateRelation = new ProductPartyRateRelation();
                productPartyRateRelation.setParty(party);
                productPartyRateRelation.setProduct(product);
                productPartyRateRelationRepository.save(productPartyRateRelation);
                Set<ProductPartyRateRelation> productPartyRateRelationSet = party.getProductPartyRateRelations();
                productPartyRateRelationSet.add(productPartyRateRelation);
                party.setProductPartyRateRelations(productPartyRateRelationSet);
                partyRepository.save(party);
            }

        }

    }

    /**
     * method to save the itemparty rate relation data
     *
     * @param productPartyRateIds
     * @param rates
     * @param discounts
     * @param creditDays
     * @param allowedPriceUpper
     * @param allowedDevLower
     */
    @Transactional(rollbackOn = {Exception.class})
    public void saveOrUpdateItemParty(String[] productPartyRateIds, String[] rates, String[] discounts, String[] creditDays, String[] allowedPriceUpper, String[] allowedDevLower) {

        MastroLogUtils.info(ProductService.class, "Going to save product party relation details {}" + productPartyRateIds);

        for (int i = 0; i < productPartyRateIds.length; i++) {
            PartyPriceList partyPriceList;
            ProductPartyRateRelation productPartyRateRelation = productPartyRateRelationRepository.findById(Long.parseLong(productPartyRateIds[i])).get();
            if (productPartyRateRelation.getPartyPriceList() == null) {
                partyPriceList = new PartyPriceList();
                Party party = productPartyRateRelation.getParty();
                partyPriceList.setCreditDays(Integer.parseInt(creditDays[i]));
                partyPriceList.setRate(Double.parseDouble(rates[i]));
                if (party.getPartyType().equals("Supplier")) {
                    partyPriceList.setDiscount(Double.parseDouble(discounts[i]));
                    partyPriceList.setAllowedPriceDevPerUpper(Double.parseDouble(allowedPriceUpper[i]));
                    partyPriceList.setAllowedPriceDevPerLower(Double.parseDouble(allowedDevLower[i]));
                } else {
                    Set<PriceList> priceListSet = priceListService.getAllPriceList().stream()
                            .filter(priceListData -> (null != priceListData))
                            .filter(priceListData -> (1 != priceListData.getPricelistDeleteStatus()))
                            .collect(Collectors.toSet());
                    for (PriceList priceList : priceListSet) {
                        if (party.getCategoryType().equals(priceList.getCategoryType())) {
                            partyPriceList.setDiscount(priceList.getDiscountPercentage());
                            partyPriceList.setAllowedPriceDevPerUpper(priceList.getAllowedPriceDevPerUpper());
                            partyPriceList.setAllowedPriceDevPerLower(priceList.getAllowedPriceDevPerLower());
                        }
                    }
                }
            } else {
                MastroLogUtils.info(ProductService.class, "Going to edit product party relation details {}" + productPartyRateIds);
                partyPriceList = productPartyRateRelation.getPartyPriceList();
                Party party = productPartyRateRelation.getParty();
                partyPriceList.setCreditDays(Integer.parseInt(creditDays[i]));
                partyPriceList.setRate(Double.parseDouble(rates[i]));
                if (party.getPartyType().equals("Supplier")) {
                    partyPriceList.setDiscount(Double.parseDouble(discounts[i]));
                    partyPriceList.setAllowedPriceDevPerUpper(Double.parseDouble(allowedPriceUpper[i]));
                    partyPriceList.setAllowedPriceDevPerLower(Double.parseDouble(allowedDevLower[i]));
                } else {
                    Set<PriceList> priceListSet = priceListService.getAllPriceList().stream()
                            .filter(priceListData -> (null != priceListData))
                            .filter(priceListData -> (1 != priceListData.getPricelistDeleteStatus()))
                            .collect(Collectors.toSet());
                    for (PriceList priceList : priceListSet) {
                        if (party.getCategoryType().equals(priceList.getCategoryType())) {
                            partyPriceList.setDiscount(priceList.getDiscountPercentage());
                            partyPriceList.setAllowedPriceDevPerUpper(priceList.getAllowedPriceDevPerUpper());
                            partyPriceList.setAllowedPriceDevPerLower(priceList.getAllowedPriceDevPerLower());
                        }
                    }
                }
            }
            productPartyRateRelation.setPartyPriceList(partyPriceList);
            productPartyRateRelationRepository.save(productPartyRateRelation);
        }

    }

    /**
     * get all productpartyrate relations
     *
     * @return the result set
     */
    public List<ProductPartyRateRelation> getAllProductPartyRateRelation() {
        List<ProductPartyRateRelation> productPartyRateRelations = new ArrayList<ProductPartyRateRelation>();
        productPartyRateRelationRepository.findAll().forEach(productPartyRateRelation1 -> productPartyRateRelations.add(productPartyRateRelation1));
        return productPartyRateRelations;
    }

    @Transactional(rollbackOn = {Exception.class})
    public void saveOrUpdatePartyItems(String[] productPartyRateIds, String[] rates, String[] remarks) {

        MastroLogUtils.info(ProductService.class, "Going to save party item relation details {}" + productPartyRateIds);

        for (int i = 0; i < productPartyRateIds.length; i++) {
            PartyPriceList partyPriceList;
            ProductPartyRateRelation productPartyRateRelation = productPartyRateRelationRepository.findById(Long.parseLong(productPartyRateIds[i])).get();
            if (productPartyRateRelation.getPartyPriceList() == null) {
                partyPriceList = new PartyPriceList();
                Party party = productPartyRateRelation.getParty();
                partyPriceList.setCreditDays(0);
                partyPriceList.setRate(Double.parseDouble(rates[i]));
                partyPriceList.setRemarks(remarks[i]);
                if (party.getPartyType().equals("Supplier")) {
                    partyPriceList.setDiscount(Double.parseDouble("0"));
                    partyPriceList.setAllowedPriceDevPerUpper(Double.parseDouble("0"));
                    partyPriceList.setAllowedPriceDevPerLower(Double.parseDouble("0"));
                } else {
                    Set<PriceList> priceListSet = priceListService.getAllPriceList().stream()
                            .filter(priceListData -> (null != priceListData))
                            .filter(priceListData -> (1 != priceListData.getPricelistDeleteStatus()))
                            .collect(Collectors.toSet());
                    for (PriceList priceList : priceListSet) {
                        if (party.getCategoryType().equals(priceList.getCategoryType())) {
                            partyPriceList.setDiscount(priceList.getDiscountPercentage());
                            partyPriceList.setAllowedPriceDevPerUpper(priceList.getAllowedPriceDevPerUpper());
                            partyPriceList.setAllowedPriceDevPerLower(priceList.getAllowedPriceDevPerLower());
                        }
                    }
                }
            } else {
                MastroLogUtils.info(ProductService.class, "Going to edit product party relation details {}" + productPartyRateIds);
                partyPriceList = productPartyRateRelation.getPartyPriceList();
                Party party = productPartyRateRelation.getParty();
                partyPriceList.setCreditDays(partyPriceList.getCreditDays());
                partyPriceList.setRate(Double.parseDouble(rates[i]));
                partyPriceList.setRemarks(remarks[i]);
                if (party.getPartyType().equals("Supplier")) {
                    partyPriceList.setDiscount(partyPriceList.getDiscount());
                    partyPriceList.setAllowedPriceDevPerUpper(partyPriceList.getAllowedPriceDevPerUpper());
                    partyPriceList.setAllowedPriceDevPerLower(partyPriceList.getAllowedPriceDevPerLower());
                } else {
                    Set<PriceList> priceListSet = priceListService.getAllPriceList().stream()
                            .filter(priceListData -> (null != priceListData))
                            .filter(priceListData -> (1 != priceListData.getPricelistDeleteStatus()))
                            .collect(Collectors.toSet());
                    for (PriceList priceList : priceListSet) {
                        if (party.getCategoryType().equals(priceList.getCategoryType())) {
                            partyPriceList.setDiscount(priceList.getDiscountPercentage());
                            partyPriceList.setAllowedPriceDevPerUpper(priceList.getAllowedPriceDevPerUpper());
                            partyPriceList.setAllowedPriceDevPerLower(priceList.getAllowedPriceDevPerLower());
                        }
                    }
                }
            }
            productPartyRateRelation.setPartyPriceList(partyPriceList);
            productPartyRateRelationRepository.save(productPartyRateRelation);
        }

    }

}
