package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.GRN;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.GRNRequestModel;

public interface GRNService {

    GRN createGRN(GRNRequestModel grnRequestModel) throws ModelNotFoundException;

    GRN getGRNById(Long id);

    GRN saveOrUpdateGRNItemDetails(GRNRequestModel grnRequestModel, Long grnId) throws ModelNotFoundException;

}
