package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.PriceList;

import java.util.List;

public interface PriceListService {

    List<PriceList> getAllPriceList();

    PriceList getPriceListById(Long id);

    void saveOrUpdatePriceList(PriceList priceList);

    void deletePriceList(Long id);
}
