package com.capfsd.stock.service.impl;

import com.capfsd.stock.dto.*;
import com.capfsd.stock.entity.StockPriceDetailEntity;
import com.capfsd.stock.feign.SectorServiceFeignClient;
import com.capfsd.stock.repository.StockPriceReportRepository;
import com.capfsd.stock.service.*;
import com.capfsd.stock.util.LogUtils;
import com.capfsd.stock.util.ResponseResult;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class StockPriceReportServiceImpl implements IStockPriceReportService {
    @Autowired
    private StockPriceReportRepository stockPriceReportRepository;
    @Autowired
    private IIpoDetailService ipoDetailService;
    @Autowired
    private ICompanyService companyService;
    @Autowired
    private IQueryService queryService;

    @Autowired
    private SectorServiceFeignClient sectorServiceFeignClient;

    @Override
    // Used to compare and draw up the charts

    public StockPriceComposerDto composeList(List<String> companyCode, List<String> sectorCode, String stockExchangeCode, String startDate, String endDate, String duration) throws Exception {

       /*  Need to find out corresponding company code related to the company, and includes in the selected stock exchange, that means to get company code from company name
         select company_code from t_ipo_detail where company_name like '%companyName%'
         query t_stock_price_detail for finding detail date, time and price for all of the company_code selected
        If selection is company, the stock_exchange_code is already been selected*/

        // List<StockPriceRecordDto> stockPriceRecordDtoList = new ArrayList<StockPriceRecordDto>();
        List<List<StockPriceDetailEntity>> stock = new ArrayList<List<StockPriceDetailEntity>>();

        List<CompositeCompanyDto> compositeCompanyDtoList = new ArrayList<CompositeCompanyDto>();

        try {
            List<StockPriceDetailEntity> stockPriceDetailEntityList = new ArrayList<StockPriceDetailEntity>();

            if (companyCode != null) {
                for (int i = 0; i < companyCode.size(); i++) {
                    switch (duration) {
                        case "Y":
                            stockPriceDetailEntityList = stockPriceReportRepository.getDetailForCompanyByYear(companyCode.get(i), stockExchangeCode, startDate, endDate);
                            break;
                        case "M":
                            stockPriceDetailEntityList = stockPriceReportRepository.getDetailForCompanyByMonth(companyCode.get(i), stockExchangeCode, startDate, endDate);
                            break;
                        case "W":
                            stockPriceDetailEntityList = stockPriceReportRepository.getDetailForCompanyByWeek(companyCode.get(i), stockExchangeCode, startDate, endDate);
                            break;
                        case "Q":
                            stockPriceDetailEntityList = stockPriceReportRepository.getDetailForCompanyByQuarter(companyCode.get(i), stockExchangeCode, startDate, endDate);
                            break;
                    }
                    if (stockPriceDetailEntityList == null) {
                        break;
                    }
                    stock.add(stockPriceDetailEntityList);
                }
            }
            if (sectorCode != null) {
                for (int i = 0; i < sectorCode.size(); i++) {
                    switch (duration) {
                        case "Y":
                            stockPriceDetailEntityList = stockPriceReportRepository.getDetailForSectorByYear(sectorCode.get(i), stockExchangeCode, startDate, endDate);
                            break;
                        case "M":
                            stockPriceDetailEntityList = stockPriceReportRepository.getDetailForSectorByMonth(sectorCode.get(i), stockExchangeCode, startDate, endDate);
                            break;
                        case "W":
                            stockPriceDetailEntityList = stockPriceReportRepository.getDetailForSectorByWeek(sectorCode.get(i), stockExchangeCode, startDate, endDate);
                            break;
                        case "Q":
                            stockPriceDetailEntityList = stockPriceReportRepository.getDetailForSectorByQuarter(sectorCode.get(i), stockExchangeCode, startDate, endDate);
                            break;
                    }
                    if (stockPriceDetailEntityList == null) {
                        break;
                    }
                    stock.add(stockPriceDetailEntityList);
                }
            }
        } catch (
                Exception e) {
            LogUtils.getInst(this).error(e.getMessage());
        }

        if (stock == null) {
            return null;
        }

        List<StockPriceRecordListDto> stockPriceTotalList = new ArrayList<StockPriceRecordListDto>();

        // stockPriceRecordListDto represents one company or one section for comparison
        for (
                int count = 0; count < stock.size(); count++) {
            List<StockPriceDetailEntity> stockPriceDetailList = stock.get(count);
            // sp represents one query
            StockPriceRecordListDto stockPriceRecordListDto = new StockPriceRecordListDto();
            List<StockPriceRecordDto> stockPriceRecordList = new ArrayList<StockPriceRecordDto>();
            for (int l = 0; l < stockPriceDetailList.size(); l++) {
                StockPriceRecordDto stockPriceRecordDto = new StockPriceRecordDto();
                BeanUtils.copyProperties(stockPriceDetailList.get(l), stockPriceRecordDto);
                stockPriceRecordList.add(stockPriceRecordDto);
            }
            stockPriceRecordListDto.setStockPriceRecordDto(stockPriceRecordList);
            stockPriceTotalList.add(stockPriceRecordListDto);
        }

        // Get first list to handle about the header, currently represents date+time
        List<StockPriceDetailEntity> stockPriceHeaderList = stock.get(0);
        List<StockPriceHeaderRecordDto> stockPriceHeaderRecordList = new ArrayList<StockPriceHeaderRecordDto>();
        for (
                int m = 0; m < stockPriceHeaderList.size(); m++) {
            java.sql.Date date = stockPriceHeaderList.get(m).getDate();
            java.sql.Time time = stockPriceHeaderList.get(m).getTime();
            java.util.Date datetime = stockPriceHeaderList.get(m).getDatetime();
            // StockExchangeCode is used for label
            String label = stockPriceHeaderList.get(m).getStockExchangeCode();
            StockPriceHeaderRecordDto stockPriceHeaderRecordDto = new StockPriceHeaderRecordDto();
            stockPriceHeaderRecordDto.setDate(date.toString());
            stockPriceHeaderRecordDto.setTime(time.toString());
            stockPriceHeaderRecordDto.setDatetime(datetime.toString());
            stockPriceHeaderRecordDto.setStockExchangeCode(label);
            stockPriceHeaderRecordList.add(stockPriceHeaderRecordDto);
        }

        StockPriceHeaderDto stockPriceHeaderDto = new StockPriceHeaderDto();
        stockPriceHeaderDto.setStockPriceHeaderRecordDtoList(stockPriceHeaderRecordList);
        StockPriceComposerDto stockPriceComposerDto = new StockPriceComposerDto();
        stockPriceComposerDto.setStockPriceRecordListDto(stockPriceTotalList);
        stockPriceComposerDto.setStockPriceHeaderDto(stockPriceHeaderDto);
        return stockPriceComposerDto;
    }

    @Override
    public HashMap<String, Object> compareSingleJSONResult
            (List<String> companyCode, List<String> sectorCode, String stockExchangeCode, String startDate, String
                    endDate, String duration) {
        HashMap<String, Object> jsonFusionComponentMap = new LinkedHashMap<String, Object>();
        // Composing jsonFusionChart
        //      JSONObject jsonFusionChart = new JSONObject();
        HashMap<String, Object> jsonFusionChartMap = new LinkedHashMap<String, Object>();
        //      jsonFusionChart.put("theme", "fusion");

        jsonFusionComponentMap.put(Constants.FC_CHART_NAME, jsonFusionChartMap);
        jsonFusionChartMap.put(Constants.FC_THEME_NAME, Constants.FC_THEME_VALUE);
        jsonFusionChartMap.put(Constants.FC_CAPTION_NAME, Constants.FC_CAPTION_VALUE);
        String xaxisValue = null;
        switch (duration) {
            case "Y":
                xaxisValue = "By year";
                break;
            case "Q":
                xaxisValue = "By quarter";
                break;
            case "M":
                xaxisValue = "By month";
                break;
            case "W":
                xaxisValue = "By week";
                break;
        }
        jsonFusionChartMap.put(Constants.FC_XAXIS_NAME, xaxisValue);
        jsonFusionChartMap.put(Constants.FC_YAXIS_NAME, Constants.FC_YAXIS_VALUE);
        jsonFusionChartMap.put(Constants.FC_NUMBER_SUFFIX_NAME, Constants.FC_NUMBER_SUFFIX_VALUE);
        jsonFusionChartMap.put(Constants.FC_SHOW_NAME, Constants.FC_SHOW_VALUE);
        jsonFusionChartMap.put(Constants.FC_FORMAT_NUMBER_SCALE_NAME, Constants.FC_FORMAT_NUMBER_SCALE_VALUE);

        List<String> code = new ArrayList<String>();
        if (companyCode != null) {

            for (int count = 0; count < companyCode.size(); count++) {
                code.add(companyCode.get(count));
            }
        }

        if (sectorCode != null) {
            for (int count = 0; count < sectorCode.size(); count++) {
                code.add(sectorCode.get(count));
            }
        }

        StockPriceComposerDto stockPriceComposerDto = null;
        // Invoke compare method, return back StockPriceComposerDto representing the head and dataset
        try {

            stockPriceComposerDto = composeList(companyCode, sectorCode, stockExchangeCode, startDate, endDate, duration);
            if (stockPriceComposerDto == null) {
                return null;
            }

            StockPriceHeaderDto stockPriceHeaderDto = stockPriceComposerDto.getStockPriceHeaderDto();
            List<StockPriceHeaderRecordDto> stockPriceHeaderRecordDtoList = stockPriceHeaderDto.getStockPriceHeaderRecordDtoList();
            List<StockPriceRecordListDto> stockPriceRecordList = stockPriceComposerDto.getStockPriceRecordListDto();

            int size = stockPriceHeaderRecordDtoList.size();
            if (stockPriceRecordList.size() > 1)
                return null;
            int size2 = stockPriceRecordList.get(0).getStockPriceRecordDto().size();

            if (size != size2)
                return null;

            StockPriceRecordListDto stockPriceRecordListDto = stockPriceRecordList.get(0);
            //     List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
            List jsonDataRecordArrayList = new ArrayList();
            List<StockPriceRecordDto> stockPriceRecordDtoList = stockPriceRecordListDto.getStockPriceRecordDto();
            // Repeating and iterating through whole Dto composed and responded from entity query

            if (stockPriceComposerDto.getStockPriceRecordListDto().size() != 1) {
                return null;
            }

            for (int i = 0; i < size; i++) {
                // For each company or sector, set the label to be displayed, usually shoud be date or time, equal
                // to all of the comapnies or sectors selected
                //       jsonFusionCategoryArray.put(i, new JSONObject().put("label", stockPriceHeaderRecordDtoList.get(i).getDate() + "-" + stockPriceHeaderRecordDtoList.get(i).getTime()));
                HashMap dataRecord = new LinkedHashMap();
                dataRecord.put(Constants.FC_LABEL_NAME, stockPriceHeaderRecordDtoList.get(i).getStockExchangeCode());
                StockPriceRecordDto stockPriceRecordDto = stockPriceRecordDtoList.get(i);
                //       jsonDataRecordArray.put(i, new JSONObject().put("value", stockPriceRecordDto.getPricePerShare()));
                dataRecord.put(Constants.FC_VALUE_NAME, stockPriceRecordDto.getPricePerShare());
                jsonDataRecordArrayList.add(dataRecord);
            }

            jsonFusionComponentMap.put(Constants.FC_DATA_NAME, jsonDataRecordArrayList);
            return jsonFusionComponentMap;
        } catch (Exception e) {
            LogUtils.getInst(this).error(e.getMessage());
        }
        return null;
    }


    @Override
    public HashMap<String, Object> compareMultiJSONResult
            (List<String> companyCode, List<String> sectorCode, String stockExchangeCode, String startDate, String
                    endDate, String duration) throws Exception {
        //       JSONObject jsonFusionComponent = new JSONObject();

        HashMap<String, Object> jsonFusionComponentMap = new LinkedHashMap<String, Object>();
        // Composing jsonFusionChart
        //      JSONObject jsonFusionChart = new JSONObject();
        HashMap<String, Object> jsonFusionChartMap = new LinkedHashMap<String, Object>();
        //      jsonFusionChart.put("theme", "fusion");

        jsonFusionComponentMap.put(Constants.FC_CHART_NAME, jsonFusionChartMap);
        jsonFusionChartMap.put(Constants.FC_THEME_NAME, Constants.FC_THEME_VALUE);
        // jsonFusionChartMap.put(Constants.FC_CAPTION_NAME, Constants.FC_CAPTION_VALUE);
        jsonFusionChartMap.put(Constants.FC_CAPTION_NAME, Constants.FC_CAPTION_VALUE);
        String xaxisValue = null;
        switch (duration) {
            case "Y":
                xaxisValue = "By year";
                break;
            case "Q":
                xaxisValue = "By quarter";
                break;
            case "M":
                xaxisValue = "By month";
                break;
            case "W":
                xaxisValue = "By week";
                break;
        }
        jsonFusionChartMap.put(Constants.FC_XAXIS_NAME, xaxisValue);
        jsonFusionChartMap.put(Constants.FC_YAXIS_NAME, Constants.FC_YAXIS_VALUE);
        jsonFusionChartMap.put(Constants.FC_NUMBER_SUFFIX_NAME, Constants.FC_NUMBER_SUFFIX_VALUE);
        jsonFusionChartMap.put(Constants.FC_PLOT_FILL_ALPHA_NAME, Constants.FC_PLOT_FILL_ALPHA_VALUE);
        jsonFusionChartMap.put(Constants.FC_DIV_LINE_IS_DASHED_NAME, Constants.FC_DIV_LINE_IS_DASHED_VALUE);
        jsonFusionChartMap.put(Constants.FC_DIV_LINE_DASH_LEN_NAME, Constants.FC_DIV_LINE_DASH_LEN_NAME);
        jsonFusionChartMap.put(Constants.FC_DIV_LINE_DASH_GAP_NAME, Constants.FC_DIV_LINE_DASH_GAP_VALUE);
        jsonFusionChartMap.put(Constants.FC_SHOW_NAME, Constants.FC_SHOW_VALUE);
        jsonFusionChartMap.put(Constants.FC_FORMAT_NUMBER_SCALE_NAME, Constants.FC_FORMAT_NUMBER_SCALE_VALUE);

//        StringBuffer stf = new StringBuffer();
//        for(int i=0; i<code.size(); i++)
//        {
//            stf.append(code.get(i)).append(" ");
//        }
        //       jsonFusionChart.put("caption", "Comparison among " + stf.toString());
        //       jsonFusionChart.put("xAxisname", "Quarter");
        //       jsonFusionChart.put("yAxisname", "Stock Price");
        //       jsonFusionChart.put("numberPrefix", "$");
        //       jsonFusionChart.put("plotFillAlpha", "80");
        //       jsonFusionChart.put("divLineIsDashed", "1");
        //       jsonFusionChart.put("divLineDashLen", "1");
        //       jsonFusionChart.put("divLineGapLen", "1");
        //       jsonFusionComponent.put("chart", jsonFusionChart);


        List<HashMap<String, String>> code = new ArrayList<HashMap<String, String>>();
        if (companyCode != null) {

            for (int count = 0; count < companyCode.size(); count++) {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("TYPE", "COMPANY");
                hashMap.put("CODE", companyCode.get(count));
                code.add(hashMap);
            }
        }

        if (sectorCode != null) {
            for (int count = 0; count < sectorCode.size(); count++) {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("TYPE", "SECTOR");
                hashMap.put("CODE", sectorCode.get(count));
                code.add(hashMap);
            }
        }

        try {
            StockPriceComposerDto stockPriceComposerDto = null;
            // Invoke compare method, return back StockPriceComposerDto representing the head and dataset
            stockPriceComposerDto = composeList(companyCode, sectorCode, stockExchangeCode, startDate, endDate, duration);
            if (stockPriceComposerDto == null) {
                return null;
            }

            StockPriceHeaderDto stockPriceHeaderDto = stockPriceComposerDto.getStockPriceHeaderDto();
            List<StockPriceHeaderRecordDto> stockPriceHeaderRecordDtoList = stockPriceHeaderDto.getStockPriceHeaderRecordDtoList();
            // Composing category
            //     JSONArray jsonFusionCategoryArray = new JSONArray();
            List jsonFusionCategoryArrayList = new ArrayList();

            for (int i = 0; i < stockPriceHeaderRecordDtoList.size(); i++) {
                // For each company or sector, set the label to be displayed, usually shoud be date or time, equal
                // to all of the comapnies or sectors selected
                //       jsonFusionCategoryArray.put(i, new JSONObject().put("label", stockPriceHeaderRecordDtoList.get(i).getDate() + "-" + stockPriceHeaderRecordDtoList.get(i).getTime()));
                HashMap label = new LinkedHashMap();
                label.put(Constants.FC_LABEL_NAME, stockPriceHeaderRecordDtoList.get(i).getStockExchangeCode());
                jsonFusionCategoryArrayList.add(label);
            }

            //   JSONObject jsonFusionCategory = new JSONObject();
            HashMap<String, Object> jsonFusionCategoryMap = new LinkedHashMap<String, Object>();
            //   jsonFusionCategory.put("category", jsonFusionCategoryArray);
            jsonFusionCategoryMap.put(Constants.FC_CATEGORY_NAME, jsonFusionCategoryArrayList);
            //    JSONArray jsonFusionCategoriesArray = new JSONArray();
            List jsonFusionCategoriesArrayList = new ArrayList();
            //   jsonFusionCategoriesArray.put(0,jsonFusionCategory);
            jsonFusionCategoriesArrayList.add(jsonFusionCategoryMap);
            //    List<JSONObject>  jsonDataSetRoot= new ArrayList<JSONObject>();
            List<HashMap<String, Object>> jsonDataSetRootList = new ArrayList<HashMap<String, Object>>();
            // Composing data set
            //    JSONArray jsonFusionDataSetArray = new JSONArray();
            List jsonFusionDataSetArrayList = new ArrayList();
            // Repeating for the company or sector to be compared
            List<StockPriceRecordListDto> stockPriceRecordList = stockPriceComposerDto.getStockPriceRecordListDto();
            for (int j = 0; j < stockPriceRecordList.size(); j++) {
                StockPriceRecordListDto stockPriceRecordListDto = stockPriceRecordList.get(j);
                //     List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
                List<LinkedHashMap<String, Object>> jsonObjectArrayList = new ArrayList<LinkedHashMap<String, Object>>();
                // Following is one dataset only
                //    JSONObject jsonDataSet = new JSONObject();
                HashMap<String, Object> jsonDataSetMap = new LinkedHashMap<String, Object>();
                //    JSONArray jsonDataRecordArray = new JSONArray();
                List jsonDataRecordArrayList = new ArrayList();
                List<StockPriceRecordDto> stockPriceRecordDtoList = stockPriceRecordListDto.getStockPriceRecordDto();
                // Repeating and iterating through whole Dto composed and responded from entity query
                for (int i = 0; i < stockPriceRecordDtoList.size(); i++) {
                    StockPriceRecordDto stockPriceRecordDto = stockPriceRecordDtoList.get(i);
                    //       jsonDataRecordArray.put(i, new JSONObject().put("value", stockPriceRecordDto.getPricePerShare()));
                    HashMap hashMap = new LinkedHashMap();
                    hashMap.put(Constants.FC_VALUE_NAME, stockPriceRecordDto.getPricePerShare());
                    jsonDataRecordArrayList.add(hashMap);
                }

                //   jsonDataSet.put("data", jsonDataRecordArray);
                jsonDataSetMap.put(Constants.FC_DATA_NAME, jsonDataRecordArrayList);
                // Finding and setting for the seriesname, usually to be company name or sector name
                //   jsonDataSet.put("seriesname", code[j]);
                //             jsonDataSetMap.put(Constants.FC_SERIESNAME_NAME, code.get(j));


                HashMap hashMap = code.get(j);
                String type = (String) hashMap.get("TYPE");
                if (type.equals("COMPANY")) {
                    List<CompositeCompanyDto> compositeCompanyDtoList = queryService.findByCompanyCodeAndStockExchangeCode(stockExchangeCode, (String) hashMap.get("CODE"));
                    jsonDataSetMap.put(Constants.FC_SERIESNAME_NAME, compositeCompanyDtoList.get(0).getCompanyName());
                } else if (type.equals("SECTOR")) {
//                    List<SectorDto> sectorDtoList = sectorService.findBySectorCode((String) hashMap.get("CODE"));
//                    jsonDataSetMap.put(Constants.FC_SERIESNAME_NAME, sectorDtoList.get(0).getSectorName());
                      String currentSectorCode = (String)hashMap.get("CODE");
                      ResponseResult<SectorDto> sectorResult = sectorServiceFeignClient.getBySectorCode(currentSectorCode);
                      String sectorName =  (sectorResult.getCode() == 0 ? sectorResult.getData().getSectorName() : "");
                      jsonDataSetMap.put(Constants.FC_SERIESNAME_NAME, sectorName);
                }


                // Adding composed dataset into json data object
                //   jsonDataSetRoot.add(jsonDataSet);
                jsonDataSetRootList.add(jsonDataSetMap);
            }
            //   jsonFusionComponent.put("dataset", jsonDataSetRoot);
            jsonFusionComponentMap.put(Constants.FC_DATASET_NAME, jsonDataSetRootList);
            //   jsonFusionComponent.put("categories", jsonFusionCategoriesArray);
            jsonFusionComponentMap.put(Constants.FC_CATEGORIES_NAME, jsonFusionCategoriesArrayList);
            LogUtils.getInst(this).debug(jsonFusionComponentMap.toString());
            return jsonFusionComponentMap;
        } catch (Exception e) {
            LogUtils.getInst(this).error(e.getMessage());
        }
        return null;
    }

    public List<StockPriceDetailDto> exportResult(List<String> companyCode, List<String> sectorCode, String stockExchangeCode, String startDate, String
            endDate, String duration) throws Exception {
        List<StockPriceDetailEntity> stockPriceDetailEntityList = new ArrayList<StockPriceDetailEntity>();
        List<StockPriceDetailDto> stockPriceDetailDtoList = new ArrayList<StockPriceDetailDto>();
        if (companyCode != null) {
            for (int i = 0; i < companyCode.size(); i++) {
                switch (duration) {
                    case "Y":
                        stockPriceDetailEntityList = stockPriceReportRepository.getDetailForCompanyByYear(companyCode.get(i), stockExchangeCode, startDate, endDate);
                        break;
                    case "M":
                        stockPriceDetailEntityList = stockPriceReportRepository.getDetailForCompanyByMonth(companyCode.get(i), stockExchangeCode, startDate, endDate);
                        break;
                    case "W":
                        stockPriceDetailEntityList = stockPriceReportRepository.getDetailForCompanyByWeek(companyCode.get(i), stockExchangeCode, startDate, endDate);
                        break;
                    case "Q":
                        stockPriceDetailEntityList = stockPriceReportRepository.getDetailForCompanyByQuarter(companyCode.get(i), stockExchangeCode, startDate, endDate);
                        break;
                }
                for (int j = 0; j < stockPriceDetailEntityList.size(); j++) {
                    StockPriceDetailDto stockPriceDetailDto = new StockPriceDetailDto();
                    stockPriceDetailDto.setDuration(stockPriceDetailEntityList.get(j).getStockExchangeCode());
                    stockPriceDetailDto.setPricePerShare(stockPriceDetailEntityList.get(j).getPricePerShare());
                    stockPriceDetailDto.setCompanyCode(stockPriceDetailEntityList.get(j).getCompanyCode());
                    stockPriceDetailDto.setSectorCode(stockPriceDetailEntityList.get(j).getSectorCode());
                    stockPriceDetailDtoList.add(stockPriceDetailDto);
                }
            }
        }
        if (sectorCode != null) {
            for (int i = 0; i < sectorCode.size(); i++) {
                switch (duration) {
                    case "Y":
                        stockPriceDetailEntityList = stockPriceReportRepository.getDetailForSectorByYear(sectorCode.get(i), stockExchangeCode, startDate, endDate);
                        break;
                    case "M":
                        stockPriceDetailEntityList = stockPriceReportRepository.getDetailForSectorByMonth(sectorCode.get(i), stockExchangeCode, startDate, endDate);
                        break;
                    case "W":
                        stockPriceDetailEntityList = stockPriceReportRepository.getDetailForSectorByWeek(sectorCode.get(i), stockExchangeCode, startDate, endDate);
                        break;
                    case "Q":
                        stockPriceDetailEntityList = stockPriceReportRepository.getDetailForSectorByQuarter(sectorCode.get(i), stockExchangeCode, startDate, endDate);
                        break;
                }
                for (int j = 0; j < stockPriceDetailEntityList.size(); j++) {
                    StockPriceDetailDto stockPriceDetailDto = new StockPriceDetailDto();
                    stockPriceDetailDto.setDuration(stockPriceDetailEntityList.get(j).getStockExchangeCode());
                    stockPriceDetailDto.setPricePerShare(stockPriceDetailEntityList.get(j).getPricePerShare());
                    stockPriceDetailDto.setCompanyCode(stockPriceDetailEntityList.get(j).getCompanyCode());
                    stockPriceDetailDto.setSectorCode(stockPriceDetailEntityList.get(j).getSectorCode());
                    stockPriceDetailDtoList.add(stockPriceDetailDto);
                }
            }
        }
        return stockPriceDetailDtoList;
    }

    @Override
    public List<StockPriceDetailEntity> getStockPriceDetail(String companyCode, String stockExchangeCode) {
        List<StockPriceDetailEntity> stockPriceDetailEntity = stockPriceReportRepository.getStockPriceDetail(companyCode, stockExchangeCode);
        return stockPriceDetailEntity;
    }

}
