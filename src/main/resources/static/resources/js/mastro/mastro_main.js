$(document).ready(function(){

  //add category start
                   $("body").on('click','.addCategoyss',function (e) {

                    e.preventDefault();

                  $('#catalogId').val($(this).data('catalogid'));

                      });


                  //add category end
 //add subcategory start
                                     $("body").on('click','.addSubCategorys',function (e) {

                                      e.preventDefault();

                                    $('#categorysId').val($(this).data('categoryyid'));

                                        });


   //add subcategory end
    // Add Price List Start

     $("#addPriceListForm").validate({
         rules: {
            partyType: {
                 required: true,
             },
            categoryType: {
                required: true,
            },
            pricelistName: {
                required: true,
            },
             discountPercentage: {
                            required: true,
                            number:true
                        },
           allowedPriceDevPerUpper: {
                    number:true
                              },
          allowedPriceDevPerLower: {
                  number:true
              }
         }
     });
    //Add Price List End
      // edit Price List Start

      $("#editPriceListForm").validate({
        rules: {

           pricelistName: {
               required: true,
           },

                        discountPercentage: {
                                       required: true,
                                       number:true
                                   },
                      allowedPriceDevPerUpper: {
                               number:true
                                         },
                     allowedPriceDevPerLower: {
                             number:true
                         }
        }
    });
   //edit Price List End
       //hsn create validation start
              $("#hsnForm").validate({
                               rules: {
                                entryDate: {
                                 required:true
                                      },
                                 hsnCode: {
                                    required:true
                                  },
                                  gstGoodsName: {
                                   required:true
                                    },
                                  effectiveFrom: {
                                      required:true
                                        },
                                    sgst: {
                                    required:true,
                                            number:true

                                             },
                                    cgst: {
                                       required:true,
                                        number:true

                                          },
                                          utgst: {

                                        number:true

                                         },
                                          cess: {

                                           number:true

                                         },
                                   igst: {
                                   required:true,
                                  number:true

                                        }

                               }
                           });
     //hsn create validation start
  // Add item Start

   $("#createItemForm").validate({
    rules: {
       hsnId: {
            required: true,
        },
        baseUOM: {
           required: true,
       },
       baseQuantity: {
           required: true,
       },
       basePrice: {
        required: true,
    },
    colour: {
            required: true,
        },
         dimension: {
                    required: true,
                },
     brandId: {
                 required: true,
             }


    },

});
//Add item End

       $(function(datepicker) {

       //Add assets dates Start
           $('#addAssetInstallationDate .input-group.date').datepicker({
               todayBtn: "linked",
               keyboardNavigation: false,
               forceParse: false,
               calendarWeeks: true,
               autoclose: true
           });
           $('#assetEffectiveDate .input-group.date').datepicker({
               todayBtn: "linked",
               keyboardNavigation: false,
               forceParse: false,
               calendarWeeks: true,
               autoclose: true
           });

       //add assets dates End

        // Add HSN start
           $('#addHsnEntryDate .input-group.date').datepicker({
               todayBtn: "linked",
               keyboardNavigation: false,
               forceParse: false,
               calendarWeeks: true,
               autoclose: true
           });
           $('#addHsnEffectiveDate .input-group.date').datepicker({
               todayBtn: "linked",
               keyboardNavigation: false,
               forceParse: false,
               calendarWeeks: true,
               autoclose: true
           });
           // Add HSN end
           // Edit HSN start
           $('#editHsnEntryDate .input-group.date').datepicker({
               todayBtn: "linked",
               keyboardNavigation: false,
               forceParse: false,
               calendarWeeks: true,
               autoclose: true
           });
           $('#editHsnEffectiveDate .input-group.date').datepicker({
               todayBtn: "linked",
               keyboardNavigation: false,
               forceParse: false,
               calendarWeeks: true,
               autoclose: true
           });
           // Edit HSN end

       });

       $(function() {
           //add assets Characteristics start
           var count1=1;
           $("#addAssetCharacteristics").click(function(){
               $("#assetCharacteristics").append("<div class='row assetCharacteristicsBox'><div class='col-lg-12 text-right'><button type='button' class='btn btn-danger dim removeAssetCharacteristics' ><i class='fa fa-times-circle'></i></button></div><div class='col-lg-6'><div class='form-group'><label>Characteristics <i class='fa fa-asterisk'></i></label><select class='form-control m-b' required name='assetCharacteristicsModel["+count1+"].character'><option value=''>Select</option><option value='Colour'>Colour</option><option value='Size'>Size</option><option value='Other'>Other</option></select></div><div class='form-group'><label>Value <i class='fa fa-asterisk '></i></label><input name='assetCharacteristicsModel["+count1+"].value' type='text' class='form-control' required></div></div><div class='col-lg-6'><div class='form-group'><label>Remarks</label><textarea class='form-control ' rows='5' name='assetCharacteristicsModel["+count1+"].assetRemarks'></textarea></div></div><div class='col-lg-12'><div class='hr-line-dashed'></div></div></div>");
            count1++;
             });
           $(document).on('click', '.removeAssetCharacteristics', function() {
               $(this).parents('.assetCharacteristicsBox').remove();
           });
           //add assets Characteristics end
            //add Maintenance Activities start
            var count2=1;
            $("#addAssetMaintenanceActivities").click(function(){
               $("#assetMaintenanceActivities").append(" <div class='row assetMaintenanceActivitiesBox' ><div class='col-lg-12 text-right'><button class='btn btn-danger dim removeassetMaintenanceActivities' type='button'><i class='fa fa-times-circle'></i></button></div><div class='col-lg-4'><div class='form-group'><label> Activity Name</label><input  name='assetMaintenanceActivitiesModel["+count2+"].activityName' type='text' class='form-control'></div><div class='form-group'><label> Upper Limit</label><input  name='assetMaintenanceActivitiesModel["+count2+"].upperLimit' type='text' class='form-control'></div><div class='form-group'><label>Category</label><select class='form-control m-b' name='assetMaintenanceActivitiesModel["+count2+"].category'><option value=''>Select</option><option value='Repair'>Repair</option><option value='Instalation'>Instalation</option><option value='Other'>Other</option></select></div></div><div class='col-lg-4'><div class='form-group'><label>Standard Observation</label><input  name='assetMaintenanceActivitiesModel["+count2+"].standardObservation' type='text' class='form-control'></div><div class='form-group'><label>Tolerance(%)</label><input  name='assetMaintenanceActivitiesModel["+count2+"].tolerence' type='text' class='form-control '></div></div><div class='col-lg-4'><div class='form-group'><label>Lower Limit</label><input  name='assetMaintenanceActivitiesModel["+count2+"].tolerenceLowerlimit' type='text' class='form-control'></div><div class='form-group'><label>Frequency(In Days)</label><input  name='assetMaintenanceActivitiesModel["+count2+"].frequency' type='text' class='form-control'></div></div> <div class='col-lg-12'><div class='hr-line-dashed'></div></div></div>");
            count2++;
             });
           $(document).on('click', '.removeassetMaintenanceActivities', function() {
               $(this).parents('.assetMaintenanceActivitiesBox').remove();
           });
           //add Maintenance Activities end
            //add Other CheckList start
            var count3=1;
            $("#addAssetOtherCheckList").click(function(){
               $("#assetOtherCheckList").append(" <div class='row assetOtherCheckListBox' ><div class='col-lg-12 text-right'><button class='btn btn-danger dim removeAssetOtherCheckList' type='button'><i class='fa fa-times-circle'></i></button></div><div class='col-lg-6'><div class='form-group'><label>CheckList</label><select class='form-control m-b' name='assetCheckListModel["+count3+"].checkList'><option>Select</option><option value='check1'>check1</option><option value='check2'>check2</option><option value='Other'>Other</option></select></div></div><div class='col-lg-6'><div class='form-group'><label>Remarks</label><textarea class='form-control ' rows='1' name='assetCheckListModel["+count3+"].remarks'></textarea></div></div><div class='col-lg-12'><div class='hr-line-dashed'></div></div></div>");
            count3++;
             });
           $(document).on('click', '.removeAssetOtherCheckList', function() {
               $(this).parents('.assetOtherCheckListBox').remove();
           });
           //add Other CheckList end

         // add item HSN Code start
                  $(".selectHsnCode").select2({
                      theme: 'bootstrap4',
                  });
                  // add item HSN Code end
                    // add item Brand  start
                                    $(".selectBrandCode").select2({
                                        theme: 'bootstrap4',
                                    });
                                    // add item Brand end
           // add item uom start
            var uomcount=0;
           $(".addUom").click(function(){
           //to get all uoms for option start
            /*	$.ajax({
                 url: '/master/getAllUOMDetails',
                 type: 'GET',
                 dataType : 'json',
                 data: { },

                 success: function(data){
                          if(data.success) {

                                      alert("ok");

                   $.each(data.data.roles, function(i){

                   });
                                           }

                                      },

             error: function(jqXHR, textStatus)
              {
              alert('Error Occured');
               }
                  });*/
      //to get all uoms for option end
               $("#uomBox").append("<div class='row uomDiv'><div class='col-lg-4 '><div class='form-group  row'><label class='col-sm-12 col-form-label'>Transaction Type <i class='fa fa-asterisk'></i></label><div class='col-sm-12'><select class='form-control m-b' name='productUOMModelList["+uomcount+"].transactionType' required><option value=''>Select</option><option value='Purchase'>Purchase</option><option value='Sales'>Sales</option></select></div></div></div><div class='col-lg-4 '><div class='form-group  row'><label class='col-sm-12 col-form-label'>Transaction UOM <i class='fa fa-asterisk'></i></label><div class='col-sm-12'><select class='form-control m-b' name='productUOMModelList["+uomcount+"].uomId' required><option value=''>Select</option><option value='1'>Kg</option><option value='2'>Piece</option><option value='3'>Meter</option><option value='4'>Pack</option><option value='5'>Square ft</option><option value='6'>Inches</option><option value='7'>Each</option><option value='8'>Dozen</option><option value='9'>Case</option><option value='10'>Box</option><option value='11'>Single</option></select></div></div></div><div class='col-lg-4'><div class='form-group  row'><label class='col-sm-12 col-form-label'>Convention Factor <i class='fa fa-asterisk'></i></label><div class='col-sm-10'><input type='text' required class='form-control' name='productUOMModelList["+uomcount+"].convertionFactor'></div><div class='col-sm-2 text-right'><button class='btn btn-danger dim removeUom' type='button'><i class='fa fa-times-circle'></i></button></div></div></div></div>");
               uomcount++;
             });
             $(document).on('click', '.removeUom', function() {
               $(this).parents('.uomDiv').remove();
           });
           // add item uom end

       });

            $(function() {
                   //add assets Characteristics in edit start

                                        var countchar1=document.getElementById("charCountId").value;

                                        $("#editAssetCharacteristics").click(function(){
                                            $("#assetCharacteristics").append("<div class='row assetCharacteristicsBox'><div class='col-lg-12 text-right'><button type='button' class='btn btn-danger dim removeAssetCharacteristics' ><i class='fa fa-times-circle'></i></button></div><div class='col-lg-6'><div class='form-group'><label>Characteristics <i class='fa fa-asterisk'></i></label><select class='form-control m-b' required name='assetCharacteristicsModel["+countchar1+"].character'><option value=''>Select</option><option value='Colour'>Colour</option><option value='Size'>Size</option><option value='Other'>Other</option></select></div><div class='form-group'><label>Value <i class='fa fa-asterisk '></i></label><input name='assetCharacteristicsModel["+countchar1+"].value' type='text' class='form-control' required></div></div><div class='col-lg-6'><div class='form-group'><label>Remarks</label><textarea class='form-control ' rows='5' name='assetCharacteristicsModel["+countchar1+"].assetRemarks'></textarea></div></div><div class='col-lg-12'><div class='hr-line-dashed'></div></div></div>");
                                        countchar1++;
                                          });
                                        $(document).on('click', '.removeAssetCharacteristics', function() {
                                            $(this).parents('.assetCharacteristicsBox').remove();
                                        });
                                        //add assets Characteristics in edit end

                                        //add Maintenance Activities edit start
                                                    var countmain2=document.getElementById("mainCountId").value;

                                                    $("#editAssetMaintenanceActivities").click(function(){
                                                       $("#assetMaintenanceActivities").append(" <div class='row assetMaintenanceActivitiesBox' ><div class='col-lg-12 text-right'><button class='btn btn-danger dim removeassetMaintenanceActivities' type='button'><i class='fa fa-times-circle'></i></button></div><div class='col-lg-4'><div class='form-group'><label> Activity Name</label><input  name='assetMaintenanceActivitiesModel["+countmain2+"].activityName' type='text' class='form-control'></div><div class='form-group'><label> Upper Limit</label><input  name='assetMaintenanceActivitiesModel["+countmain2+"].upperLimit' type='text' class='form-control'></div><div class='form-group'><label>Category</label><select class='form-control m-b' name='assetMaintenanceActivitiesModel["+countmain2+"].category'><option value=''>Select</option><option value='Repair'>Repair</option><option value='Instalation'>Instalation</option><option value='Other'>Other</option></select></div></div><div class='col-lg-4'><div class='form-group'><label>Standard Observation</label><input  name='assetMaintenanceActivitiesModel["+countmain2+"].standardObservation' type='text' class='form-control'></div><div class='form-group'><label>Tolerance(%)</label><input  name='assetMaintenanceActivitiesModel["+countmain2+"].tolerence' type='text' class='form-control '></div></div><div class='col-lg-4'><div class='form-group'><label>Lower Limit</label><input  name='assetMaintenanceActivitiesModel["+countmain2+"].tolerenceLowerlimit' type='text' class='form-control'></div><div class='form-group'><label>Frequency(In Days)</label><input  name='assetMaintenanceActivitiesModel["+countmain2+"].frequency' type='text' class='form-control'></div></div> <div class='col-lg-12'><div class='hr-line-dashed'></div></div></div>");
                                                    countmain2++;
                                                     });
                                                   $(document).on('click', '.removeassetMaintenanceActivities', function() {
                                                       $(this).parents('.assetMaintenanceActivitiesBox').remove();
                                                   });
                                                   //add Maintenance Activities edit end

                                                     //add Other CheckList edit start
                                                               var countcheck3=document.getElementById("checkCountId").value;

                                                               $("#editAssetOtherCheckList").click(function(){
                                                                  $("#assetOtherCheckList").append(" <div class='row assetOtherCheckListBox' ><div class='col-lg-12 text-right'><button class='btn btn-danger dim removeAssetOtherCheckList' type='button'><i class='fa fa-times-circle'></i></button></div><div class='col-lg-6'><div class='form-group'><label>CheckList</label><select class='form-control m-b' name='assetCheckListModel["+countcheck3+"].checkList'><option>Select</option><option value='check1'>check1</option><option value='check2'>check2</option><option value='Other'>Other</option></select></div></div><div class='col-lg-6'><div class='form-group'><label>Remarks</label><textarea class='form-control ' rows='1' name='assetCheckListModel["+countcheck3+"].remarks'></textarea></div></div><div class='col-lg-12'><div class='hr-line-dashed'></div></div></div>");
                                                               countcheck3++;
                                                                });
                                                              $(document).on('click', '.removeAssetOtherCheckList', function() {
                                                                  $(this).parents('.assetOtherCheckListBox').remove();
                                                              });
                                                              //add Other CheckList edit end
            });


            // Remove Price List Start
               $('.removePriceList').click(function () {

               var pricelistId=$(this).data('pricelistids');

                   swal({
                       title: "Are you sure?",
                       text: "You will not be able to recover this Price List!",
                       type: "warning",
                       showCancelButton: true,
                       confirmButtonColor: "#0094db",
                       confirmButtonText: "Yes, delete it!",
                       closeOnConfirm: false
                   }, function () {

                   $.ajax({
                                      url: '/master/deletePriceListDetails',
                                      type: 'POST',
                                      dataType : 'json',
                                      data: { 'pricelistId': pricelistId },

                                      success: function(data){
                                               if(data.success) {

                                var redirectionUrl= "/master/getPriceListMaster";
                                window.location.href = redirectionUrl;
                                                                }

                                                           },

                                  error: function(jqXHR, textStatus)
                                  {
                                     alert('Error Occured');
                                  }
                  });

                       swal("Deleted!", "Item has been deleted.", "success");
                   });
               });
           //Remove Price List End

            //remove assets start
                  $('.removeAsset').click(function () {

                   var assetids=$(this).data('assetids');

                      swal({
                          title: "Are you sure?",
                          text: "You will not be able to recover this Item!",
                          type: "warning",
                          showCancelButton: true,
                          confirmButtonColor: "#0094db",
                          confirmButtonText: "Yes, delete it!",
                          closeOnConfirm: false
                      }, function () {

                        $.ajax({
                                                 url: '/master/deleteAssetDetails',
                                                 type: 'POST',
                                                 dataType : 'json',
                                                 data: { 'assetids': assetids },

                                                 success: function(data){
                                                          if(data.success) {

                                           var redirectionUrl= "/master/getAssetList";
                                           window.location.href = redirectionUrl;
                                                                           }

                                                                      },

                                             error: function(jqXHR, textStatus)
                                             {
                                                alert('Error Occured');
                                             }
                             });
                          swal("Deleted!", "Item has been deleted.", "success");
                      });
                  });

                  // remove assets  end

// Remove category start
    $('.removeCategory').click(function () {
     var categoryidremove=$(this).data('categoryidremove');
        swal({
            title: "Are you sure?",
            text: "You will not be able to recover this Item!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#0094db",
            confirmButtonText: "Yes, delete it!",
            closeOnConfirm: false
        }, function () {
           $.ajax({
               url: '/master/deleteCategoryDetails',
               type: 'POST',
               dataType : 'json',
               data: { 'categoryid':categoryidremove },

                success: function(data){
                  if(data.success) {

                  var redirectionUrl= "/master/getCatalog";
                  window.location.href = redirectionUrl;
                    }

                 },

               error: function(jqXHR, textStatus)
                 {
              alert('Error Occured');
                  }
               });
            swal("Deleted!", "Item has been deleted.", "success");
        });
    });
    // Remove category end
    // Remove subcategory start
    $('.removeSubCategory').click(function () {
    var subcategoryidremove=$(this).data('subcategoryremoveid');
        swal({
            title: "Are you sure?",
            text: "You will not be able to recover this Item!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#0094db",
            confirmButtonText: "Yes, delete it!",
            closeOnConfirm: false
        }, function () {

         $.ajax({
                       url: '/master/deleteSubCategoryDetails',
                       type: 'POST',
                       dataType : 'json',
                       data: { 'subCategoryid':subcategoryidremove },

                        success: function(data){
                          if(data.success) {

                          var redirectionUrl= "/master/getCatalog";
                          window.location.href = redirectionUrl;
                            }

                         },

                       error: function(jqXHR, textStatus)
                         {
                      alert('Error Occured');
                          }
                       });
            swal("Deleted!", "Item has been deleted.", "success");
        });
    });
    // Remove subcategory end

//Enable Product start
                      $('.enableProduct').click(function () {
                       var productId=$(this).data('productid');
                          swal({
                              title: "Are you sure?",
                              text: "You want to Enable product!",
                              type: "warning",
                              showCancelButton: true,
                              confirmButtonColor: "#0094db",
                              confirmButtonText: "Yes, enabled!",
                              closeOnConfirm: false
                          }, function () {

                          $.ajax({
                                              url: '/master/enableOrDisableProduct',
                                              type: 'POST',
                                              dataType : 'json',
                                             data: { 'productId': productId },
                                              success: function(data){
                                                  if(data.success) {
                                                      var redirectionUrl= "/master/getProduct";
                                                      window.location.href = redirectionUrl;
                                                  }
                                              },
                                              error: function(jqXHR, textStatus) {
                                                  alert('Error Occured');
                                              }
                                          });
                             /* swal("Activated!", "Item has been Activated.", "success");*/

                          });
                      });
            //End Product end

//Disable Product start
                      $('.disableProduct').click(function () {
                       var productId=$(this).data('productid');
                          swal({
                              title: "Are you sure?",
                              text: "You want to Disable product!",
                              type: "warning",
                              showCancelButton: true,
                              confirmButtonColor: "#0094db",
                              confirmButtonText: "Yes, disabled!",
                              closeOnConfirm: false
                          }, function () {

                          $.ajax({
                                              url: '/master/enableOrDisableProduct',
                                              type: 'POST',
                                              dataType : 'json',
                                             data: { 'productId': productId },
                                              success: function(data){
                                                  if(data.success) {
                                                      var redirectionUrl= "/master/getProduct";
                                                      window.location.href = redirectionUrl;
                                                  }
                                              },
                                              error: function(jqXHR, textStatus) {
                                                  alert('Error Occured');
                                              }
                                          });
                             /* swal("Activated!", "Item has been Activated.", "success");*/

                          });
                      });
            //Disable Product end

});

 //get pricelist edit start
  $("body").on('click','.pricelistEdit',function (e) {

    e.preventDefault();
     var pricelistId=$(this).data('pricelistid');

     	$.ajax({
      url: '/master/getPriceListforEdit',
      type: 'GET',
      dataType : 'json',
      data: { 'pricelistId': pricelistId },

      success: function(data){
               if(data.success) {

                            $('#pricelistId').val(data.data.pricelistId);
                            $('#pricelistName').val(data.data.pricelistName);
                            $('#discountPercentage').val(data.data.discountPercentage);
                            $('#allowedPriceDevPerUpper').val(data.data.allowedPriceDevPerUpper);
                            $('#allowedPriceDevPerLower').val(data.data.allowedPriceDevPerLower);

                                }

                           },

  error: function(jqXHR, textStatus)
   {
   alert('Error Occured');
    }
       });
   });
    //get pricelist edit end

//get Category edit start
  $("body").on('click','.editCategorys',function (e) {

    e.preventDefault();
     var categoryId=$(this).data('categoryid');

     	$.ajax({
      url: '/master/getCategoryForEdit',
      type: 'GET',
      dataType : 'json',
      data: { 'categoryId': categoryId },

      success: function(data){
               if(data.success) {

                            $('#categoryId').val(data.data.categoryId);
                            $('#categoryShortCode').val(data.data.categoryShortCode);
                            $('#categoryType').val(data.data.categoryType);
                            $('#categoryName').val(data.data.categoryName);
                            $('#categoryDescription').val(data.data.categoryDescription);

                                }

                           },

  error: function(jqXHR, textStatus)
   {
   alert('Error Occured');
    }
       });
   });
    //get category edit end

    //get SubCategory edit start
      $("body").on('click','.editSubCategorys',function (e) {

        e.preventDefault();
         var subCategoryId=$(this).data('subcategoryid');

         	$.ajax({
          url: '/master/getSubCategoryForEdit',
          type: 'GET',
          dataType : 'json',
          data: { 'subCategoryId': subCategoryId },

          success: function(data){
                   if(data.success) {

                                $('#subCategoryId').val(data.data.subCategoryId);

                                $('#subCategoryName').val(data.data.subCategoryName);
                                $('#subCategoryDescription').val(data.data.subCategoryDescription);

                                    }

                               },

      error: function(jqXHR, textStatus)
       {
       alert('Error Occured');
        }
           });
       });
        //get Subcategory edit end




