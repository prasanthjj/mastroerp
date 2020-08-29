package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.ItemStockDetails;
import com.erp.mastro.entities.PurchaseOrder;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.IndentItemPartyGroupRequestModel;

import java.util.List;

public interface PurchaseOrderService {

    ItemStockDetails IndentItemPartyGroup(IndentItemPartyGroupRequestModel indentItemPartyGroupRequestModel) throws ModelNotFoundException;

    ItemStockDetails IndentItemGroupDatas(IndentItemPartyGroupRequestModel indentItemPartyGroupRequestModel) throws ModelNotFoundException;

    void generatePurchaseOrders(String indentIds);

    List<PurchaseOrder> getAllPurchaseOrders();

    void removeIndentItemGroup(Long indentIteamId, Long indentItemGroupId);

    PurchaseOrder getPurchaseOrderById(Long id);

    ItemStockDetails IndentItemGroupDatasInEdit(IndentItemPartyGroupRequestModel indentItemPartyGroupRequestModel) throws ModelNotFoundException;

    void editGeneratePurchaseOrders(String indentIds, String purchaseId);
}
