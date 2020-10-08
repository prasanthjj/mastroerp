package com.erp.mastro.controller;


import com.erp.mastro.constants.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class StockLedgerController {
    @RequestMapping("/reports/getLedgerReport")
    public String getLedgerReport(Model model) {

        //model.addAttribute("ledgerReportForm", new DepartmentRequestModel());
        model.addAttribute(Constants.REPORT_MODULE, Constants.REPORT_MODULE);
        model.addAttribute("ledgerReportTab", "ledgerReport");
        // model.addAttribute("departmentList", departmentList);
        return "views/itemwise_stock_ledger";

    }
}
