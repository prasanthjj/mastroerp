package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.Indent;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.IndentModel;

import java.util.List;

public interface IndentService {

    List<Indent> getAllIndents();

    Indent getIndentById(Long id);

    Indent createIndent(IndentModel indentModel) throws ModelNotFoundException;

    Indent saveOrUpdateIndentItemDetails(IndentModel indentModel) throws ModelNotFoundException;

    void removeIndentItem(Long indentId, Long indentItemId);
}
