package com.erp.mastro.service;


import com.erp.mastro.entities.Catalog;
import com.erp.mastro.entities.Category;
import com.erp.mastro.entities.HSN;
import com.erp.mastro.repository.HSNRepository;
import com.erp.mastro.service.interfaces.HSNService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@SpringBootTest
public class HSNServiceTest {

    @Autowired
    private HSNService hsnService;

    @MockBean
    private HSNRepository hsnRepository;

    public List<HSN> addHSN(){
        List<HSN> hsns = new ArrayList<HSN>();
        Stream<HSN> stream = Stream.of(new HSN(1L,new Date(),"abc","a1","H1","SH","hname","gstname",new Date(),0.12,0.12,0.06,0.06),
                new HSN(2L,new Date(),"efg","b1","H2","SH2","hname2","gstname1",new Date(),0.12,0.12,0.06,0.06));
        hsns = stream.collect(Collectors.toList());
        return hsns;
    }
    public HSN addHSNs() {
        HSN hsn = new HSN(3L,new Date(),"abcde","a2","H3","SH3","hname3","gstname2",new Date(),0.12,0.12,0.06,0.06);
        return hsn;
    }

    @Test
    public void testHSNSizeEqual() {
        when(hsnRepository.findAll()).thenReturn(addHSN());
        Assert.assertEquals(2,hsnService.getAllHSN().size());
    }
    @Test
    public void testHSNSizeNotEqual() {
        when(hsnRepository.findAll()).thenReturn(addHSN());
        Assert.assertNotEquals(1,hsnService.getAllHSN().size());
    }

    @Test
    public void testGetById() {

        when(hsnRepository.findById(3L)).thenReturn(Optional.of(addHSNs()));
        Assert.assertEquals(addHSNs().getId(),hsnService.getHSNById(addHSNs().getId()).getId());
    }

    @Test
    public void testSaveHSN()
    {
        HSN hsn = new HSN(3L,new Date(),"abcde","a2","H3","SH3","hname3","gstname2",new Date(),0.12,0.12,0.06,0.06);
        hsnService.saveOrUpdateHSN(hsn);
        verify(hsnRepository, times(1)).save(hsn);
    }

    @Test
    public void testDeleteHSN() {
        hsnService.deleteHSN(addHSNs().getId());
        verify(hsnRepository, times(1)).deleteById(addHSNs().getId());
    }

    @Test
    public void testGetHSNValidationSucess() {

        when(hsnRepository.findById(addHSNs().getId())).thenReturn(Optional.of(addHSNs()));
        Assert.assertEquals("abcde", hsnService.getHSNById(addHSNs().getId()).getSection());
        Assert.assertEquals("a2", hsnService.getHSNById(addHSNs().getId()).getChapter());
        Assert.assertEquals("H3", hsnService.getHSNById(addHSNs().getId()).getHeading());
        Assert.assertEquals("SH3", hsnService.getHSNById(addHSNs().getId()).getSubHeading());
        Assert.assertEquals("hname3", hsnService.getHSNById(addHSNs().getId()).getHsn_name());
        Assert.assertEquals("gstname2", hsnService.getHSNById(addHSNs().getId()).getGstGoodsName());
    }


}

