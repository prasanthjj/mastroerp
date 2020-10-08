package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.POInvoice;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.POInvoiceRequestModel;

public interface POInvoiceService {

    void generatePOInvoice(POInvoiceRequestModel poInvoiceRequestModel) throws ModelNotFoundException;

    POInvoice getPOInvoiceyId(Long id);
}
