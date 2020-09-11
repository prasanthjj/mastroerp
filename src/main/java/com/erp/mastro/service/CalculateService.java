package com.erp.mastro.service;

import com.erp.mastro.entities.HSN;
import com.erp.mastro.entities.Product;
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

    public Double calculateTotalPriceCgstAmount(Double total, HSN hsn) {
        Double totalCgst = total * (hsn.getCgst() / 100);
        return Math.round(totalCgst * 100.0) / 100.0;
    }

    public Double calculateTotalPriceSgstAmount(Double total, HSN hsn) {
        Double totalSgst = total * (hsn.getSgst() / 100);
        return Math.round(totalSgst * 100.0) / 100.0;
    }

    public Double calculateTotalPriceCessAmount(Double total, HSN hsn) {
        Double totalCess = 0.0;
        if (hsn.getCess() != null) {
            totalCess = total * (hsn.getCess() / 100);
        }
        return Math.round(totalCess * 100.0) / 100.0;
    }

    public Double calculateTaxableValueInSo(Double quantity, Product product) {
        Double taxableValue = quantity * product.getBasePrice();
        return Math.round(taxableValue * 100.0) / 100.0;
    }

    public Double calculateCgstAmountForSO(Double taxableValue, HSN hsn) {
        Double totalCgstAmount = taxableValue * (hsn.getCgst() / 100);
        return Math.round(totalCgstAmount * 100.0) / 100.0;
    }

    public Double calculateSgstAmountForSO(Double taxableValue, HSN hsn) {
        Double totalSgstAmount = taxableValue * (hsn.getSgst() / 100);
        return Math.round(totalSgstAmount * 100.0) / 100.0;
    }

    public Double totalPriceForSO(Double taxableValue, Double cgstAmount, Double sgstAmount) {
        Double totalPrice = taxableValue + cgstAmount + sgstAmount;
        return Math.round(totalPrice * 100.0) / 100.0;
    }

}
