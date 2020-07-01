package com.erp.mastro.service;


import com.erp.mastro.entities.HSN;
import com.erp.mastro.model.request.HSNRequestModel;
import com.erp.mastro.repository.HSNRepository;
import com.erp.mastro.service.interfaces.HSNService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return hsnRepository.findById(id).get();
    }

    public void saveOrUpdateHSN(HSNRequestModel hsnRequestModel) {
        if (hsnRequestModel.getId() == null) {
            HSN hsn = new HSN();
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
        } else {
            HSN hsn = hsnRepository.findById(hsnRequestModel.getId()).get();
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
    }

    public void deleteHSN(Long id) {
        hsnRepository.deleteById(id);
    }

    public void deleteHsnDetails(Long id) {

        HSN hsn = getHSNById(id);
        hsn.setHsnDeleteStatus(1);
        hsnRepository.save(hsn);

    }
}
