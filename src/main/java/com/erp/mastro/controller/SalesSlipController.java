package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.*;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.GRNRequestModel;
import com.erp.mastro.model.request.ProductRequestModel;
import com.erp.mastro.model.request.SalesSlipRequestModel;
import com.erp.mastro.repository.ProductUOMRepository;
import com.erp.mastro.service.interfaces.GRNService;
import com.erp.mastro.service.interfaces.PartyService;
import com.erp.mastro.service.interfaces.ProductService;
import com.erp.mastro.service.interfaces.SalesSlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    ProductService productService;

    @Autowired
    GRNService grnService;

    @Autowired
    private UserController userController;

    @Autowired
    private ProductUOMRepository productUOMRepository;

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
            model.addAttribute("salesSlipForm", new SalesSlipRequestModel());
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

    /**
     * Method to get product details in sales
     *
     * @param partyId
     * @param productId
     * @return product details
     */
    @RequestMapping(value = "/getProductDetailsInSales", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponse getProductDetailsInSales(@RequestParam("partyIdSale") Long partyId, @RequestParam("productIdSale") Long productId) {
        try {
            MastroLogUtils.info(SalesSlipController.class, "Going to get product details : {}" + productId);
            Product product = productService.getProductById(productId);

            Set<ProductUOM> productUOMS = product.getProductUOMSet().stream()
                    .filter(productuomData -> (null != productuomData))
                    .filter(productuomData -> (productuomData.getTransactionType().equals("Sales")))
                    .collect(Collectors.toSet());
            Set<ProductRequestModel.ProductUOMModel> productUOMModels = new HashSet<>();
            for (ProductUOM productUOM : productUOMS) {
                ProductRequestModel.ProductUOMModel productUOMModel = new ProductRequestModel.ProductUOMModel();
                productUOMModel.setId(productUOM.getId());
                productUOMModel.setNameuom(productUOM.getUom().getUOM());
                productUOMModels.add(productUOMModel);
            }
            Party party = partyService.getPartyById(partyId);
            Double ratevalue = 0.0d;
            if (!party.getIndustryType().getIndustryType().equals("Cash Customer")) {
                Set<ProductPartyRateRelation> productPartyRateRelationSet = product.getProductPartyRateRelations().stream()
                        .filter(productPartyData -> (null != productPartyData))
                        .filter(productPartyData -> (partyId.equals(productPartyData.getParty().getId())))
                        .collect(Collectors.toSet());
                if (!productPartyRateRelationSet.isEmpty()) {
                    ratevalue = productPartyRateRelationSet.stream().findFirst().get().getPartyPriceList().getRate();
                }
            } else {
                ratevalue = product.getBasePrice();
            }

            return new GenericResponse(true, "get product details")
                    .setProperty("rate", ratevalue)
                    .setProperty("salesuoms", productUOMModels);

        } catch (Exception e) {
            MastroLogUtils.error(this, e.getMessage(), e);
            throw e;
        }
    }

    @RequestMapping(value = "/getProductPartyGrnItemsInSales", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponse getProductPartyGrnItemsInSales(@RequestParam("partyIdSale") Long partyId, @RequestParam("productIdSale") Long productId, @RequestParam("productSaleUomId") Long productSaleUomId) {
        try {
            MastroLogUtils.info(SalesSlipController.class, "Going to get ProductPartyGrnItemsInSales details : {}" + productId);
            Product product = productService.getProductById(productId);
            Branch currentBranch = userController.getCurrentUser().getUserSelectedBranch().getCurrentBranch();
            Set<GRN> grnSet = grnService.getAllGRNs().stream()
                    .filter(grnData -> (null != grnData))
                    .filter(grnData -> (grnData.getBranch().getId().equals(currentBranch.getId())))
                    .filter(grnData -> (grnData.getStatus().equals("Approve")))
                    .collect(Collectors.toSet());

            Set<GRNItems> grnItemsSet = new HashSet<>();
            for (GRN grn : grnSet) {
                if (!grn.getGrnItems().isEmpty()) {
                    grnItemsSet.addAll(grn.getGrnItems());
                }
            }
            Set<GRNItems> grnItemsSetFinal = new HashSet<>();
            for (GRNItems grnItem : grnItemsSet) {
                if (grnItem.getIndentItemPartyGroup().getItemStockDetails().getStock().getProduct().getId().equals(product.getId())) {
                    grnItemsSetFinal.add(grnItem);
                }
            }

            List<GRNRequestModel.GRNItemModel> grnItemModels = new ArrayList<>();
            List<GRNItems> grnItemsList = grnItemsSetFinal.stream()
                    .filter(grnitemData -> (null != grnitemData))
                    .filter(grnitemData -> (0 != grnitemData.getAccepted()))
                    .sorted(Comparator.comparing(
                            GRNItems::getId).reversed())
                    .collect(Collectors.toList());
            ProductUOM salesUom = productUOMRepository.findById(productSaleUomId).get();
            for (GRNItems grnItems : grnItemsList) {
                GRNRequestModel.GRNItemModel grnItemModel = new GRNRequestModel.GRNItemModel();
                grnItemModel.setId(grnItems.getId());
                grnItemModel.setAcceptedqty(grnItems.getAccepted());
                grnItemModel.setPurchaseuom(grnItems.getIndentItemPartyGroup().getItemStockDetails().getPurchaseUOM().getUOM());
                grnItemModel.setGrnid(grnItems.getGrn().getId());
                grnItemModel.setItemname(grnItems.getIndentItemPartyGroup().getItemStockDetails().getStock().getProduct().getProductName());
                Uom purchaseUOM = grnItems.getIndentItemPartyGroup().getItemStockDetails().getPurchaseUOM();
                ProductUOM productUOMPurchase = grnItems.getIndentItemPartyGroup().getItemStockDetails().getStock().getProduct().getProductUOMSet().stream()
                        .filter(productuomData -> (null != productuomData))
                        .filter(productuomData -> (productuomData.getTransactionType().equals("Purchase")))
                        .filter(productuomData -> (productuomData.getUom().getId().equals(purchaseUOM.getId())))
                        .findFirst().get();
                Double grnItemQtyInBaseUOM = grnItems.getAccepted() * productUOMPurchase.getConvertionFactor();
                Double grnItemQtyInSalesUOM = grnItemQtyInBaseUOM / salesUom.getConvertionFactor();
                grnItemModel.setAcceptedqtyinsalesuom(Math.round(grnItemQtyInSalesUOM * 100.0) / 100.0);
                grnItemModel.setSalesuom(salesUom.getUom().getUOM());
                grnItemModels.add(grnItemModel);
            }

            return new GenericResponse(true, "get ProductPartyGrnItems details")
                    .setProperty("productname", product.getProductName())
                    .setProperty("grnitems", grnItemModels);

        } catch (Exception e) {
            MastroLogUtils.error(this, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Method to save salesslipgrnitem
     *
     * @param grnItemsId
     * @param partyId
     * @param productSalesIds
     * @param rateValue
     * @param qtyEnter
     * @param salesUOMId
     * @param salesslipId
     * @return the response
     */
    @RequestMapping(value = "/saveSelectedGrnItems", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponse saveSelectedGrnItems(@RequestParam("grnItemsId") Long grnItemsId, @RequestParam("partyId") Long partyId, @RequestParam("productSalesIds") Long productSalesIds, @RequestParam("rateValue") Double rateValue, @RequestParam("qtyEnter") Double qtyEnter, @RequestParam("salesUOMId") Long salesUOMId, @RequestParam("salesslipid") Long salesslipId) {
        try {
            MastroLogUtils.info(SalesSlipController.class, "Going to saveSelectedGrnItems details : {}" + grnItemsId);
            salesSlipService.grnUpdationOnSale(productSalesIds, partyId, qtyEnter, salesUOMId, rateValue, grnItemsId, salesslipId);
            return new GenericResponse(true, "save details details")
                    .setProperty("saleslipId", salesslipId);

        } catch (Exception e) {
            MastroLogUtils.error(this, e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/getsSalesSlipBasic")
    public String getsSalesSlipBasic(@RequestParam("salesSlipId") Long salesSlipId, HttpServletRequest request, Model model) {
        MastroLogUtils.info(SalesSlipController.class, "Going to get sales slip details : {}" + salesSlipId);
        try {
            model.addAttribute("inventoryModule", "inventory");
            model.addAttribute("deliveryChellanTab", "deliveryChellan");
            SalesSlip salesSlip = salesSlipService.getSalesSlipById(salesSlipId);
            BillingDetails billingDetails = salesSlip.getParty().getBillingDetails().stream().findFirst().get();
            ContactDetails contactDetails = salesSlip.getParty().getContactDetails().stream().findFirst().get();
            model.addAttribute("billingDetails", billingDetails);
            model.addAttribute("contactDetails", contactDetails);
            model.addAttribute("salesSlipDetails", salesSlip);
            model.addAttribute("salesSlipForm", new SalesSlipRequestModel());
            Double grandTotal = 0.0d;
            for (SalesSlipItems salesSlipItems : salesSlip.getSalesSlipItemsSet()) {
                grandTotal = grandTotal + salesSlipItems.getTotalAmount() + salesSlipItems.getCessAmount() + salesSlipItems.getCgstAmount() + salesSlipItems.getSgstAmount();
            }
            model.addAttribute("grandTotal", Math.round(grandTotal * 100.0) / 100.0);
            return "views/addPurchaseSlip";
        } catch (Exception e) {
            MastroLogUtils.error(SalesSlipController.class, e.getMessage());
            throw e;
        }
    }

    @PostMapping("/saveSalesSlipFullDetails")
    public String saveSalesSlipFullDetails(@ModelAttribute("salesSlipForm") @Valid SalesSlipRequestModel salesSlipRequestModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(SalesSlipController.class, "Going to save  sales slip full details : {}" + salesSlipRequestModel.toString());
        try {
            model.addAttribute("inventoryModule", "inventory");
            model.addAttribute("deliveryChellanTab", "deliveryChellan");
            salesSlipService.saveSalesSlipFullDate(salesSlipRequestModel);

            return "redirect:/inventory/getDeliveryChellan";
        } catch (ModelNotFoundException e) {
            MastroLogUtils.error(this, "sales slip model empty", e);
            return "redirect:/inventory/getDeliveryChellan";
        } catch (Exception e) {
            MastroLogUtils.error(SalesSlipController.class, e.getMessage());
            throw e;
        }
    }

}
