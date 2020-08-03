package com.erp.mastro.service;

import com.erp.mastro.entities.Branch;
import com.erp.mastro.entities.BranchRegistration;
import com.erp.mastro.model.request.BranchRequestModel;
import com.erp.mastro.repository.BranchRegistrationRepository;
import com.erp.mastro.repository.BranchRepository;
import com.erp.mastro.service.interfaces.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
    public Branch getBranchById(Long id) {
        return branchRepository.findById(id).get();
    }

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void saveOrUpdateBranch(BranchRequestModel branchRequestModel) {

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
            branch.setCreationDate(branchRequestModel.getCreationDate());

            BranchRegistration branchRegistration = new BranchRegistration();
            branchRegistration.setTanNo(branchRequestModel.getTanNo());
            branchRegistration.setVatTinNo(branchRequestModel.getVatTinNo());
            branchRegistration.setVatDate(branchRequestModel.getVatDate());
            branchRegistration.setCstTinNo(branchRequestModel.getCstTinNo());
            branchRegistration.setCstdate(branchRequestModel.getCstdate());
            branchRegistration.setJuridical(branchRequestModel.getJuridical());
            branchRegistration.setSTaxNo(branchRequestModel.getSTaxNo());
            branchRegistration.setStaxDate(branchRequestModel.getStaxDate());
            branchRegistration.setPanNo(branchRequestModel.getPanNo());
            branchRegistration.setPanDate(branchRequestModel.getPanDate());
            branchRegistration.setPfAccount(branchRequestModel.getPfAccount());
            branchRegistration.setPfDate(branchRequestModel.getPfDate());
            branchRegistration.setEsicAccount(branchRequestModel.getEsicAccount());
            branchRegistration.setEsicDate(branchRequestModel.getEsicDate());
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
            branchRegistration.setCinDate(branchRequestModel.getCinDate());

            branch.setBranchRegistration(branchRegistration);
            branchRepository.save(branch);
        } else {
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
            branch.setCreationDate(branchRequestModel.getCreationDate());

            branchRegistration.setTanNo(branchRequestModel.getTanNo());
            branchRegistration.setVatTinNo(branchRequestModel.getVatTinNo());
            branchRegistration.setVatDate(branchRequestModel.getVatDate());
            branchRegistration.setCstTinNo(branchRequestModel.getCstTinNo());
            branchRegistration.setCstdate(branchRequestModel.getCstdate());
            branchRegistration.setJuridical(branchRequestModel.getJuridical());
            branchRegistration.setSTaxNo(branchRequestModel.getSTaxNo());
            branchRegistration.setStaxDate(branchRequestModel.getStaxDate());
            branchRegistration.setPanNo(branchRequestModel.getPanNo());
            branchRegistration.setPanDate(branchRequestModel.getPanDate());
            branchRegistration.setPfAccount(branchRequestModel.getPfAccount());
            branchRegistration.setPfDate(branchRequestModel.getPfDate());
            branchRegistration.setEsicAccount(branchRequestModel.getEsicAccount());
            branchRegistration.setEsicDate(branchRequestModel.getEsicDate());
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
            branchRegistration.setCinDate(branchRequestModel.getCinDate());

            branch.setBranchRegistration(branchRegistration);
            branchRepository.save(branch);
        }
    }

    @Override
    public void deleteBranch(Long id) {
        branchRepository.deleteById(id);
    }

    @Transactional(rollbackOn = {Exception.class})
    public void deleteBranchDetails(Long id) {
        Branch branch = getBranchById(id);
        branch.setBranchDeleteStatus(1);
        branchRepository.save(branch);
    }

}
