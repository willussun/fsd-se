package com.capfsd.stock.controller;

import com.capfsd.stock.service.IStockPriceDetailService;
import com.capfsd.stock.util.LogUtils;
import com.capfsd.stock.vo.StockPriceDetailVo;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping
public class StockPriceDetailGenerator {
    @Autowired
    private IStockPriceDetailService stockPriceDetailService;

    private static final int MAX = 200;
    private static final int MIN = 100;

    @RequestMapping(value = "/generator", method = RequestMethod.GET)
    public void Generate(
            @RequestParam(value = "companyCode", defaultValue = "", required = true) String companyCode,
            @RequestParam(value = "sectorCode", defaultValue = "", required = true) String sectorCode,
            @RequestParam(value = "stockExchangeCode", defaultValue = "", required = true) String stockExchangeCode
            )
    {
        Integer max = 200;
        Integer min = 100;
            try {
            List<StockPriceDetailVo> stockPriceDetailVoList = new ArrayList<StockPriceDetailVo>();
            for (int k =1; k <= 12; k++)
            {
                String headDate;
                if(k<=9)
                {
                    headDate = "2019" + "-0" + k + "-";
                }
                else headDate = "2019" + "-" + k + "-";
            for (int i = 1; i <= 28; i++) {
                {
                    String strDate = null;
                    if(i <= 9)
                    {
                        strDate = headDate + "0" + i;
                    }
                    else strDate = headDate + i;

                    for (int j = 0; j < 2; j++) {
                        StockPriceDetailVo stockPriceDetailVo = new StockPriceDetailVo();
                        String strTime = (10 + j) + ":00" + ":00";
                        stockPriceDetailVo.setCompanyCode(companyCode);
                        stockPriceDetailVo.setStockExchangeCode(stockExchangeCode);
                        stockPriceDetailVo.setSectorCode(sectorCode);
                        Double price = null;
                        Double price2 = null;
                        price = Math.random() * (max - min) + min;
                        price2 = Math.random() * (max - min) + min;
                        price = price*price2;
                        stockPriceDetailVo.setPricePerShare(price);
                        java.sql.Date newDateSql = java.sql.Date.valueOf(strDate);
                        java.sql.Time newTimeSql = java.sql.Time.valueOf(strTime);
                        java.util.Date stockDatetime = new java.util.Date();
                        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        stockDatetime = f.parse(newDateSql.toString() + " " + newTimeSql.toString());
                        stockPriceDetailVo.setDate(newDateSql);
                        stockPriceDetailVo.setTime(newTimeSql);
                        stockPriceDetailVo.setDatetime(stockDatetime);
                        stockPriceDetailVoList.add(stockPriceDetailVo);
                    }
                }
            }
              }
            for (int m = 0; m < stockPriceDetailVoList.size(); m++) {
                stockPriceDetailService.addStockPriceDetail(stockPriceDetailVoList.get(m)); }

        } catch (ParseException e) {
                LogUtils.getInst(this).error(e.getMessage());
        }
    }
}