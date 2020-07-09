package com.erp.mastro.controller;

import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.Catalog;
import com.erp.mastro.model.request.CatalogRequestModel;
import com.erp.mastro.service.interfaces.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/master")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @GetMapping("/getCatalog")
    public String getCatalog(Model model) {

        try {
            List<Catalog> catalogList = new ArrayList<>();
            for (Catalog catalog : catalogService.getAllCatalogs()) {
                if (catalog.getCatalogDeleteStatus() != 1) {
                    catalogList.add(catalog);
                }
            }
            model.addAttribute("catalogForm", new CatalogRequestModel());
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("catalogTab", "catalog");
            model.addAttribute("catalogList", catalogList);
            return "views/catalogList";

        } catch (Exception e) {
            throw e;
        }

    }

    @PostMapping("/deleteCatalogDetails")
    @ResponseBody
    public GenericResponse deleteCatalog(Model model, HttpServletRequest request, @RequestParam("catalogId") Long catalogId) {

        try {

            catalogService.deleteCatalogDetails(catalogId);
            return new GenericResponse(true, "delete catalog details");

        } catch (Exception e) {

            return new GenericResponse(false, e.getMessage());

        }

    }
}
