package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.GRN;
import com.erp.mastro.entities.GRNItems;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.GRNRequestModel;

import java.util.List;

public interface GRNService {

    GRN createGRN(GRNRequestModel grnRequestModel) throws ModelNotFoundException;

    GRN getGRNById(Long id);

    GRN saveOrUpdateGRNItemDetails(GRNRequestModel grnRequestModel, Long grnId) throws ModelNotFoundException;

    List<GRN> getAllGRNs();

    void stockUpdationBasedOnGRN(GRN grn);

    GRNItems getGRNItemById(Long id);

    boolean qtyMismatchChecking(Long indentItemPartyGroupId, Double dcQty, Double pendingQty);

}
