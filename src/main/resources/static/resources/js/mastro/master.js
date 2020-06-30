$(document).ready(function(){
    $('.removeBrand').click(function () {
        swal({
            title: "Are you sure?",
            text: "You will not be able to recover this Brand!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#0094db",
            confirmButtonText: "Yes, delete it!",
            closeOnConfirm: false
        }, function () {
            swal("Deleted!", "Item has been deleted.", "success");
        });
    });

});

 //get brand edit start
  $("body").on('click','.brandEdit',function (e) {

    e.preventDefault();
     var brandId=$(this).data('brandid');
     alert(brandId);
     	$.ajax({
      url: '/master/getBrandForEdit',
      type: 'GET',
      dataType : 'json',
      data: { 'brandId': brandId },

      success: function(data){
               if(data.success) {
                           alert("ok");
                           alert(data.data.brandId);
                           alert(data.data.brandName);
                           alert(data.data.brandDescription);

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



