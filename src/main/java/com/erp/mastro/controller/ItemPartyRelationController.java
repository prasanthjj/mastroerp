package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.dao.AutoPopulateDAO;
import com.erp.mastro.entities.Party;
import com.erp.mastro.entities.Product;
import com.erp.mastro.model.request.PartyRequestModel;
import com.erp.mastro.model.request.ProductRequestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    /**
     * Method to get autopopulate items
     *
     * @param searchTerm
     * @return item list
     */
    @RequestMapping(value = "/autopopulate/items", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponse products(@RequestParam("searchTerm") String searchTerm) {
        try {
            List<Product> productsFinal = new ArrayList<>();
            List<Product> products = autoPopulateDao.getAutoPopulateList("productName", searchTerm, Product.class, 50);
            productsFinal = products.stream()
                    .filter(productData -> (null != productData))
                    .filter(productData -> (true == productData.isEnabled()))
                    .collect(Collectors.toList());
            Set<ProductRequestModel> productRequestModels = new HashSet<ProductRequestModel>();
            for (Product product : productsFinal) {
                ProductRequestModel productRequestModel = new ProductRequestModel();
                productRequestModel.setId(product.getId());
                productRequestModel.setProductname(product.getProductName());
                productRequestModels.add(productRequestModel);
            }
            return new GenericResponse(true, "get items")
                    .setProperty("products", productRequestModels);

        } catch (Exception e) {
            MastroLogUtils.error(this, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Get parts in autocomplete
     *
     * @param searchTerm
     * @return partys
     */
    @RequestMapping(value = "/autopopulate/party", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponse getPartys(@RequestParam("searchTerm") String searchTerm) {
        try {
            List<Party> partyFinal = new ArrayList<>();
            List<Party> partys = autoPopulateDao.getAutoPopulateList("partyName", searchTerm, Party.class, 50);
            partyFinal = partys.stream()
                    .filter(partysData -> (null != partysData))
                    .filter(partysData -> (true == partysData.isEnabled()))
                    .collect(Collectors.toList());
            Set<PartyRequestModel> partyRequestModels = new HashSet<PartyRequestModel>();
            for (Party party : partyFinal) {
                PartyRequestModel partyRequestModel = new PartyRequestModel();
                partyRequestModel.setId(party.getId());
                partyRequestModel.setPartysname(party.getPartyName());
                partyRequestModels.add(partyRequestModel);
            }
            return new GenericResponse(true, "get partys")
                    .setProperty("partys", partyRequestModels);

        } catch (Exception e) {
            MastroLogUtils.error(this, e.getMessage(), e);
            throw e;
        }
    }


}


