package com.erp.mastro.controller;

import com.erp.mastro.common.MastroApplicationUtils;
import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.constants.Constants;
import com.erp.mastro.entities.*;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.GRNRequestModel;
import com.erp.mastro.model.request.POInvoiceRequestModel;
import com.erp.mastro.service.interfaces.HSNService;
import com.erp.mastro.service.interfaces.POInvoiceService;
import com.erp.mastro.service.interfaces.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/purchase")
public class POInvoiceController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private UserController userController;

    @Autowired
    HSNService hsnService;

    @Autowired
    POInvoiceService poInvoiceService;

    /**
     * Method to load create po invoice
     *
     * @param request
     * @param poId
     * @param model
     * @return
     */
    @RequestMapping(value = "/getPurchaseOrderInvoice", method = RequestMethod.GET)
    public String getPurchaseOrderInvoice(HttpServletRequest request, @RequestParam("poId") Long poId, Model model) {
        MastroLogUtils.info(POInvoiceController.class, "Going to get create po invoice" + poId);
        try {
            model.addAttribute("purchaseModule", "purchaseModule");
            model.addAttribute("purchaseTab", "purchase");
            model.addAttribute("poInvoiceForm", new POInvoiceRequestModel());
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
                Double cessTotal = 0d;
                Double loadingChargeSum = 0d;
                Double totalCgstfinal = 0d;
                Double totalSgstfinal = 0d;
                Double totalTaxableValuefinal = 0d;
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
                    cessTotal = cessTotal + (total * (grnItems.getCessRate() / 100));
                    grnpoItemsModel.setItemCgstAmt(totalCGST);
                    grnpoItemsModel.setItemSgstAmt(totalSGST);
                    Double finalItemTotal = total + totalCGST + totalSGST;
                    grnpoItemsModel.setFinalItemTotal(finalItemTotal);
                    subTotal = subTotal + finalItemTotal;
                    poGRNItemsModelList.add(grnpoItemsModel);
                    loadingChargeSum = loadingChargeSum + (grnItems.getIndentItemPartyGroup().getItemStockDetails().getStock().getProduct().getLoadingCharge() * grnItems.getQuantityDc() * productUOMPurchase.getConvertionFactor());
                    totalCgstfinal = totalCgstfinal + totalCGST;
                    totalSgstfinal = totalSgstfinal + totalSGST;
                    totalTaxableValuefinal = totalTaxableValuefinal + total;
                }
                HSN loadingHSN = hsnService.getAllHSN().stream()
                        .filter(hsnData -> (null != hsnData))
                        .filter(hsnData -> (1 != hsnData.getHsnDeleteStatus()))
                        .filter(hsnData -> hsnData.getHsnCode().equals("7314"))
                        .findFirst().get();
                Double loadingChargeSgstAmt = loadingChargeSum * (loadingHSN.getSgst() / 100);
                Double loadingChargeCgstAmt = loadingChargeSum * (loadingHSN.getCgst() / 100);
                totalCgstfinal = totalCgstfinal + loadingChargeCgstAmt;
                totalSgstfinal = totalSgstfinal + loadingChargeSgstAmt;
                totalTaxableValuefinal = totalTaxableValuefinal + loadingChargeSum;
                Double finalLoadingCharge = loadingChargeSum + loadingChargeSgstAmt + loadingChargeCgstAmt;
                model.addAttribute("loadingHSN", loadingHSN);
                model.addAttribute("grnItems", poGRNItemsModelList);
                model.addAttribute("subTotal", MastroApplicationUtils.roundTwoDecimals(subTotal));
                model.addAttribute("loadingChargeSum", MastroApplicationUtils.roundTwoDecimals(loadingChargeSum));
                model.addAttribute("loadingChargeSgstAmt", MastroApplicationUtils.roundTwoDecimals(loadingChargeSgstAmt));
                model.addAttribute("loadingChargeCgstAmt", MastroApplicationUtils.roundTwoDecimals(loadingChargeCgstAmt));
                model.addAttribute("finalLoadingCharge", MastroApplicationUtils.roundTwoDecimals(finalLoadingCharge));
                model.addAttribute("totalCgst", MastroApplicationUtils.roundTwoDecimals(totalCgstfinal));
                model.addAttribute("totalSgst", MastroApplicationUtils.roundTwoDecimals(totalSgstfinal));
                model.addAttribute("totalTaxableValue", MastroApplicationUtils.roundTwoDecimals(totalTaxableValuefinal));
                if (loadingHSN.getCess() != null) {
                    cessTotal = cessTotal + (loadingChargeSum * (loadingHSN.getCess() / 100));
                }
                model.addAttribute("cessTotal", MastroApplicationUtils.roundTwoDecimals(cessTotal));
                model.addAttribute("totalAmt", MastroApplicationUtils.roundTwoDecimals(subTotal + finalLoadingCharge + cessTotal));
                Double amtForRound = MastroApplicationUtils.roundTwoDecimals(subTotal + finalLoadingCharge + cessTotal);
                Long roundedGrandTotal = Math.round(MastroApplicationUtils.roundTwoDecimals(subTotal + finalLoadingCharge + cessTotal));
                Double roundedGrandTotalInDouble = Double.parseDouble(roundedGrandTotal.toString());
                Double roundedAmt = MastroApplicationUtils.roundAmount(amtForRound, roundedGrandTotalInDouble);
                model.addAttribute("roundValue", MastroApplicationUtils.roundTwoDecimals(roundedAmt));
                model.addAttribute("grandTotal", MastroApplicationUtils.roundTwoDecimals(roundedGrandTotalInDouble));

            }
            return "views/createPOInvoice";

        } catch (Exception e) {
            MastroLogUtils.error(POInvoiceController.class, "Error occured while getting po invoice create page", e);
            throw e;
        }
    }

    /**
     * Method to generate po invoice
     *
     * @param request
     * @param model
     * @return po list
     */
    @PostMapping("/createPOInvoice")
    public String createPOInvoice(HttpServletRequest request, Model model, @ModelAttribute("poInvoiceForm") @Valid POInvoiceRequestModel poInvoiceRequestModel) {

        try {
            Long poId = Long.parseLong(request.getParameter("poId"));
            MastroLogUtils.info(POInvoiceController.class, " create po invoice for the po" + poId);
            model.addAttribute("purchaseModule", "purchaseModule");
            model.addAttribute("purchaseTab", "purchase");
            poInvoiceService.generatePOInvoice(poInvoiceRequestModel);
            return "redirect:/purchase/getPurchaseOrderList";

        } catch (ModelNotFoundException e) {
            MastroLogUtils.error(this, "poinvoice model is empty", e);
            return "redirect:/purchase/getPurchaseOrderList";
        } catch (Exception e) {
            MastroLogUtils.error(POInvoiceController.class, e.getMessage());
            throw e;
        }
    }
}
