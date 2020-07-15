package com.erp.mastro.controller;

import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.Assets;
import com.erp.mastro.model.request.AssetRequestModel;
import com.erp.mastro.service.interfaces.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/master")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @GetMapping("/getAssetList")
    public String getAssetList(Model model) {

        try {
            List<Assets> assetsList = new ArrayList<>();
            for (Assets asset : assetService.getAllAssets()) {
                if (asset.getAssetDeleteStatus() != 1) {
                    assetsList.add(asset);
                }
            }
            model.addAttribute("assetForm", new AssetRequestModel());
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("assetTab", "asset");
            model.addAttribute("assetList", assetsList);

            return "views/assetsMaster";

        } catch (Exception e) {
            throw e;
        }

    }

    @GetMapping("/getCreateAsset")
    public String getCreateAsset(Model model) {

        try {
            model.addAttribute("assetForm", new AssetRequestModel());
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("assetTab", "asset");
            return "views/createAssets";

        } catch (Exception e) {
            throw e;
        }

    }

    @PostMapping("/saveAsset")
    public String saveAsset(@ModelAttribute("assetForm") @Valid AssetRequestModel assetRequestModel, HttpServletRequest request, Model model) {

        try {
            assetService.saveOrUpdateAssets(assetRequestModel);
            return "redirect:/master/getAssetList";
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/getAssetEdit", method = RequestMethod.GET)
    public String getAssetEdit(HttpServletRequest request, @RequestParam("assetId") Long assetId, Model model) {

        try {
            AssetRequestModel assetRequestModel = new AssetRequestModel();
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("assetTab", "asset");
            model.addAttribute("assetForm", assetService.getAssetsById(assetId));
            return "views/editAssetMaster";

        } catch (Exception e) {
            throw e;
        }

    }

    @PostMapping("/deleteAssetDetails")
    @ResponseBody
    public GenericResponse deleteAssetDetails(Model model, HttpServletRequest request, @RequestParam("assetids") Long assetId) {

        try {

            assetService.deleteAssetDetails(assetId);
            return new GenericResponse(true, "delete asset details");

        } catch (Exception e) {

            return new GenericResponse(false, e.getMessage());

        }

    }

    @GetMapping("/viewAsset")
    public String getViewAsset(Model model, @RequestParam("assetId") Long assetId, HttpServletRequest req) {

        try {
            Assets assets = assetService.getAssetsById(assetId);
            model.addAttribute("assetDetails", assets);
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("assetTab", "asset");
            return "views/viewAssets";
        } catch (Exception e) {
            throw e;
        }

    }
}
