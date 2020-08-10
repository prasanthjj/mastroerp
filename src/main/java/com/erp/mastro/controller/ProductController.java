package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.config.UserDetailsServiceImpl;
import com.erp.mastro.constants.Constants;
import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.*;
import com.erp.mastro.exception.FileStoreException;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.ProductRequestModel;
import com.erp.mastro.repository.UOMRepository;
import com.erp.mastro.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Product controller include all product operations
 */
@Controller
@RequestMapping("/master")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private HSNService hsnService;

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private UOMRepository uomRepository;

    @Autowired
    private BrandService brandService;

    @Autowired
    private SubcategoryService subcategoryService;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    /**
     * Method to get all products
     *
     * @param model
     * @return all product list
     */
    @GetMapping("/getProduct")
    public String getProduct(Model model) {

        try {

            List<Product> productList = productService.getAllProducts().stream()
                    .filter(productData -> (null != productData))
                    .sorted(Comparator.comparing(
                            Product::getId).reversed())
                    .collect(Collectors.toList());
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("productTab", "product");
            model.addAttribute("productList", productList);

            return "views/productMaster";

        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * Method to load create product page
     *
     * @param model
     * @return create product page
     */
    @GetMapping("/getCreateProduct")
    public String getCreateProduct(Model model) {

        try {
            model.addAttribute("productForm", new ProductRequestModel());
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("productTab", "product");
            List<HSN> hsnList = hsnService.getAllHSN().stream()
                    .filter(hsnData -> (null != hsnData))
                    .filter(hsnData -> (1 != hsnData.getHsnDeleteStatus()))
                    .sorted(Comparator.comparing(
                            HSN::getId).reversed())
                    .collect(Collectors.toList());
            model.addAttribute("hsnList", hsnList);
            List<Catalog> catalogList = catalogService.getAllCatalogs();
            model.addAttribute("catalogList", catalogList);
            List<Brand> brandList = brandService.getAllBrands().stream()
                    .filter(brandData -> (null != brandData))
                    .filter(brandData -> (1 != brandData.getBrandDeleteStatus()))
                    .sorted(Comparator.comparing(
                            Brand::getId).reversed())
                    .collect(Collectors.toList());
            model.addAttribute("brandList", brandList);
            Iterable<Uom> uomSet = uomRepository.findAll();
            Set<Uom> uoms = new HashSet<>();
            for (Uom uom : uomSet) {
                uoms.add(uom);
            }
            model.addAttribute("uomList", uoms);
            return "views/createProduct";

        } catch (Exception e) {
            throw e;
        }

    }

    /**
     * save productimages
     *
     * @param file
     * @return response
     */
    @RequestMapping(value = "/productImageSave", method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse saveProductDocs(@RequestParam("file") MultipartFile file) {
        try {
            Map<String, byte[]> files = (Map<String, byte[]>) userDetailsServiceImpl.getDataMap().get(Constants.PRODUCT);
            if (files == null) {
                files = new HashMap<>(5);
            }

            files.put(file.getOriginalFilename(), file.getBytes());
            userDetailsServiceImpl.getDataMap().put(Constants.PRODUCT, files);

            return new GenericResponse(true, "profileimages has been saved successfully")
                    .setProperty("id", "");
        } catch (Exception e) {
            MastroLogUtils.error(this, "Error occurred while uploading product : {}",
                    e);
            return new GenericResponse(false, e.getMessage());
        }
    }

    /**
     * Method to save product
     *
     * @param productRequestModel
     * @param request
     * @param model
     * @return product list
     */
    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute("productForm") @Valid ProductRequestModel productRequestModel, HttpServletRequest request, Model model) {

        try {
            productService.saveOrUpdateProduct(productRequestModel);
            return "redirect:/master/getProduct";
        } catch (ModelNotFoundException e) {
            MastroLogUtils.error(this, "ProductModel empty", e);
            return "redirect:/master/getProduct";
        } catch (FileStoreException e) {
            MastroLogUtils.error(this, "File store issue", e);
            return "redirect:/master/getProduct";
        } catch (Exception e) {
            MastroLogUtils.error(AssetController.class, "Error occured while save product : {}", e);
            throw e;
        }
    }

    @PostMapping("/deleteProductDetails")
    @ResponseBody
    public GenericResponse deleteProductDetails(Model model, HttpServletRequest request, @RequestParam("productId") Long productId) {

        try {

            productService.deleteProductDetails(productId);
            return new GenericResponse(true, "delete product details");

        } catch (Exception e) {

            return new GenericResponse(false, e.getMessage());

        }

    }

    @RequestMapping(value = "/getProductEdit", method = RequestMethod.GET)
    public String getProductEdit(HttpServletRequest request, @RequestParam("productId") Long productId, Model model) {

        try {
            ProductRequestModel productRequestModel = new ProductRequestModel();
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("productTab", "product");
            model.addAttribute("productForm", productService.getProductById(productId));
            return "views/editProductMaster";

        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Method to get subcategory for product
     *
     * @param model
     * @param request
     * @param subcategoryId
     * @return subcategory
     */
    @GetMapping("/getSubcategoryDetails")
    @ResponseBody
    public GenericResponse getSubcategoryDetails(Model model, HttpServletRequest request, @RequestParam("subcategoryId") Long subcategoryId) {
        MastroLogUtils.info(BrandController.class, "Going to get subcategory Details : {}");
        try {

            SubCategory subCategory = subcategoryService.getSubCategoryById(subcategoryId);
            return new GenericResponse(true, "get brand details")
                    .setProperty("subCatName", subCategory.getSubCategoryName())
                    .setProperty("categoryName", subCategory.getCategory().getCategoryName());

        } catch (Exception e) {
            MastroLogUtils.error(this, "Error Occured while getting subcategory details :{}", e);
            throw e;

        }

    }

   /* @GetMapping("/getAllUOMDetails")
    @ResponseBody
    public GenericResponse getAllUoms(Model model, HttpServletRequest request) {
        MastroLogUtils.info(BrandController.class, "Going to get all uoms : {}");
        try {

            Iterable<Uom> uomSet =uomRepository.findAll();
            Set<Uom> uoms=new HashSet<>();
            for(Uom uom:uomSet)
            {
                uoms.add(uom);
            }
            Set<UOMModel> allUOM = new HashSet<>();
            for (Uom uomss: uomSet){
                UOMModel uomModel= new UOMModel();
                uomModel.setId(uomss.getId());
                uomModel.setUom(uomss.getUOM());
                allUOM.add(uomModel);
            }
            return new GenericResponse(true, "get UOM details")
                    .setProperty("fullUOM",allUOM);

        } catch (Exception e) {
            MastroLogUtils.error(this, "Error Occured while getting UOM details :{}", e);
            throw e;

        }

    }
*/

}
