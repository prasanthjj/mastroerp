$(document).ready(function(){

    //Remove role start
     $('.removeRole').click(function () {
        var rolesId=$(this).data('roleids');
        swal({
            title: "Are you sure?",
            text: "You will not be able to recover this Role!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#0094db",
            confirmButtonText: "Yes, delete it!",
            closeOnConfirm: false
        }, function () {
            $.ajax({
                url: '/admin/deleteRolesDetails',
                type: 'POST',
                dataType : 'json',
                data: { 'roleId': rolesId },
                success: function(data){
                    if(data.success) {
                        var redirectionUrl= "/admin/getRole";
                        window.location.href = redirectionUrl;
                    }
                },
                error: function(jqXHR, textStatus) {
                    alert('Error Occured');
                }
            });
            swal("Deleted!", "Item has been deleted.", "success");
        });

    });
    //Remove role end



    // Remove user start
    $('.removeUser').click(function () {
    var userId=$(this).data('userid');
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
                        url: '/admin/deleteUserDetails',
                        type: 'POST',
                        dataType : 'json',
                        data: { 'userId': userId },
                        success: function(data){
                            if(data.success) {
                                var redirectionUrl= "/admin/addUser";
                                window.location.href = redirectionUrl;
                            }
                        },
                        error: function(jqXHR, textStatus) {
                            alert('Error Occured');
                        }
                    });
            swal("Deleted!", "Item has been deleted.", "success");
        });
    });
    // Remove user end

    //Activate User
                      $('.activateUser').click(function () {
                       var userId=$(this).data('userid');
                          swal({
                              title: "Are you sure?",
                              text: "You want to activate user!",
                              type: "warning",
                              showCancelButton: true,
                              confirmButtonColor: "#0094db",
                              confirmButtonText: "Yes, activated!",
                              closeOnConfirm: false
                          }, function () {

                          $.ajax({
                                              url: '/admin/getActivateOrDeactivateUser',
                                              type: 'GET',
                                              dataType : 'json',
                                             data: { 'userId': userId },
                                              success: function(data){
                                                  if(data.success) {
                                                      var redirectionUrl= "/admin/addUser";
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
            //End Active User



      //Deactivate User
           $('.deactiveUser').click(function () {
                       var userId=$(this).data('userid');
                          swal({
                              title: "Are you sure?",
                              text: "You want to deactivate user!",
                              type: "warning",
                              showCancelButton: true,
                              confirmButtonColor: "#0094db",
                              confirmButtonText: "Yes, deactivate it!",
                              closeOnConfirm: false
                          }, function () {

                          $.ajax({
                                              url: '/admin/getActivateOrDeactivateUser',
                                              type: 'GET',
                                              dataType : 'json',
                                             data: { 'userId': userId },
                                              success: function(data){
                                                  if(data.success) {
                                                   var redirectionUrl= "/admin/addUser";
                                                    window.location.href = redirectionUrl;

                                                  }
                                              },
                                              error: function(jqXHR, textStatus) {
                                                  alert('Error Occured');
                                              }
                                          });

/*
                           swal("Activated!", "Item has been Activated.", "success");
*/

                          });
                      });


    //End Deactive User


    // Add Branch start
    $(function(datepicker) {
        $('#vatEffectFrmDate .input-group.date').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        });
        $('#cstEffectFrmDate .input-group.date').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        });
        $('#sTaxEffectFrmDate .input-group.date').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        });
        $('#incomeTaxEffectFrmDate .input-group.date').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        });
        $('#pfEffectFrmDate .input-group.date').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        });
        $('#esicEffectFrmDate .input-group.date').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        });
        $('#cinEffectFrmDate .input-group.date').datepicker({
            todayBtn: "linked",
            keyboardNavigation: false,
            forceParse: false,
            calendarWeeks: true,
            autoclose: true
        });
    });
    //Add Branch  End

    // Remove Branch Start
    $('.removeBranch').click(function () {
       var branchsId=$(this).data('branchsid');
        swal({
            title: "Are you sure?",
            text: "You will not be able to recover this Branch!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#0094db",
            confirmButtonText: "Yes, delete it!",
            closeOnConfirm: false
        }, function () {

        $.ajax({
                            url: '/admin/deleteBranchDetails',
                            type: 'POST',
                            dataType : 'json',
                            data: { 'branchId': branchsId },
                            success: function(data){
                                if(data.success) {
                                    var redirectionUrl= "/admin/getBranch";
                                    window.location.href = redirectionUrl;
                                }
                            },
                            error: function(jqXHR, textStatus) {
                                alert('Error Occured');
                            }
                        });
            swal("Deleted!", "Item has been deleted.", "success");

        });
    });
    // Remove Branch End



//Edit Role Start
$("body").on('click','.roleEdit',function (e) {
    e.preventDefault();
    var roleId =$(this).data('roleid');
    $.ajax({
        url: '/admin/getRoleForEdit',
        type: 'GET',
        dataType : 'json',
        data: { 'roleId': roleId },
        success: function(data){
            if(data.success) {
                $('#roleId').val(data.data.roleId);
                $('#rolename').val(data.data.roleName);
                $('#roledescription').val(data.data.roleDescription);
            }
        },
        error: function(jqXHR, textStatus) {
            alert('Error Occured');
        }
    });

});
//Edit Role End

//Edit User Start
$("body").on('click','.userEdit',function (e) {
    e.preventDefault();
    var userId =$(this).data('userid');
    $.ajax({
        url: '/admin/getUserForEdit',
        type: 'GET',
        dataType : 'json',
        data: { 'userId': userId },
        success: function(data){
            if(data.success) {

                $('#useremail').val(data.data.email);
                 $("#userRoles").html("");
                  $("#userBranch").html("");

                   $.each(data.data.roles, function(i){
                    ﻿$('#userRoles').append($('<option selected="selected">').val(this['id']).text(this['role']));
                    });

                   $.each(data.data.fullroles, function(i){
/*
                  var optionRow =$('<option selected="selected">').val(this['id']).text(this['role']);*/

                  ﻿$('#userRoles').append($('<option>').val(this['id']).text(this['role']));

                  });

                   $.each(data.data.branch, function(i){
                           ﻿$('#userBranch').append($('<option selected="selected">').val(this['id']).text(this['branchname']));
                    });

                    $.each(data.data.fullbranch, function(i){
                                    var id=this['id'];/*
                        var optionRow1 =$('<option selected="selected">').val(this['id']).text(this['branchname']);
*/                   ﻿$('#userBranch').append($('<option>').val(this['id']).text(this['branchname']));
                                    });

            }
        },
        error: function(jqXHR, textStatus) {
            alert('Error Occured');
        }
    });

});
//Edit User End

  //User Role Starts

        $(".addUserRole").select2({
                theme: 'bootstrap4',
            });

        $(".editUserRole").select2({
                 theme: 'bootstrap4',
                    });
 //User Role Ends

//User Branch Starts

        $(".addUserBranch").select2({
                theme: 'bootstrap4',
            });

        $(".editUserBranch").select2({
                 theme: 'bootstrap4',
                    });
//User Branch Ends


 $(".branchList").select2({
                theme: 'bootstrap4',
            });


});