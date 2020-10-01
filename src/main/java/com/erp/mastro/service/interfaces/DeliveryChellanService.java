package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.DeliveryChellan;
import com.erp.mastro.model.request.DeliveryChellanRequestModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface DeliveryChellanService {

    List<DeliveryChellan> getAllDeliveryChellan();

    DeliveryChellan getDeliveryChellanId(Long id);

    void saveOrUpdateDeliveryChellan(DeliveryChellanRequestModel deliveryChellanRequestModel, Set<String> values);
}
