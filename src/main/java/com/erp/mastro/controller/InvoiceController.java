package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.entities.*;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.SalesSlipRequestModel;
import com.erp.mastro.service.interfaces.HSNService;
import com.erp.mastro.service.interfaces.SalesSlipService;
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
@RequestMapping("/sales")
public class InvoiceController {

    @Autowired
    private UserController userController;

    @Autowired
    private SalesSlipService salesSlipService;

    @Autowired
    private HSNService hsnService;

    /**
     * Method to get sales slip invoice list
     *
     * @param model
     * @return list
     */
    @GetMapping("/getInvoiceList")
    public String getInvoiceList(Model model) {
        MastroLogUtils.info(InvoiceController.class, "Method to get sales slip invoice list :");
        try {
            Branch currentBranch = userController.getCurrentUser().getUserSelectedBranch().getCurrentBranch();
            model.addAttribute("salesModule", "salesModule");
            model.addAttribute("invoiceTab", "invoice");
            List<SalesSlip> salesSlipList = currentBranch.getSalesSlips().stream()
                    .filter(salesSlipData -> (null != salesSlipData))
                    .filter(salesSlipData -> (null != salesSlipData.getStatus()))
                    .filter(salesSlipData -> (salesSlipData.getStatus().equals("Draft")))
                    .sorted(Comparator.comparing(
                            SalesSlip::getId).reversed())
                    .collect(Collectors.toList());
            model.addAttribute("salesSlip", salesSlipList);

            return "views/SalesInvoice";

        } catch (Exception e) {
            MastroLogUtils.error(InvoiceController.class, e.getMessage());
            throw e;
        }

    }

    @RequestMapping(value = "/getInvoiceViaSalesSlip", method = RequestMethod.GET)
    public String getInvoiceViaSalesSlip(HttpServletRequest request, @RequestParam("salesSlipId") Long salesSlipId, Model model) {
        MastroLogUtils.info(InvoiceController.class, "Going to get create invoice via sales slip" + salesSlipId);
        try {
            model.addAttribute("salesModule", "salesModule");
            model.addAttribute("invoiceTab", "invoice");
            model.addAttribute("salesSlipForm", new SalesSlipRequestModel());
            if (salesSlipId != null) {

                SalesSlip salesSlip = salesSlipService.getSalesSlipById(salesSlipId);
                model.addAttribute("salesSlip", salesSlip);
                BillingDetails billingDetails = salesSlip.getParty().getBillingDetails().stream().findFirst().get();
                ContactDetails contactDetails = salesSlip.getParty().getContactDetails().stream().findFirst().get();
                model.addAttribute("billingDetails", billingDetails);
                model.addAttribute("contactDetails", contactDetails);
                HSN loadingHSN = hsnService.getAllHSN().stream()
                        .filter(hsnData -> (null != hsnData))
                        .filter(hsnData -> (1 != hsnData.getHsnDeleteStatus()))
                        .filter(hsnData -> hsnData.getHsnCode().equals("7314"))
                        .findFirst().get();
                model.addAttribute("loadingHSN", loadingHSN);

            }
            return "views/createSalesSlipInvoice";

        } catch (Exception e) {
            MastroLogUtils.error(InvoiceController.class, "Error occured while getting  invoice via sales slip", e);
            throw e;
        }
    }

    @PostMapping("/createSalesSlipInvoice")
    public String createSalesSlipInvoice(HttpServletRequest request, Model model, @ModelAttribute("salesSlipForm") @Valid SalesSlipRequestModel salesSlipRequestModel) {

        try {
            MastroLogUtils.info(InvoiceController.class, " create sales slip invoice " + salesSlipRequestModel.toString());
            model.addAttribute("salesModule", "salesModule");
            model.addAttribute("invoiceTab", "invoice");
            salesSlipService.generateSalesSlipInvoice(salesSlipRequestModel);
            return "redirect:/sales/getInvoiceList";

        } catch (ModelNotFoundException e) {
            MastroLogUtils.error(this, "poinvoice model is empty", e);
            return "redirect:/sales/getInvoiceList";
        } catch (Exception e) {
            MastroLogUtils.error(InvoiceController.class, e.getMessage());
            throw e;
        }
    }
}
