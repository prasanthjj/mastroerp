package com.erp.mastro.service;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.entities.SalaryComponent;
import com.erp.mastro.model.request.SalaryComponentRequestModel;
import com.erp.mastro.repository.SalaryComponentRepository;
import com.erp.mastro.service.interfaces.SalaryComponentService;
import com.erp.mastro.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SalaryComponentServiceImpl implements SalaryComponentService {

    private Logger logger = LoggerFactory.getLogger(SalaryComponentServiceImpl.class);

    @Autowired
    SalaryComponentRepository salaryComponentRepository;

    /**
     * method to get all Salary Component
     *
     * @return salaryComponent list
     */
    @Override
    public List<SalaryComponent> getAllSalaryComonents() {
        List<SalaryComponent> salaryComponentList=new ArrayList<>();
        salaryComponentRepository.findAll().forEach(salaryComponent -> salaryComponentList.add(salaryComponent));
        return salaryComponentList;
    }

    /**
     * method to get salary component according to id
     *
     * @param id
     * @return salrycomponent
     */

    @Override
    public SalaryComponent getSalaryComponentId(Long id) {
        return salaryComponentRepository.findById(id).get();
    }

    /**
     * Save or edit salary Branch
     *
     * @param salaryComponentRequestModel
     * @throws ParseException
     */
    @Override
    @Transactional(rollbackOn = {Exception.class})
    public void saveOrUpdateSalaryComponent(SalaryComponentRequestModel salaryComponentRequestModel , String value)  {
        if (salaryComponentRequestModel.getId()==null){
            MastroLogUtils.info(SalaryComponentService.class, "Going to save salarycomponent {}" + salaryComponentRequestModel.toString());
            SalaryComponent salaryComponent =new SalaryComponent();
            salaryComponent.setComponentType(salaryComponentRequestModel.getComponentType());
            salaryComponent.setComponentName(salaryComponentRequestModel.getComponentName());
            salaryComponent.setPayslipName(salaryComponentRequestModel.getPayslipName());
            //salaryComponent.setCalculation_Type(salaryComponentRequestModel.getCalculation_Type());
            salaryComponent.setCalculation_Type(value);
            if(salaryComponent.getCalculation_Type().equals("Flat-Amount")){
                salaryComponent.setAmount(salaryComponentRequestModel.getAmount());
            }
            else {
                salaryComponent.setAmount(salaryComponentRequestModel.getPercentageAmount());
            }
            salaryComponent.setStatus(true);
            salaryComponentRepository.save(salaryComponent);
            MastroLogUtils.info(SalaryComponentService.class, "Saved " + salaryComponent.getComponentName() + " successfully.");

        }else{
            MastroLogUtils.info(SalaryComponentService.class, "Going to edit Salary Component {}" + salaryComponentRequestModel.toString());
            SalaryComponent salaryComponent = getSalaryComponentId(salaryComponentRequestModel.getId());
            //SalaryComponent salaryComponent = salaryComponentRepository.findById(salaryComponentRequestModel.getId()).get();
            salaryComponent.setComponentType(salaryComponentRequestModel.getComponentType());
            salaryComponent.setComponentName(salaryComponentRequestModel.getComponentName());
            salaryComponent.setPayslipName(salaryComponentRequestModel.getPayslipName());
            salaryComponent.setCalculation_Type(value);
            salaryComponent.setAmount(salaryComponentRequestModel.getAmount());
            salaryComponentRepository.save(salaryComponent);
            MastroLogUtils.info(SalaryComponentService.class, "Updated " + salaryComponent.getComponentName() + " successfully.");
        }

    }

    /**
     * Activate or Deactivate Salary Component
     *
     * @param id
     */
    @Override
    public void activateOrDeactivateSalaryComponent(Long id) {
        if (id != null) {
            SalaryComponent salaryComponent = getSalaryComponentId(id);
            if (salaryComponent.isStatus()) {
                salaryComponent.setStatus(false);
            } else {
                salaryComponent.setStatus(true);
            }
            salaryComponentRepository.save(salaryComponent);
            MastroLogUtils.info(UserService.class, "Activate or Deactivate " + salaryComponent.getComponentName() + " successfully.");
        } else {
            MastroLogUtils.info(UserService.class, "Salary Component  Id null");
        }

    }
}
