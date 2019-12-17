package com.capfsd.stock.repository;

import com.capfsd.stock.entity.CompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

    @Query(value = "SELECT u FROM CompanyEntity u WHERE UPPER(u.companyName) like CONCAT('%',UPPER(:keyword),'%')")
    Page<CompanyEntity> findAllCompaniesByKeywordWithPagination(@Param("keyword") String keyword, Pageable pageable);

    Optional<CompanyEntity> findByIdAndStatus(Long id, Integer status);

    @Query(value = "SELECT u FROM CompanyEntity u WHERE UPPER(u.companyName) like CONCAT('%',UPPER(:keyword),'%') AND u.status = :status")
    Page<CompanyEntity>  findAllCompaniesByKeywordAndStatusWithPagination(@Param("keyword") String keyword,
                                                                              @Param("status") Integer status,
                                                                              Pageable pageable
                                                                 );

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update CompanyEntity u set u.status =:status where id =:id")
    Integer updateCompanyByIdWithStatus(@Param("id") Long id, @Param("status") Integer status);

    Optional<CompanyEntity> findByCompanyName(String companyName);

    @Query(value = "select * from t_company where id  in (select distinct company_id from t_ipo_detail where t_ipo_detail.stock_exchange_code = :stockExchangeCode)", nativeQuery = true)
    List<CompanyEntity> findByStockExchangeCode(@Param("stockExchangeCode") String stockExchangeCode);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update CompanyEntity u set u.companyName=:companyName, u.boardDirector=:boardDirector, u.ceo=:ceo, u.briefWriteup=:briefWriteup, u.turnover=:turnover where u.id=:id")
    Integer updateCompanyById(@Param("id") Long id,
                              @Param("companyName") String companyName,
                              @Param("boardDirector") String boardDirector,
                              @Param("ceo") String ceo,
                              @Param("briefWriteup") String briefWriteup,
                              @Param("turnover") Double turnover);

}
