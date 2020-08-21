package com.erp.mastro.service;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.entities.PriceList;
import com.erp.mastro.exception.MastroEntityException;
import com.erp.mastro.model.request.PriceListRequestModel;
import com.erp.mastro.repository.PriceListRepository;
import com.erp.mastro.service.interfaces.PriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class PriceListServiceImpl implements PriceListService {

    @Autowired
    private PriceListRepository priceListRepository;

    public List<PriceList> getAllPriceList() {
        List<PriceList> priceList = new ArrayList<PriceList>();
        priceListRepository.findAll().forEach(price -> priceList.add(price));
        return priceList;
    }

    public PriceList getPriceListById(Long id) throws MastroEntityException {
        MastroLogUtils.info(PriceListService.class, "Going to get pricelist by id :{}" + id);
        PriceList priceList = priceListRepository.findById(id).get();
        if (priceList == null) {
            throw new MastroEntityException("Pricelist Entity not found");
        }
        return priceList;

    }

    @Transactional(rollbackOn = {Exception.class})
    public PriceList saveOrUpdatePriceList(PriceListRequestModel priceListRequestModel) {

        if (priceListRequestModel.getId() == null) {
            PriceList priceList = new PriceList();
            priceList.setPricelistName(priceListRequestModel.getPricelistName());
            priceList.setCategoryType(priceListRequestModel.getCategoryType());
            priceList.setPartyType(priceListRequestModel.getPartyType());
            priceList.setDiscountPercentage(priceListRequestModel.getDiscountPercentage());
            priceList.setAllowedPriceDevPerUpper(priceListRequestModel.getAllowedPriceDevPerUpper());
            priceList.setAllowedPriceDevPerLower(priceListRequestModel.getAllowedPriceDevPerLower());
            priceListRepository.save(priceList);
            return priceList;

        } else {
            PriceList priceList = priceListRepository.findById(priceListRequestModel.getId()).get();
            priceList.setPricelistName(priceListRequestModel.getPricelistName());
            priceList.setDiscountPercentage(priceListRequestModel.getDiscountPercentage());
            priceList.setAllowedPriceDevPerUpper(priceListRequestModel.getAllowedPriceDevPerUpper());
            priceList.setAllowedPriceDevPerLower(priceListRequestModel.getAllowedPriceDevPerLower());
            priceListRepository.save(priceList);
            return priceList;
        }
    }

    public void deletePriceList(Long id) {
        priceListRepository.deleteById(id);
    }

    @Transactional(rollbackOn = {Exception.class})
    public void deletePriceListDetails(Long id) throws MastroEntityException {
        MastroLogUtils.info(PriceListService.class, "Going to delete pricelistdetails :{}" + id);
        PriceList priceList = getPriceListById(id);
        priceList.setPricelistDeleteStatus(1);
        priceListRepository.save(priceList);

    }
}
