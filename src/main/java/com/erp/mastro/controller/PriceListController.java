package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.PriceList;
import com.erp.mastro.exception.MastroEntityException;
import com.erp.mastro.model.request.PriceListRequestModel;
import com.erp.mastro.service.interfaces.PriceListService;
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
public class PriceListController {

    @Autowired
    private PriceListService priceListService;

    @GetMapping("/getPriceListMaster")
    public String getPriceListMaster(Model model) {
        MastroLogUtils.info(PriceListController.class, "Going to get all pricelist :{}");
        try {
            List<PriceList> priceList = priceListService.getAllPriceList().stream()
                    .filter(priceData -> (null != priceData))
                    .filter(priceData -> (1 != priceData.getPricelistDeleteStatus()))
                    .sorted(Comparator.comparing(
                            PriceList::getId).reversed())
                    .collect(Collectors.toList());
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("priceListTab", "priceList");
            model.addAttribute("priceList", priceList);
            model.addAttribute("priceListForm", new PriceListRequestModel());
            return "views/pricelistMaster";

        } catch (Exception e) {
            MastroLogUtils.error(PriceListController.class, "Error occured while getting pricelist:{}", e);
            throw e;
        }

    }

    @PostMapping("/savePriceList")
    public String savePriceList(@ModelAttribute("priceListForm") @Valid PriceListRequestModel priceListRequestModel, HttpServletRequest request, Model model) throws MastroEntityException {
        MastroLogUtils.info(PriceListController.class, "Going to save pricelist :{}");
        try {
            priceListService.saveOrUpdatePriceList(priceListRequestModel);
            return "redirect:/master/getPriceListMaster";
        } catch (Exception e) {
            MastroLogUtils.error(PriceListController.class, "Error occured while saving pricelist :{}", e);
            throw e;
        }
    }

    @GetMapping("/getPriceListforEdit")
    @ResponseBody
    public GenericResponse getPriceListforEdit(Model model, HttpServletRequest request, @RequestParam("pricelistId") Long pricelistId) throws MastroEntityException {
        MastroLogUtils.info(PriceListController.class, "Going to get pricelist for edit :{}", +pricelistId);
        try {

            PriceList priceListdetails = priceListService.getPriceListById(pricelistId);
            return new GenericResponse(true, "get pricelist details")
                    .setProperty("pricelistId", priceListdetails.getId())
                    .setProperty("pricelistName", priceListdetails.getPricelistName())
                    .setProperty("categoryType", priceListdetails.getCategoryType())
                    .setProperty("partyType", priceListdetails.getPartyType())
                    .setProperty("discountPercentage", priceListdetails.getDiscountPercentage())
                    .setProperty("allowedPriceDevPerUpper", priceListdetails.getAllowedPriceDevPerUpper())
                    .setProperty("allowedPriceDevPerLower", priceListdetails.getAllowedPriceDevPerLower());

        } catch (MastroEntityException e) {
            MastroLogUtils.error(PriceListController.class, "Pricelist Entity not found" + pricelistId, e);
            return new GenericResponse(false, e.getMessage());
        } catch (Exception e) {
            MastroLogUtils.error(PriceListController.class, "Error occured while getting pricelist for edit :{}" + pricelistId, e);
            return new GenericResponse(false, e.getMessage());

        }
    }


    @PostMapping("/deletePriceListDetails")
    @ResponseBody
    public GenericResponse deletePriceListDetails(Model model, HttpServletRequest request, @RequestParam("pricelistId") Long pricelistId) {
        MastroLogUtils.info(PriceListController.class, "Going to delete pricelist:{}", +pricelistId);
        try {

            priceListService.deletePriceListDetails(pricelistId);
            return new GenericResponse(true, "delete pricelist details");

        } catch (MastroEntityException e) {
            MastroLogUtils.error(PriceListController.class, "Pricelist Entity not found" + pricelistId, e);
            return new GenericResponse(false, e.getMessage());
        } catch (Exception e) {
            MastroLogUtils.error(PriceListController.class, "Error occured while deleting pricelist  :{}" + pricelistId, e);
            return new GenericResponse(false, e.getMessage());

        }

    }

}
