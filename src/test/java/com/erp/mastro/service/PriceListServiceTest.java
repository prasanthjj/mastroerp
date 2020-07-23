package com.erp.mastro.service;

import com.erp.mastro.entities.PriceList;
import com.erp.mastro.model.request.PriceListRequestModel;
import com.erp.mastro.repository.PriceListRepository;
import com.erp.mastro.service.interfaces.PriceListService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class PriceListServiceTest {


    @Autowired
    private PriceListService priceListService;

    @MockBean
    private PriceListRepository priceListRepository;

    private boolean True;
    private boolean False;

    public PriceListRequestModel addPricelistModel() {
        PriceListRequestModel priceListRequestModel = new PriceListRequestModel();
        priceListRequestModel.setPricelistName("PL1");
        priceListRequestModel.setCategoryType("A");
        priceListRequestModel.setPartyType("Customer");
        priceListRequestModel.setDiscountPercentage(0.10);
        priceListRequestModel.setAllowedPriceDevPerUpper(0.06);
        priceListRequestModel.setAllowedPriceDevPerLower(0.05);
        return priceListRequestModel;
    }

    @Test
    public void testSavePriceList() {
        PriceList priceList = priceListService.saveOrUpdatePriceList(addPricelistModel());
        Assert.assertEquals("PL1", priceList.getPricelistName());
        Assert.assertEquals("A", priceList.getCategoryType());
        Assert.assertEquals("Customer", priceList.getPartyType());
        Assert.assertEquals(0.10, priceList.getDiscountPercentage(), 100);
        Assert.assertEquals(0.06, priceList.getAllowedPriceDevPerUpper(), 100);
        Assert.assertEquals(0.05, priceList.getAllowedPriceDevPerLower(), 100);
    }


}
