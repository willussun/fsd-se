package com.capfsd.stock.controller;

import com.capfsd.stock.dto.StockPriceDetailDto;
import com.capfsd.stock.entity.StockPriceDetailEntity;
import com.capfsd.stock.service.IStockPriceReportService;
import com.capfsd.stock.util.LogUtils;
import com.capfsd.stock.util.ResponseResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/compare")

public class StockPriceReportController {
    @Autowired
    private IStockPriceReportService stockPriceReportService;

/*
    Following message is used for multi input, including several companies, or several sectors, or company vs sector
*/

    @GetMapping("/multiresult")
    public ResponseResult compareMultiJSON(
            @RequestParam(value = "companyCodeList", defaultValue = "", required = false) String companyCodeList,
            @RequestParam(value = "sectorCodeList", defaultValue = "", required = false) String sectorCodeList,
            @RequestParam(value = "stockExchangeCode", defaultValue = "", required = false) String stockExchangeCode,
            @RequestParam(value = "startDate", defaultValue = "", required = false) String startDate,
            @RequestParam(value = "endDate", defaultValue = "", required = false) String endDate,
            @RequestParam(value = "duration", defaultValue = "", required = false) String duration

    ) {
        // Need to order company first, then sector

        System.out.println("Company = " + companyCodeList);
        System.out.println("Sector = " + sectorCodeList);
        if ((StringUtils.isBlank(companyCodeList) || companyCodeList.trim().equals("null")) && (StringUtils.isBlank(sectorCodeList) || sectorCodeList.trim().equals("null"))) {
            return ResponseResult.fail("Your selection is empty.", null);
        }
        List<String> companyCode = null;
        if (!StringUtils.isBlank(companyCodeList) && !companyCodeList.equals("null")) {
            companyCode = Arrays.asList(companyCodeList.trim().split((",")));
        }
        List<String> sectorCode = null;
        if (!StringUtils.isBlank(sectorCodeList) && !sectorCodeList.equals("null")) {
            sectorCode = Arrays.asList(sectorCodeList.trim().split((",")));
        }

        List<HashMap<String, Object>> list = new ArrayList<>();

        HashMap<String, Object> result = new LinkedHashMap<String, Object>();
        try {
            HashMap<String, Object> compareResult = stockPriceReportService.compareMultiJSONResult(companyCode, sectorCode, stockExchangeCode, startDate, endDate, duration);
            if (compareResult == null) {
                return ResponseResult.fail("Your query result is empty.", null);
            }

            return ResponseResult.success("Your report is generated.", compareResult, null);

        } catch (Exception e) {
            LogUtils.getInst(this).debug(e.getMessage());
        }
        return null;
    }

    @GetMapping("/singleresult")
    public ResponseResult compareSingleJSON(
            @RequestParam(value = "companyCodeList", defaultValue = "", required = false) String companyCodeList,
            @RequestParam(value = "sectorCodeList", defaultValue = "", required = false) String sectorCodeList,
            @RequestParam(value = "stockExchangeCode", defaultValue = "", required = false) String stockExchangeCode,
            @RequestParam(value = "startDate", defaultValue = "", required = false) String startDate,
            @RequestParam(value = "endDate", defaultValue = "", required = false) String endDate,
            @RequestParam(value = "duration", defaultValue = "", required = false) String duration) {

        System.out.println("Company = " + companyCodeList);
        System.out.println("Sector = " + sectorCodeList);

        if ((StringUtils.isBlank(companyCodeList) || companyCodeList.trim().equals("null")) && (StringUtils.isBlank(sectorCodeList) || sectorCodeList.trim().equals("null"))) {
            return ResponseResult.fail("Your selection is empty.", null);
        }

        if (!StringUtils.isBlank(companyCodeList) && !companyCodeList.trim().equals("null") && !StringUtils.isBlank(sectorCodeList) && !sectorCodeList.trim().equals("null")) {
            return ResponseResult.fail("Single chart, only one selection is permitted", null);
        }

        List<String> companyCode = null;
        if (!StringUtils.isBlank(companyCodeList) && !companyCodeList.equals("null")) {
            companyCode = Arrays.asList(companyCodeList.trim().split((",")));
        }
        List<String> sectorCode = null;
        if (!StringUtils.isBlank(sectorCodeList) && !sectorCodeList.equals("null")) {
            sectorCode = Arrays.asList(sectorCodeList.trim().split((",")));
        }

        List<HashMap<String, Object>> list = new ArrayList<>();

        HashMap<String, Object> result = new LinkedHashMap<String, Object>();

        try {
            HashMap<String, Object> compareResult = stockPriceReportService.compareSingleJSONResult(companyCode, sectorCode, stockExchangeCode, startDate, endDate, duration);
            if (compareResult == null) {
                return ResponseResult.fail("Your query result is empty.", null);
            }

            return ResponseResult.success("Your report is generated.", compareResult, null);

        } catch (Exception e) {
            LogUtils.getInst(this).debug(e.getMessage());
        }
        return null;
    }


    @GetMapping("/export")
    public ResponseResult exportReport(
            @RequestParam(value = "companyCodeList", defaultValue = "", required = false) String companyCodeList,
            @RequestParam(value = "sectorCodeList", defaultValue = "", required = false) String sectorCodeList,
            @RequestParam(value = "stockExchangeCode", defaultValue = "", required = false) String stockExchangeCode,
            @RequestParam(value = "startDate", defaultValue = "", required = false) String startDate,
            @RequestParam(value = "endDate", defaultValue = "", required = false) String endDate,
            @RequestParam(value = "duration", defaultValue = "", required = false) String duration) {
        System.out.println("Company = " + companyCodeList);
        System.out.println("Sector = " + sectorCodeList);
        if ((StringUtils.isBlank(companyCodeList) || companyCodeList.trim().equals("null")) && (StringUtils.isBlank(sectorCodeList) || sectorCodeList.trim().equals("null"))) {
            return ResponseResult.fail("Your selection is empty.", null);
        }
        List<String> companyCode = null;
        if (!StringUtils.isBlank(companyCodeList) && !companyCodeList.equals("null")) {
            companyCode = Arrays.asList(companyCodeList.trim().split((",")));
        }
        List<String> sectorCode = null;
        if (!StringUtils.isBlank(sectorCodeList) && !sectorCodeList.equals("null")) {
            sectorCode = Arrays.asList(sectorCodeList.trim().split((",")));
        }
        List<StockPriceDetailDto> stockPriceDetailDtoList = new ArrayList<StockPriceDetailDto>();
        try {
            stockPriceDetailDtoList = stockPriceReportService.exportResult(companyCode, sectorCode, stockExchangeCode, startDate, endDate, duration);
        }
        catch (Exception e) {
            LogUtils.getInst(this).debug(e.getMessage());
        }
        return ResponseResult.success("Your report is successfully downloaded", stockPriceDetailDtoList, null);
    }

}
