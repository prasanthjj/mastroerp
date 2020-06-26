package com.erp.mastro.service;

import com.erp.mastro.entities.Party;
import com.erp.mastro.entities.ProductPartyRateRelation;
import com.erp.mastro.repository.PartyRateRepository;
import com.erp.mastro.repository.PartyRepository;
import com.erp.mastro.service.interfaces.PartyRateService;
import com.erp.mastro.service.interfaces.PartyService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@SpringBootTest
public class PartyRateRelationTest {

    @Autowired
    private PartyRateService partyRateService;

    @MockBean
    private PartyRateRepository partyRateRepository;

    public Set<ProductPartyRateRelation> addPartysRates() {

        Set<ProductPartyRateRelation> productPartyRateRelationSet = new HashSet<>();
        Party party = new Party(1L, "a1", "a2", "a3", "a4", "a5", "a6", "a7", new Date(), "a9", "a10", "a11", "a12", "a13");
        Stream<ProductPartyRateRelation> stream = Stream.of(new ProductPartyRateRelation(1L, 15.90, "a2", 20.98, "a4", 30.5, 57.90, party),
                new ProductPartyRateRelation(2L, 10.89, "b2", 70.91, "b4", 80.25, 65.90, party));
        productPartyRateRelationSet = stream.collect(Collectors.toSet());
        return productPartyRateRelationSet;

    }

    public ProductPartyRateRelation addPartysRate() {

        Party party = new Party(1L, "a1", "a2", "a3", "a4", "a5", "a6", "a7", new Date(), "a9", "a10", "a11", "a12", "a13");
        ProductPartyRateRelation productPartyRateRelation = new ProductPartyRateRelation(1L, 15.90, "a2", 20.98, "a4", 30.5, 57.90, party);
        return productPartyRateRelation;

    }

    @Test
    public void testGetPartyRateSizeEqual() {

        when(partyRateRepository.findAll()).thenReturn(addPartysRates());
        Assert.assertEquals(2, partyRateService.getAllPartyRateRelation().size());

    }

    @Test
    public void testGetPartyRateSizeNotEqual() {

        when(partyRateRepository.findAll()).thenReturn(addPartysRates());
        Assert.assertNotEquals(1, partyRateService.getAllPartyRateRelation().size());

    }

    @Test
    public void testGetById() {

        when(partyRateRepository.findById(1L)).thenReturn(Optional.of(addPartysRate()));
        Assert.assertEquals(addPartysRate().getId(), partyRateService.getPartyRateRelationsById(addPartysRate().getId()).getId());

    }

    @Test
    public void testSavePartyRate() {
        Party party = new Party(1L, "a1", "a2", "a3", "a4", "a5", "a6", "a7", new Date(), "a9", "a10", "a11", "a12", "a13");
        ProductPartyRateRelation productPartyRateRelation = new ProductPartyRateRelation(1L, 15.90, "a2", 20.98, "a4", 30.5, 57.90, party);
        partyRateService.saveOrUpdatePartyRateRelation(productPartyRateRelation);
        verify(partyRateRepository, times(1)).save(productPartyRateRelation);
    }

    @Test
    public void testDeletePartyRate() {

        partyRateService.deletePartyRateRelation(addPartysRate().getId());
        verify(partyRateRepository, times(1)).deleteById(addPartysRate().getId());

    }


}
