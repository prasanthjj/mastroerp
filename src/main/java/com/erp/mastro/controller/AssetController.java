package com.erp.mastro.controller;

import com.erp.mastro.common.MastroLogUtils;
import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.Assets;
import com.erp.mastro.exception.ModelNotFoundException;
import com.erp.mastro.model.request.AssetRequestModel;
import com.erp.mastro.service.interfaces.AssetService;
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
@RequestMapping("/master")
/**
 * Asset controller with all asset operations include list,create,edit,view and edit
 */
public class AssetController {

    @Autowired
    private AssetService assetService;

    /**
     * The method for get the full asset list
     *
     * @param model
     * @return the asset list
     */
    @GetMapping("/getAssetList")
    public String getAssetList(Model model) {
        MastroLogUtils.info(AssetController.class, "Going to get Asset List : {}");
        try {
            List<Assets> assetsList = assetService.getAllAssets().stream()
                    .filter(assetData -> (null != assetData))
                    .filter(assetData -> (1 != assetData.getAssetDeleteStatus()))
                    .sorted(Comparator.comparing(
                            Assets::getId).reversed())
                    .collect(Collectors.toList());
            model.addAttribute("assetForm", new AssetRequestModel());
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("assetTab", "asset");
            model.addAttribute("assetList", assetsList);

            return "views/assetsMaster";

        } catch (Exception e) {
            MastroLogUtils.error(AssetController.class, "Error occured while getting assets list: {}", e);
            throw e;
        }

    }

    /**
     * method to load the create asset page
     *
     * @param model
     * @return the asset creation page
     */
    @GetMapping("/getCreateAsset")
    public String getCreateAsset(Model model) {
        MastroLogUtils.info(AssetController.class, "Going to get CreateAsset : {}");
        try {
            model.addAttribute("assetForm", new AssetRequestModel());
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("assetTab", "asset");
            return "views/createAssets";

        } catch (Exception e) {
            MastroLogUtils.error(AssetController.class, "Error occured while getting CreateAsset : {}");
            throw e;
        }

    }

    /**
     * Method to save and edit asset
     *
     * @param assetRequestModel
     * @param request
     * @param model
     * @return saved asset in asset list
     */
    @PostMapping("/saveAsset")
    public String saveAsset(@ModelAttribute("assetForm") @Valid AssetRequestModel assetRequestModel, HttpServletRequest request, Model model) {
        MastroLogUtils.info(AssetController.class, "Going to save asset : {}");
        try {
            assetService.saveOrUpdateAssets(assetRequestModel);
            return "redirect:/master/getAssetList";
        } catch (ModelNotFoundException e) {
            MastroLogUtils.error(this, "AssetModel empty", e);
            return "redirect:/master/getAssetList";
        } catch (Exception e) {
            MastroLogUtils.error(AssetController.class, "Error occured while editing asset : {}", e);
            throw e;
        }

    }

    /**
     * method to load the asset edit page
     *
     * @param request
     * @param assetId
     * @param model
     * @return the asset data for edit
     */
    @RequestMapping(value = "/getAssetEdit", method = RequestMethod.GET)
    public String getAssetEdit(HttpServletRequest request, @RequestParam("assetId") Long assetId, Model model) {
        MastroLogUtils.info(AssetController.class, "Going to edit asset : {}" + assetId);
        try {

            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("assetTab", "asset");
            if (assetId != null) {
                model.addAttribute("assetForm", new AssetRequestModel(assetService.getAssetsById(assetId)));
                model.addAttribute("assetCharSize", assetService.getAssetsById(assetId).getAssetCharacteristics().size());
                model.addAttribute("assetMainSize", assetService.getAssetsById(assetId).getAssetMaintenanceActivities().size());
                model.addAttribute("assetCheckSize", assetService.getAssetsById(assetId).getAssetChecklists().size());
            }

            return "views/editAssets";

        } catch (Exception e) {
            MastroLogUtils.error(AssetController.class, "Error occured while editing asset : {}", e);
            throw e;
        }

    }

    /**
     * Method to delete asset
     *
     * @param model
     * @param request
     * @param assetId
     * @return deleted asset list
     */
    @PostMapping("/deleteAssetDetails")
    @ResponseBody
    public GenericResponse deleteAssetDetails(Model model, HttpServletRequest request, @RequestParam("assetids") Long assetId) {
        MastroLogUtils.info(AssetController.class, "Going to delete Asset : {}");
        try {
            if (assetId != null) {
                assetService.deleteAssetDetails(assetId);
                return new GenericResponse(true, "delete asset details");
            } else {
                return new GenericResponse(false, "asset id null");
            }

        } catch (Exception e) {
            MastroLogUtils.error(AssetController.class, "Error occured while deleting asset : {}", e);
            return new GenericResponse(false, e.getMessage());

        }

    }

    /**
     * method to view asset
     *
     * @param model
     * @param assetId
     * @param req
     * @return the required asset view
     */
    @GetMapping("/viewAsset")
    public String getViewAsset(Model model, @RequestParam("assetId") Long assetId, HttpServletRequest req) {
        MastroLogUtils.info(AssetController.class, "Going to view Asset :{}" + assetId);
        try {
            Assets assets = assetService.getAssetsById(assetId);
            model.addAttribute("assetDetails", assets);
            model.addAttribute("masterModule", "masterModule");
            model.addAttribute("assetTab", "asset");
            return "views/viewAssets";
        } catch (Exception e) {
            MastroLogUtils.error(AssetController.class, "Error occured while viewing asset : {}", e);
            throw e;
        }

    }
}
