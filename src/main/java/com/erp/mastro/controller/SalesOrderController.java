package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.entities.*;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.SalesOrderRequestModel;
import com.erp.mastro.service.interfaces.HSNService;
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

    @Autowired
    HSNService hsnService;

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
                    .filter(salesData -> null != salesData.getParty())
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
            model.addAttribute("partysDetails", party);
            ContactDetails contactDetails = party.getContactDetails().stream().filter(contactItem -> (null != contactItem))
                    .findFirst().get();
            model.addAttribute("partyContactDetails", contactDetails);
            BillingDetails billingDetails = party.getBillingDetails().stream().filter(billingItem -> (null != billingItem))
                    .findFirst().get();
            model.addAttribute("partyBillingDetails", billingDetails);
            Double subTotal = 0d;
            Double totalCess = 0d;
            Double totalTaxableValue = 0d;
            Double cgstAmt = 0d;
            Double sgstAmt = 0d;
            Double loadingCharge = 0d;
            Double grandTotal = 0d;
            for (SalesOrderProduct salesOrderProduct : salesOrder.getSalesOrderProductSet()) {
                if (salesOrderProduct.getProduct() != null) {
                    subTotal = subTotal + salesOrderProduct.getTotalPrice();
                }
                if (salesOrderProduct.getProduct().getHsn().getCess() != null) {
                    Double taxableValue = 0d;
                    taxableValue = salesOrderProduct.getTaxableValue();
                    totalCess = totalCess + taxableValue * (salesOrderProduct.getProduct().getHsn().getCess() / 100);
                }
                HSN hsn = hsnService.getAllHSN().stream()
                        .filter(hsnData -> (null != hsnData))
                        .filter(hsnData -> (1 != hsnData.getHsnDeleteStatus()))
                        .filter(hsnData -> hsnData.getHsnCode().equals("7314"))
                        .findFirst().get();
                totalTaxableValue = totalTaxableValue + salesOrderProduct.getTaxableValue();
                cgstAmt = totalTaxableValue * (hsn.getCgst() / 100);
                sgstAmt = totalTaxableValue * (hsn.getSgst() / 100);
                loadingCharge = cgstAmt + sgstAmt;

                grandTotal = subTotal + loadingCharge + totalCess;
                model.addAttribute("hsnDetails", hsn.getHsnCode());
            }

            model.addAttribute("grandTotal", Math.round(grandTotal * 100.0) / 100.0);
            model.addAttribute("totalTaxableValue", Math.round(totalTaxableValue * 100.0) / 100.0);
            model.addAttribute("cgstAmt", Math.round(cgstAmt * 100.0) / 100.0);
            model.addAttribute("sgstAmt", Math.round(sgstAmt * 100.0) / 100.0);
            model.addAttribute("loadingCharge", Math.round(loadingCharge * 100.0) / 100.0);
            model.addAttribute("subTotal", Math.round(subTotal * 100.0) / 100.0);
            model.addAttribute("totalCess", Math.round(totalCess * 100.0) / 100.0);
            model.addAttribute("salesDetails", salesOrder);

            return "views/addSalesOrder";

        } catch (Exception e) {
            MastroLogUtils.error(SalesOrderController.class, "Error occured while getting product : {}", e);
            throw e;
        }

    }

    /**
     * Method to create SO
     *
     * @param request
     * @param model
     * @param salesOrderRequestModel
     * @return
     * @throws ModelNotFoundException
     */

    @PostMapping("/createSO")
    public String createSO(HttpServletRequest request, Model model, @ModelAttribute("salesForm") @Valid SalesOrderRequestModel salesOrderRequestModel) throws ModelNotFoundException {
        MastroLogUtils.info(SalesOrderController.class, "Going to saveSalesOrderProduct  : {}");
        Long salesId = Long.parseLong(request.getParameter("salesIds"));
        Double grandTotal = Double.parseDouble(request.getParameter("grandTotal"));
        try {
            SalesOrder salesOrder = salesOrderService.getSalesorderById(salesId);
            salesOrderService.generateSalesOrder(salesOrderRequestModel, salesOrder, grandTotal);

            return "redirect:/sales/getSalesOrder";
        } catch (Exception e) {
            MastroLogUtils.error(SalesOrderController.class, "Error occured while creating SalesOrder : {}", e);
            throw e;
        }
    }


}
