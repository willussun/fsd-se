package com.capfsd.stock.service.impl;

import com.capfsd.stock.dto.CompanyDto;
import com.capfsd.stock.dto.IpoDetailDto;
import com.capfsd.stock.dto.PageDto;
import com.capfsd.stock.entity.CompanyEntity;
import com.capfsd.stock.entity.IpoDetailEntity;
import com.capfsd.stock.repository.CompanyRepository;
import com.capfsd.stock.service.IIpoDetailService;
import com.capfsd.stock.service.IPageService;
import com.capfsd.stock.service.ICompanyService;
import com.capfsd.stock.util.LogUtils;
import com.capfsd.stock.vo.CompanyVo;
import com.capfsd.stock.vo.IpoDetailVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CompanyServiceImpl implements ICompanyService {
    @Autowired
    private IPageService pageService;

    @Autowired
    private IIpoDetailService ipoDetailService;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public void addCompany(CompanyVo companyVo) {

            CompanyEntity companyEntity = convertToEntity(null, companyVo);
            companyRepository.save(companyEntity);
    }

    @Override
    public void deleteCompanyById(Long id) {
            Optional<CompanyEntity> optionalCompanyEntity = findById(id);
            optionalCompanyEntity.ifPresent(companyEntity -> companyRepository.delete(companyEntity));
    }

/*    @Override
    public CompanyDto updateCompany(Long id, CompanyVo companyVo) {
        CompanyEntity companyEntity = convertToEntity(id, companyVo);
        return convertToDto(companyRepository.save(companyEntity));
    }*/

    @Override
    public Integer updateCompany(Long id, CompanyVo companyVo) {
        CompanyEntity companyEntity = convertToEntity(id, companyVo);
        companyRepository.updateCompanyById(id, companyEntity.getCompanyName(), companyEntity.getBoardDirector(),companyEntity.getCeo(), companyEntity.getBriefWriteup(),companyEntity.getTurnover());
        return 1;
    }

    @Override
    public Optional<CompanyDto> findCompanyById(Long id) {
            CompanyDto companyDto = null;
            Optional<CompanyEntity> optionalCompanyEntity = findById(id);
            if (optionalCompanyEntity.isPresent()) {
                companyDto = convertToDto(optionalCompanyEntity.get());
            }
            return Optional.ofNullable(companyDto);
      }

    @Override
    public PageDto<CompanyDto> findAll(String keyword, Pageable pageable) {
            Page<CompanyEntity> companyPage = companyRepository.findAllCompaniesByKeywordWithPagination(keyword, pageable);
            return pageService.convertToPageDto(companyPage, this::convertToDto);
      }

    @Override
    public PageDto<CompanyDto> findAllByStatus(String keyword, Integer status, Pageable pageable){
            Page<CompanyEntity> companyPage = companyRepository.findAllCompaniesByKeywordAndStatusWithPagination(keyword, status, pageable);
            return pageService.convertToPageDto(companyPage, this::convertToDto);
    }

    private CompanyEntity convertToEntity(Long id, CompanyVo companyVo) {
            CompanyEntity companyEntity = new CompanyEntity();
            BeanUtils.copyProperties(companyVo, companyEntity);
            ArrayList<IpoDetailEntity> ipoDetailEntityList = new ArrayList<IpoDetailEntity>();
            for (IpoDetailVo ipoDetailVo : companyVo.getIpoDetailVoList()) {
                IpoDetailEntity ipoDetailEntity = new IpoDetailEntity();
                BeanUtils.copyProperties(ipoDetailVo, ipoDetailEntity);
                ipoDetailEntityList.add(ipoDetailEntity);
            }
            companyEntity.setIpoDetailEntityList(ipoDetailEntityList);
            if (id != null) {
                Optional<CompanyEntity> optionalCompanyEntity = findById(id);
                optionalCompanyEntity.ifPresent(r -> {
                    companyEntity.setId(r.getId());
                    companyEntity.setCreatedBy(r.getCreatedBy());
                    companyEntity.setCreatedTime(r.getCreatedTime());
                    companyEntity.setUpdatedBy(r.getUpdatedBy());
                    companyEntity.setUpdatedTime(new Date());
                });
            }

            return companyEntity;
     }

    @Override
    public CompanyDto convertToDto(CompanyEntity companyEntity) {
            CompanyDto companyDto = new CompanyDto();
            BeanUtils.copyProperties(companyEntity, companyDto);

            List<IpoDetailDto> ipoDetailDtoList = new ArrayList<>();
            companyEntity.getIpoDetailEntityList().forEach(ipoDetail ->
            {
                    ipoDetailDtoList.add(ipoDetailService.convertToDto(ipoDetail));
            });

            /*for(int i=0; i<companyEntity.getIpoDetailEntityList().size(); i++)
            {
                IpoDetailDto ipoDetailDto = new IpoDetailDto();
                ipoDetailDto = ipoDetailService.convertToDto(companyEntity.getIpoDetailEntityList().get(i));
                ipoDetailDtoList.add(ipoDetailDto);
            }*/
            companyDto.setIpoDetailDtoList(ipoDetailDtoList);
            return companyDto;
    }

    @Override
    public Optional<CompanyEntity> findById(Long id){
            return companyRepository.findById(id);
    }

    @Override
    public Integer updateCompanyByIdWithStatus(Long id, Integer status)  {
            return companyRepository.updateCompanyByIdWithStatus(id, status);
    }

    private Optional<CompanyEntity> findByCompanyName(String companyName) {
            return companyRepository.findByCompanyName(companyName);

    }

    @Override
    public CompanyDto findDtoByCompanyName(String companyName) {
            Optional<CompanyEntity> companyEntity = findByCompanyName(companyName);
            if(companyEntity.isPresent())
            {
                return convertToDto(findByCompanyName(companyName).get());
            }
            else return null;
    }

    @Override
    public List<CompanyDto> findByStockExchangeCode(String stockExchangeCode) {
        List<CompanyEntity> companyList = companyRepository.findByStockExchangeCode(stockExchangeCode);
        ArrayList<CompanyDto> companyDtoList = new ArrayList<CompanyDto>();
        for (CompanyEntity companyEntity : companyList) {
            CompanyDto companyDto = new CompanyDto();
            BeanUtils.copyProperties(companyEntity, companyDto);
            companyDtoList.add(companyDto);
        }
        return companyDtoList;
    }
}
