package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.entities.Indent;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.IndentModel;
import com.erp.mastro.service.interfaces.IndentService;
import com.erp.mastro.service.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * controller include all indent methods
 */
@Controller
@RequestMapping("/indent")
public class IndentController {

    @Autowired
    private ProductService productService;

    @Autowired
    private IndentService indentService;

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
            List<Indent> indentList = new ArrayList<>();
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
     * method to  get create indent page
     *
     * @param model
     * @return create indent page
     */
    @GetMapping("/getCreateIndent")
    public String getCreateIndent(Model model) {
        MastroLogUtils.info(IndentController.class, "Going to get CreateIndent : {}");
        try {
            model.addAttribute("indentForm", new IndentModel());
            model.addAttribute("inventoryModule", "inventoryModule");
            model.addAttribute("indentTab", "indent");
            return "views/addIndent";

        } catch (Exception e) {
            MastroLogUtils.error(IndentController.class, "Error occured while creating indent : {}");
            throw e;
        }

    }

    @PostMapping("/createIndent")
    public String createIndent(@ModelAttribute("indentForm") @Valid IndentModel indentModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(IndentController.class, "Going to createIndent and indent items : {}");
        try {
            Indent indent = indentService.createIndent(indentModel);
            model.addAttribute("inventoryModule", "inventoryModule");
            model.addAttribute("indentTab", "indent");
            model.addAttribute("indentDetails", indent);
            return "views/addIndent";
        } catch (ModelNotFoundException e) {
            MastroLogUtils.error(this, "Indentmodel empty", e);
            return "views/addIndent";
        } catch (Exception e) {
            MastroLogUtils.error(IndentController.class, e.getMessage());
            throw e;
        }
    }

}
