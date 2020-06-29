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




