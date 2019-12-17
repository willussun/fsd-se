package com.capfsd.stock.dto;

import java.util.Date;

public class IpoDetailDto {
    // foreign key referencing companyDto
    private String ipoCode;
    private String companyCode;


    public String getOpenDatetime() {
        return openDatetime;
    }

    public void setOpenDatetime(String openDatetime) {
        this.openDatetime = openDatetime;
    }

    private String openDatetime;

    @Override
    public String toString() {
        return "IpoDetailDto{" +
                "ipoCode='" + ipoCode + '\'' +
                ", companyCode='" + companyCode + '\'' +
                ", openDatetime='" + openDatetime + '\'' +
                ", stockExchangeDto=" + stockExchangeDto +
                ", stockExchangeCode='" + stockExchangeCode + '\'' +
                ", pricePerShare=" + pricePerShare +
                ", totalNumberShare=" + totalNumberShare +
                ", remarks='" + remarks + '\'' +
                ", id=" + id +
                ", companyDto=" + companyDto +
                '}';
    }

    public String getIpoCode() {
        return ipoCode;
    }

    public void setIpoCode(String ipoCode) {
        this.ipoCode = ipoCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getStockExchangeCode() {
        return stockExchangeCode;
    }

    public void setStockExchangeCode(String stockExchangeCode) {
        this.stockExchangeCode = stockExchangeCode;
    }

    public Double getPricePerShare() {
        return pricePerShare;
    }

    public void setPricePerShare(Double pricePerShare) {
        this.pricePerShare = pricePerShare;
    }

    public Long getTotalNumberShare() {
        return totalNumberShare;
    }

    public void setTotalNumberShare(Long totalNumberShare) {
        this.totalNumberShare = totalNumberShare;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompanyDto getCompanyDto() {
        return companyDto;
    }

    public void setCompanyDto(CompanyDto companyDto) {
        this.companyDto = companyDto;
    }

    public StockExchangeDto getStockExchangeDto() {
        return stockExchangeDto;
    }

    public void setStockExchangeDto(StockExchangeDto stockExchangeDto) {
        this.stockExchangeDto = stockExchangeDto;
    }

    private StockExchangeDto stockExchangeDto;

    private String stockExchangeCode;
    private Double pricePerShare;
    private Long totalNumberShare;
    private String remarks;
    private Long id;
    private CompanyDto companyDto;
}
