package com.erp.mastro.controller;

import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.PriceList;
import com.erp.mastro.model.request.PriceListRequestModel;
import com.erp.mastro.service.interfaces.PriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/master")
public class PriceListController {

    @Autowired
    private PriceListService priceListService;

    @GetMapping("/getPriceListMaster")
    public String getPriceListMaster(Model model) {

        try {
            List<PriceList> priceList = priceListService.getAllPriceList();
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("priceListTab", "priceList");
            model.addAttribute("priceList", priceList);
            model.addAttribute("priceListForm", new PriceListRequestModel());
            return "views/priceListMaster";

        } catch (Exception e) {
            throw e;
        }

    }

    @PostMapping("/savePriceList")
    public String savePriceList(@ModelAttribute("priceListForm") @Valid PriceListRequestModel priceListRequestModel, HttpServletRequest request, Model model) {

        try {
            priceListService.saveOrUpdatePriceList(priceListRequestModel);
            return "redirect:/master/getPriceListMaster";
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/deletePriceListDetails")
    @ResponseBody
    public GenericResponse deletePriceListDetails(Model model, HttpServletRequest request, @RequestParam("pricelistId") Long pricelistId) {

        try {

            priceListService.deletePriceListDetails(pricelistId);
            return new GenericResponse(true, "delete pricelist details");

        } catch (Exception e) {

            return new GenericResponse(false, e.getMessage());

        }

    }

}
