package com.erp.mastro.service;

import com.erp.mastro.entities.PriceList;
import com.erp.mastro.repository.PriceListRepository;
import com.erp.mastro.service.interfaces.PriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void saveOrUpdatePriceList(PriceList priceList) {
        priceListRepository.save(priceList);
    }

    public void deletePriceList(Long id) {
        priceListRepository.deleteById(id);
    }
}
