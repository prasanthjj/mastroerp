package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.PriceList;
import com.erp.mastro.exception.MastroEntityException;
import com.erp.mastro.model.request.PriceListRequestModel;

import java.util.List;

public interface PriceListService {

    List<PriceList> getAllPriceList();

    PriceList getPriceListById(Long id) throws MastroEntityException;

    PriceList saveOrUpdatePriceList(PriceListRequestModel priceListRequestModel);

    void deletePriceList(Long id);

    void deletePriceListDetails(Long id) throws MastroEntityException;
}
