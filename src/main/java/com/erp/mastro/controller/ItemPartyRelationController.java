package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.dao.AutoPopulateDAO;
import com.erp.mastro.entities.Product;
import com.erp.mastro.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller to include itemparty methods
 */
@Controller
@RequestMapping("/master")
public class ItemPartyRelationController {

    @Autowired
    AutoPopulateDAO autoPopulateDao;

    @Autowired
    ProductService productService;

    /**
     * Method to get itemparty page
     *
     * @param model
     * @return
     */
    @GetMapping("/getItemPartys")
    public String getItemPartys(Model model) {
        MastroLogUtils.info(ItemPartyRelationController.class, "Going to get ItemParty : {}");

        try {

            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("itemPartyTab", "itemParty");
            return "views/itemPartyRelationMaster";

        } catch (Exception e) {
            MastroLogUtils.error(ItemPartyRelationController.class, "Error occured while getting itemparty :{}", e);
            throw e;
        }

    }

    /**
     * Method to get selected product details
     * @param model
     * @param req
     * @return selected product details
     */
    @GetMapping("/getSelectedProduct")
    public String getSelectedProduct(Model model, HttpServletRequest req) {
        Long productId = Long.parseLong(req.getParameter("selectedProduct"));
        MastroLogUtils.info(ItemPartyRelationController.class, "Going to get Product :{}" + productId);
        try {
            Product product = productService.getProductById(productId);
            model.addAttribute("productDetails", product);
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("itemPartyTab", "itemParty");
            return "views/itemPartyRelationMaster";

        } catch (Exception e) {
            MastroLogUtils.error(AssetController.class, "Error occured while getting product : {}", e);
            throw e;
        }

    }

}


