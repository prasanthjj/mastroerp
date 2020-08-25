package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.dao.AutoPopulateDAO;
import com.erp.mastro.entities.Product;
import com.erp.mastro.entities.User;
import com.erp.mastro.model.request.ProductRequestModel;
import com.erp.mastro.service.interfaces.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/autopopulate")
public class StockAutoCompleteController {

    @Autowired
    AutoPopulateDAO autoPopulateDao;

    @Autowired
    UserController userController;

    @Autowired
    StockService stockService;

    /**
     * Method to get autopopulate items
     *
     * @param searchTerm
     * @return item list
     */
    @RequestMapping(value = "/stock", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponse products(@RequestParam("searchTerm") String searchTerm) {
        try {
            MastroLogUtils.info(com.erp.mastro.controller.AutocompleteController.class, "Going to get product in autocomplete : {}");
            User userDetails = userController.getCurrentUser();
            List<Product> products = autoPopulateDao.getAutoPopulateList("productName", searchTerm, Product.class, 50);
            List<Product> productsFinal = products.stream()
                    .filter(productData -> (null != productData))
                    .filter(productData -> (true == productData.isEnabled()))
                    /*.filter(productData -> (userDetails.getUserSelectedBranch().getCurrentBranch().getId()
                            != productData.getStockDetailsSet().getBranch().getId()))*/
                    .collect(Collectors.toList());
              /*  List<Stock> stockDetailsList = new ArrayList<>();
             for (Product product : productsFinal) {
                     stockDetailsList = product.getStockDetailsSet().stream()
                            .filter(productstockData -> (null != productstockData))
                            .filter(productstockData -> (!productstockData.getBranch().getId().equals(userDetails.getUserSelectedBranch().getCurrentBranch().getId())))
                                    .collect(Collectors.toList());
                }
                List<Product> productList = new ArrayList<>();
             for (Stock stockDetails : stockDetailsList) {
                 productList.add(stockDetails.getProduct());

             }*/

            Set<ProductRequestModel> productRequestModels = new HashSet<ProductRequestModel>();
            for (Product product : productsFinal) {
                ProductRequestModel productRequestModel = new ProductRequestModel();
                productRequestModel.setId(product.getId());
                productRequestModel.setProductname(product.getProductName());
                productRequestModels.add(productRequestModel);
            }
            return new GenericResponse(true, "get items")
                    .setProperty("products", productRequestModels);

        } catch (Exception e) {
            MastroLogUtils.error(this, e.getMessage(), e);
            throw e;
        }
    }

}
