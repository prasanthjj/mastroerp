package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.StockRequestModel;
import com.erp.mastro.service.interfaces.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/inventory")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/getStock")
    public String getStock(Model model) {
        MastroLogUtils.info(StockController.class, "Going to get all stocks : {}");
        try {
            model.addAttribute("inventoryModule", "inventoryModule");
            model.addAttribute("stockTab", "stock");
            return "views/stockDetails";

        } catch (Exception e) {
            MastroLogUtils.error(StockController.class, "Error occured while getting all stocks : { }", e);
            throw e;

        }
    }

    @GetMapping("/getCreateStock")
    public String getCreateStock(Model model) {
        MastroLogUtils.info(StockController.class, "Going to get stock :{}");
        try {
            model.addAttribute("stockForm", new StockRequestModel());
            model.addAttribute("inventoryModule", "inventoryModule");
            model.addAttribute("stockTab", "stock");
            return "views/createStockDetails";
        } catch (Exception e) {
            MastroLogUtils.error(StockController.class, "Error occured while adding stock : {}", e);
            throw e;
        }
    }

    @PostMapping("/saveStock")
    public String saveStock(@ModelAttribute("stockForm") @Valid StockRequestModel stockRequestModel, HttpServletRequest request, Model model) throws ModelNotFoundException {
        MastroLogUtils.info(StockController.class, "Going to save stock : {}");
        try {
            stockService.saveOrUpdateStockDetails(stockRequestModel);
            return "redirect:/inventory/getStock";
        } catch (Exception e) {
            MastroLogUtils.error(StockController.class, "Error occured while saving stocks : {}");
            throw e;
        }
    }

}
