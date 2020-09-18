package com.erp.mastro.controller;

import com.erp.mastro.common.MastroApplicationUtils;
import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.constants.Constants;
import com.erp.mastro.entities.*;
import com.erp.mastro.model.request.GRNRequestModel;
import com.erp.mastro.service.interfaces.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/purchase")
public class POInvoiceController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private UserController userController;

    @RequestMapping(value = "/getPurchaseOrderInvoice", method = RequestMethod.GET)
    public String getPurchaseOrderInvoice(HttpServletRequest request, @RequestParam("poId") Long poId, Model model) {
        MastroLogUtils.info(POInvoiceController.class, "Going to get create po invoice" + poId);
        try {
            model.addAttribute("purchaseModule", "purchaseModule");
            model.addAttribute("purchaseTab", "purchase");
            if (poId != null) {
                PurchaseOrder purchaseOrder = purchaseOrderService.getPurchaseOrderById(poId);
                model.addAttribute("purchaseOrder", purchaseOrder);
                Party party = purchaseOrder.getParty();
                model.addAttribute("party", party);
                BillingDetails billingDetails = party.getBillingDetails().stream().findFirst().get();
                model.addAttribute("billingDetails", billingDetails);
                Branch branch = userController.getCurrentUser().getUserSelectedBranch().getCurrentBranch();
                model.addAttribute("branchDetails", branch);
                Set<GRNItems> grnItemsSet = new HashSet<>();
                List<GRN> grnSet = purchaseOrder.getGrnSet().stream()
                        .filter(grn -> (null != grn))
                        .filter(grn -> (!grn.getStatus().equals(Constants.STATUS_INITIAL)))
                        .filter(grn -> (!grn.getStatus().equals(Constants.STATUS_DISCARD)))
                        .sorted(Comparator.comparing(
                                GRN::getId).reversed())
                        .collect(Collectors.toList());
                for (GRN grn : grnSet) {
                    grnItemsSet.addAll(grn.getGrnItems());
                }
                List<GRNRequestModel.GRNPOItemsModel> poGRNItemsModelList = new ArrayList<>();
                Double subTotal = 0d;
                Double tax = 0d;
                for (GRNItems grnItems : grnItemsSet) {
                    GRNRequestModel.GRNPOItemsModel grnpoItemsModel = new GRNRequestModel.GRNPOItemsModel();
                    grnpoItemsModel.setQuantityDc(grnItems.getQuantityDc());
                    grnpoItemsModel.setNameOfProduct(grnItems.getIndentItemPartyGroup().getItemStockDetails().getStock().getProduct().getProductName());
                    grnpoItemsModel.setUomPurchase(grnItems.getIndentItemPartyGroup().getItemStockDetails().getPurchaseUOM().getUOM());
                    grnpoItemsModel.setHsncode(grnItems.getHsnCode());
                    grnpoItemsModel.setGrnno(grnItems.getGrn().getGrnNo());
                    grnpoItemsModel.setRate(grnItems.getIndentItemPartyGroup().getRate());
                    grnpoItemsModel.setDiscount(grnItems.getDiscount());
                    grnpoItemsModel.setUomBase(grnItems.getIndentItemPartyGroup().getItemStockDetails().getStock().getProduct().getUom().getUOM());
                    Double total = 0d;
                    Uom purchaseUOM = grnItems.getIndentItemPartyGroup().getItemStockDetails().getPurchaseUOM();
                    ProductUOM productUOMPurchase = grnItems.getIndentItemPartyGroup().getItemStockDetails().getStock().getProduct().getProductUOMSet().stream()
                            .filter(productUOMData -> (null != productUOMData))
                            .filter(productUOMData -> (productUOMData.getTransactionType().equals("Purchase")))
                            .filter(productUOMData -> (productUOMData.getUom().getId().equals(purchaseUOM.getId())))
                            .findFirst().get();
                    total = MastroApplicationUtils.calculateTotalPrice(grnItems.getIndentItemPartyGroup().getRate(), grnItems.getQuantityDc() * productUOMPurchase.getConvertionFactor(), grnItems.getDiscount());
                    grnpoItemsModel.setItemTotal(total);
                    Double totalCGST = total * (grnItems.getCgstRate() / 100);
                    Double totalSGST = total * (grnItems.getSgstRate() / 100);
                    grnpoItemsModel.setItemCgstAmt(totalCGST);
                    grnpoItemsModel.setItemSgstAmt(totalSGST);
                    Double finalItemTotal = total + totalCGST + totalSGST;
                    grnpoItemsModel.setFinalItemTotal(finalItemTotal);
                    subTotal = subTotal + finalItemTotal;
                    poGRNItemsModelList.add(grnpoItemsModel);
                }
                model.addAttribute("grnItems", poGRNItemsModelList);
                model.addAttribute("subTotal", MastroApplicationUtils.roundTwoDecimals(subTotal));

            }
            return "views/createPOInvoice";

        } catch (Exception e) {
            MastroLogUtils.error(POInvoiceController.class, "Error occured while getting po invoice create page", e);
            throw e;
        }
    }

    @PostMapping("/createPOInvoice")
    public String createPOInvoice(HttpServletRequest request, Model model) {

        try {
            Long poId = Long.parseLong(request.getParameter("poId"));
            Double grandTotal = Double.parseDouble(request.getParameter("grandTotal"));
            MastroLogUtils.info(POInvoiceController.class, " create po invoice for the po" + poId);
            model.addAttribute("purchaseModule", "purchaseModule");
            model.addAttribute("purchaseTab", "purchase");
            return "redirect:/purchase/getPurchaseOrderList";

        } catch (Exception e) {
            MastroLogUtils.error(POInvoiceController.class, e.getMessage());
            throw e;
        }
    }
}
