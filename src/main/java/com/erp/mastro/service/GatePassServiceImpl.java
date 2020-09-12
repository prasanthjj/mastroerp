package com.erp.mastro.service;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.entities.GatePass;
import com.erp.mastro.model.request.GatePassRequestModel;
import com.erp.mastro.repository.GatePassRepository;
import com.erp.mastro.service.interfaces.GatePassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Service class for Gate Pass
 */
@Service
public class GatePassServiceImpl implements GatePassService {

    @Autowired
    GatePassRepository gatePassRepository;

    /**
     * method to get all Gate Pass
     *
     * @return Gatepass list
     */

    @Override
    public List<GatePass> getAllGatePass() {

        List<GatePass> gatePassList = new ArrayList<>();
        gatePassRepository.findAll().forEach(gatePass -> gatePassList.add(gatePass));
        return gatePassList;
    }

    /**
     * method to get GatePass according to id
     *
     * @param id
     * @return gatePass
     */

    @Override
    public GatePass getGatePassId(Long id) {
        return gatePassRepository.findById(id).get();
    }

    /**
     * Save or edit Gatepass
     *
     * @param gatePassRequestModel
     */

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void saveOrUpdateGatePass(GatePassRequestModel gatePassRequestModel, String value, String val) throws ParseException {
        if (gatePassRequestModel.getId() == null) {
            MastroLogUtils.info(GatePassService.class, "Going to save GAte Pass {}" + gatePassRequestModel.toString());
            GatePass gatePass = new GatePass();
            gatePass.setVehicleMovementType(value);
            if (gatePass.getVehicleMovementType().equals("Inward")) {
                gatePass.setVehicleMovementType("Inward");
            } else {
                gatePass.setVehicleMovementType("Outward");
            }

            gatePass.setEmptyMaterial(val);
            if (gatePass.getEmptyMaterial().equals("Empty")) {
                gatePass.setEmptyMaterial("Empty");
            } else {
                gatePass.setEmptyMaterial("With_Matrial");
            }
            System.out.println("inside the impl of matrial: " + gatePass.getEmptyMaterial());

            gatePass.setEntryNo(gatePassRequestModel.getEntryNo());
            gatePass.setEntryDate(gatePassRequestModel.getEntryDate());
            String sDate1 = gatePassRequestModel.getsEntryDate();
            if (sDate1 != "") {
                Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(sDate1);

                gatePass.setEntryDate(date1);
            }

            gatePass.setVehicleNo(gatePassRequestModel.getVehicleNo());
            gatePass.setTransportName(gatePassRequestModel.getTransportName());
            gatePass.setTransportAddress(gatePassRequestModel.getTransportAddress());
            gatePass.setPreparedBy(gatePassRequestModel.getPreparedBy());
            gatePass.setMaterialDescription(gatePassRequestModel.getMaterialDescription());
            gatePass.setRemarks(gatePassRequestModel.getRemarks());
            gatePass.setReferenceDocumentNo(gatePassRequestModel.getReferenceDocumentNo());
            gatePass.setCustomerVendorName(gatePassRequestModel.getCustomerVendorName());
            gatePass.setCustomerVendorAddress(gatePassRequestModel.getCustomerVendorAddress());
            gatePass.setLRNo(gatePassRequestModel.getLRNo());
            gatePassRepository.save(gatePass);
            MastroLogUtils.info(GatePassService.class, "Saved " + gatePass.getCustomerVendorName() + " successfully.");
        } else {
            MastroLogUtils.info(GatePassService.class, "Going to edit Gate Pass {}" + gatePassRequestModel.toString());
            GatePass gatePass = getGatePassId(gatePassRequestModel.getId());
            gatePass.setVehicleMovementType(value);
            gatePass.setEmptyMaterial(val);
            gatePass.setEntryNo(gatePassRequestModel.getEntryNo());
            String sDate1 = gatePassRequestModel.getsEntryDate();

            if (sDate1 != "") {
                Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(sDate1);
                gatePass.setEntryDate(date1);
            }
            gatePass.setVehicleNo(gatePassRequestModel.getVehicleNo());
            gatePass.setTransportName(gatePassRequestModel.getTransportName());
            gatePass.setTransportAddress(gatePassRequestModel.getTransportAddress());
            gatePass.setPreparedBy(gatePassRequestModel.getPreparedBy());
            gatePass.setMaterialDescription(gatePassRequestModel.getMaterialDescription());
            gatePass.setRemarks(gatePassRequestModel.getRemarks());
            gatePass.setReferenceDocumentNo(gatePassRequestModel.getReferenceDocumentNo());
            gatePass.setCustomerVendorName(gatePassRequestModel.getCustomerVendorName());
            gatePass.setCustomerVendorAddress(gatePassRequestModel.getCustomerVendorAddress());
            gatePass.setLRNo(gatePassRequestModel.getLRNo());
            gatePassRepository.save(gatePass);
            MastroLogUtils.info(GatePassService.class, "Saved " + gatePass.getCustomerVendorName() + " successfully.");
        }

    }

    /**
     * Delete Gate Pass Details
     *
     * @param id
     */
    @Transactional(rollbackOn = {Exception.class})
    public void deleteGatePass(Long id) {
        if (id != null) {
            GatePass gatePass = getGatePassId(id);
            gatePass.setGatepassDeleteStatus(1);
            gatePassRepository.save(gatePass);
            MastroLogUtils.info(GatePassService.class, "GatePass Deleted successfully.");
        }
    }
}
