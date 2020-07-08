package com.erp.mastro.controller;

import com.erp.mastro.entities.Catalog;
import com.erp.mastro.model.request.CatalogRequestModel;
import com.erp.mastro.service.interfaces.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/master")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @GetMapping("/getCatalog")
    public String getCatalog(Model model) {

        try {
            List<Catalog> catalogList = catalogService.getAllCatalogs();
            model.addAttribute("catalogForm", new CatalogRequestModel());
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("catalogTab", "catalog");
            model.addAttribute("catalogList", catalogList);
            return "views/catalogList";

        } catch (Exception e) {
            throw e;
        }

    }
}
