package com.erp.mastro.service;

import com.erp.mastro.repository.SubCategoryRepository;
import com.erp.mastro.service.interfaces.SubcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class SubCategoryServiceTest {

    @Autowired
    private SubcategoryService subcategoryService;

    @MockBean
    private SubCategoryRepository subCategoryRepository;

}
