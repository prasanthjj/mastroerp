package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.entities.*;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.SalesOrderRequestModel;
import com.erp.mastro.service.interfaces.PartyService;
import com.erp.mastro.service.interfaces.ProductService;
import com.erp.mastro.service.interfaces.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sales")
public class SalesOrderController {

    @Autowired
    PartyService partyService;

    @Autowired
    ProductService productService;

    @Autowired
    SalesOrderService salesOrderService;

    /**
     * Method to load sales order page
     *
     * @param model
     * @return
     */
    @GetMapping("/getSalesOrder")
    public String getSalesOrder(Model model) {
        MastroLogUtils.info(SalesOrderController.class, "Going to get all sales order : {}");
        try {
            List<SalesOrder> salesOrderList = salesOrderService.getAllSalesOrder().stream()
                    .filter(salesData -> (null != salesData))
                    .sorted(Comparator.comparing(
                            SalesOrder::getId).reversed())
                    .collect(Collectors.toList());
            model.addAttribute("salesForm", new SalesOrderRequestModel());
            model.addAttribute("salesOrderList", salesOrderList);
            model.addAttribute("salesModule", "salesModule");
            model.addAttribute("salesTab", "sales");
            return "views/salesOrder";
        } catch (Exception e) {
            MastroLogUtils.error(SalesOrderController.class, "Error occured while getting sales : {}", e);
            throw e;
        }
    }

    /**
     * Method to load add sales order  page
     *
     * @param model
     * @return
     */

    @GetMapping("/getCreateSalesOrder")
    public String getCreateSalesOrder(Model model) {
        MastroLogUtils.info(SalesOrderController.class, "Going to add sales order : {}");
        try {
            model.addAttribute("salesForm", new SalesOrderRequestModel());
            model.addAttribute("salesModule", "salesModule");
            model.addAttribute("salesTab", "sales");
            return "views/addSalesOrder";

        } catch (Exception e) {
            MastroLogUtils.error(SalesOrderController.class, "Error occured while adding sales order : { }", e);
            throw e;

        }
    }

    /**
     * Method to get party in autocomplete
     *
     * @param model
     * @param req
     * @return
     */
    @GetMapping("/getSelectedPartyDetails")
    public String getSelectedPartyDetails(SalesOrderRequestModel salesOrderRequestModel, Model model, HttpServletRequest req) throws ModelNotFoundException {
        Long partyId = Long.parseLong(req.getParameter("selectedPartys"));
        MastroLogUtils.info(SalesOrderController.class, "Going to get party :{}" + partyId);
        try {
            Party party = partyService.getPartyById(partyId);
            model.addAttribute("partysDetails", party);
            SalesOrder salesOrder = salesOrderService.saveSalesOrder(salesOrderRequestModel, party);
            ContactDetails contactDetails = party.getContactDetails().stream().filter(contactItem -> (null != contactItem))
                    .findFirst().get();
            model.addAttribute("partyContactDetails", contactDetails);
            BillingDetails billingDetails = party.getBillingDetails().stream().filter(billingItem -> (null != billingItem))
                    .findFirst().get();
            model.addAttribute("partyBillingDetails", billingDetails);
            model.addAttribute("salesModule", "salesModule");
            model.addAttribute("salesTab", "sales");
            model.addAttribute("salesDetails", salesOrder);
            model.addAttribute("salesForm", new SalesOrderRequestModel());
            return "views/addSalesOrder";

        } catch (Exception e) {
            MastroLogUtils.error(SalesOrderController.class, "Error occured while getting party : {}", e);
            throw e;
        }

    }

    /**
     * Method to get selected product details
     *
     * @param model
     * @param req
     * @return selected product details
     */
    @PostMapping("/saveSelectedProduct")
    public String saveSelectedProduct(Model model, @ModelAttribute("salesForm") @Valid SalesOrderRequestModel salesOrderRequestModel, HttpServletRequest req) throws ModelNotFoundException {
        Long productId = Long.parseLong(req.getParameter("selectedProduct"));
        Long salesId = Long.parseLong(req.getParameter("salesIds"));
        Double quantity = Double.parseDouble(req.getParameter("quantity"));
        MastroLogUtils.info(SalesOrderController.class, "Going to get Product :{}" + productId);
        try {
            Product product = productService.getProductById(productId);
            model.addAttribute("productDetails", product);
            model.addAttribute("salesModule", "salesModule");
            model.addAttribute("salesTab", "sales");
            SalesOrder salesOrder = salesOrderService.getSalesorderById(salesId);
            salesOrderService.saveOrUpdateSalesOrderProduct(salesOrderRequestModel, salesOrder, product, quantity);
            model.addAttribute("salesForm", new SalesOrderRequestModel(salesOrderService.getSalesorderById(salesOrder.getId())));
            Party party = salesOrder.getParty();
            /*Party party = partyService.getPartyById(partyId);*/
            model.addAttribute("partysDetails", party);
            ContactDetails contactDetails = party.getContactDetails().stream().filter(contactItem -> (null != contactItem))
                    .findFirst().get();
            model.addAttribute("partyContactDetails", contactDetails);
            BillingDetails billingDetails = party.getBillingDetails().stream().filter(billingItem -> (null != billingItem))
                    .findFirst().get();
            model.addAttribute("partyBillingDetails", billingDetails);
            model.addAttribute("salesDetails", salesOrder);
            return "views/addSalesOrder";

        } catch (Exception e) {
            MastroLogUtils.error(SalesOrderController.class, "Error occured while getting product : {}", e);
            throw e;
        }

    }

/*

  @PostMapping("/saveSalesOrderProductInTable")
    public String saveSalesOrderProduct(@ModelAttribute("salesForm") @Valid SalesOrderRequestModel salesOrderRequestModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(SalesOrderController.class, "Going to saveSalesOrderProductInTable  : {}");
        try {

            return "redirect:/sales/getSalesOrder";
        } catch (ModelNotFoundException e) {
            MastroLogUtils.error(SalesOrderController.class,"Sales order model is empty :{}");
            return "redirect:/sales/getSalesOrder";
        } catch (Exception e) {
            MastroLogUtils.error(SalesOrderController.class,"Error occured while saving saveSalesOrderProductInTable : {}",e);
            throw e;
        }
    }
*/

}
