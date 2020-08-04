package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/master")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/getBrand")
    public String getBrand(Model model) {
        MastroLogUtils.info(BrandController.class, "Going to get all Brand :{}");
        try {
            List<Brand> brandList = brandService.getAllBrands().stream()
                    .filter(brandData -> (null != brandData))
                    .filter(brandData -> (1 != brandData.getBrandDeleteStatus()))
                    .sorted(Comparator.comparing(
                            Brand::getId).reversed())
                    .collect(Collectors.toList());
            model.addAttribute("brandForm", new BrandRequestModel());
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("brandTab", "brand");
            model.addAttribute("brandList", brandList);
            return "views/brandMaster";

        } catch (Exception e) {
            MastroLogUtils.error(this, "Error Occured while getting brand", e);
            throw e;
        }

    }

    @PostMapping("/saveBrand")
    public String saveBrand(@ModelAttribute("brandForm") @Valid BrandRequestModel brandRequestModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(BrandController.class, "Going to savebrand : {}");
        try {
            brandService.saveOrUpdateBrand(brandRequestModel);
            return "redirect:/master/getBrand";
        } catch (Exception e) {
            MastroLogUtils.error(BrandController.class, e.getMessage());
            throw e;
        }
    }

    @GetMapping("/getBrandForEdit")
    @ResponseBody
    public GenericResponse getBrandForEdit(Model model, HttpServletRequest request, @RequestParam("brandId") Long brandId) {
        MastroLogUtils.info(BrandController.class, "Going to edit Brand Details : {}");
        try {

            Brand brandDetails = brandService.getBrandId(brandId);
            return new GenericResponse(true, "get brand details")
                    .setProperty("brandId", brandDetails.getId())
                    .setProperty("brandName", brandDetails.getBrandName())
                    .setProperty("brandDescription", brandDetails.getBrandDescription());

        } catch (Exception e) {
            MastroLogUtils.error(this, "Error Occured while editing brand details :{}", e);
            throw e;

        }

    }

    @PostMapping("/deleteBrandDetails")
    @ResponseBody
    public GenericResponse deleteBrand(Model model, HttpServletRequest request, @RequestParam("brandId") Long brandId) {
        MastroLogUtils.info(BrandController.class, "Going to deletebrand");
        try {

            brandService.deleteBrandDetails(brandId);
            return new GenericResponse(true, "delete brand details");

        } catch (Exception e) {
            MastroLogUtils.error(this, "Error Occured while deleting brand details :{}", e);

            throw e;
        }

    }
}
