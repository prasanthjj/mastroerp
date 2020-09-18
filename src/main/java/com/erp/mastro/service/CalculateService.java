package com.erp.mastro.service;

import com.erp.mastro.entities.HSN;
import com.erp.mastro.entities.Product;
import com.erp.mastro.entities.ProductPartyRateRelation;
import org.springframework.stereotype.Component;

/**
 * Calculations based on hsn
 */
@Component
public class CalculateService {

    public Double calculateTotalPrice(Double rate, Double acceptQty, Double discountPer) {
        Double total = acceptQty * rate;
        Double discountAmount = total * (discountPer / 100);
        total = total - discountAmount;
        return Math.round(total * 100.0) / 100.0;
    }

    public Double calculateTotalPriceIgstAmount(Double total, HSN hsn) {
        Double totalIgst = total * (hsn.getIgst() / 100);
        return Math.round(totalIgst * 100.0) / 100.0;
    }

    public Double calculateTotalPriceCgstAmount(Double quanity, HSN hsn) {
      //  Double totalCgst = total * (hsn.getCgst() / 100);
        Double totalCgst =quanity*(hsn.getCgst() / 100);
        return Math.round(totalCgst * 100.0) / 100.0;
    }

    public Double calculateTotalPriceSgstAmount(Double quanity, HSN hsn) {
        Double totalSgst = quanity * (hsn.getSgst() / 100);
        return Math.round(totalSgst * 100.0) / 100.0;
    }

    public Double calculateTotalPriceCessAmount(Double total, HSN hsn) {
        Double totalCess = 0.0;
        if (hsn.getCess() != null) {
            totalCess = total * (hsn.getCess() / 100);
        }
        return Math.round(totalCess * 100.0) / 100.0;
    }

   /* public Double calculateTaxableValueInSo(Double quantity, Product product) {
        Double taxableValue = quantity * product.getBasePrice();
        return Math.round(taxableValue * 100.0) / 100.0;
    }*/


    // single item cgst
    public Double calculateSinglePriceCgstAmount( ProductPartyRateRelation productPartyRateRelation,HSN hsn) {
        Double itemRate = productPartyRateRelation.getPartyPriceList().getRate();
        Double itemDiscount=productPartyRateRelation.getPartyPriceList().getDiscount();
        Double itemDiscountedrate = itemRate*(itemDiscount/100.0);
        Double taxablevalueforoneitem = itemRate-itemDiscountedrate;
        Double singlecgst =taxablevalueforoneitem*(hsn.getCgst()/100);
        return Math.round(singlecgst * 100.0) / 100.0;
    }

    // Single item sgst
    public Double calculateSinglePriceSgstAmount(ProductPartyRateRelation productPartyRateRelation ,HSN hsn) {
        Double itemRate = productPartyRateRelation.getPartyPriceList().getRate();
        Double itemDiscount=productPartyRateRelation.getPartyPriceList().getDiscount();
        Double itemDiscountedrate = itemRate*(itemDiscount/100.0);
        Double taxablevalueforoneitem = itemRate-itemDiscountedrate;
        Double singlesgst =taxablevalueforoneitem*(hsn.getCgst()/100);
        return Math.round(singlesgst * 100.0) / 100.0;
    }





    public Double calculateTaxableValueInSo(Double quantity, ProductPartyRateRelation productPartyRateRelation) {


        Double taxableValue = quantity * productPartyRateRelation.getPartyPriceList().getRate();


        return Math.round(taxableValue * 100.0) / 100.0;
    }

    public Double calculateFinalTaxableValueInSo(Double quanity, ProductPartyRateRelation productPartyRateRelation) {
       // Double finaltaxableValue = taxableValue - (productPartyRateRelation.getPartyPriceList().getDiscount()/100);

        Double itemRate = productPartyRateRelation.getPartyPriceList().getRate();
        Double itemDiscount=productPartyRateRelation.getPartyPriceList().getDiscount();
        Double itemDiscountedrate = itemRate*(itemDiscount/100.0);
        Double taxablevalueforoneitem = itemRate-itemDiscountedrate;
        Double finaltaxableValue = taxablevalueforoneitem*quanity;
        return Math.round(finaltaxableValue * 100.0) / 100.0;
    }





    public Double calculateCgstAmountForSO(Double taxableValue, HSN hsn) {
        Double totalCgstAmount = taxableValue * (hsn.getCgst() / 100);
        return Math.round(totalCgstAmount * 100.0) / 100.0;
    }

    public Double calculateSgstAmountForSO(Double finaltaxableValue, HSN hsn) {
        Double totalSgstAmount = finaltaxableValue * (hsn.getSgst() / 100);
        return Math.round(totalSgstAmount * 100.0) / 100.0;
    }

    public Double totalPriceForSO(Double finaltaxableValue, Double cgstAmount, Double sgstAmount) {
        Double totalPrice = finaltaxableValue + cgstAmount + sgstAmount;
        return Math.round(totalPrice * 100.0) / 100.0;
    }



    public Double totalNetPriceForSO(Double finaltaxableValue, Double singlecgst, Double singlesgst,ProductPartyRateRelation productPartyRateRelation) {

        Double itemRate = productPartyRateRelation.getPartyPriceList().getRate();
        Double itemDiscount=productPartyRateRelation.getPartyPriceList().getDiscount();
        Double itemDiscountedrate = itemRate*(itemDiscount/100.0);
        Double totaltax=singlesgst+singlecgst;
        Double taxablevalueforoneitem = itemRate-itemDiscountedrate;
        Double taxpriceforone = taxablevalueforoneitem + (singlesgst+singlecgst);
        Double netPrice = taxablevalueforoneitem + (singlesgst+singlecgst);

/*
        Double netPrice = finaltaxableValue+cgstAmount+sgstAmount ;
*/
        return Math.round(netPrice * 100.0) / 100.0;
    }
}
