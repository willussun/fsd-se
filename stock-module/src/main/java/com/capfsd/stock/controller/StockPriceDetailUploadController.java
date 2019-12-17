package com.capfsd.stock.controller;

import com.capfsd.stock.dto.Constants;
import com.capfsd.stock.dto.IpoDetailDto;
import com.capfsd.stock.entity.IpoDetailEntity;
import com.capfsd.stock.entity.StockPriceDetailEntity;
import com.capfsd.stock.service.IIpoDetailService;
import com.capfsd.stock.service.IStockExchangeService;
import com.capfsd.stock.service.IStockPriceDetailService;
import com.capfsd.stock.util.ResponseResult;
import com.capfsd.stock.vo.StockPriceDetailVo;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import com.capfsd.stock.util.LogUtils;

@CrossOrigin
@RestController
@RequestMapping(value = "/import")
public class StockPriceDetailUploadController {
    @Autowired
    private IStockPriceDetailService stockPriceDetailService;

    @Autowired
    private IIpoDetailService ipoDetailService;
    @Autowired
    private IStockExchangeService stockExchangeService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseResult Upload(@RequestParam("file") MultipartFile file) throws Exception {
/*
        Format should be fixed, that means
        column 1: company code
        column 2: stock exchange code
        column 3: sector code
        column 4: price per share
        column 5: date (should be YYYY-MM-dd format)
        column 6: time (should be at HH:mm:ss format, and for simplicity, should be HH:00:00)
*/
        String[] columnName = {Constants.DATA_IMPORT_COMPANY_CODE, Constants.DATA_IMPORT_STOCK_CODE,  Constants.DATA_IMPORT_SECTOR_CODE, Constants.DATA_IMPORT_PRICE_PER_SHARE, Constants.DATA_IMPORT_DATE, Constants.DATA_IMPORT_TIME};
        String[] headerName = new String[6];
        String originalFileName = file.getOriginalFilename();
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(file.getInputStream());
            // Find out all of the number of sheets
            int numOfSheet = workbook.getNumberOfSheets();
            List<StockPriceDetailVo> stockPriceDetailVoList = new ArrayList<StockPriceDetailVo>();
            for (int i = 0; i < numOfSheet; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                // Get row counts for this sheet
                int lastRowNum = sheet.getLastRowNum();
                // Check whether first line is corresponding to the template
                Row firstRow = sheet.getRow(0);
                int lastCellNum = firstRow.getLastCellNum();
                // Processing first row

                for (int j = 0; j < lastCellNum; j++) {
                    headerName[j] = firstRow.getCell(j).getStringCellValue();
                }
                for (int j = 0; j < lastCellNum; j++) {
                    if (!(headerName[j].equals(columnName[j]))) {
                        return ResponseResult.fail("Incorrect format, please check and update", null);
                    }
                }
                // From the second line, for the processing
                for (int j = 1; j <= lastRowNum; j++) {
                    Row row = sheet.getRow(j);
                    StockPriceDetailVo stockPriceDetailVo = new StockPriceDetailVo();
                    String companyCode = row.getCell(0).getStringCellValue().trim();
                    String stockExchangeCode = row.getCell(1).getStringCellValue().trim();
                    String sectorCode = row.getCell(2).getStringCellValue().trim();
                    row.getCell(3).setCellType(Cell.CELL_TYPE_NUMERIC);
                    Double pricePerShare = null;
                    try {
                        pricePerShare = row.getCell(3).getNumericCellValue();
                    } catch (Exception e) {
                        LogUtils.getInst(this).debug(e.getMessage());
                        return ResponseResult.fail("Invalid numeric format, please check price per share and resubmit", null);
                    }
                    java.sql.Date stockDate = null;
                    java.sql.Date stockDate2 = null;
                    try {
                        java.util.Date date = row.getCell(4).getDateCellValue();
                        String pattern = "EEE MMM dd HH:mm:ss zzz yyyy";
                        Date date3 = new SimpleDateFormat(pattern, Locale.US).parse(date.toString());
                        stockDate2 = new java.sql.Date(date3.getTime());


                    } catch (Exception e) {
                        LogUtils.getInst(this).debug(e.getMessage());
                        return ResponseResult.fail("Invalid date format, please check and resubmit", null);
                    }
                    java.sql.Time stockTime = null;
                    java.sql.Time stockTime2 = null;
                    try {
                        java.util.Date date = row.getCell(5).getDateCellValue();
                        String pattern = "EEE MMM dd HH:mm:ss zzz yyyy";
                        Date time3 = new SimpleDateFormat(pattern, Locale.US).parse(date.toString());
                        stockTime2 = new java.sql.Time(time3.getTime());

                    } catch (Exception e) {
                        LogUtils.getInst(this).debug(e.getMessage());
                        return ResponseResult.fail("Invalid time format, please check and resubmit", null);
                    }

                    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    java.util.Date stockDatetime2 = new java.util.Date();
                    stockDatetime2 = f.parse(stockDate2.toString() + " " + stockTime2.toString());

                    //                   java.util.Date stockDatetime = new java.util.Date(stockDate.getTime() + stockTime.getTime());
                    stockPriceDetailVo.setCompanyCode(companyCode);
                    stockPriceDetailVo.setStockExchangeCode(stockExchangeCode);
                    stockPriceDetailVo.setSectorCode(sectorCode);
                    stockPriceDetailVo.setPricePerShare(pricePerShare);
                    stockPriceDetailVo.setDate(stockDate2);
                    stockPriceDetailVo.setTime(stockTime2);
                    stockPriceDetailVo.setDatetime(stockDatetime2);
                    stockPriceDetailVoList.add(stockPriceDetailVo);
                }

            }
            int updateCount = 0;
            int insertCount = 0;
            for (int num = 0; num < stockPriceDetailVoList.size(); num++) {
                List<StockPriceDetailEntity> stockPriceDetailEntityList = stockPriceDetailService.findStockPriceDetailInfo(stockPriceDetailVoList.get(num));
                if (stockPriceDetailEntityList == null || stockPriceDetailEntityList.size() == 0) {
                    stockPriceDetailService.addStockPriceDetail(stockPriceDetailVoList.get(num));
                    insertCount++;
                } else if (stockPriceDetailEntityList.size() == 1 && !(stockPriceDetailEntityList.get(0).getPricePerShare().equals(stockPriceDetailVoList.get(num).getPricePerShare()))) {
                    stockPriceDetailService.updateStockPriceDetail(stockPriceDetailVoList.get(num));
                    updateCount++;
                }
            }
                HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
                hashMap.put("updateCount", updateCount);
                hashMap.put("insertCount", insertCount);
                return ResponseResult.success("Updated record: " + updateCount + ", Insert record: " + insertCount, hashMap, null);

            } catch (Exception e) {
            LogUtils.getInst(this).debug(e.getMessage());
            return ResponseResult.fail("Failed due to system errors", null);
        }
    }


    @RequestMapping(value = "/precheck", method = RequestMethod.POST)
    public ResponseResult PreCheck(@RequestParam("file") MultipartFile file) throws Exception {
        String[] columnName = {"Company Code", "Stock Exchange Code", "Sector Code", "Price Per Share", "Date", "Time"};
        String[] headerName = new String[6];
        String originalFileName = file.getOriginalFilename();
        Map<String, Integer> headersMap = new HashMap<String, Integer>();
        Workbook workbook = null;

        // check whether stock exchange code exists in t_ipo_detail, that means already take effect.

        List<IpoDetailEntity> ipoDetailEntity = ipoDetailService.findAllIpoDetail();
        List<String> companyCodeList =new ArrayList<String>();
        for(int i=0; i<ipoDetailEntity.size(); i++) {
            companyCodeList.add(ipoDetailEntity.get(i).getCompanyCode());
        }

            try {
            workbook = new XSSFWorkbook(file.getInputStream());
            // Find out all of the number of sheets
            int numOfSheet = workbook.getNumberOfSheets();
            List<StockPriceDetailVo> stockPriceDetailVoList = new ArrayList<StockPriceDetailVo>();
            for (int i = 0; i < numOfSheet; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                // Get row counts for this sheet
                int lastRowNum = sheet.getLastRowNum();
                // Check whether first line is corresponding to the template
                Row firstRow = sheet.getRow(0);
                int lastCellNum = firstRow.getLastCellNum();
                // Processing first row

                for (int j = 0; j < lastCellNum; j++) {
                    headerName[j] = firstRow.getCell(j).getStringCellValue();
                }
                for (int j = 0; j < lastCellNum; j++) {
                    if (!(headerName[j].equals(columnName[j]))) {
                        return ResponseResult.fail("Incorrect format, please check and update", null);
                    }
                }

                // From the second line, for the processing
                for (int j = 1; j <= lastRowNum; j++) {
                    Row row = sheet.getRow(j);
                    StockPriceDetailVo stockPriceDetailVo = new StockPriceDetailVo();
                    String companyCode = row.getCell(0).getStringCellValue().trim();

                    int find = 0;
                    for(int w=0; w<companyCodeList.size(); w++)
                    {
                        if(companyCodeList.get(w).equals(companyCode.trim())){
                            find = 1;
                            break;
                        }
                    }
                    if(find == 0)
                    {
                        return ResponseResult.fail("Company code not exists in system", null);
                    }
                    String stockExchangeCode = row.getCell(1).getStringCellValue().trim();
                    String sectorCode = row.getCell(2).getStringCellValue().trim();
                    row.getCell(3).setCellType(Cell.CELL_TYPE_NUMERIC);
                    Double pricePerShare = null;
                    try {
                        pricePerShare = row.getCell(3).getNumericCellValue();
                    } catch (Exception e) {
                        LogUtils.getInst(this).debug(e.getMessage());
                        return ResponseResult.fail("Invalid numeric format, please check price per share and resubmit", null);
                    }
                    java.sql.Date stockDate = null;
                    java.sql.Date stockDate2 = null;
                    try {
                        java.util.Date date = row.getCell(4).getDateCellValue();
                        String pattern = "EEE MMM dd HH:mm:ss zzz yyyy";
                        Date date3 = new SimpleDateFormat(pattern, Locale.US).parse(date.toString());
                        stockDate2 = new java.sql.Date(date3.getTime());
                        System.out.println("STOOCKDATE STRING IS : " + stockDate2.toString());


                    } catch (Exception e) {
                        LogUtils.getInst(this).debug(e.getMessage());
                        return ResponseResult.fail("Invalid date format, please check and resubmit", null);
                    }
                    java.sql.Time stockTime = null;
                    java.sql.Time stockTime2 = null;
                    try {
                        java.util.Date date = row.getCell(5).getDateCellValue();
                        String pattern = "EEE MMM dd HH:mm:ss zzz yyyy";
                        Date time3 = new SimpleDateFormat(pattern, Locale.US).parse(date.toString());
                        stockTime2 = new java.sql.Time(time3.getTime());
                        System.out.println("STOOCKDATE STRING IS : " + stockTime2.toString());

                    } catch (Exception e) {
                        LogUtils.getInst(this).debug(e.getMessage());
                        return ResponseResult.fail("Invalid time format, please check and resubmit", null);
                    }

                    SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    java.util.Date stockDatetime2 = new java.util.Date();
                    stockDatetime2 = f.parse(stockDate2.toString() + " " + stockTime2.toString());

                    stockPriceDetailVo.setCompanyCode(companyCode);
                    stockPriceDetailVo.setStockExchangeCode(stockExchangeCode);
                    stockPriceDetailVo.setSectorCode(sectorCode);
                    stockPriceDetailVo.setPricePerShare(pricePerShare);
                    stockPriceDetailVo.setDate(stockDate2);
                    stockPriceDetailVo.setTime(stockTime2);
                    stockPriceDetailVo.setDatetime(stockDatetime2);
                    stockPriceDetailVoList.add(stockPriceDetailVo);

                }
                }
            int count = 0;
            int result = 0;
            for (int num = 0; num < stockPriceDetailVoList.size(); num++) {
                StockPriceDetailVo stockPriceDetailVo = stockPriceDetailVoList.get(num);
                List<StockPriceDetailEntity> stockPriceDetailEntityList = stockPriceDetailService.findStockPriceDetailInfo(stockPriceDetailVo);
                if(stockPriceDetailEntityList!=null && stockPriceDetailEntityList.size()!= 0) {
                    if (stockPriceDetailEntityList.size() == 1) {
                        count++;
                    }
                    if (stockPriceDetailEntityList.size() > 1) {
                        return ResponseResult.fail("Error occurred in original tables, more than 2 records found out.", null);
                    }
                }
            }
            LogUtils.getInst(this).info("Duplicate record number is : " + count);
            HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
            hashMap.put("count", count);
            return ResponseResult.success("Duplicate record number is : " + count, hashMap, null);
        }
        catch (Exception e) {
            LogUtils.getInst(this).debug(e.getMessage());
            return ResponseResult.fail("Failed due to system errors", null);
        }
    }
}
