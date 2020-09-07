package com.erp.mastro.service;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.entities.Branch;
import com.erp.mastro.entities.DeliveryChellan;
import com.erp.mastro.model.request.DeliveryChellanRequestModel;
import com.erp.mastro.repository.BranchRepository;
import com.erp.mastro.repository.DeliveryChellanRepository;
import com.erp.mastro.service.interfaces.DeliveryChellanService;
import com.erp.mastro.service.interfaces.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class DeliveryChellanServiceImpl implements DeliveryChellanService {

    @Autowired
    DeliveryChellanRepository deliveryChellanRepository;

    @Autowired
    BranchRepository branchRepository;

    @Override
    public List<DeliveryChellan> getAllDeliveryChellan() {
        List<DeliveryChellan> deliveryChellanList = new ArrayList<DeliveryChellan>();
        deliveryChellanRepository.findAll().forEach(deliveryChellan -> deliveryChellanList.add(deliveryChellan));
        return deliveryChellanList;
    }

    @Override
    public DeliveryChellan getDeliveryChellanId(Long id) {
        return deliveryChellanRepository.findById(id).get();
    }

    @Override
    public void saveOrUpdateDeliveryChellan(DeliveryChellanRequestModel deliveryChellanRequestModel,Set<String> values) {
        MastroLogUtils.info(DeliveryChellanService.class, "Going to Add DeliveryCellan {}");
        Iterable<DeliveryChellan> deliveryChellans = deliveryChellanRepository.findAll();
        Branch branch = deliveryChellanRequestModel.getBranch();
        Set<DeliveryChellan> deliveryChellanSet = new HashSet<>();


        for (String id : values) {
            DeliveryChellan deliveryChellan = deliveryChellanRepository.findById(Long.valueOf(id)).get();
            deliveryChellanSet.add(deliveryChellan);
        }
        branchRepository.save(branch);
        MastroLogUtils.info(ModuleService.class, "Added successfully.");
    }


}
