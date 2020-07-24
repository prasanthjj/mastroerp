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


