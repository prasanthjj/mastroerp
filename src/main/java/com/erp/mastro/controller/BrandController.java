package com.erp.mastro.controller;

import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.Brand;
import com.erp.mastro.model.request.BrandRequestModel;
import com.erp.mastro.service.interfaces.BrandService;
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
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/getBrand")
    public String getBrand(Model model) {

        try {
            List<Brand> brandList = new ArrayList<>();
            for (Brand brand : brandService.getAllBrands()) {
                if (brand.getBrandDeleteStatus() != 1) {
                    brandList.add(brand);
                }
            }
            model.addAttribute("brandForm", new BrandRequestModel());
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("brandTab", "brand");
            model.addAttribute("brandList", brandList);
            return "views/brandMaster";

        } catch (Exception e) {
            throw e;
        }

    }

    @PostMapping("/saveBrand")
    public String saveBrand(@ModelAttribute("brandForm") @Valid BrandRequestModel brandRequestModel, HttpServletRequest request, Model model) {

        try {
            brandService.saveOrUpdateBrand(brandRequestModel);
            return "redirect:/master/getBrand";
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/getBrandForEdit")
    @ResponseBody
    public GenericResponse getBrandForEdit(Model model, HttpServletRequest request, @RequestParam("brandId") Long brandId) {

        try {

            Brand brandDetails = brandService.getBrandId(brandId);
            return new GenericResponse(true, "get brand details")
                    .setProperty("brandId", brandDetails.getId())
                    .setProperty("brandName", brandDetails.getBrandName())
                    .setProperty("brandDescription", brandDetails.getBrandDescription());

        } catch (Exception e) {

            return new GenericResponse(false, e.getMessage());

        }

    }

    @PostMapping("/deleteBrandDetails")
    @ResponseBody
    public GenericResponse deleteBrand(Model model, HttpServletRequest request, @RequestParam("brandId") Long brandId) {

        try {

            brandService.deleteBrandDetails(brandId);
            return new GenericResponse(true, "delete brand details");

        } catch (Exception e) {

            return new GenericResponse(false, e.getMessage());

        }

    }
}
