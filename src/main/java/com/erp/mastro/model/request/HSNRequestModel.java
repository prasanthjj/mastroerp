package com.erp.mastro.model.request;

import java.util.Date;

public class HSNRequestModel {

    private Long id;
    private Date entryDate;
    private String section;
    private String chapter;
    private String heading;
    private String subHeading;
    private String hsnCode;
    private String gstGoodsName;
    private Date effectiveFrom;
    private Double sgst;
    private Double cgst;
    private Double igst;
    private Double utgst;
    private Double cess;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getSubHeading() {
        return subHeading;
    }

    public void setSubHeading(String subHeading) {
        this.subHeading = subHeading;
    }

    public String getHsnCode() {
        return hsnCode;
    }

    public void setHsnCode(String hsnCode) {
        this.hsnCode = hsnCode;
    }

    public String getGstGoodsName() {
        return gstGoodsName;
    }

    public void setGstGoodsName(String gstGoodsName) {
        this.gstGoodsName = gstGoodsName;
    }

    public Date getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(Date effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public Double getSgst() {
        return sgst;
    }

    public void setSgst(Double sgst) {
        this.sgst = sgst;
    }

    public Double getCgst() {
        return cgst;
    }

    public void setCgst(Double cgst) {
        this.cgst = cgst;
    }

    public Double getIgst() {
        return igst;
    }

    public void setIgst(Double igst) {
        this.igst = igst;
    }

    public Double getUtgst() {
        return utgst;
    }

    public void setUtgst(Double utgst) {
        this.utgst = utgst;
    }

    public Double getCess() {
        return cess;
    }

    public void setCess(Double cess) {
        this.cess = cess;
    }

    @Override
    public String toString() {
        return "HSNRequestModel{" +
                "id=" + id +
                ", entryDate=" + entryDate +
                ", section='" + section + '\'' +
                ", chapter='" + chapter + '\'' +
                ", heading='" + heading + '\'' +
                ", subHeading='" + subHeading + '\'' +
                ", hsnCode='" + hsnCode + '\'' +
                ", gstGoodsName='" + gstGoodsName + '\'' +
                ", effectiveFrom=" + effectiveFrom +
                ", sgst=" + sgst +
                ", cgst=" + cgst +
                ", igst=" + igst +
                ", utgst=" + utgst +
                ", cess=" + cess +
                '}';
    }
}
