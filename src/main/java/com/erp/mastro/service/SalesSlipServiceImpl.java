package com.erp.mastro.service;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.controller.UserController;
import com.erp.mastro.entities.Branch;
import com.erp.mastro.entities.SalesSlip;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.SalesSlipRequestModel;
import com.erp.mastro.repository.SalesSlipRepository;
import com.erp.mastro.service.interfaces.PartyService;
import com.erp.mastro.service.interfaces.SalesSlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SalesSlipServiceImpl implements SalesSlipService {

    @Autowired
    private SalesSlipRepository salesSlipRepository;

    @Autowired
    private PartyService partyService;

    @Autowired
    private UserController userController;

    /**
     * Method to save sales slip basic details
     *
     * @param salesSlipRequestModel
     * @return salesslip
     * @throws ModelNotFoundException
     */
    @Transactional(rollbackOn = {Exception.class})
    public SalesSlip createSalesSlip(SalesSlipRequestModel salesSlipRequestModel) throws ModelNotFoundException {

        SalesSlip salesSlip = new SalesSlip();
        if (salesSlipRequestModel == null) {
            throw new ModelNotFoundException("SalesSlip model is empty");
        } else {
            if (salesSlipRequestModel.getId() == null) {
                MastroLogUtils.info(SalesSlipService.class, "Going to create sales slip{}" + salesSlipRequestModel.toString());
                salesSlip.setParty(partyService.getPartyById(salesSlipRequestModel.getSelectedPartyInSalesSlip()));
                salesSlip.setTransportMode(salesSlipRequestModel.getTransportMode());
                salesSlip.setVehicleNo(salesSlipRequestModel.getVehicleNo());
                Branch currentBranch = userController.getCurrentUser().getUserSelectedBranch().getCurrentBranch();
                salesSlip.setBranch(currentBranch);
                salesSlipRepository.save(salesSlip);
            }

        }
        return salesSlip;
    }

}
