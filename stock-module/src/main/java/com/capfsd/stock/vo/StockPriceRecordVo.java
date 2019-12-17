package com.capfsd.stock.vo;

public class StockPriceRecordVo {
    private Long companyId;

    public Long getCompanyId() {
        return companyId;
    }

    @Override
    public String toString() {
        return "StockPriceRecordVo{" +
                "companyId=" + companyId +
                ", stockExchangeId=" + stockExchangeId +
                ", sectorId=" + sectorId +
                ", companyCode='" + companyCode + '\'' +
                ", stockExchangeCode='" + stockExchangeCode + '\'' +
                ", sectorCode='" + sectorCode + '\'' +
                '}';
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getStockExchangeId() {
        return stockExchangeId;
    }

    public void setStockExchangeId(Long stockExchangeId) {
        this.stockExchangeId = stockExchangeId;
    }

    public Long getSectorId() {
        return sectorId;
    }

    public void setSectorId(Long sectorId) {
        this.sectorId = sectorId;
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

    public String getSectorCode() {
        return sectorCode;
    }

    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }

    private Long stockExchangeId;
    private Long sectorId;
    private String companyCode;
    private String stockExchangeCode;
    private String sectorCode;
}
