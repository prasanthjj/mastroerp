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
                    required: true,
                    number:true
                              },
          allowedPriceDevPerLower: {
                  required: true,
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
                               required: true,
                               number:true
                                         },
                     allowedPriceDevPerLower: {
                            required: true,
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



// add salary components percentage calculation stat
/*$(".percentageCalculation").TouchSpin({
min:0,
max:100,
step:0.1,
decimals:2,
boostat:5,
maxboostedstep:10,
postfix:'%',
buttondown_class:'btn btn-white',
buttonup_class:'btn btn-white'
});*/
$("#calculationPercentage").hide();
$("input[name='calculationType']").click(function() {
if ($("#flatAmount").is(":checked")) {
$("#calculationAmount").show();
$("#calculationPercentage").hide();
} else {
$("#calculationPercentage").show();
$("#calculationAmount").hide();
}
});
// add salary components percentage calculation end



// item party rate relation add party auto-complete start
 $.get('../resources/js/api/typehead_collection.json', function(data){
    $(".addParty_head").typeahead({
       minLength : 3,
       source:data.countries });
},'json');
// item party rate relation add party auto-complete start
    // item party rate relation item auto-complete start
  /*  $.get('../resources/js/api/typehead_collection.json', function(data){
        $(".item_head").typeahead({
           minLength : 3,
           source:data.emails });
    },'json');*/
    // item party rate relation item auto-complete start
     var itemAutopopulateUrl = $('#itemAutopopulateUrl').data('url');
     $(".item_dropdown").hide();
     $(".itemautocmoplete").keyup(function () {

		var val = $(this).val().trim();
		if (val.length > 2) {
		$(".item_dropdown ul").empty();
		     $.ajax({
              url:itemAutopopulateUrl,
               type: 'GET',
               dataType : 'json',
               data: {'searchTerm':val},
               success: function(data){
                 if(data.success) {

                        $.each(data.data.products, function(i){
                                      //  $('#itemAutocomplete').append($('<input>').val(this['id']).text(this['productname']));
                                     $('.typeahead').append($('<li><a class="dropdown-item" href="#" role="option">'+ this['productname'] +' </a> </li>'));
                                      $(".item_dropdown ul").append('<li><a class="dynamicClick" data-itemsid="'+this['id'] +'" href="#">'+ this['productname'] +'</a></li>');
                                        });
                  }
                   },
               error: function(jqXHR, textStatus) {
               alert('Error Occured');
               }

              });
              $(".item_dropdown").show();
		}
		else {
        			$(".item_dropdown").hide();
        		}
		});
		//item autocomplete end

		//dynamic click for item in item autocomplete start
			$(".item_dropdown").on('click','a.dynamicClick',function (e) {
        		e.preventDefault();
        		var value = $(this).text();


                			$(".itemautocmoplete").val(value);
                			$("#itemIdInput").val($(e.target).data('itemsid'));

        		$(".item_dropdown").hide();
        	})
		//dynamic click for item in item autocomplete end

		 // item party rate relation party auto-complete start
             var partyAutopopulateUrl = $('#partyAutopopulateUrl').data('url');
             $(".party_dropdown").hide();
             $(".partyautocmoplete").keyup(function () {

        		var vals = $(this).val().trim();
        		if (vals.length > 2) {
        		$(".party_dropdown ul").empty();
        		     $.ajax({
                      url:partyAutopopulateUrl,
                       type: 'GET',
                       dataType : 'json',
                       data: {'searchTerm':vals},
                       success: function(data){
                         if(data.success) {

                                $.each(data.data.partys, function(i){
                                            
                                              $(".party_dropdown ul").append('<li><a class="dynamicClick" data-partysid="'+this['id'] +'" href="#">'+ this['partysname'] +'</a></li>');
                                                });
                          }
                           },
                       error: function(jqXHR, textStatus) {
                       alert('Error Occured');
                       }

                      });
                      $(".party_dropdown").show();
        		}
        		else {
                			$(".party_dropdown").hide();
                		}
        		});
        		//party autocomplete end

        		//dynamic click for party in party autocomplete start
                			$(".party_dropdown").on('click','a.dynamicClick',function (e) {
                        		e.preventDefault();
                        		var value = $(this).text();


                                			$(".partyautocmoplete").val(value);
                                			$("#partyIdInput").val($(e.target).data('partysid'));

                        		$(".party_dropdown").hide();
                        	})
                		//dynamic click for party in party autocomplete end
                		//get selected party type in partytype autocomplete start
                         	$("#partysType").on("change", function(e){
                         	 var typeOfParty =$('#partysType').val();

                         	 $("#partyTypesValue").val(typeOfParty);


                                            });
                                            //get selected party type in partytype autocomplete end
                	 //  party item rate relation party type auto-complete start
                                 var partyTypeAutopopulateUrl = $('#partyTypeAutopopulateUrl').data('url');


                                 $(".parts_dropdown").hide();
                                 $(".partyautocmopletes").keyup(function () {

                            		var vals = $(this).val().trim();

                            		if (vals.length > 2) {
                            		var partsTypee=document.getElementById("partyTypesValue").value;

                            		$(".parts_dropdown ul").empty();
                            		     $.ajax({
                                          url:partyTypeAutopopulateUrl,
                                           type: 'GET',
                                           dataType : 'json',
                                           data: {'searchTerm':vals,'partyType':partsTypee},
                                           success: function(data){
                                             if(data.success) {

                                                    $.each(data.data.partys, function(i){

                                                                  $(".parts_dropdown ul").append('<li><a class="dynamicClick" data-partyssid="'+this['id'] +'" href="#">'+ this['partysname'] +'</a></li>');
                                                                    });
                                              }
                                               },
                                           error: function(jqXHR, textStatus) {
                                           alert('Error Occured');
                                           }

                                          });
                                          $(".parts_dropdown").show();
                            		}
                            		else {
                                    			$(".parts_dropdown").hide();
                                    		}
                            		});
                            		//party item rate relation party type auto-complete end


//dynamic click for party in partytype autocomplete start
                			$(".parts_dropdown").on('click','a.dynamicClick',function (e) {
                        		e.preventDefault();
                        		var value = $(this).text();


                                			$(".partyautocmopletes").val(value);
                                			$("#partysIdInput").val($(e.target).data('partyssid'));

                        		$(".parts_dropdown").hide();
                        	})
                		//dynamic click for party in partytype autocomplete end
 // party item rate relation item auto-complete start
             var itemAutopopulateUrls = $('#itemAutopopulateUrl').data('url');
                $(".produts_dropdown").hide();
                $(".productsautocmoplete").keyup(function () {

           		var valu = $(this).val().trim();

           		if (valu.length > 2) {

           		$(".produts_dropdown ul").empty();
           		     $.ajax({
                         url:itemAutopopulateUrls,
                          type: 'GET',
                          dataType : 'json',
                          data: {'searchTerm':valu},
                          success: function(data){
                            if(data.success) {

                                   $.each(data.data.products, function(i){
                                                 //  $('#itemAutocomplete').append($('<input>').val(this['id']).text(this['productname']));
                                                $('.typeahead').append($('<li><a class="dropdown-item" href="#" role="option">'+ this['productname'] +' </a> </li>'));
                                                 $(".produts_dropdown ul").append('<li><a class="dynamicClick" data-itemssid="'+this['id'] +'" href="#">'+ this['productname'] +'</a></li>');
                                                   });
                             }
                              },
                          error: function(jqXHR, textStatus) {
                          alert('Error Occured');
                          }

                         });
                         $(".produts_dropdown").show();
           		}
           		else {
                   			$(".produts_dropdown").hide();
                   		}
           		});
        		//party item rate relation item auto-complete end

        		//dynamic click for item in party item relation autocomplete start
                			$(".produts_dropdown").on('click','a.dynamicClick',function (e) {
                        		e.preventDefault();
                        		var value = $(this).text();


                                			$(".productsautocmoplete").val(value);
                                			$("#productsIdInput").val($(e.target).data('itemssid'));

                        		$(".produts_dropdown").hide();
                        	})
                		//dynamic click for item in party item relation autocomplete end

    // add item party rate table start

    $('.itemPartyRateTable').on('change', ':checkbox', function () {
    var $inpts = $(this).closest('tr').find('input:text,textarea,input:hidden').prop("disabled", !$(this).is(':checked'));
    }).find(':checkbox').change();

    // add item party rate table end

// add party item rate table start

  $('.partyItemRateTable').on('change', ':checkbox', function () {
    var $inpts = $(this).closest('tr').find('input:text,textarea').prop("disabled", !$(this).is(':checked'));
    }).find(':checkbox').change();

    // add party item  rate table end
    // item party rate relation change tab start


    $('.relationBtn').click(function (event) {
        event.preventDefault();
        event.stopPropagation();
        id = this.id;
        if (id == 'itemBtn') {
            $('#itemBox').slideToggle("slow");
            $('#partyBox').hide();
            $('#itemBtn').toggleClass("active");
            if ($('#itemBtn').hasClass('active')){
                $('#partyBtn').removeClass("active");
            }

        }
        else if (id == 'partyBtn'){

            $('#itemBox').hide();
            $('#partyBox').slideToggle("slow");
            $('#partyBtn').toggleClass("active");
            if ($('#partyBtn').hasClass('active')){
                $("#itemBtn").removeClass("active");
            }
        }
    });
    // item party rate relation change tab end
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
           // party creation date start
               $('#partyCreationDate .input-group.date').datepicker({
                   todayBtn: "linked",
                   keyboardNavigation: false,
                   forceParse: false,
                   calendarWeeks: true,
                   autoclose: true,
               });
               //party creation date end


       });

       $(function() {

       //industrytype start
                                              $('#industryType').change(function () {

                                               if ($('#industryType').val() == 'addPartyIndustryType') {
                                                   $('#addPartyIndustryType').toggle();
                                                   $('#addPartyIndustryType').css({"background-color": "rgb(0 0 0 / 35%)"});

                                               }
                                               else {
                                                   $('#addPartyIndustryType').hide();

                                               }
                                           });
                                           $(document).on('click', '.closePartyIndustryType', function() {
                                               $('#addPartyIndustryType').toggle();
                                           });
                                           //industrytype end

          //add party contact details start
            var countpc=1;
             $("#addPartyContactDetails").click(function(){
            $("#partyContactDetails").append(" <div class='row partyContactDetailsBox'><div class='col-lg-12 text-right'><button class='btn btn-danger dim removePartyContactDetails' type='button'><i class='fa fa-times-circle'></i></button></div><div class='col-lg-4'><div class='form-group'><label>Contact Details<i class='fa fa-asterisk'></i></label><input id='name' name='contactDetailsModelList["+count1+"].contactPersonName' type='text' required class='form-control '></div><div class='form-group'><label>Address</label><textarea class='form-control ' rows='5' name='contactDetailsModelList["+count1+"].address'></textarea></div></div><div class='col-lg-4'><div class='form-group'><label>Designation<i class='fa fa-asterisk'></i></label><input name='contactDetailsModelList["+count1+"].designation' required type='text' class='form-control'></div><div class='form-group'><label>Telephone No.</label><input name='contactDetailsModelList["+count1+"].telephoneNo' type='text' class='form-control'></div><div class='form-group'><label>Mobile No.<i class='fa fa-asterisk'></i></label><input id='' name='contactDetailsModelList["+count1+"].mobileNo' required type='text' class='form-control'></div><div class='form-group'><label>Fax No.</label><input name='contactDetailsModelList["+count1+"].faxNo' type='text' class='form-control'></div></div><div class='col-lg-4'><div class='form-group'><label>Department</label><inputtype='text' name='contactDetailsModelList["+count1+"].department' class='form-control'></div><div class='form-group'><label>Alt Telephone No.</label><input id='' name='contactDetailsModelList["+count1+"].altTelephoneNo' type='text' class='form-control'></div><div class='form-group'><label>Alt Mobile No.</label><input name='contactDetailsModelList["+count1+"].altMobileNo' type='text' class='form-control'></div><div class='form-group'><label> Email ID</label><input name='contactDetailsModelList["+count1+"].emailId' type='email' class='form-control'></div></div><div class='col-lg-12'><div class='hr-line-dashed'></div></div></div>");
              countpc++;
              });
              $(document).on('click', '.removePartyContactDetails', function() {
                $(this).parents('.partyContactDetailsBox').remove();
               });
            //add party contact details end

            // add party credit days start
            $("#ckbCheckAll").click(function () {
                $(".checkBoxClass").prop('checked', $(this).prop('checked'));
                if($(".checkBoxClass").prop("checked") == true){
                  $(".checkBoxClass").closest('tr').find('input:text,textarea').removeAttr("disabled");
                }
                else if($(this).prop("checked") == false){

                  $(".checkBoxClass").closest('tr').find('input:text,textarea').attr("disabled", "disabled");

                }
            });
            $('.creditDaysTable').on('change', ':checkbox', function () {
            var $inpts = $(this).closest('tr').find('input:text,textarea').prop("disabled", !$(this).is(':checked'));
            }).find(':checkbox').change();

            // add party credit days end


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

               $("#uomBox").append("<div class='row uomDiv'><div class='col-lg-4 '><div class='form-group  row'><label class='col-sm-12 col-form-label'>Transaction Type <i class='fa fa-asterisk'></i></label><div class='col-sm-12'><select class='form-control m-b' name='productUOMModelList["+uomcount+"].transactionType' required><option value=''>Select</option><option value='Purchase'>Purchase</option><option value='Sales'>Sales</option></select></div></div></div><div class='col-lg-4 '><div class='form-group  row'><label class='col-sm-12 col-form-label'>Transaction UOM <i class='fa fa-asterisk'></i></label><div class='col-sm-12'><select class='form-control m-b' name='productUOMModelList["+uomcount+"].uomId' required><option value=''>Select</option><option value='1'>Kg</option><option value='2'>Piece</option><option value='3'>Meter</option><option value='4'>Pack</option><option value='5'>Square ft</option><option value='6'>Inches</option><option value='7'>Each</option><option value='8'>Dozen</option><option value='9'>Case</option><option value='10'>Box</option><option value='11'>Single</option><option value='12'>Feet</option></select></div></div></div><div class='col-lg-4'><div class='form-group  row'><label class='col-sm-12 col-form-label'>Convention Factor <i class='fa fa-asterisk'></i></label><div class='col-sm-10'><input type='text' required class='form-control' name='productUOMModelList["+uomcount+"].convertionFactor'></div><div class='col-sm-2 text-right'><button class='btn btn-danger dim removeUom' type='button'><i class='fa fa-times-circle'></i></button></div></div></div></div>");
               uomcount++;
             });
             $(document).on('click', '.removeUom', function() {
               $(this).parents('.uomDiv').remove();
           });
           // add item uom end

           // add party Industry Type start
                           $(".partyIndustryType").select2({
                               theme: 'bootstrap4',
                           });
                           // add party Industry Type end
                           // add party Relationship Manager start
                           $(".partyRelationshipManager").select2({
                               theme: 'bootstrap4',
                           });

                           // add party Relationship Manager end

                            // add party billing country start
                                       $(".billingCountry").select2({
                                           theme: 'bootstrap4',
                                       });
                                       // add party billing country end
                                       // add party billing state start
                                       $(".billingState").select2({
                                           theme: 'bootstrap4',
                                       });
                                       // add party billing state end
                                       // add party billing city start
                                       $(".billingCity").select2({
                                           theme: 'bootstrap4',
                                       });
                                       // add party billing city end


                                       //edit party contact details start
                                                   var editcountpc=document.getElementById("partycontactCountId").value;
                                                    $("#editPartyContactDetails").click(function(){
                                                   $("#partyContactDetails").append(" <div class='row partyContactDetailsBox'><div class='col-lg-12 text-right'><button class='btn btn-danger dim removePartyContactDetails' type='button'><i class='fa fa-times-circle'></i></button></div><div class='col-lg-4'><div class='form-group'><label>Contact Details<i class='fa fa-asterisk'></i></label><input id='name' name='contactDetailsModelList["+editcountpc+"].contactPersonName' type='text' required class='form-control '></div><div class='form-group'><label>Address</label><textarea class='form-control ' rows='5' name='contactDetailsModelList["+editcountpc+"].address'></textarea></div></div><div class='col-lg-4'><div class='form-group'><label>Designation<i class='fa fa-asterisk'></i></label><input name='contactDetailsModelList["+editcountpc+"].designation' required type='text' class='form-control'></div><div class='form-group'><label>Telephone No.</label><input name='contactDetailsModelList["+editcountpc+"].telephoneNo' type='text' class='form-control'></div><div class='form-group'><label>Mobile No.<i class='fa fa-asterisk'></i></label><input id='' name='contactDetailsModelList["+editcountpc+"].mobileNo' required type='text' class='form-control'></div><div class='form-group'><label>Fax No.</label><input name='contactDetailsModelList["+count1+"].faxNo' type='text' class='form-control'></div></div><div class='col-lg-4'><div class='form-group'><label>Department</label><inputtype='text' name='contactDetailsModelList["+editcountpc+"].department' class='form-control'></div><div class='form-group'><label>Alt Telephone No.</label><input id='' name='contactDetailsModelList["+editcountpc+"].altTelephoneNo' type='text' class='form-control'></div><div class='form-group'><label>Alt Mobile No.</label><input name='contactDetailsModelList["+editcountpc+"].altMobileNo' type='text' class='form-control'></div><div class='form-group'><label> Email ID</label><input name='contactDetailsModelList["+editcountpc+"].emailId' type='email' class='form-control'></div></div><div class='col-lg-12'><div class='hr-line-dashed'></div></div></div>");
                                                     editcountpc++;
                                                     });
                                                     $(document).on('click', '.removePartyContactDetails', function() {
                                                       $(this).parents('.partyContactDetailsBox').remove();
                                                      });
                                                   //edit party contact details end



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
/*
    //Remove salary components start
    $('.removeSalaryComponents').click(function () {
        swal({
            title: "Are you sure?",
            text: "You will not be able to recover this Item!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#0094db",
            confirmButtonText: "Yes, delete it!",
            closeOnConfirm: false
        }, function () {
            swal("Deleted!", "Item has been deleted.", "success");
        });
    });
    //Remove salary components End*/

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
            //Enable Product end

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

            //remove Product image start
                                  $('.productImgRemove').click(function () {

                                   var productId=$(this).data('productid');
                                   var filename=$(this).data('imgname');


                                      swal({
                                          title: "Are you sure?",
                                          text: "You want to delete!",
                                          type: "warning",
                                          showCancelButton: true,
                                          confirmButtonColor: "#0094db",
                                          confirmButtonText: "Yes, deleted!",
                                          closeOnConfirm: false
                                      }, function () {

                                      $.ajax({
                                                          url: '/master/deleteProductImages',
                                                          type: 'POST',
                                                          dataType : 'json',
                                                         data: { 'productImgFileName': filename,'productId': productId },
                                                          success: function(data){
                                                              if(data.success) {

                                                                  var redirectionUrl= "/master/getProductEdit?productId="+productId;
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
                        //remove Product image end
               //Activate Party
                                     $('.activateParty').click(function () {
                                      var partyId=$(this).data('partyid');

                                         swal({
                                             title: "Are you sure?",
                                             text: "You want to activate party!",
                                             type: "warning",
                                             showCancelButton: true,
                                             confirmButtonColor: "#0094db",
                                             confirmButtonText: "Yes, activated!",
                                             closeOnConfirm: false
                                         }, function () {

                                         $.ajax({
                                                             url: '/master/activateOrDeactivateParty',
                                                             type: 'POST',
                                                             dataType : 'json',
                                                            data: { 'partyId': partyId },
                                                             success: function(data){

                                                                 if(data.success) {
                                                                     var redirectionUrl= "/master/getPartys";
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
                           //End Active party

                     //Deactivate Party
                          $('.deactiveParty').click(function () {
                                      var partyId=$(this).data('partyid');
                                         swal({
                                             title: "Are you sure?",
                                             text: "You want to deactivate Party!",
                                             type: "warning",
                                             showCancelButton: true,
                                             confirmButtonColor: "#0094db",
                                             confirmButtonText: "Yes, deactivate it!",
                                             closeOnConfirm: false
                                         }, function () {
                                         $.ajax({
                                                             url: '/master/activateOrDeactivateParty',
                                                             type: 'POST',
                                                             dataType : 'json',
                                                            data: { 'partyId': partyId },
                                                             success: function(data){
                                                                 if(data.success) {
                                                                  var redirectionUrl= "/master/getPartys";
                                                                   window.location.href = redirectionUrl;
                                                                 }
                                                             },
                                                             error: function(jqXHR, textStatus) {
                                                                 alert('Error Occured');
                                                             }
                                                         });
               /*
                                          swal("Activated!", "Party has been Activated.", "success");
               */
                                         });
                                     });

                   //End Deactive Party


                   $('.removeHsn').click(function () {
                          var hsnId=$(this).data('hsnids');
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
                                         url: '/master/deleteHsnDetails',
                                         type: 'POST',
                                         dataType : 'json',
                                         data: { 'hsnId': hsnId },

                                         success: function(data){
                                                  if(data.success) {

                                   var redirectionUrl= "/master/getHSN";
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


$("#indtype").click(function (e) {

                                                e.preventDefault();

                                                var industryTypeUrl = $('#industryTypeUrl').data('url');

                                               var formData = new FormData(document.getElementById("addPartyIndustryTypeForm"));
                                                                  $.ajax({

                                                                  url: industryTypeUrl,
                                                                  data: formData,
                                                                  processData: false,
                                                                  contentType: false,

                                                                  type: 'POST',

                                                                  success: function(data) {

                                                                  if(data.success) {
                                                                  $('#addPartyIndustryType').hide();
                                                      $('#industryType').empty();
                                                     $('#inputids').val(data.data.industryid);
                                                     $('#inputids').text(data.data.industryType);
                                                      $("#industryType").html("");
                                                              $.each(data.data.fullindustrytypes, function(i){
                                                              $('#industryType').append($('<option>').val(this['id']).text(this['industryType']));

                                                              });
                                                              $('#industryType').append($('<option>').val('addPartyIndustryType').text('addPartyIndustryType'));

                                                                           }
                                                                        }
                                                                     });
                          });





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
                             $('#categoryType').text(data.data.categoryType);
                             $('#partyType').text(data.data.partyType);
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




