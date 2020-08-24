package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.Product;
import com.erp.mastro.entities.StockDetails;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.StockRequestModel;
import com.erp.mastro.service.interfaces.ProductService;
import com.erp.mastro.service.interfaces.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/inventory")
public class StockController {

    @Autowired
    StockService stockService;

    @Autowired
    ProductService productService;

    /**
     * Method to load stock page
     *
     * @param model
     * @return
     */

    @GetMapping("/getStock")
    public String getStock(Model model) {
        MastroLogUtils.info(StockController.class, "Going to get all stocks : {}");
        try {
            List<StockDetails> stockDetailsList = stockService.getAllStockDetails().stream()
                    .filter(stockData -> (null != stockData))
                    .sorted(Comparator.comparing(StockDetails::getId).reversed())
                    .collect(Collectors.toList());
            model.addAttribute("inventoryModule", "inventoryModule");
            model.addAttribute("stockTab", "stock");
            model.addAttribute("stockDetailsList", stockDetailsList);
            return "views/stockDetails";

        } catch (Exception e) {
            MastroLogUtils.error(StockController.class, "Error occured while getting all stocks : { }", e);
            throw e;

        }
    }

    /**
     * Method to get create Stock
     *
     * @param model
     * @return
     */

    @GetMapping("/getCreateStock")
    public String getCreateStock(Model model) {
        MastroLogUtils.info(StockController.class, "Going to get stock :{}");
        try {
            model.addAttribute("stockForm", new StockRequestModel());
            model.addAttribute("inventoryModule", "inventoryModule");
            model.addAttribute("stockTab", "stock");
            return "views/createStockDetails";
        } catch (Exception e) {
            MastroLogUtils.error(StockController.class, "Error occured while adding stock : {}", e);
            throw e;
        }
    }

    /**
     * Method to get selected product
     *
     * @param model
     * @param req
     * @return
     */

    @GetMapping("/getStockSelectedProduct")
    public String getStockSelectedProduct(Model model, HttpServletRequest req) {
        Long productId = Long.parseLong(req.getParameter("selectedStockProduct"));
        MastroLogUtils.info(StockController.class, "Going to get product : {}", +productId);
        try {
            Product product = productService.getProductById(productId);
            model.addAttribute("productDetails", product);
            model.addAttribute("inventoryModule", "inventoryModule");
            model.addAttribute("stockTab", "stock");
            model.addAttribute("stockForm", new StockRequestModel());
            return "views/createStockDetails";
        } catch (Exception e) {
            MastroLogUtils.error(StockController.class, "Error occured while getting product :{}", e);
            throw e;
        }

    }

    /**
     * Method to save stock
     *
     * @param stockRequestModel
     * @param request
     * @param model
     * @return
     * @throws ModelNotFoundException
     */

    @PostMapping("/saveStock")
    public String saveStock(@ModelAttribute("stockForm") @Valid StockRequestModel stockRequestModel, HttpServletRequest request, Model model) throws ModelNotFoundException {
        MastroLogUtils.info(StockController.class, "Going to save stock : {}");
        try {
            stockService.saveOrUpdateStockDetails(stockRequestModel);
            return "redirect:/inventory/getStock";
        } catch (Exception e) {
            MastroLogUtils.error(StockController.class, "Error occured while saving stocks : {}");
            throw e;
        }
    }

    /**
     * Method to view stock
     *
     * @param model
     * @param stockId
     * @param req
     * @return
     */
    @GetMapping("/viewStock")
    public String getViewStock(Model model, @RequestParam("stockId") Long stockId, HttpServletRequest req) {
        MastroLogUtils.info(StockController.class, "Going to view Stock : {}" + stockId);
        try {
            StockDetails stockDetails = stockService.getStockById(stockId);
            model.addAttribute("stockDetails", stockDetails);
            model.addAttribute("inventoryModule", "inventoryModule");
            model.addAttribute("stockTab", "stock");
            return "views/view_stock_details";
        } catch (Exception e) {
            MastroLogUtils.error(StockController.class, "Error occured while viewing Stock : {}" + stockId, e);
            throw e;
        }
    }

    /**
     * Method to edit stock
     *
     * @param model
     * @param stockId
     * @param req
     * @return
     */
    @RequestMapping(value = "/getEditStock", method = RequestMethod.GET)
    public String getEditStock(Model model, @RequestParam("stockId") Long stockId, HttpServletRequest req) {
        MastroLogUtils.info(StockController.class, "Going to edit stock : {}" + stockId);
        try {
            StockRequestModel stockRequestModel = new StockRequestModel();
            model.addAttribute("inventoryModule", "inventoryModule");
            model.addAttribute("stockTab", "stock");
            model.addAttribute("stockForm", stockService.getStockById(stockId));
            return "views/edit_stock_details";
        } catch (Exception e) {
            MastroLogUtils.error(StockController.class, "Error occured while editing Stock : {}" + stockId, e);
            throw e;
        }

    }

    /**
     * Method to Delete Stockdetails by id
     *
     * @param model
     * @param request
     * @param stockId
     * @return
     */
    @PostMapping("/deleteStockDetails")
    @ResponseBody
    public GenericResponse deleteStockDetails(Model model, HttpServletRequest request, @RequestParam("stockId") Long stockId) {
        MastroLogUtils.info(StockController.class, "Going to delete StockDetails by id :{}" + stockId);
        try {

            stockService.deleteStockDetails(stockId);
            return new GenericResponse(true, "delete stock details");

        } catch (Exception e) {
            MastroLogUtils.error(StockController.class, "Error occured while deleting stock details :{}" + stockId, e);
            return new GenericResponse(false, e.getMessage());

        }

    }

}