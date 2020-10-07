package com.erp.mastro.service;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.constants.Constants;
import com.erp.mastro.entities.Branch;
import com.erp.mastro.entities.BranchRegistration;
import com.erp.mastro.model.request.BranchRequestModel;
import com.erp.mastro.repository.BranchRepository;
import com.erp.mastro.service.interfaces.BranchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service class for Branch
 */
@Service
public class BranchServiceImpl implements BranchService {

    private Logger logger = LoggerFactory.getLogger(BranchServiceImpl.class);

    @Autowired
    BranchRepository branchRepository;

    /**
     * method to get all branch
     *
     * @return branch list
     */
    @Override
    public List<Branch> getAllBranch() {
        List<Branch> branchList = new ArrayList<Branch>();
        branchRepository.findAll().forEach(branch -> branchList.add(branch));
        return branchList;
    }

    /**
     * method to get branch according to id
     *
     * @param id
     * @return branch
     */
    @Override
    public Branch getBranchById(Long id) {
        return branchRepository.findById(id).get();
    }

    /**
     * Save or edit branch
     *
     * @param branchRequestModel
     * @throws ParseException
     */
    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void saveOrUpdateBranch(BranchRequestModel branchRequestModel) throws ParseException {
        if (branchRequestModel.getId() == null) {
            MastroLogUtils.info(BranchService.class, "Going to Add branch {}" + branchRequestModel.toString());
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
            branch.setLandNo(branchRequestModel.getLandNo());
            branch.setBranchAddress(branchRequestModel.getBranchAddress());
            branch.setWebsite(branchRequestModel.getWebsite());
            branch.setFaxNo(branchRequestModel.getFaxNo());
            branch.setPinCode(branchRequestModel.getPinCode());
            branch.setBranchCode(branchRequestModel.getBranchCode());

            BranchRegistration branchRegistration = new BranchRegistration();
            branchRegistration.setTanNo(branchRequestModel.getTanNo());
            branchRegistration.setVatTinNo(branchRequestModel.getVatTinNo());
            String sDate1 = branchRequestModel.getsVatDate();
            if (sDate1 != "") {
                Date date1 = new SimpleDateFormat(Constants.DATEFORMAT_MM_DD_YYYY).parse(sDate1);
                branchRegistration.setVatDate(date1);
            }
            branchRegistration.setCstTinNo(branchRequestModel.getCstTinNo());
            String sDate2 = branchRequestModel.getsCstdate();
            if (sDate2 != "") {
                Date date2 = new SimpleDateFormat(Constants.DATEFORMAT_MM_DD_YYYY).parse(sDate2);
                branchRegistration.setCstdate(date2);
            }
            branchRegistration.setJuridical(branchRequestModel.getJuridical());
            branchRegistration.setSTaxNo(branchRequestModel.getSTaxNo());
            String sDate3 = branchRequestModel.getsStaxDate();
            if (sDate3 != "") {
                Date date3 = new SimpleDateFormat(Constants.DATEFORMAT_MM_DD_YYYY).parse(sDate3);
                branchRegistration.setStaxDate(date3);
            }
            branchRegistration.setPanNo(branchRequestModel.getPanNo());
            String sDate4 = branchRequestModel.getsPanDate();
            if (sDate4 != "") {
                Date date4 = new SimpleDateFormat(Constants.DATEFORMAT_MM_DD_YYYY).parse(sDate4);
                branchRegistration.setPanDate(date4);
            }
            branchRegistration.setPfAccount(branchRequestModel.getPfAccount());
            String sDate5 = branchRequestModel.getsPfDate();
            if (sDate5 != "") {
                Date date5 = new SimpleDateFormat(Constants.DATEFORMAT_MM_DD_YYYY).parse(sDate5);
                branchRegistration.setPfDate(date5);
            }
            branchRegistration.setEsicAccount(branchRequestModel.getEsicAccount());
            String sDate6 = branchRequestModel.getsEsicDate();
            if (sDate6 != "") {
                Date date6 = new SimpleDateFormat(Constants.DATEFORMAT_MM_DD_YYYY).parse(sDate6);
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
            String sDate7 = branchRequestModel.getsGstDate();
            if (sDate7 != "") {
                Date date7 = new SimpleDateFormat(Constants.DATEFORMAT_MM_DD_YYYY).parse(sDate7);
                branchRegistration.setGstDate(date7);
            }
            branch.setBranchRegistration(branchRegistration);
            branchRepository.save(branch);
            MastroLogUtils.info(BranchService.class, "Added " + branch.getBranchName() + " successfully.");
        } else {
            MastroLogUtils.info(BranchService.class, "Going to edit branch {}" + branchRequestModel.toString());
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
            branch.setLandNo(branchRequestModel.getLandNo());
            branch.setBranchAddress(branchRequestModel.getBranchAddress());
            branch.setWebsite(branchRequestModel.getWebsite());
            branch.setFaxNo(branchRequestModel.getFaxNo());
            branch.setPinCode(branchRequestModel.getPinCode());
            branch.setBranchCode(branchRequestModel.getBranchCode());

            branchRegistration.setTanNo(branchRequestModel.getTanNo());
            branchRegistration.setVatTinNo(branchRequestModel.getVatTinNo());
            String sDate1 = branchRequestModel.getsVatDate();
            if (sDate1 != "") {
                Date date1 = new SimpleDateFormat(Constants.DATEFORMAT_MM_DD_YYYY).parse(sDate1);
                branchRegistration.setVatDate(date1);
            }
            branchRegistration.setCstTinNo(branchRequestModel.getCstTinNo());
            String sDate2 = branchRequestModel.getsCstdate();
            if (sDate2 != "") {
                Date date2 = new SimpleDateFormat(Constants.DATEFORMAT_MM_DD_YYYY).parse(sDate2);
                branchRegistration.setCstdate(date2);
            }
            branchRegistration.setJuridical(branchRequestModel.getJuridical());
            branchRegistration.setSTaxNo(branchRequestModel.getSTaxNo());
            branchRegistration.setSTaxNo(branchRequestModel.getSTaxNo());
            String sDate3 = branchRequestModel.getsStaxDate();
            if (sDate3 != "") {
                Date date3 = new SimpleDateFormat(Constants.DATEFORMAT_MM_DD_YYYY).parse(sDate3);
                branchRegistration.setStaxDate(date3);
            }
            branchRegistration.setPanNo(branchRequestModel.getPanNo());
            String sDate4 = branchRequestModel.getsPanDate();
            if (sDate4 != "") {
                Date date4 = new SimpleDateFormat(Constants.DATEFORMAT_MM_DD_YYYY).parse(sDate4);
                branchRegistration.setPanDate(date4);
            }
            branchRegistration.setPfAccount(branchRequestModel.getPfAccount());
            String sDate5 = branchRequestModel.getsPfDate();
            if (sDate5 != "") {
                Date date5 = new SimpleDateFormat(Constants.DATEFORMAT_MM_DD_YYYY).parse(sDate5);
                branchRegistration.setPfDate(date5);
            }
            branchRegistration.setEsicAccount(branchRequestModel.getEsicAccount());
            String sDate6 = branchRequestModel.getsEsicDate();
            if (sDate6 != "") {
                Date date6 = new SimpleDateFormat(Constants.DATEFORMAT_MM_DD_YYYY).parse(sDate6);
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
            String sDate7 = branchRequestModel.getsGstDate();
            if (sDate7 != "") {
                Date date7 = new SimpleDateFormat(Constants.DATEFORMAT_MM_DD_YYYY).parse(sDate7);
                branchRegistration.setGstDate(date7);
            }
            branch.setBranchRegistration(branchRegistration);
            branchRepository.save(branch);
            MastroLogUtils.info(BranchService.class, "Updated " + branch.getBranchName() + " successfully.");
        }
    }

    /**
     * Delete Branch
     *
     * @param id
     */
    @Override
    public void deleteBranch(Long id) {
        if (id != null) {
            branchRepository.deleteById(id);
            MastroLogUtils.info(BranchService.class, "Deleted successfully.");
        }
    }

    /**
     * Delete Branch Details
     *
     * @param id
     */
    @Transactional(rollbackOn = {Exception.class})
    public void deleteBranchDetails(Long id) {
        if (id != null) {
            Branch branch = getBranchById(id);
            branch.setBranchDeleteStatus(1);
            branchRepository.save(branch);
            MastroLogUtils.info(BranchService.class, "Branch Deleted successfully.");
        }
    }

}
