package com.capfsd.stock.controller;

import com.capfsd.stock.dto.CompositeCompanyDto;
import com.capfsd.stock.entity.StockPriceDetailEntity;
import com.capfsd.stock.service.IQueryService;
import com.capfsd.stock.service.IStockPriceReportService;
import com.capfsd.stock.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping(value = "/query")
public class QueryController {

    @Autowired
    private IQueryService iQueryService;

    @Autowired
    private IStockPriceReportService iStockPriceReportService;

    @GetMapping(value = "/se")
    public ResponseResult getByStockExchangeCode(
            @RequestParam(value = "stockExchangeCode", defaultValue = "", required = false) String stockExchangeCode)
        {
            List<CompositeCompanyDto> compositeCompanyDtoList = iQueryService.findByStockExchangeCode(stockExchangeCode);
            return ResponseResult.success("get companies successfully", compositeCompanyDtoList, null);
    }

    @GetMapping(value = "/price")
    public ResponseResult getPrice(
            @RequestParam(value = "companyCode", defaultValue = "", required = false) String companyCode,
            @RequestParam(value = "stockExchangeCode", defaultValue = "", required = false) String stockExchangeCode,
            @RequestParam(value = "rowNum", defaultValue="20", required = false) Integer rowNum)
    {
       List<StockPriceDetailEntity> stockPriceDetailEntityList = iStockPriceReportService.getStockPriceDetail(companyCode, stockExchangeCode);
       List<StockPriceDetailEntity> stockPriceDetailEntityListResult = new ArrayList<StockPriceDetailEntity>();

       int num=0;
       if(stockPriceDetailEntityList.size()<=rowNum)
           num=stockPriceDetailEntityList.size();
       else num= rowNum;

        DecimalFormat df = new DecimalFormat("#.00");

       for(int i=0;i<num; i++)
       {
           StockPriceDetailEntity stockPriceDetailEntity = stockPriceDetailEntityList.get(i);
           double pricePerShare = stockPriceDetailEntityList.get(i).getPricePerShare();
           double roundedPricePerShare = Double.valueOf(df.format(pricePerShare));
           stockPriceDetailEntity.setPricePerShare(roundedPricePerShare);
           stockPriceDetailEntityListResult.add(stockPriceDetailEntity);
       }
       return ResponseResult.success("get price successfully", stockPriceDetailEntityListResult, null);
    }
}
