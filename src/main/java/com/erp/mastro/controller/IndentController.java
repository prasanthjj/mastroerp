package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.*;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.IndentModel;
import com.erp.mastro.model.request.ProductRequestModel;
import com.erp.mastro.model.request.StockRequestModel;
import com.erp.mastro.service.interfaces.IndentService;
import com.erp.mastro.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * controller include all indent methods
 */
@Controller
@RequestMapping("/inventory")
public class IndentController {

    @Autowired
    private ProductService productService;

    @Autowired
    private IndentService indentService;

    @Autowired
    private UserController userController;

    /**
     * method to get indent details
     *
     * @param model
     * @return indent list
     */
    @GetMapping("/getIndentList")
    public String getIndentList(Model model) {
        MastroLogUtils.info(IndentController.class, "Going to get indent list: {}");
        try {
            Branch currentBranch = userController.getCurrentUser().getUserSelectedBranch().getCurrentBranch();
            List<Indent> indentList = indentService.getAllIndents().stream()
                    .filter(indentData -> (null != indentData))
                    .filter(indentData -> (1 != indentData.getIndentDeleteStatus()))
                    .filter(indentItem -> (indentItem.getBranch().getId().equals(currentBranch.getId())))
                    .sorted(Comparator.comparing(
                            Indent::getId).reversed())
                    .collect(Collectors.toList());
            model.addAttribute("indentForm", new IndentModel());
            model.addAttribute("inventoryModule", "inventoryModule");
            model.addAttribute("indentTab", "indent");
            model.addAttribute("indentList", indentList);

            return "views/indentMaster";

        } catch (Exception e) {
            MastroLogUtils.error(AssetController.class, "Error occured while getting indent list: {}", e);
            throw e;
        }

    }


    /**
     * Method to get product details in Indent
     *
     * @param productId

     * @return product details
     */
    @RequestMapping(value = "/getProductDetailsInIndent", method = RequestMethod.GET)
    @ResponseBody
    public GenericResponse getProductDetailsInIndent(@RequestParam("productIdIndent") Long productId) {
        try {
            MastroLogUtils.info(SalesSlipController.class, "Going to get product details : {}" + productId);
            Product product = productService.getProductById(productId);

            Set<ProductUOM> productUOMS = product.getProductUOMSet().stream()
                    .filter(productuomData -> (null != productuomData))
                    .filter(productuomData -> (productuomData.getTransactionType().equals("Purchase")))
                    .collect(Collectors.toSet());
            Set<ProductRequestModel.ProductUOMModel> productUOMModels = new HashSet<>();
            for (ProductUOM productUOM : productUOMS) {
                ProductRequestModel.ProductUOMModel productUOMModel = new ProductRequestModel.ProductUOMModel();
                productUOMModel.setId(productUOM.getUom().getId());
                productUOMModel.setNameuom(productUOM.getUom().getUOM());
                productUOMModels.add(productUOMModel);
            }

            Branch currentBranch = userController.getCurrentUser().getUserSelectedBranch().getCurrentBranch();
            Set<Stock> stocks = product.getStockSet().stream()
                    .filter(stockItem -> stockItem.getBranch().getId().equals(currentBranch.getId()))
                    .filter(stockData -> (1 != stockData.getStockDeleteStatus()))
                    .collect(Collectors.toSet());
            Set<StockRequestModel> stockRequestModels = new HashSet<>();
            StockRequestModel stockRequestModel = new  StockRequestModel();
            for(Stock stock :stocks){

              //  ProductRequestModel.ProductUOMModel productUOMModel = new ProductRequestModel.ProductUOMModel();
                stockRequestModel.setId(stock.getId());
                stockRequestModel.setCurrentStock(stock.getCurrentStock());
                stockRequestModels.add(stockRequestModel);
                ItemStockDetails itemStockDetails ;

            }


            return new GenericResponse(true, "get product details")
                    .setProperty("stocks", stockRequestModel.getCurrentStock())
                   .setProperty("baseuoms", product.getUom().getUOM())
                    .setProperty("purchaseuoms", productUOMModels);
        } catch (Exception e) {
            MastroLogUtils.error(this, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * method to  get create indent page
     *
     * @param model
     * @return create indent page
     */
    @GetMapping("/getCreateIndent")
    public String getCreateIndent(Model model) {
        MastroLogUtils.info(IndentController.class, "Going to get CreateIndent : {}");
        try {
            List<Indent> indentList = indentService.getAllIndents();

            model.addAttribute("indentForm", new IndentModel());
            model.addAttribute("inventoryModule", "inventoryModule");
            model.addAttribute("indentTab", "indent");
            model.addAttribute("indentList", indentList);
            return "views/addIndent";

        } catch (Exception e) {
            MastroLogUtils.error(IndentController.class, "Error occured while creating indent : {}");
            throw e;
        }

    }

    /**
     * Method to create indent
     *
     * @param indentModel
     * @param request
     * @param model
     * @return indent page
     */
    @PostMapping("/createIndent")
    public String createIndent(@ModelAttribute("indentForm") @Valid IndentModel indentModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(IndentController.class, "Going to createIndent and indent items : {}" + indentModel.toString());
        try {
            Indent indent = indentService.createIndent(indentModel);
            model.addAttribute("inventoryModule", "inventoryModule");
            model.addAttribute("indentTab", "indent");
            model.addAttribute("indentDetails", indent);
            model.addAttribute("indentForm", new IndentModel(indentService.getIndentById(indent.getId())));
            Indent indentModelsss = indentService.getIndentById(indent.getId());


            return "views/addIndent";
        } catch (ModelNotFoundException e) {
            MastroLogUtils.error(this, "Indentmodel empty", e);
            return "views/addIndent";
        } catch (Exception e) {
            MastroLogUtils.error(IndentController.class, e.getMessage());
            throw e;
        }
    }

    /**
     * method to save indent items
     *
     * @param indentModel
     * @param request
     * @param model
     * @return indent list
     */
    @PostMapping("/saveIndent")
    public String saveIndent(@ModelAttribute("indentForm") @Valid IndentModel indentModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(IndentController.class, "Going to save indent item details: {}" + indentModel.toString());
        try {
          //  indentService.saveOrUpdateIndentItemDetails(indentModel);
            return "redirect:/inventory/getIndentList";
        } /*catch (ModelNotFoundException e) {
            MastroLogUtils.error(this, "indent model empty", e);
            return "redirect:/inventory/getIndentList";
        } */catch (Exception e) {
            MastroLogUtils.error(IndentController.class, "Error occured while save indent item details : {}", e);
            throw e;
        }

    }

    /**
     * Method To View Indent
     *
     * @param model
     * @param indentId
     * @param req
     * @return
     */

    @GetMapping("/viewIndent")
    public String getViewIndent(Model model, @RequestParam("indentId") Long indentId, HttpServletRequest req) {
        MastroLogUtils.info(IndentController.class, "Going to view Indent : {}" + indentId);
        try {
            Indent indent = indentService.getIndentById(indentId);
            model.addAttribute("indentDetails", indent);
            model.addAttribute("inventoryModule", "inventoryModule");
            model.addAttribute("indentTab", "indent");
            return "views/view_indent";
        } catch (Exception e) {
            MastroLogUtils.error(IndentController.class, "Error occured while viewing Indent : {}" + indentId, e);
            throw e;
        }
    }

    /**
     * Method to Load edit page
     *
     * @param model
     * @param
     * @param
     * @return
     */

    @RequestMapping(value = "/getEditIndent", method = RequestMethod.GET)
    public String getEditIndent(Model model, @RequestParam("indentId") Long indentId, HttpServletRequest req) {
        MastroLogUtils.info(StockController.class, "Going to edit indent : {}" + indentId);
        try {
            Indent indent = indentService.getIndentById(indentId);
            model.addAttribute("inventoryModule", "inventoryModule");
            model.addAttribute("indentTab", "indent");
            model.addAttribute("indentDetails", indent);
            model.addAttribute("indentForm", new IndentModel(indentService.getIndentById(indent.getId())));
            return "views/editIndent";
        } catch (Exception e) {
            MastroLogUtils.error(IndentController.class, "Error occured while editing Indent : {}" + indentId, e);
            throw e;
        }

    }

    /**
     * Method to edit page
     *
     * @param indentModel
     * @param request
     * @param model
     * @return
     */
    @PostMapping("/createEditIndent")
    public String createEditIndent(@ModelAttribute("indentForm") @Valid IndentModel indentModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(IndentController.class, "Going to edit indent : {}" + indentModel.toString());
        try {
            Indent indent = indentService.createIndent(indentModel);
            model.addAttribute("inventoryModule", "inventoryModule");
            model.addAttribute("indentTab", "indent");
            model.addAttribute("indentDetails", indent);
            model.addAttribute("indentForm", new IndentModel(indentService.getIndentById(indent.getId())));
            return "views/editIndent";
        } catch (ModelNotFoundException e) {
            MastroLogUtils.error(this, "Indentmodel empty", e);
            return "views/editIndent";
        } catch (Exception e) {
            MastroLogUtils.error(IndentController.class, e.getMessage());
            throw e;
        }
    }

    /**
     * Method to remove indent item
     *
     * @param model
     * @param request
     * @param indentItemId
     * @param indentId
     * @return removed response
     */
    @PostMapping("/removeIndentItem")
    @ResponseBody
    public GenericResponse removeIndentItem(Model model, HttpServletRequest request, @RequestParam("indentItemId") Long indentItemId, @RequestParam("indentId") Long indentId) {
        MastroLogUtils.info(IndentController.class, "Going to remove indent item" + indentItemId);
        try {

            indentService.removeIndentItem(indentId, indentItemId);
            return new GenericResponse(true, "delete indent item details");

        } catch (Exception e) {
            MastroLogUtils.error(this, "Error Occured while deleting indent item details :{}", e);

            throw e;
        }

    }

    /**
     * Method to Delete Indentdetails by id
     *
     * @param model
     * @param request
     * @param indentId
     * @return
     */
    @PostMapping("/deleteIndentDetails")
    @ResponseBody
    public GenericResponse deleteIndentDetails(Model model, HttpServletRequest request, @RequestParam("indentId") Long indentId) {
        MastroLogUtils.info(IndentController.class, "Going to delete IndentDetails by id :{}" + indentId);
        try {

            indentService.deleteIndentDetails(indentId);
            return new GenericResponse(true, "delete indent details");

        } catch (Exception e) {
            MastroLogUtils.error(IndentController.class, "Error occured while deleting indent details :{}" + indentId, e);
            return new GenericResponse(false, e.getMessage());

        }

    }

}
