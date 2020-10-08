package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.Branch;
import com.erp.mastro.model.request.BranchRequestModel;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public interface BranchService {

    List<Branch> getAllBranch();

    Branch getBranchById(Long id);

    void saveOrUpdateBranch(BranchRequestModel branchRequestModel) throws ParseException;

    void deleteBranch(Long id);

    public void deleteBranchDetails(Long id);

}
