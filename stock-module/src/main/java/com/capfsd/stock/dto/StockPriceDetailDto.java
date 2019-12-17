package com.capfsd.stock.dto;

import java.sql.Date;
import java.sql.Time;

public class StockPriceDetailDto {
    @Override
    public String toString() {
        return "StockPriceDetailDto{" +
                ", duration='" + duration + '\'' +
                ", pricePerShare=" + pricePerShare +
                ", companyCode='" + companyCode + '\'' +
                ", sectorCode='" + sectorCode + '\'' +
                '}';
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    private String duration;


    public Double getPricePerShare() {
        return pricePerShare;
    }

    public void setPricePerShare(Double pricePerShare) {
        this.pricePerShare = pricePerShare;
    }

    private Double pricePerShare;

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getSectorCode() {
        return sectorCode;
    }

    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }

    private String companyCode;
    private String sectorCode;
}
