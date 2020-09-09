package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.SalesSlip;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.SalesSlipRequestModel;

public interface SalesSlipService {

    SalesSlip createSalesSlip(SalesSlipRequestModel salesSlipRequestModel) throws ModelNotFoundException;
}
