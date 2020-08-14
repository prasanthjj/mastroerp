package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.custom.responseBody.ListResponse;
import com.erp.mastro.dao.AutoPopulateDAO;
import com.erp.mastro.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.erp.mastro.common.AutopopulateUtil.populateResponseProducts;

@Controller
@RequestMapping("/master")
public class ItemPartyRelationController {

    @Autowired
    AutoPopulateDAO autoPopulateDao;

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

    @RequestMapping(value = "/autopopulate/items", method = RequestMethod.GET)
    @ResponseBody
    public ListResponse products(@RequestParam("searchTerm") String searchTerm) {
        try {
            List<Product> products = autoPopulateDao.getAutoPopulateList("productName", searchTerm, Product.class, 5);
            return populateResponseProducts(products);

        } catch (Exception e) {
            MastroLogUtils.error(this, e.getMessage(), e);
            return new ListResponse(false);
        }
    }

}


