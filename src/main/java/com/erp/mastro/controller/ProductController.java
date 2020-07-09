package com.erp.mastro.controller;

import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.Product;
import com.erp.mastro.model.request.ProductRequestModel;
import com.erp.mastro.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/master")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getProduct")
    public String getProduct(Model model) {

        try {
            List<Product> productList = new ArrayList<>();
            for (Product product : productService.getAllProducts()) {
                if (product.getProductDeleteStatus() != 1) {
                    productList.add(product);
                }
            }
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("productTab", "product");
            model.addAttribute("productList", productList);
            return "views/productMaster";

        } catch (Exception e) {
            throw e;
        }

    }

    @GetMapping("/getCreateProduct")
    public String getCreateProduct(Model model) {

        try {
            model.addAttribute("productForm", new ProductRequestModel());
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("productTab", "product");
            return "views/createProductMaster";

        } catch (Exception e) {
            throw e;
        }

    }

    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute("productForm") @Valid ProductRequestModel productRequestModel, HttpServletRequest request, Model model) {

        try {
            productService.saveOrUpdateProduct(productRequestModel);
            return "redirect:/master/getProduct";
        } catch (Exception e) {
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


}
