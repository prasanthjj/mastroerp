package com.erp.mastro.controller;

import com.erp.mastro.entities.Assets;
import com.erp.mastro.model.request.AssetRequestModel;
import com.erp.mastro.service.interfaces.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AssetController {

    @Autowired
    private AssetService assetService;

    @GetMapping("/master/getAssetList")
    public String getAssetList(Model model) {

        try {
            List<Assets> assetsList = new ArrayList<>();
            model.addAttribute("assetForm", new AssetRequestModel());
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("assetTab", "asset");

            return "views/assetsMaster";

        } catch (Exception e) {
            throw e;
        }

    }

    @GetMapping("/master/getCreateAsset")
    public String getCreateAsset(Model model) {

        try {
            model.addAttribute("assetForm", new AssetRequestModel());
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("assetTab", "asset");
            return "views/createAssetMaster";

        } catch (Exception e) {
            throw e;
        }

    }

    @PostMapping("/master/saveAsset")
    public String saveAsset(@ModelAttribute("assetForm") @Valid AssetRequestModel assetRequestModel, HttpServletRequest request, Model model) {

        try {
            assetService.saveOrUpdateAssets(assetRequestModel);
            return "redirect:/master/getAssetList";
        } catch (Exception e) {
            throw e;
        }
    }
}
