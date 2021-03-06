package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.dao.AutoPopulateDAO;
import com.erp.mastro.entities.*;
import com.erp.mastro.model.request.BranchRequestModel;
import com.erp.mastro.model.request.PartyRequestModel;
import com.erp.mastro.model.request.ProductRequestModel;
import com.erp.mastro.service.IndentServiceImpl;
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

/**
 * Autopoulate controller
 */
@Controller
@RequestMapping("/autopopulate")
public class AutocompleteController {

    @Autowired
    AutoPopulateDAO autoPopulateDao;

    @Autowired
    IndentServiceImpl indentService;

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
    @RequestMapping(value = "/items", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponse products(@RequestParam("searchTerm") String searchTerm) {
        try {
            MastroLogUtils.info(AutocompleteController.class, "Going to get product in autocomplete : {}");
            List<Product> productsFinal;
            List<Product> products = autoPopulateDao.getAutoPopulateList("productName", searchTerm, Product.class, 50);
            productsFinal = products.stream()
                    .filter(productData -> (null != productData))
                    .filter(productData -> (true == productData.isEnabled()))
                    .collect(Collectors.toList());
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

    /**
     * Get parts in autocomplete
     *
     * @param searchTerm
     * @return partys
     */
    @RequestMapping(value = "/party", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponse getPartys(@RequestParam("searchTerm") String searchTerm) {
        try {
            MastroLogUtils.info(AutocompleteController.class, "Going to get party in autocomplete : {}");
            List<Party> partyFinal;
            List<Party> partys = autoPopulateDao.getAutoPopulateList("partyName", searchTerm, Party.class, 50);
            partyFinal = partys.stream()
                    .filter(partysData -> (null != partysData))
                    .filter(partysData -> (true == partysData.isEnabled()))
                    .collect(Collectors.toList());
            Set<PartyRequestModel> partyRequestModels = new HashSet<PartyRequestModel>();
            for (Party party : partyFinal) {
                PartyRequestModel partyRequestModel = new PartyRequestModel();
                partyRequestModel.setId(party.getId());
                partyRequestModel.setPartysname(party.getPartyName());
                partyRequestModels.add(partyRequestModel);
            }
            return new GenericResponse(true, "get partys")
                    .setProperty("partys", partyRequestModels);

        } catch (Exception e) {
            MastroLogUtils.error(this, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Party autocomplete with party type
     *
     * @param searchTerm
     * @param partyType
     * @return the response
     */
    @RequestMapping(value = "/party/type", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponse getPartysOnType(@RequestParam("searchTerm") String searchTerm, @RequestParam("partyType") String partyType) {
        try {
            MastroLogUtils.info(AutocompleteController.class, "Going to get party in autocomplete based on type : {}");
            List<Party> partyFinal;
            List<Party> partys = autoPopulateDao.getAutoPopulateList("partyName", searchTerm, Party.class, 50);
            partyFinal = partys.stream()
                    .filter(partysData -> (null != partysData))
                    .filter(partysData -> (true == partysData.isEnabled()))
                    .filter(partysData -> (partyType.equals(partysData.getPartyType())))
                    .collect(Collectors.toList());
            Set<PartyRequestModel> partyRequestModels = new HashSet<PartyRequestModel>();
            for (Party party : partyFinal) {
                PartyRequestModel partyRequestModel = new PartyRequestModel();
                partyRequestModel.setId(party.getId());
                partyRequestModel.setPartysname(party.getPartyName());
                partyRequestModels.add(partyRequestModel);
            }
            return new GenericResponse(true, "get partys")
                    .setProperty("partys", partyRequestModels);

        } catch (Exception e) {
            MastroLogUtils.error(this, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * autocomplete method for item in indent shows only that branch stock items
     *
     * @param searchTerm
     * @return the item list
     */
    @RequestMapping(value = "/items/indent", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponse productsInIndent(@RequestParam("searchTerm") String searchTerm) {
        try {
            MastroLogUtils.info(AutocompleteController.class, "Going to get product in autocomplete : {}");
            List<Product> productsFinal;
            List<Product> products = autoPopulateDao.getAutoPopulateList("productName", searchTerm, Product.class, 50);
            productsFinal = products.stream()
                    .filter(productData -> (null != productData))
                    .filter(productData -> (true == productData.isEnabled()))
                    .collect(Collectors.toList());

            Branch currentBranch = userController.getCurrentUser().getUserSelectedBranch().getCurrentBranch();
            Set<Product> productSet = new HashSet<>();
            for (Product product : productsFinal) {
                Set<Stock> stocksSet = indentService.getAllStocks().stream()
                        .filter(stockData -> (null != stockData))
                        .filter(stockData -> (currentBranch.getId().equals(stockData.getBranch().getId())))
                        .filter(stockData -> (product.getId().equals(stockData.getProduct().getId())))
                        .filter(stockData -> (1 != stockData.getStockDeleteStatus()))
                        .collect(Collectors.toSet());
                if (stocksSet.isEmpty() == false) {
                    Stock stock = stocksSet.stream().findFirst().get();
                    productSet.add(stock.getProduct());
                } else {
                    MastroLogUtils.info(AutocompleteController.class, "stock set is empty for the product" + product.getId() + "in branch" + currentBranch.getId());
                }
            }
            Set<ProductRequestModel> productRequestModels = new HashSet<ProductRequestModel>();
            for (Product product : productSet) {
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

    /**
     * Method to get autopopulate items
     *
     * @param searchTerm
     * @return item list
     */
    @RequestMapping(value = "/stock", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponse productsInStock(@RequestParam("searchTerm") String searchTerm) {
        try {
            MastroLogUtils.info(com.erp.mastro.controller.AutocompleteController.class, "Going to get product in autocomplete : {}");
            List<Product> products = autoPopulateDao.getAutoPopulateList("productName", searchTerm, Product.class, 50);
            List<Product> productsFinal = products.stream()
                    .filter(productData -> (null != productData))
                    .filter(productData -> (true == productData.isEnabled()))
                    .collect(Collectors.toList());

            Branch currentBranch = userController.getCurrentUser().getUserSelectedBranch().getCurrentBranch();
            Set<Product> productSet = new HashSet<>();
            for (Product product : productsFinal) {
                Set<Stock> stocksSet = stockService.getAllStockDetails().stream()
                        .filter(stockData -> (null != stockData))
                        .filter(stockData -> (currentBranch.getId().equals(stockData.getBranch().getId())))
                        .filter(stockData -> (product.getId().equals(stockData.getProduct().getId())))
                        .filter(stockData -> (1 != stockData.getStockDeleteStatus()))
                        .collect(Collectors.toSet());
                if (stocksSet.isEmpty() == true) {
                    productSet.add(product);
                } else {
                    MastroLogUtils.info(AutocompleteController.class, "product" + product.getId() + "in branch" + currentBranch.getId() + "already in stock");
                }
            }
            Set<ProductRequestModel> productRequestModels = new HashSet<ProductRequestModel>();
            for (Product product : productSet) {
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


    /**
     * Method to get autopopulate items
     *
     * @param searchTerm
     * @return item list
     */
    @RequestMapping(value = "/branch", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponse branches(@RequestParam("searchTerm") String searchTerm) {
        try {
            MastroLogUtils.info(AutocompleteController.class, "Going to get Branch in autocomplete : {}");
            List<Branch> branchFinal;
            List<Branch> branches = autoPopulateDao.getAutoPopulateList("branchName", searchTerm, Branch.class, 50);
            branchFinal = branches.stream()
                    .filter(branchData -> (null != branchData))
                    .filter(branchData -> (1 != branchData.getBranchDeleteStatus()))
                    .collect(Collectors.toList());
            Set<BranchRequestModel> branchRequestModels = new HashSet<BranchRequestModel>();
            for (Branch branch : branchFinal) {
                BranchRequestModel branchRequestModel = new BranchRequestModel();
                branchRequestModel.setId(branch.getId());
                branchRequestModel.setBranchName(branch.getBranchName());
                branchRequestModels.add(branchRequestModel);
            }
            return new GenericResponse(true, "get items")
                    .setProperty("branches", branchRequestModels);

        } catch (Exception e) {
            MastroLogUtils.error(this, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Method to get product in sales slip
     *
     * @param searchTerm
     * @param partyId
     * @return products
     */
    @RequestMapping(value = "/items/salesslip", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponse productsInSalesSlip(@RequestParam("searchTerm") String searchTerm, @RequestParam("partyId") Long partyId) {
        try {
            MastroLogUtils.info(AutocompleteController.class, "Going to get product in autocomplete : {}");
            List<Product> productsFinal;
            List<Product> products = autoPopulateDao.getAutoPopulateList("productName", searchTerm, Product.class, 50);
            productsFinal = products.stream()
                    .filter(productData -> (null != productData))
                    .filter(productData -> (true == productData.isEnabled()))
                    .collect(Collectors.toList());

            Branch currentBranch = userController.getCurrentUser().getUserSelectedBranch().getCurrentBranch();
            Set<Product> productSet = new HashSet<>();
            for (Product product : productsFinal) {
                Set<Stock> stocksSet = indentService.getAllStocks().stream()
                        .filter(stockData -> (null != stockData))
                        .filter(stockData -> (currentBranch.getId().equals(stockData.getBranch().getId())))
                        .filter(stockData -> (product.getId().equals(stockData.getProduct().getId())))
                        .filter(stockData -> (1 != stockData.getStockDeleteStatus()))
                        /* .filter(stockData -> (0 != stockData.getCurrentStock()))*/
                        .collect(Collectors.toSet());
                if (stocksSet.isEmpty() == false) {
                    Stock stock = stocksSet.stream().findFirst().get();
                    productSet.add(stock.getProduct());
                } else {
                    MastroLogUtils.info(AutocompleteController.class, "stock set is empty for the product" + product.getId() + "in branch" + currentBranch.getId());
                }
            }
            Set<ProductRequestModel> productRequestModels = new HashSet<ProductRequestModel>();
            for (Product product : productSet) {
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

    @RequestMapping(value = "/party/customer", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponse getCustomerPartys(@RequestParam("searchTerm") String searchTerm) {
        try {
            MastroLogUtils.info(AutocompleteController.class, "Going to get party in autocomplete : {}");
            List<Party> partyFinal;
            List<Party> partys = autoPopulateDao.getAutoPopulateList("partyName", searchTerm, Party.class, 50);
            partyFinal = partys.stream()
                    .filter(partysData -> (null != partysData))
                    .filter(partysData -> (true == partysData.isEnabled()))
                    .filter(partysData -> (partysData.getPartyType().equals("Customer")))
                    .collect(Collectors.toList());
            Set<PartyRequestModel> partyRequestModels = new HashSet<PartyRequestModel>();
            for (Party party : partyFinal) {
                PartyRequestModel partyRequestModel = new PartyRequestModel();
                partyRequestModel.setId(party.getId());
                partyRequestModel.setPartysname(party.getPartyName());
                partyRequestModels.add(partyRequestModel);
            }
            return new GenericResponse(true, "get partys")
                    .setProperty("partys", partyRequestModels);

        } catch (Exception e) {
            MastroLogUtils.error(this, e.getMessage(), e);
            throw e;
        }
    }

    @RequestMapping(value = "/items/customer", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponse productsInCustomer(@RequestParam("searchTerm") String searchTerm, @RequestParam("partyId") Long partyId) {
        try {
            MastroLogUtils.info(AutocompleteController.class, "Going to get product in autocomplete : {}");
            List<Product> productsFinal;
            List<Product> products = autoPopulateDao.getAutoPopulateList("productName", searchTerm, Product.class, 50);
            productsFinal = products.stream()
                    .filter(productData -> (null != productData))
                    .filter(productData -> (true == productData.isEnabled()))
                    .collect(Collectors.toList());

            Branch currentBranch = userController.getCurrentUser().getUserSelectedBranch().getCurrentBranch();
            Set<Product> productSet = new HashSet<>();
            for (Product product : productsFinal) {
                Set<Stock> stocksSet = indentService.getAllStocks().stream()
                        .filter(stockData -> (null != stockData))
                        .filter(stockData -> (currentBranch.getId().equals(stockData.getBranch().getId())))
                        .filter(stockData -> (product.getId().equals(stockData.getProduct().getId())))
                        .filter(stockData -> (1 != stockData.getStockDeleteStatus()))
                        .collect(Collectors.toSet());
                if (stocksSet.isEmpty() == false) {
                    Stock stock = stocksSet.stream().findFirst().get();
                    productSet.add(stock.getProduct());
                } else {
                    MastroLogUtils.info(AutocompleteController.class, "stock set is empty for the product" + product.getId() + "in branch" + currentBranch.getId());
                }
            }

            Set<ProductRequestModel> productRequestModels = new HashSet<ProductRequestModel>();
            for (Product product : productSet) {
                Set<ProductPartyRateRelation> productPartyRateRelationSet=product.getProductPartyRateRelations().stream()
                        .filter(productPartyData -> (null !=productPartyData))
                        .filter(productPartyData -> (partyId.equals(productPartyData.getParty().getId())))
                        .collect(Collectors.toSet());
                if(productPartyRateRelationSet.isEmpty()==false)
                {
                    ProductRequestModel productRequestModel = new ProductRequestModel();
                    productRequestModel.setId(product.getId());
                    productRequestModel.setProductname(product.getProductName());
                    productRequestModels.add(productRequestModel);
                }

            }

            /*Set<ProductRequestModel> productRequestModels = new HashSet<ProductRequestModel>();
            for (Product product : productSet) {
                ProductRequestModel productRequestModel = new ProductRequestModel();
                productRequestModel.setId(product.getId());
                productRequestModel.setProductname(product.getProductName());
                productRequestModels.add(productRequestModel);

            }*/
            return new GenericResponse(true, "get items")
                    .setProperty("products", productRequestModels);

        } catch (Exception e) {
            MastroLogUtils.error(this, e.getMessage(), e);
            throw e;
        }
    }


}
