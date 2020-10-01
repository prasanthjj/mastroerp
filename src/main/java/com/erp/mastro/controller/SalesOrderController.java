package com.erp.mastro.controller;

import com.erp.mastro.common.MastroApplicationUtils;
import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.*;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.SalesOrderRequestModel;
import com.erp.mastro.repository.SalesOrderRepository;
import com.erp.mastro.service.interfaces.HSNService;
import com.erp.mastro.service.interfaces.PartyService;
import com.erp.mastro.service.interfaces.ProductService;
import com.erp.mastro.service.interfaces.SalesOrderService;
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
public class SalesOrderController {

    @Autowired
    PartyService partyService;

    @Autowired
    ProductService productService;

    @Autowired
    SalesOrderService salesOrderService;

    @Autowired
    SalesOrderRepository salesOrderRepository;

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
                    .filter(salesData -> null != salesData.getGrandTotal())
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
            Double loadingCharge = 0d;
            Double grandTotal = 0d;
            Double finalTotal= 0d;
            Double itemlodingcharge =0d;
            Double LodingChargeTotal =0d;
           Double TotalLodingChargeCgst =0d;
            Double TotalLodingChargeSgst =0d;
            Double roundOff =0d;
            int  roundoffvalue;
            for (SalesOrderProduct salesOrderProduct : salesOrder.getSalesOrderProductSet()) {
                if (salesOrderProduct.getProduct() != null) {
                    subTotal = subTotal + salesOrderProduct.getTotalPrice();
                }
                if (salesOrderProduct.getProduct().getHsn().getCess() != null) {
                    Double taxableValue = 0d;
                    taxableValue = salesOrderProduct.getFinalTaxableValue();
                    totalCess = totalCess + taxableValue * (salesOrderProduct.getProduct().getHsn().getCess() / 100);
                }
                HSN hsn = hsnService.getAllHSN().stream()
                        .filter(hsnData -> (null != hsnData))
                        .filter(hsnData -> (1 != hsnData.getHsnDeleteStatus()))
                        .filter(hsnData -> hsnData.getHsnCode().equals("7314"))
                        .findFirst().get();
                       Double igstAmt = hsn.getIgst() ;

                loadingCharge = salesOrderProduct.getProduct().getLoadingCharge();
                Double itemQuanity = salesOrderProduct.getQuantity();
                 itemlodingcharge= itemlodingcharge + (itemQuanity * loadingCharge);


                TotalLodingChargeCgst = itemlodingcharge *  (hsn.getCgst() / 100);
                 TotalLodingChargeSgst = itemlodingcharge *  (hsn.getSgst() / 100);
                LodingChargeTotal =  itemlodingcharge+TotalLodingChargeCgst+TotalLodingChargeSgst;

                finalTotal = subTotal + LodingChargeTotal + totalCess;
                model.addAttribute("hsnDetails", hsn.getHsnCode());
                model.addAttribute("igstAmt", Math.round(igstAmt * 100.0) / 100.0);
            }

            //Methods for rounoff starts
            Double grandTotals= MastroApplicationUtils.roundTwoDecimals(finalTotal);
            double doubleNumber =grandTotals;
            String doubleAsString = String.valueOf(doubleNumber);
            int indexOfDecimal = doubleAsString.indexOf(".");
            String integernumberstring =doubleAsString.substring(0, indexOfDecimal);
            String decimalpart = doubleAsString.substring(indexOfDecimal);

            int intNumber = Integer.parseInt(integernumberstring);
            double lastDigit = intNumber % 10 ;
           int a = integernumberstring.length()-1;
           if(lastDigit < 5)
           {
               Double integernumber=  Double.parseDouble(integernumberstring);

               Double number = integernumber;
               double place =10;
               double result = number / place;
               result = Math.floor(result);
               result *= place;
               roundoffvalue=(int)result;
               roundOff = roundoffvalue-finalTotal;


           }else {
               Double integernumber=  Double.parseDouble(integernumberstring);
               Double number = integernumber;

               double place =10;
               double result = number / place;
               result = Math.ceil(result);
               result *= place;
              roundoffvalue=(int)result;
              roundOff = roundoffvalue-finalTotal;
           }
            //Method for round off Ends


            model.addAttribute("grandTotal", roundoffvalue);
            model.addAttribute("roundOff", MastroApplicationUtils.roundTwoDecimals(roundOff));
          //  model.addAttribute("grandTotal",  MastroApplicationUtils.roundTwoDecimals(grandTotal));
            model.addAttribute("finalTotal",  MastroApplicationUtils.roundTwoDecimals(finalTotal));
           // model.addAttribute("grandTotal", Math.round(grandTotal * 100.0) / 100.0);
            model.addAttribute("totalTaxableValue", MastroApplicationUtils.roundTwoDecimals(itemlodingcharge));
            model.addAttribute("cgstAmt", MastroApplicationUtils.roundTwoDecimals(TotalLodingChargeCgst));
            model.addAttribute("sgstAmt",  MastroApplicationUtils.roundTwoDecimals(TotalLodingChargeSgst));
            model.addAttribute("loadingCharge", MastroApplicationUtils.roundTwoDecimals(LodingChargeTotal));
            model.addAttribute("subTotal", MastroApplicationUtils.roundTwoDecimals(subTotal));
            model.addAttribute("totalCess",  MastroApplicationUtils.roundTwoDecimals(totalCess));
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
        Double finalTotal = Double.parseDouble(request.getParameter("finalTotal"));
        Double roundOff = Double.parseDouble(request.getParameter("roundOff"));

        try {
            SalesOrder salesOrder = salesOrderService.getSalesorderById(salesId);
            salesOrderService.generateSalesOrder(salesOrderRequestModel, salesOrder,grandTotal,finalTotal,roundOff);

            return "redirect:/sales/getSalesOrder";
        } catch (Exception e) {
            MastroLogUtils.error(SalesOrderController.class, "Error occured while creating SalesOrder : {}", e);
            throw e;
        }
    }
    /**
     * Method to get purchase order preview
     *
     * @param request
     * @param soId
     * @param model
     * @return preview
     */
    @RequestMapping(value = "/getSalesOrderPreview", method = RequestMethod.GET)
    public String getSalesOrderPreview(HttpServletRequest request, @RequestParam("soId") Long soId, Model model) {
        MastroLogUtils.info(SalesOrderController.class, "Going to get SalesOrderPreview :{}" + soId);
        try {

            model.addAttribute("salesModule", "salesModule");
            model.addAttribute("salesTab", "sales");
            SalesOrder salesOrder = salesOrderService.getSalesorderById(soId);
            model.addAttribute("salesOrderDetails", salesOrder);
            Party party = salesOrder.getParty();
            ContactDetails contactDetails = party.getContactDetails().stream().filter(contactItem -> (null != contactItem))
                    .findFirst().get();
            model.addAttribute("partyContactDetails", contactDetails);
            model.addAttribute("partysDetails", party);
            BillingDetails billingDetails = party.getBillingDetails().stream().filter(billingItem -> (null != billingItem))
                    .findFirst().get();
            model.addAttribute("partyBillingDetails", billingDetails);


            model.addAttribute("salesForm", salesOrderService.getSalesorderById(soId));
            Double subTotal = 0d;
            Double totalCess = 0d;
            Double loadingCharge = 0d;
            Double grandTotal = 0d;
            Double finalTotal= 0d;
            Double itemlodingcharge =0d;
            Double LodingChargeTotal =0d;
            Double TotalLodingChargeCgst =0d;
            Double TotalLodingChargeSgst =0d;
            Double roundOff =0d;
            int  roundoffvalue;
            for (SalesOrderProduct salesOrderProduct : salesOrder.getSalesOrderProductSet()) {
                if (salesOrderProduct.getProduct() != null) {
                    subTotal = subTotal + salesOrderProduct.getTotalPrice();
                }
                if (salesOrderProduct.getProduct().getHsn().getCess() != null) {
                    Double taxableValue = 0d;
                    taxableValue = salesOrderProduct.getFinalTaxableValue();
                    totalCess = totalCess + taxableValue * (salesOrderProduct.getProduct().getHsn().getCess() / 100);
                }
                HSN hsn = hsnService.getAllHSN().stream()
                        .filter(hsnData -> (null != hsnData))
                        .filter(hsnData -> (1 != hsnData.getHsnDeleteStatus()))
                        .filter(hsnData -> hsnData.getHsnCode().equals("7314"))
                        .findFirst().get();
                Double igstAmt = hsn.getIgst() ;

                loadingCharge = salesOrderProduct.getProduct().getLoadingCharge();
                Double itemQuanity = salesOrderProduct.getQuantity();
                itemlodingcharge= itemlodingcharge + (itemQuanity * loadingCharge);


                TotalLodingChargeCgst = itemlodingcharge *  (hsn.getCgst() / 100);
                TotalLodingChargeSgst = itemlodingcharge *  (hsn.getSgst() / 100);
                LodingChargeTotal =  itemlodingcharge+TotalLodingChargeCgst+TotalLodingChargeSgst;

                finalTotal = subTotal + LodingChargeTotal + totalCess;
                model.addAttribute("hsnDetails", hsn.getHsnCode());
                model.addAttribute("igstAmt", Math.round(igstAmt * 100.0) / 100.0);
            }

            //Methods for rounoff starts
            Double grandTotals= MastroApplicationUtils.roundTwoDecimals(finalTotal);
            double doubleNumber =grandTotals;
            String doubleAsString = String.valueOf(doubleNumber);
            int indexOfDecimal = doubleAsString.indexOf(".");
            String integernumberstring =doubleAsString.substring(0, indexOfDecimal);
            String decimalpart = doubleAsString.substring(indexOfDecimal);

            int intNumber = Integer.parseInt(integernumberstring);
            double lastDigit = intNumber % 10 ;
            int a = integernumberstring.length()-1;
            if(lastDigit < 5)
            {
                Double integernumber=  Double.parseDouble(integernumberstring);

                Double number = integernumber;
                double place =10;
                double result = number / place;
                result = Math.floor(result);
                result *= place;
                roundoffvalue=(int)result;
                roundOff = roundoffvalue-finalTotal;


            }else {
                Double integernumber=  Double.parseDouble(integernumberstring);
                Double number = integernumber;

                double place =10;
                double result = number / place;
                result = Math.ceil(result);
                result *= place;
                roundoffvalue=(int)result;
                roundOff = roundoffvalue-finalTotal;
            }
            //Method for round off Ends



            model.addAttribute("grandTotal", roundoffvalue);
            model.addAttribute("roundOff", MastroApplicationUtils.roundTwoDecimals(roundOff));
            model.addAttribute("finalTotal",  MastroApplicationUtils.roundTwoDecimals(finalTotal));

            //  model.addAttribute("grandTotal",  MastroApplicationUtils.roundTwoDecimals(grandTotal));
            //model.addAttribute("grandTotal",  grandTotal);
            // model.addAttribute("grandTotal", Math.round(grandTotal * 100.0) / 100.0);
            model.addAttribute("totalTaxableValue", MastroApplicationUtils.roundTwoDecimals(itemlodingcharge));
            model.addAttribute("cgstAmt", MastroApplicationUtils.roundTwoDecimals(TotalLodingChargeCgst));
            model.addAttribute("sgstAmt",  MastroApplicationUtils.roundTwoDecimals(TotalLodingChargeSgst));
            model.addAttribute("loadingCharge", MastroApplicationUtils.roundTwoDecimals(LodingChargeTotal));
            model.addAttribute("subTotal", MastroApplicationUtils.roundTwoDecimals(subTotal));
            model.addAttribute("totalCess",  MastroApplicationUtils.roundTwoDecimals(totalCess));
            model.addAttribute("salesDetails", salesOrder);

            return "views/salesOrderPreview";



        } catch (Exception e) {
            MastroLogUtils.error(SalesOrderController.class, "Error occured while getSalesOrderPreview :{}" + soId, e);
            throw e;
        }
    }


    @PostMapping("/soApprove")
    @ResponseBody
    public GenericResponse soApprove(Model model, HttpServletRequest request, @RequestParam("reason") String reason, @RequestParam("soids") Long soId) {
        MastroLogUtils.info(SalesOrderController.class, "Going to approve Sales Order" + soId);
        try {
            SalesOrder salesOrder = salesOrderService.getSalesorderById(soId);
            salesOrder.setStatus("Approve");
            salesOrder.setReason(reason);
            salesOrderRepository.save(salesOrder);
            return new GenericResponse(true, "approve Sales Order");

        } catch (Exception e) {
            MastroLogUtils.error(this, "Error Occured on approve Sales Order:{}", e);

            throw e;
        }

    }


    @PostMapping("/soDiscard")
    @ResponseBody
    public GenericResponse soDiscard(Model model, HttpServletRequest request, @RequestParam("reason") String reason, @RequestParam("soids") Long soId) {
        MastroLogUtils.info(SalesOrderController.class, "Going to Discard GRN" + soId);
        try {

            SalesOrder salesOrder = salesOrderService.getSalesorderById(soId);
            salesOrder.setStatus("Discard");
            salesOrder.setReason(reason);
            salesOrderRepository.save(salesOrder);
            return new GenericResponse(true, "Disgard Sales Order");

        } catch (Exception e) {
            MastroLogUtils.error(this, "Error Occured on Discard sales Order:{}", e);

            throw e;
        }

    }

    @PostMapping("/soReview")
    @ResponseBody
    public GenericResponse soReview(Model model, HttpServletRequest request, @RequestParam("reason") String reason, @RequestParam("soids") Long soId) {
        MastroLogUtils.info(SalesOrderController.class, "Going to Review so" + soId);
        try {
            SalesOrder salesOrder = salesOrderService.getSalesorderById(soId);

            salesOrder.setStatus("Reviewed");
            salesOrder.setReason(reason);
            salesOrderRepository.save(salesOrder);
            return new GenericResponse(true, "soReview");

        } catch (Exception e) {
            MastroLogUtils.error(this, "Error Occured on soReview :{}", e);

            throw e;
        }

    }

    @RequestMapping(value = "/getSalesOrderOnReview", method = RequestMethod.GET)
    public String getSalesOrderOnReview(HttpServletRequest request, @RequestParam("soId") Long soId, Model model) {


            model.addAttribute("salesModule", "salesModule");
            model.addAttribute("salesTab", "sales");
            SalesOrder salesOrder = salesOrderService.getSalesorderById(soId);
            model.addAttribute("salesOrderDetails", salesOrder);
            return "views/editSoOnReview";

    }



}
