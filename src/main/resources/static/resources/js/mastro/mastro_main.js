$(document).ready(function(){
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

    $(function(datepicker) {
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
    //add party contact details start
    $("#addPartyContactDetails").click(function(){
        $("#partyContactDetails").append(" <div class='row partyContactDetailsBox'><div class='col-lg-12 text-right'><button class='btn btn-danger dim removePartyContactDetails' type='button'><i class='fa fa-times-circle'></i></button></div><div class='col-lg-4'><div class='form-group'><label>Contact Person Name<i class='fa fa-asterisk'></i></label><input id='name' name='contactPersonName' type='text' required class='form-control '></div><div class='form-group'><label>Address</label><textarea class='form-control ' rows='5'></textarea></div></div><div class='col-lg-4'><div class='form-group'><label>Designation<i class='fa fa-asterisk'></i></label><input name='contactPersonDesignation' required type='text' class='form-control'></div><div class='form-group'><label>Telephone No.</label><input name='contactPersonTel' type='text' class='form-control'></div><div class='form-group'><label>Mobile No.<i class='fa fa-asterisk'></i></label><input id='' name='contactPersonMobile' required type='text' class='form-control'></div><div class='form-group'><label>Fax No.</label><input id='' name='' type='text' class='form-control'></div></div><div class='col-lg-4'><div class='form-group'><label>Department</label><inputtype='text' class='form-control'></div><div class='form-group'><label>Alt Telephone No.</label><input id='' name='' type='text' class='form-control'></div><div class='form-group'><label>Alt Mobile No.</label><input id='' name='contactPersonAltTel' type='text' class='form-control'></div><div class='form-group'><label> Email ID</label><input id='' name='' type='email' class='form-control'></div></div><div class='col-lg-12'><div class='hr-line-dashed'></div></div></div>");
     });
     $(document).on('click', '.removePartyContactDetails', function() {
        $(this).parents('.partyContactDetailsBox').remove();
    });
    //add party contact details end
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

    });

      // Add Price List Start

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
   //Add Price List End
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

// Remove Party Start
$('.removeParty').click(function () {
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
// Remove Party End

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


