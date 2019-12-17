package com.capfsd.stock.vo;
import com.capfsd.stock.dto.StockPriceRecordDto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class StockPriceCompareVo {
    public String getCompareAreaType() {
        return compareAreaType;
    }

    public void setCompareAreaType(String compareAreaType) {
        this.compareAreaType = compareAreaType;
    }

    public String getCompareDateType() {
        return compareDateType;
    }

    public void setCompareDateType(String compareDateType) {
        this.compareDateType = compareDateType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    private String compareAreaType;

    public List<StockPriceRecordVo> getStockPriceRecordVo() {
        return stockPriceRecordVo;
    }

    @Override
    public String toString() {
        return "StockPriceCompareVo{" +
                "compareAreaType='" + compareAreaType + '\'' +
                ", compareDateType='" + compareDateType + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", stockPriceRecordVo=" + stockPriceRecordVo +
                '}';
    }

    public void setStockPriceRecordVo(List<StockPriceRecordVo> stockPriceRecordVo) {
        this.stockPriceRecordVo = stockPriceRecordVo;
    }

    private String compareDateType;
    private Date startDate;
    private Date endDate;

    private List<StockPriceRecordVo>  stockPriceRecordVo = new ArrayList<StockPriceRecordVo>();
}
