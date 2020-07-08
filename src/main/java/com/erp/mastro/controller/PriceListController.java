package com.erp.mastro.controller;

import com.erp.mastro.entities.PriceList;
import com.erp.mastro.model.request.PriceListRequestModel;
import com.erp.mastro.service.interfaces.PriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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


}
