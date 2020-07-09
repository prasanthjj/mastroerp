package com.erp.mastro.service;

import com.erp.mastro.entities.PriceList;
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

    public PriceList getPriceListById(Long id) {
        return priceListRepository.findById(id).get();
    }

    @Transactional(rollbackOn = {Exception.class})
    public void saveOrUpdatePriceList(PriceListRequestModel priceListRequestModel) {

        if (priceListRequestModel.getId() == null) {
            PriceList priceList = new PriceList();
            priceList.setName(priceListRequestModel.getName());
            priceList.setCategoryType(priceListRequestModel.getCategoryType());
            priceList.setPartyType(priceListRequestModel.getPartyType());
            priceList.setDiscountPercentage(priceListRequestModel.getDiscountPercentage());
            priceList.setAllowedPriceDevPerUpper(priceListRequestModel.getAllowedPriceDevPerUpper());
            priceList.setAllowedPriceDevPerLower(priceListRequestModel.getAllowedPriceDevPerLower());
            priceListRepository.save(priceList);

        } else {
            PriceList priceList = priceListRepository.findById(priceListRequestModel.getId()).get();
            priceList.setName(priceListRequestModel.getName());
            priceList.setCategoryType(priceListRequestModel.getCategoryType());
            priceList.setPartyType(priceListRequestModel.getPartyType());
            priceList.setDiscountPercentage(priceListRequestModel.getDiscountPercentage());
            priceList.setAllowedPriceDevPerUpper(priceListRequestModel.getAllowedPriceDevPerUpper());
            priceList.setAllowedPriceDevPerLower(priceListRequestModel.getAllowedPriceDevPerLower());
            priceListRepository.save(priceList);
        }
    }

    public void deletePriceList(Long id) {
        priceListRepository.deleteById(id);
    }
}
