package com.capfsd.stock.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_ipo_detail")
public class IpoDetailEntity extends BaseEntity {
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

    public CompanyEntity getCompanyEntity() {
        return companyEntity;
    }

    public void setCompanyEntity(CompanyEntity companyEntity) {
        this.companyEntity = companyEntity;
    }
//    7. total_number_share
//    10. remark
//    11. company_sys_id???			12345678

    @Column(name = "ipo_code", unique = true, nullable = false)
    private String ipoCode;

    @Column(name = "company_code", nullable = false)
    private String companyCode;

    @Column(name = "stock_exchange_code", nullable = false)
    private String stockExchangeCode;

    @Column(name = "price_per_share", nullable = false)
    private Double pricePerShare;

    @Column(name = "total_number_share", nullable = false)
    private Long totalNumberShare;

    @Column(name= "remarks")
    private String remarks;

//    @ManyToOne
//    @JsonIgnore
//    @JoinColumn(name = "company_id", insertable = false, updatable = false)
//    private CompanyEntity companyEntity;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "company_id")
//    @JsonIgnoreProperties
//    @ToString.Exclude
//    @Column(name = "company_id", nullable = false)
//    private long companyId;
//    private CompanyEntity companyEntity;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="company_id")
    private CompanyEntity companyEntity;

    public StockExchangeEntity getStockExchangeEntity() {
        return stockExchangeEntity;
    }

    public void setStockExchangeEntity(StockExchangeEntity stockExchangeEntity) {
        this.stockExchangeEntity = stockExchangeEntity;
    }

    @Override
    public String toString() {
        return "IpoDetailEntity{" +
                "ipoCode='" + ipoCode + '\'' +
                ", companyCode='" + companyCode + '\'' +
                ", stockExchangeCode='" + stockExchangeCode + '\'' +
                ", pricePerShare=" + pricePerShare +
                ", totalNumberShare=" + totalNumberShare +
                ", remarks='" + remarks + '\'' +
                ", openDatetime=" + openDatetime +
                '}';
    }

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="se_id")
    private StockExchangeEntity stockExchangeEntity;

    public Date getOpenDatetime() {
        return openDatetime;
    }

    public void setOpenDatetime(Date openDatetime) {
        this.openDatetime = openDatetime;
    }

    @Column(name = "open_datetime")
    private java.util.Date openDatetime;

}
