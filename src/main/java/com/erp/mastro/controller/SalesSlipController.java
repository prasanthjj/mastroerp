package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.BillingDetails;
import com.erp.mastro.entities.ContactDetails;
import com.erp.mastro.entities.Party;
import com.erp.mastro.entities.SalesSlip;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.SalesSlipRequestModel;
import com.erp.mastro.service.interfaces.PartyService;
import com.erp.mastro.service.interfaces.SalesSlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Controller include sales slip methods
 */
@Controller
@RequestMapping("/inventory")
public class SalesSlipController {

    @Autowired
    PartyService partyService;

    @Autowired
    SalesSlipService salesSlipService;

    /**
     * Method to load create sales slip
     *
     * @param model
     * @return sales slip page
     */
    @GetMapping("/getCreateSalesSlip")
    public String getCreateSalesSlip(Model model) {
        MastroLogUtils.info(SalesSlipController.class, "Going to add sales slip : {}");
        try {
            model.addAttribute("salesSlipForm", new SalesSlipRequestModel());
            model.addAttribute("inventoryModule", "inventory");
            model.addAttribute("deliveryChellanTab", "deliveryChellan");
            return "views/addPurchaseSlip";

        } catch (Exception e) {
            MastroLogUtils.error(SalesSlipController.class, "Error occured while adding sales slip: { }", e);
            throw e;

        }
    }

    /**
     * Method to get party details
     *
     * @param partyId
     * @return details
     */
    @RequestMapping(value = "/getPartyDetailsInSales", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponse getPartyDetailsInSales(@RequestParam("partyIdSale") Long partyId) {
        try {
            MastroLogUtils.info(SalesSlipController.class, "Going to get party details : {}" + partyId);
            Party party = partyService.getPartyById(partyId);
            BillingDetails billingDetails = party.getBillingDetails().stream().findFirst().get();
            ContactDetails contactDetails = party.getContactDetails().stream().findFirst().get();

            return new GenericResponse(true, "get party details")
                    .setProperty("contactperson", billingDetails.getContactPersonName())
                    .setProperty("city", billingDetails.getCity())
                    .setProperty("state", billingDetails.getState())
                    .setProperty("country", billingDetails.getCountry())
                    .setProperty("pincode", billingDetails.getPinCode())
                    .setProperty("phoneno", billingDetails.getTelephoneNo())
                    .setProperty("email", billingDetails.getEmailId())
                    .setProperty("delivarycontactperson", contactDetails.getContactPersonName())
                    .setProperty("delivarydesignation", contactDetails.getDesignation())
                    .setProperty("delivaryaddress", contactDetails.getAddress())
                    .setProperty("deliveryphoneno", contactDetails.getMobileNo())
                    .setProperty("deliveryemail", contactDetails.getEmailId())
                    .setProperty("category", party.getCategoryType());

        } catch (Exception e) {
            MastroLogUtils.error(this, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Method to save salesslip basic details
     *
     * @param salesSlipRequestModel
     * @param request
     * @param model
     * @return
     */
    @PostMapping("/createSalesSlipBasic")
    public String createSalesSlipBasic(@ModelAttribute("salesSlipForm") @Valid SalesSlipRequestModel salesSlipRequestModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(SalesSlipController.class, "Going to create basic sales slip details : {}" + salesSlipRequestModel.toString());
        try {
            model.addAttribute("inventoryModule", "inventory");
            model.addAttribute("deliveryChellanTab", "deliveryChellan");
            SalesSlip salesSlip = salesSlipService.createSalesSlip(salesSlipRequestModel);
            BillingDetails billingDetails = salesSlip.getParty().getBillingDetails().stream().findFirst().get();
            ContactDetails contactDetails = salesSlip.getParty().getContactDetails().stream().findFirst().get();
            model.addAttribute("billingDetails", billingDetails);
            model.addAttribute("contactDetails", contactDetails);
            model.addAttribute("salesSlipDetails", salesSlip);
            return "views/addPurchaseSlip";
        } catch (ModelNotFoundException e) {
            MastroLogUtils.error(this, "sales slip model empty", e);
            return "views/addPurchaseSlip";
        } catch (Exception e) {
            MastroLogUtils.error(SalesSlipController.class, e.getMessage());
            throw e;
        }
    }

}
