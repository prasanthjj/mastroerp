package com.erp.mastro.service;

import com.erp.mastro.entities.HSN;
import org.springframework.stereotype.Component;

/**
 * Calculations based on hsn
 */
@Component
public class CalculateService {

    public Double calculateTotalPrice(Double rate, Double dcQty, Double discountPer) {
        Double total = dcQty * rate;
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
}
