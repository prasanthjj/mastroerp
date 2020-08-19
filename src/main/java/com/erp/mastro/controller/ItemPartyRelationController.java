package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.dao.AutoPopulateDAO;
import com.erp.mastro.entities.Product;
import com.erp.mastro.model.request.ItemPartyRelationModel;
import com.erp.mastro.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
            model.addAttribute("itemPartyForm", new ItemPartyRelationModel());
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
            model.addAttribute("itemPartyForm", new ItemPartyRelationModel());
            return "views/itemPartyRelationMaster";

        } catch (Exception e) {
            MastroLogUtils.error(AssetController.class, "Error occured while getting product : {}", e);
            throw e;
        }

    }

    /**
     * Method to addPartyToProduct
     *
     * @param model
     * @param req
     * @return the result itemparty
     */
    @PostMapping("/associatePartyToProduct")
    public String associatePartyToProduct(Model model, HttpServletRequest req) {

        MastroLogUtils.info(ItemPartyRelationController.class, "Going to associate party to product :{}");
        try {
            Long productId = Long.parseLong(req.getParameter("productDetailsId"));
            Long partyId = Long.parseLong(req.getParameter("selectedParty"));
            if (productId != null && partyId != null) {
                productService.addPartyToProduct(productId, partyId);
                model.addAttribute("productDetails", productService.getProductById(productId));
            }
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("itemPartyTab", "itemParty");
            model.addAttribute("itemPartyForm", new ItemPartyRelationModel());
            return "views/itemPartyRelationMaster";

        } catch (Exception e) {
            MastroLogUtils.error(AssetController.class, "Error occured while associate party to product : {}", e);
            throw e;
        }

    }

    @PostMapping("/saveItemParty")
    public String saveItemParty(@ModelAttribute("itemPartyForm") @Valid ItemPartyRelationModel itemPartyRelationModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(ItemPartyRelationController.class, "Going to save itemparty datas : {}");
        try {

            String[] productPartyRateIds = request.getParameterValues("productPartyRateId");
            String[] ratess = request.getParameterValues("rate");
            String[] discountss = request.getParameterValues("discount");
            String[] creditDays = request.getParameterValues("creditDays");
            String[] allowedPriceDevPerUppers = request.getParameterValues("allowedPriceDevPerUpper");
            String[] allowedPriceDevPerLowers = request.getParameterValues("allowedPriceDevPerLower");
            productService.saveOrUpdateItemParty(productPartyRateIds, ratess, discountss, creditDays, allowedPriceDevPerUppers, allowedPriceDevPerLowers);

            return "redirect:/master/getItemPartys";
        } catch (Exception e) {
            MastroLogUtils.error(ItemPartyRelationController.class, "Error occured while save itemparty : {}", e);
            throw e;
        }
    }

}


