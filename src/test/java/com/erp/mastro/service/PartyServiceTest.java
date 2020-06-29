package com.erp.mastro.service;

import com.erp.mastro.entities.*;
import com.erp.mastro.repository.PartyRepository;
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
public class PartyServiceTest {

    @Autowired
    private PartyService partyService;

    @MockBean
    private PartyRepository partyRepository;

    public Set<Party> addPartys() {

        Set<Party> partys = new HashSet<>();
        Stream<Party> stream = Stream.of(new Party(1L,"a1","a2","a3","a4","a5","a6","a7",new Date(),"a9","a10","a11","a12","a13"),
                new Party(2L,"b1","b2","b3","b4","b5","b6","b7",new Date(),"b9","b10","b11","b12","b13"));
        partys= stream.collect(Collectors.toSet());
        return partys;

    }

    public Party addParty() {

       Party party= new Party(1L,"a1","a2","a3","a4","a5","a6","a7",new Date(),"a9","a10","a11","a12","a13");
       return party;

    }

    @Test
    public void testGetPartysSizeEqual() {

        when(partyRepository.findAll()).thenReturn(addPartys());
        Assert.assertEquals(2,partyService.getAllPartys().size());

    }

    @Test
    public void testGetPartysSizeNotEqual() {

        when(partyRepository.findAll()).thenReturn(addPartys());
        Assert.assertNotEquals(1,partyService.getAllPartys().size());

    }

    @Test
    public void testGetById() {

        when(partyRepository.findById(1L)).thenReturn(Optional.of(addParty()));
        Assert.assertEquals(addParty().getId(),partyService.getPartyById(addParty().getId()).getId());

    }

    @Test
    public void testSaveParty()
    {
        Party party= new Party(1L,"a1","a2","a3","a4","a5","a6","a7",new Date(),"a9","a10","a11","a12","a13");
        partyService.saveOrUpdateParty(party);
        verify(partyRepository, times(1)).save(party);
    }

    @Test
    public void testDeleteParty() {

        partyService.deleteParty(addParty().getId());
        verify(partyRepository,times(1)).deleteById(addParty().getId());

    }

    @Test
    public void testSavePartyContactDetails()
    {

        Party party=addParty();
        Set<ContactDetails> contactDetailsSet=new HashSet<>();
        ContactDetails contactDetails=new ContactDetails(1L,"a1","a2","a3","a4","a5","a6","a7","a8","a9","a10","a11",party);
        contactDetailsSet.add(contactDetails);
        partyService.saveOrUpdatePartyContactDetails(party,contactDetailsSet);
        verify(partyRepository, times(1)).save(party);

    }

    @Test
    public void testSavePartyBankDetails()
    {

        Party party=addParty();
        Set<BankDetails> bankDetailsSet=new HashSet<>();
        BankDetails bankDetails=new BankDetails(1L,"a1","a2","a3","a4","a5","a6","a7",party);
        bankDetailsSet.add(bankDetails);
        partyService.saveOrUpdatePartyBankDetails(party,bankDetailsSet);
        verify(partyRepository, times(1)).save(party);

    }

    @Test
    public void testSavePartyBillingDetails()
    {

        Party party=addParty();
        Set<BillingDetails> billingDetailsSet=new HashSet<>();
        BillingDetails billingDetails=new BillingDetails(1L,"a1","a2","a3","a4","a5","a6","a7","a8","a9","a10","a11","a12","a13","a14","a15","a16","a17",party);
        billingDetailsSet.add(billingDetails);
        partyService.saveOrUpdatePartyBillingDetails(party,billingDetailsSet);
        verify(partyRepository, times(1)).save(party);

    }

    @Test
    public void testSavePartyCreditDetails()
    {

        Party party=addParty();
        Set<CreditDetails> creditDetailsSet=new HashSet<>();
        CreditDetails creditDetails=new CreditDetails(1L,"a1","a2","a3",50.00,"a5",new Branch(),party);
        creditDetailsSet.add(creditDetails);
        partyService.saveOrUpdatePartyCreditDetails(party,creditDetailsSet);
        verify(partyRepository, times(1)).save(party);

    }
    @Test
    public void testGetPartyValidationSucess() {

        when(partyRepository.findById(addParty().getId())).thenReturn(Optional.of(addParty()));
        Assert.assertEquals("a1",partyService.getPartyById(addParty().getId()).getPartyType());
        Assert.assertEquals("a2",partyService.getPartyById(addParty().getId()).getPartyCode());
        Assert.assertEquals("a3",partyService.getPartyById(addParty().getId()).getPartyName());
        Assert.assertEquals("a4",partyService.getPartyById(addParty().getId()).getAliasName());
        Assert.assertEquals("a5",partyService.getPartyById(addParty().getId()).getStatus());
        Assert.assertEquals("a6",partyService.getPartyById(addParty().getId()).getCategoryType());
        Assert.assertEquals("a7",partyService.getPartyById(addParty().getId()).getTransporterName());
        Assert.assertEquals("a9",partyService.getPartyById(addParty().getId()).getOldReferCode());
        Assert.assertEquals("a10",partyService.getPartyById(addParty().getId()).getRelationshipMananger());
        Assert.assertEquals("a11",partyService.getPartyById(addParty().getId()).getIndustryType());
        Assert.assertEquals("a12",partyService.getPartyById(addParty().getId()).getEmailId());
        Assert.assertEquals("a13",partyService.getPartyById(addParty().getId()).getTransactionType());

    }

    @Test
    public void testSaveorUpdatePartyProducts()
    {

        Party party=addParty();
        Set<Product> productSet=new HashSet<>();
        Catalog catalog = new Catalog(3L, "A ","b");
        Category category= new Category(1L,"a1","a2","a3","a4",catalog);
        SubCategory subCategory = new SubCategory(1L, "a11", "a22", category);
        HSN hsn = new HSN();
        Set<ProductImages> productImages1 = new HashSet<>();
        Set<ProductUOM> productUOMSet1=new HashSet<>();
        Product product=new Product(1L,"a11","a22","a33","a44","a55","a66","a77",subCategory,hsn,"a88","a99",productImages1,productUOMSet1);
        productSet.add(product);
        partyService.saveOrUpdatePartyProducts(party,productSet);
        verify(partyRepository, times(1)).save(party);

    }

}
