package com.erp.mastro.service;


import com.erp.mastro.dao.BranchRepository;
import com.erp.mastro.entities.Branch;
import com.erp.mastro.service.interfaces.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {

    @Autowired
    private BranchRepository branchRepository;

    @Override
    public List<Branch> getAllBranch()
    {
        List<Branch> branchList = new ArrayList<Branch>();
        branchRepository.findAll().forEach(branch -> branchList.add(branch));
        return branchList;
    }
    public Branch getBranchById(Long id) {return branchRepository.findById(id).get();}

    public void saveOrUpdateBranch(Branch branch) {branchRepository.save(branch);}

    public void deleteBranch(Long id) {branchRepository.deleteById(id);}
}
