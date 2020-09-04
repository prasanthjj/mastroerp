package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.model.request.SalesSlipRequestModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller include sales slip methods
 */
@Controller
public class SalesSlipController {

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
            return "views/addSalesSlip";

        } catch (Exception e) {
            MastroLogUtils.error(SalesSlipController.class, "Error occured while adding sales slip: { }", e);
            throw e;

        }
    }

}
