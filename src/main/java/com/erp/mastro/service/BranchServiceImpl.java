package com.erp.mastro.service;

import com.erp.mastro.entities.BranchRegistration;
import com.erp.mastro.model.request.BranchRequestModel;
import com.erp.mastro.repository.BranchRegistrationRepository;
import com.erp.mastro.repository.BranchRepository;
import com.erp.mastro.entities.Branch;
import com.erp.mastro.service.interfaces.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    BranchRepository branchRepository;

    @Autowired
    BranchRegistrationRepository branchRegistrationRepository;

    @Override
    public List<Branch> getAllBranch() {
        List<Branch> branchList = new ArrayList<Branch>();
        branchRepository.findAll().forEach(branch -> branchList.add(branch));
        return branchList;
    }

    @Override
    public Branch getBranchById(Long id) { return branchRepository.findById(id).get(); }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void saveOrUpdateBranch(BranchRequestModel branchRequestModel) throws ParseException {
        if (branchRequestModel.getId() == null) {

            Branch branch = new Branch();
            branch.setBranchName(branchRequestModel.getBranchName());
            branch.setCountryName(branchRequestModel.getCountryName());
            branch.setBranchCode(branchRequestModel.getBranchCode());
            branch.setStateName(branchRequestModel.getStateName());
            branch.setCityName(branchRequestModel.getCityName());
            branch.setBranchPrefix(branchRequestModel.getBranchPrefix());
            branch.setLocalCurrency(branchRequestModel.getLocalCurrency());
            branch.setEmailId(branchRequestModel.getEmailId());
            branch.setPhoneNo(branchRequestModel.getPhoneNo());
            branch.setBranchAddress(branchRequestModel.getBranchAddress());
            branch.setWebsite(branchRequestModel.getWebsite());
            branch.setFaxNo(branchRequestModel.getFaxNo());
            branch.setPinCode(branchRequestModel.getPinCode());


            BranchRegistration branchRegistration = new BranchRegistration();
            branchRegistration.setTanNo(branchRequestModel.getTanNo());
            branchRegistration.setVatTinNo(branchRequestModel.getVatTinNo());
            String sDate1=branchRequestModel.getVatDate();
            if(sDate1!="")
            {
                System.out.println("inside if");
                Date date1=new SimpleDateFormat("MM/dd/yyyy").parse(sDate1);
                branchRegistration.setVatDate(date1);
            }

            branchRegistration.setCstTinNo(branchRequestModel.getCstTinNo());
            String sDate2=branchRequestModel.getCstdate();
            if(sDate2!="")
            {
                System.out.println("inside if");
                Date date2=new SimpleDateFormat("MM/dd/yyyy").parse(sDate2);
                branchRegistration.setCstdate(date2);
            }

            branchRegistration.setJuridical(branchRequestModel.getJuridical());
            branchRegistration.setSTaxNo(branchRequestModel.getSTaxNo());
            String sDate3=branchRequestModel.getStaxDate();
            if(sDate3!="")
            {
                System.out.println("inside if");
                Date date3=new SimpleDateFormat("MM/dd/yyyy").parse(sDate3);
                branchRegistration.setStaxDate(date3);
            }

            branchRegistration.setPanNo(branchRequestModel.getPanNo());
            String sDate4=branchRequestModel.getPanDate();
            if(sDate4!="")
            {
                System.out.println("inside if");
                Date date4=new SimpleDateFormat("MM/dd/yyyy").parse(sDate4);
                branchRegistration.setPanDate(date4);
            }

            branchRegistration.setPfAccount(branchRequestModel.getPfAccount());

            String sDate5=branchRequestModel.getPfDate();
            if(sDate5!="")
            {
                System.out.println("inside if");
                Date date5=new SimpleDateFormat("MM/dd/yyyy").parse(sDate5);
                branchRegistration.setPfDate(date5);
            }

            branchRegistration.setEsicAccount(branchRequestModel.getEsicAccount());
            String sDate6=branchRequestModel.getEsicDate();
            if(sDate6!="")
            {
                System.out.println("inside if");
                Date date6=new SimpleDateFormat("MM/dd/yyyy").parse(sDate6);
                branchRegistration.setEsicDate(date6);
            }

            branchRegistration.setEccNo(branchRequestModel.getEccNo());
            branchRegistration.setPlaNo(branchRequestModel.getPlaNo());
            branchRegistration.setRange(branchRequestModel.getRange());
            branchRegistration.setDivision(branchRequestModel.getDivision());
            branchRegistration.setRangeAddress(branchRequestModel.getRangeAddress());
            branchRegistration.setDivisionAddress(branchRequestModel.getDivisionAddress());
            branchRegistration.setCommissionerate(branchRequestModel.getCommissionerate());
            branchRegistration.setExempted(branchRequestModel.getExempted());
            branchRegistration.setCommissionerateAddress(branchRequestModel.getCommissionerateAddress());
            branchRegistration.setLimit(branchRequestModel.getLimit());
            branchRegistration.setGstIn(branchRequestModel.getGstIn());
            branchRegistration.setCinNo(branchRequestModel.getCinNo());
            String sDate7=branchRequestModel.getCinDate();
            if(sDate7!="")
            {
                System.out.println("inside if");
                Date date7=new SimpleDateFormat("MM/dd/yyyy").parse(sDate7);
                branchRegistration.setCinDate(date7);
            }
            branch.setBranchRegistration(branchRegistration);
            branchRepository.save(branch);
        }
        else {
            Branch branch = getBranchById(branchRequestModel.getId());
            BranchRegistration branchRegistration = branch.getBranchRegistration();

            branch.setBranchName(branchRequestModel.getBranchName());
            branch.setCountryName(branchRequestModel.getCountryName());
            branch.setBranchCode(branchRequestModel.getBranchCode());
            branch.setStateName(branchRequestModel.getStateName());
            branch.setCityName(branchRequestModel.getCityName());
            branch.setBranchPrefix(branchRequestModel.getBranchPrefix());
            branch.setLocalCurrency(branchRequestModel.getLocalCurrency());
            branch.setEmailId(branchRequestModel.getEmailId());
            branch.setPhoneNo(branchRequestModel.getPhoneNo());
            branch.setBranchAddress(branchRequestModel.getBranchAddress());
            branch.setWebsite(branchRequestModel.getWebsite());
            branch.setFaxNo(branchRequestModel.getFaxNo());
            branch.setPinCode(branchRequestModel.getPinCode());


            branchRegistration.setTanNo(branchRequestModel.getTanNo());
            branchRegistration.setVatTinNo(branchRequestModel.getVatTinNo());
            String sDate1=branchRequestModel.getVatDate();
            if(sDate1!="")
            {

                Date date1=new SimpleDateFormat("MM/dd/yyyy").parse(sDate1);
                branchRegistration.setVatDate(date1);
            }
            branchRegistration.setCstTinNo(branchRequestModel.getCstTinNo());
            String sDate2=branchRequestModel.getCstdate();
            if(sDate2!="")
            {

                Date date2=new SimpleDateFormat("MM/dd/yyyy").parse(sDate2);
                branchRegistration.setCstdate(date2);
            }
            branchRegistration.setJuridical(branchRequestModel.getJuridical());
            branchRegistration.setSTaxNo(branchRequestModel.getSTaxNo());
            branchRegistration.setSTaxNo(branchRequestModel.getSTaxNo());
            String sDate3=branchRequestModel.getStaxDate();
            if(sDate3!="")
            {

                Date date3=new SimpleDateFormat("MM/dd/yyyy").parse(sDate3);
                branchRegistration.setStaxDate(date3);
            }

            branchRegistration.setPanNo(branchRequestModel.getPanNo());
            String sDate4=branchRequestModel.getPanDate();
            if(sDate4!="")
            {
                System.out.println("inside if edit");
                Date date4=new SimpleDateFormat("MM/dd/yyyy").parse(sDate4);
                branchRegistration.setPanDate(date4);
            }

            branchRegistration.setPfAccount(branchRequestModel.getPfAccount());
            String sDate5=branchRequestModel.getPfDate();
            if(sDate5!="")
            {

                Date date5=new SimpleDateFormat("MM/dd/yyyy").parse(sDate5);
                branchRegistration.setPfDate(date5);
            }


            branchRegistration.setEsicAccount(branchRequestModel.getEsicAccount());
            String sDate6=branchRequestModel.getEsicDate();
            if(sDate6!="")
            {
                System.out.println("inside if edit");
                Date date6=new SimpleDateFormat("MM/dd/yyyy").parse(sDate6);
                branchRegistration.setEsicDate(date6);
            }

            branchRegistration.setEccNo(branchRequestModel.getEccNo());
            branchRegistration.setPlaNo(branchRequestModel.getPlaNo());
            branchRegistration.setRange(branchRequestModel.getRange());
            branchRegistration.setDivision(branchRequestModel.getDivision());
            branchRegistration.setRangeAddress(branchRequestModel.getRangeAddress());
            branchRegistration.setDivisionAddress(branchRequestModel.getDivisionAddress());
            branchRegistration.setCommissionerate(branchRequestModel.getCommissionerate());
            branchRegistration.setExempted(branchRequestModel.getExempted());
            branchRegistration.setCommissionerateAddress(branchRequestModel.getCommissionerateAddress());
            branchRegistration.setLimit(branchRequestModel.getLimit());
            branchRegistration.setGstIn(branchRequestModel.getGstIn());
            branchRegistration.setCinNo(branchRequestModel.getCinNo());
            String sDate7=branchRequestModel.getCinDate();
            if(sDate7!="")
            {
                System.out.println("inside if edit");
                Date date7=new SimpleDateFormat("MM/dd/yyyy").parse(sDate7);
                branchRegistration.setCinDate(date7);
            }
             branch.setBranchRegistration(branchRegistration);
            branchRepository.save(branch);
        }
    }

    @Override
    public void deleteBranch(Long id) { branchRepository.deleteById(id); }

    @Transactional(rollbackOn = {Exception.class})
    public void deleteBranchDetails(Long id) {
        Branch branch = getBranchById(id);
        branch.setBranchDeleteStatus(1);
        branchRepository.save(branch);
    }

}
