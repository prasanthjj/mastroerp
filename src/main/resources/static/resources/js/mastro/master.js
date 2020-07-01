$(document).ready(function(){
//remove brand start
    $('.removeBrand').click(function () {
      var brandId=$(this).data('brandids');

        swal({
            title: "Are you sure?",
            text: "You will not be able to recover this Brand!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#0094db",
            confirmButtonText: "Yes, delete it!",
            closeOnConfirm: false
        }, function () {


             	$.ajax({
                   url: '/master/deleteBrandDetails',
                   type: 'POST',
                   dataType : 'json',
                   data: { 'brandId': brandId },

                   success: function(data){
                            if(data.success) {

             var redirectionUrl= "/master/getBrand";
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
//remove brand end
    //Remove HSN start
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
    // Remove HSN end
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

 //get brand edit start
  $("body").on('click','.brandEdit',function (e) {

    e.preventDefault();
     var brandId=$(this).data('brandid');

     	$.ajax({
      url: '/master/getBrandForEdit',
      type: 'GET',
      dataType : 'json',
      data: { 'brandId': brandId },

      success: function(data){
               if(data.success) {

                            $('#brandId').val(data.data.brandId);
                            $('#brandnames').val(data.data.brandName);

                            $('#branddescription').val(data.data.brandDescription);

                                }

                           },

  error: function(jqXHR, textStatus)
   {
   alert('Error Occured');
    }
       });
   });
    //get brand edit end



