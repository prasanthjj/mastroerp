package com.erp.mastro.service.interfaces;


import com.erp.mastro.entities.Branch;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BranchService {

    List<Branch> getAllBranch();

    Branch getBranchById(Long id);

    void saveOrUpdateBranch(Branch branch);

    void deleteBranch(Long id);

}
