package com.erp.mastro.service;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.entities.HSN;
import com.erp.mastro.model.request.HSNRequestModel;
import com.erp.mastro.repository.HSNRepository;
import com.erp.mastro.service.interfaces.HSNService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class HSNServiceImpl implements HSNService {

    @Autowired
    private HSNRepository hsnRepository;


    public List<HSN> getAllHSN() {
        List<HSN> hsnList = new ArrayList<HSN>();
        hsnRepository.findAll().forEach(hsn -> hsnList.add(hsn));
        return hsnList;
    }

    public HSN getHSNById(Long id) {
        MastroLogUtils.info(HSNService.class, "Going to get all hsn by id :" + id);
        return hsnRepository.findById(id).get();
    }

    @Transactional(rollbackOn = {Exception.class})
    public void saveOrUpdateHSN(HSNRequestModel hsnRequestModel) {
        if (hsnRequestModel.getId() == null) {
            MastroLogUtils.info(HSNService.class, "Going to Add HSN  " + hsnRequestModel.toString());
            HSN hsn = new HSN();
            setHSNData(hsnRequestModel, hsn);
        } else {
            MastroLogUtils.info(HSNService.class, "Going to Edit HSN  " + hsnRequestModel.toString());
            HSN hsn = hsnRepository.findById(hsnRequestModel.getId()).get();
            setHSNData(hsnRequestModel, hsn);
        }
    }

    private void setHSNData(HSNRequestModel hsnRequestModel, HSN hsn) {
        hsn.setEntryDate(hsnRequestModel.getEntryDate());
        hsn.setSection(hsnRequestModel.getSection());
        hsn.setChapter(hsnRequestModel.getChapter());
        hsn.setHeading(hsnRequestModel.getHeading());
        hsn.setSubHeading(hsnRequestModel.getSubHeading());
        hsn.setHsnCode(hsnRequestModel.getHsnCode());
        hsn.setGstGoodsName(hsnRequestModel.getGstGoodsName());
        hsn.setEffectiveFrom(hsnRequestModel.getEffectiveFrom());
        hsn.setSgst(hsnRequestModel.getSgst());
        hsn.setCgst(hsnRequestModel.getCgst());
        hsn.setIgst(hsnRequestModel.getIgst());
        hsn.setUtgst(hsnRequestModel.getUtgst());
        hsn.setCess(hsnRequestModel.getCess());
        hsnRepository.save(hsn);
    }

    public void deleteHSN(Long id) {
        hsnRepository.deleteById(id);
    }

    @Transactional(rollbackOn = {Exception.class})
    public void deleteHsnDetails(Long id) {
        MastroLogUtils.info(HSNService.class, "Going to delete HsnDetails by id :" + id);
        HSN hsn = getHSNById(id);
        hsn.setHsnDeleteStatus(1);
        hsnRepository.save(hsn);

    }
}
