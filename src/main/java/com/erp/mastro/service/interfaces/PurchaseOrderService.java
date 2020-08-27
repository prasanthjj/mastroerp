package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.ItemStockDetails;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.IndentItemPartyGroupRequestModel;

public interface PurchaseOrderService {

    ItemStockDetails IndentItemPartyGroup(IndentItemPartyGroupRequestModel indentItemPartyGroupRequestModel) throws ModelNotFoundException;

    ItemStockDetails IndentItemGroupDatas(IndentItemPartyGroupRequestModel indentItemPartyGroupRequestModel) throws ModelNotFoundException;
}
