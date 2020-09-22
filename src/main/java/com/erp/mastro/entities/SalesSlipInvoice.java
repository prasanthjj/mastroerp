package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "sales_slip_invoice")
public class SalesSlipInvoice extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_slip_id")
    private SalesSlip salesSlip;

    @Column(name = "total_amt")
    private Double totalAmt;

    @Column(name = "round_off")
    private Double roundOff;

    @Column(name = "grand_total")
    private Double grandTotal;

    @Column(name = "total_loading_charge")
    private Double totalLoadingCharge;

    @Column(name = "total_cess")
    private Double totalCess;

    @Column(name = "sub_total")
    private Double subTotal;

    @Column(name = "total_taxable_value")
    private Double totalTaxableValue;

    @Column(name = "total_cgst")
    private Double totalCgst;

    @Column(name = "total_sgst")
    private Double totalSgst;

    @Column(name = "sales_slip_invoice_no")
    private String salesSlipInvoiceNo;

}
