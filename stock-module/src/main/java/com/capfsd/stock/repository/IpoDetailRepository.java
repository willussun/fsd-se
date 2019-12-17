package com.capfsd.stock.repository;

import com.capfsd.stock.entity.CompanyEntity;
import com.capfsd.stock.entity.IpoDetailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IpoDetailRepository extends JpaRepository<IpoDetailEntity, Long> {
   Page<IpoDetailEntity> findAllByIpoCodeContainsIgnoreCase(String name, Pageable pageable);

//   @Query(value = "select * from t_ipo_detail where t_ipo_detail.stock_exchange_code = :stockExchangeCode)", nativeQuery = true)
//   List<IpoDetailEntity> findByStockExchangeCode(@Param("stockExchangeCode") String stockExchangeCode);

   List<IpoDetailEntity> findByStockExchangeCode(String stockExchangeCode);
   List<IpoDetailEntity> findByCompanyCode(String companyCode);

   @Query(value = "SELECT u FROM IpoDetailEntity u WHERE UPPER(u.companyCode) like CONCAT('%',UPPER(:keyword),'%')")
   Page<IpoDetailEntity> findAllIpoDetailsByKeywordWithPagination(@Param("keyword") String keyword, Pageable pageable);

   @Query(value = "SELECT * FROM t_ipo_detail", nativeQuery = true)
   List<IpoDetailEntity> findAllIpoDetailEntity();

}