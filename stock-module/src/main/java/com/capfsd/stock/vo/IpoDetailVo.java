package com.capfsd.stock.vo;
//import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/* Vo need to include company_id which could be mapped and find out the company information, however, it doesn't need
 to include whole company Vo, seems IpoDetail can't modify information of company*/

public class IpoDetailVo {
    @NotBlank(message = "ipo must not be blank")
    private String ipoCode;

    public String getOpenDatetime() {
        return openDatetime;
    }

    @Override
    public String toString() {
        return "IpoDetailVo{" +
                "ipoCode='" + ipoCode + '\'' +
                ", openDatetime='" + openDatetime + '\'' +
                ", companyCode='" + companyCode + '\'' +
                ", stockExchangeCode='" + stockExchangeCode + '\'' +
                ", pricePerShare=" + pricePerShare +
                ", totalNumberShare=" + totalNumberShare +
                ", companyId=" + companyId +
                ", stockExchangeId=" + stockExchangeId +
                ", id=" + id +
                ", remarks='" + remarks + '\'' +
                '}';
    }

    public void setOpenDatetime(String openDatetime) {
        this.openDatetime = openDatetime;
    }

    private String openDatetime;

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

    @NotBlank(message = "company code must not be blank")
    private String companyCode;

    @NotBlank(message = "stockExchangeCode must not be blank")
    private String stockExchangeCode;

    private Double pricePerShare;

    private Long totalNumberShare;

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    private Long companyId;

    public Long getStockExchangeId() {
        return stockExchangeId;
    }

    public void setStockExchangeId(Long stockExchangeId) {
        this.stockExchangeId = stockExchangeId;
    }

    private Long stockExchangeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    private Long id;

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    private String remarks;
}
