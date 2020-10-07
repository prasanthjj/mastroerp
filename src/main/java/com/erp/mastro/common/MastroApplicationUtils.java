package com.erp.mastro.common;

import com.erp.mastro.constants.Constants;
import com.erp.mastro.entities.Branch;
import com.erp.mastro.entities.HSN;
import com.erp.mastro.entities.Product;
import com.erp.mastro.entities.StockLedger;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class that included all common Util methods
 */
public class MastroApplicationUtils {
    private final GrantedAuthority adminAuthority = new SimpleGrantedAuthority(
            "ROLE_ADMIN");

    /**
     * Returns converted Date String
     *
     * @param dateString String from the view
     * @param date       Date from Entity
     * @return String format of Date
     */
    public static String getStringFromDate(String dateString, Date date) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(Constants.DATEFORMAT_MM_DD_YYYY);
        if (dateString != null) {
            return dateString;
        }
        if (date == null) {
            return "";
        } else {
            return df.format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        }
    }

    public static Timestamp convertToTimestamp(LocalDateTime date) {
        Timestamp timestamp = Timestamp.valueOf(date);
        return timestamp;
    }

    public boolean isAdminAuthority(final Authentication authentication) {
        return CollectionUtils.isNotEmpty(authentication.getAuthorities())
                && authentication.getAuthorities().contains(adminAuthority);
        /* || authentication.getAuthorities().contains(Constants.ROLE_SUPERADMIN)*/
    }

    public static Double roundTwoDecimals(Double amount) {
        DecimalFormat twoDForm = new DecimalFormat("#.####");
        return Double.valueOf(twoDForm.format(amount));
    }

    public static String generateName(String branchCode, String type, long id) {
        String str = branchCode + "-" + type + "-" + String.format("%05d", id);
        return str;
    }

    /**
     * Method to generate code
     *
     * @param type
     * @param id
     * @return string
     */
    public static String generateCode(String type, Long id) {
        String str = type + "-" + String.format("%05d", id);
        return str;
    }

    /**
     * Below are the methods used for calculating the totals and Taxes
     */

    /**
     * Method to calculate the total price from Rate, Quantity and discount %
     *
     * @param rate
     * @param acceptQty
     * @param discountPercentage
     * @return
     */
    public static Double calculateTotalPrice(Double rate, Double acceptQty, Double discountPercentage) {
        Double total = acceptQty * rate;
        Double discountAmount = 0d;
        if (discountPercentage != null || discountPercentage != 0) {
            discountAmount = total * (discountPercentage / 100);
        }
        total = total - discountAmount;
        return MastroApplicationUtils.roundTwoDecimals(total);
    }

    /**
     * @param total
     * @param taxRate
     * @return
     */
    public static Double calculateTax(Double total, Double taxRate) {
        return MastroApplicationUtils.roundTwoDecimals(total * (taxRate / 100));

    }

    /**
     * @param quantity
     * @param product
     * @return
     */
    public static Double calculateTaxableValueInSo(Double quantity, Product product) {
        Double taxableValue = quantity * product.getBasePrice();
        return Math.round(taxableValue * 100.0) / 100.0;
    }

    /**
     * @param taxableValue
     * @param cgstAmount
     * @param sgstAmount
     * @return
     */
    public static Double totalPriceForSO(Double taxableValue, Double cgstAmount, Double sgstAmount) {
        Double totalPrice = taxableValue + cgstAmount + sgstAmount;
        return Math.round(totalPrice * 100.0) / 100.0;
    }

    /**
     * Method with  round amount
     *
     * @param amtForRound
     * @param roundedGrandTotalInDouble
     * @return rounded value
     */
    public static Double roundAmount(Double amtForRound, Double roundedGrandTotalInDouble) {

        Double roundValue = 0.0d;
        roundValue = amtForRound - roundedGrandTotalInDouble;
        return MastroApplicationUtils.roundTwoDecimals(roundValue * (-1));
    }

    /**
     * Net price in sales slip
     *
     * @param rate
     * @param hsn
     * @param productSalesUOMConvertionFactor
     * @param discount
     * @return net price
     */
    public static Double totalNetPriceForSalesSlip(Double rate, HSN hsn, Double productSalesUOMConvertionFactor, Double discount) {

        Double itemRate = rate * productSalesUOMConvertionFactor;
        Double itemDiscountedrate = itemRate * (discount / 100.0);
        Double taxablevalueforoneitem = itemRate - itemDiscountedrate;
        Double singlesgst = taxablevalueforoneitem * ((hsn.getSgst()) / 100);
        Double singlecgst = taxablevalueforoneitem * ((hsn.getCgst()) / 100);
        Double netPrice = taxablevalueforoneitem + (singlesgst + singlecgst);
        return MastroApplicationUtils.roundTwoDecimals(netPrice);

    }

    public static int roundForGrandTotalInSalesSlip(Double amtForRound) {

        Double grandTotals = MastroApplicationUtils.roundTwoDecimals(amtForRound);
        double doubleNumber = grandTotals;
        String doubleAsString = String.valueOf(doubleNumber);
        int indexOfDecimal = doubleAsString.indexOf(".");
        String integernumberstring = doubleAsString.substring(0, indexOfDecimal);
        String decimalpart = doubleAsString.substring(indexOfDecimal);

        int intNumber = Integer.parseInt(integernumberstring);
        double lastDigit = intNumber % 10;
        int a = integernumberstring.length() - 1;
        int roundoffvalue;
        if (lastDigit < 5) {
            Double integernumber = Double.parseDouble(integernumberstring);
            Double number = integernumber;
            double place = 10;
            double result = number / place;
            result = Math.floor(result);
            result *= place;
            roundoffvalue = (int) result;

        } else {
            Double integernumber = Double.parseDouble(integernumberstring);
            Double number = integernumber;
            double place = 10;
            double result = number / place;
            result = Math.ceil(result);
            result *= place;
            roundoffvalue = (int) result;
        }
        return roundoffvalue;
    }

    /**
     * Method to calculate average consumption
     *
     * @param product
     * @param branch
     * @return average consumption
     */
    public static Double averageConsumption(Product product, Branch branch) {

        Double averageConsumption = 0.0d;
        Set<StockLedger> stockLedgers = product.getStockLedgers().stream()
                .filter(stockGeneralData -> (null != stockGeneralData))
                .filter(stockGeneralData -> stockGeneralData.getBranch().getId().equals(branch.getId()))
                .collect(Collectors.toSet());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int year = cal.get(Calendar.YEAR);
        String fromDate = year + "-01-01";
        String toDate = year + "-12-31";
        Set<StockLedger> stockLedgerFinal = new HashSet<>();
        for (StockLedger stockLedger : stockLedgers) {
            String creationDate = formatter.format(stockLedger.getCreationDate());
            if ((creationDate.compareToIgnoreCase(fromDate) >= 0) && (creationDate.compareToIgnoreCase(toDate) <= 0)) {
                stockLedgerFinal.add(stockLedger);
            }
        }
        Double totalIssued = 0.0d;
        for (StockLedger stockGenerall : stockLedgerFinal) {
            if (stockGenerall.getIssuedStock() != null) {
                totalIssued = totalIssued + stockGenerall.getIssuedStock();
            }

        }
        averageConsumption = totalIssued / 12;
        return MastroApplicationUtils.roundTwoDecimals(averageConsumption);

    }

}
