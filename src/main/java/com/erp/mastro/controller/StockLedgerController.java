package com.erp.mastro.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class StockLedgerController {
    @RequestMapping("/reports/getLedgerReport")
    public String getLedgerReport(Model model) {

            //model.addAttribute("ledgerReportForm", new DepartmentRequestModel());
            model.addAttribute("reportModule", "reportModule");
            model.addAttribute("ledgerReportTab", "ledgerReport");
           // model.addAttribute("departmentList", departmentList);
            return "views/itemwise_stock_ledger";

    }
}
