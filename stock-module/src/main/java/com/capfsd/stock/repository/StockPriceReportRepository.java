package com.capfsd.stock.repository;

import com.capfsd.stock.dto.StockPriceRecordListDto;
import com.capfsd.stock.entity.StockExchangeEntity;
import com.capfsd.stock.entity.StockPriceDetailEntity;
import com.capfsd.stock.service.IStockExchangeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StockPriceReportRepository extends JpaRepository<StockPriceDetailEntity, Long> {
//    @Query(value = "select * from t_stock_price_detail where company_code = :companyCode and stock_exchange_code = :stockExchangeCode " +
//            "and date>= :startDate and date<= :endDate", nativeQuery = true)
//    List<StockPriceDetailEntity> compareByCompanyCode(
//            @Param("companyCode") String companyCode,
//            @Param("stockExchangeCode") String stockExchangeCode,
//            @Param("startDate") String startDate,
//            @Param("endDate") String endDate
//    );

    // data format means %x%v means by week, %Y%m means by month, %Y means by year, for sector
    @Query(value = "select a.id as id, a.company_code as company_code, a.sector_code as sector_code, " +
            "a.created_by as created_by, a.created_time as created_time, " +
            "a.updated_by as updated_by, a.updated_time as updated_time, " +
            "date_format(a.datetime, '%x%v') stock_exchange_code, AVG(price_per_share) as price_per_share, date, time, datetime " +
            "FROM t_stock_price_detail a where sector_code = :sectorCode and stock_exchange_code = :stockExchangeCode " +
            "and date>= :startDate and date <= :endDate group by " +
            "date_format(a.datetime, '%x%v')", nativeQuery = true)
    List<StockPriceDetailEntity> getDetailForSectorByWeek(
            @Param("sectorCode") String sector,
            @Param("stockExchangeCode") String stockExchangeCode,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );


    @Query(value = "select a.id as id, a.company_code as company_code, a.sector_code as sector_code, " +
            "a.created_by as created_by, a.created_time as created_time, " +
            "a.updated_by as updated_by, a.updated_time as updated_time, " +
    "date_format(a.datetime, '%Y%m') stock_exchange_code, AVG(price_per_share) as price_per_share, date, time, datetime " +
    "FROM t_stock_price_detail a where sector_code = :sectorCode and stock_exchange_code = :stockExchangeCode " +
    "and date>= :startDate and date <= :endDate group by " +
    "date_format(a.datetime, '%Y%m')", nativeQuery = true)
    List<StockPriceDetailEntity> getDetailForSectorByMonth(
            @Param("sectorCode") String sector,
            @Param("stockExchangeCode") String stockExchangeCode,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

    @Query(value = "select a.id as id, a.company_code as company_code, a.sector_code as sector_code, " +
            "a.created_by as created_by, a.created_time as created_time, " +
            "a.updated_by as updated_by, a.updated_time as updated_time, " +
            "date_format(a.datetime, '%Y') stock_exchange_code, AVG(price_per_share) as price_per_share, date, time, datetime " +
            "FROM t_stock_price_detail a where sector_code = :sectorCode and stock_exchange_code = :stockExchangeCode " +
            "and date>= :startDate and date <= :endDate group by " +
            "date_format(a.datetime, '%Y')", nativeQuery = true)
    List<StockPriceDetailEntity> getDetailForSectorByYear(
            @Param("sectorCode") String sector,
            @Param("stockExchangeCode") String stockExchangeCode,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );
    // data format for quarte FLOOR((date_format(datetime, '%m')+2)/3)), for sector
    @Query(value = "select a.id as id, a.company_code as company_code, a.sector_code as sector_code, " +
            "a.created_by as created_by, a.created_time as created_time, " +
            "a.updated_by as updated_by, a.updated_time as updated_time, " +
            "concat(date_format(datetime, '%Y'),FLOOR((date_format(datetime, '%m')+2)/3)) stock_exchange_code, AVG(price_per_share) as price_per_share, date, time, datetime " +
            "FROM t_stock_price_detail a where sector_code = :sectorCode and stock_exchange_code = :stockExchangeCode " +
            "and date>= :startDate and date <= :endDate group by " +
            "concat(date_format(datetime, '%Y'),FLOOR((date_format(datetime, '%m')+2)/3))", nativeQuery = true)
    List<StockPriceDetailEntity> getDetailForSectorByQuarter(
            @Param("sectorCode") String companyCode,
            @Param("stockExchangeCode") String stockExchangeCode,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

    // data format means %x%v means by week, %Y%m means by month, %Y means by year, for sector

    @Query(value = "select a.id as id, a.company_code as company_code, a.sector_code as sector_code, " +
            "a.created_by as created_by, a.created_time as created_time, " +
            "a.updated_by as updated_by, a.updated_time as updated_time, " +
            "date_format(a.datetime, '%x%v') " +
            " stock_exchange_code, AVG(price_per_share) as price_per_share, date, time, datetime " +
            "FROM t_stock_price_detail a where company_code = :companyCode and stock_exchange_code = :stockExchangeCode " +
            "and date>= :startDate and date <= :endDate group by " +
            "date_format(a.datetime, '%x%v')" , nativeQuery = true)
    List<StockPriceDetailEntity> getDetailForCompanyByWeek(
            @Param("companyCode") String companyCode,
            @Param("stockExchangeCode") String stockExchangeCode,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

    @Query(value = "select a.id as id, a.company_code as company_code, a.sector_code as sector_code, " +
            "a.created_by as created_by, a.created_time as created_time, " +
            "a.updated_by as updated_by, a.updated_time as updated_time, " +
            "date_format(a.datetime, '%Y%m') " +
            " stock_exchange_code, AVG(price_per_share) as price_per_share, date, time, datetime " +
            "FROM t_stock_price_detail a where company_code = :companyCode and stock_exchange_code = :stockExchangeCode " +
            "and date>= :startDate and date <= :endDate group by " +
            "date_format(a.datetime, '%Y%m')" , nativeQuery = true)
    List<StockPriceDetailEntity> getDetailForCompanyByMonth(
            @Param("companyCode") String companyCode,
            @Param("stockExchangeCode") String stockExchangeCode,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

    @Query(value = "select a.id as id, a.company_code as company_code, a.sector_code as sector_code, " +
            "a.created_by as created_by, a.created_time as created_time, " +
            "a.updated_by as updated_by, a.updated_time as updated_time, " +
            "date_format(a.datetime, '%Y') " +
            " stock_exchange_code, AVG(price_per_share) as price_per_share, date, time, datetime " +
            "FROM t_stock_price_detail a where company_code = :companyCode and stock_exchange_code = :stockExchangeCode " +
            "and date>= :startDate and date <= :endDate group by " +
            "date_format(a.datetime, '%Y')" , nativeQuery = true)
    List<StockPriceDetailEntity> getDetailForCompanyByYear(
            @Param("companyCode") String companyCode,
            @Param("stockExchangeCode") String stockExchangeCode,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

    // data format for quarter FLOOR((date_format(datetime, '%m')+2)/3)), for sector
    @Query(value = "select a.id as id, a.company_code as company_code, a.sector_code as sector_code, " +
            "a.created_by as created_by, a.created_time as created_time, " +
            "a.updated_by as updated_by, a.updated_time as updated_time, " +
            "concat(date_format(datetime, '%Y'),FLOOR((date_format(datetime, '%m')+2)/3)) stock_exchange_code, AVG(price_per_share) as price_per_share, date, time, datetime " +
            "FROM t_stock_price_detail a where company_code = :companyCode and stock_exchange_code = :stockExchangeCode " +
            "and date>= :startDate and date <= :endDate group by " +
            "concat(date_format(datetime, '%Y'),FLOOR((date_format(datetime, '%m')+2)/3))", nativeQuery = true)
    List<StockPriceDetailEntity> getDetailForCompanyByQuarter(
            @Param("companyCode") String CompanyCode,
            @Param("stockExchangeCode") String stockExchangeCode,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

    @Query(value = "select * from t_stock_price_detail where stock_exchange_code=:stockExchangeCode and company_code=:companyCode order by datetime DESC", nativeQuery=true)
    List<StockPriceDetailEntity> getStockPriceDetail(
            @Param("companyCode") String companyCode,
            @Param("stockExchangeCode") String stockExchangeCode
    );

}
