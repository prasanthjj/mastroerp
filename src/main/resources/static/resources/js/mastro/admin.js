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
                           //swal("Activated!", "Item has been Activated.", "success");
          });
   });
    //End Deactive User


//Activate Department

    $('.activateDepartment').click(function () {
        var departmentId=$(this).data('departmentid');
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
                url: '/hr/activateOrDeactivateDepartment',
                type: 'GET',
                dataType : 'json',
                data: { 'departmentId': departmentId },
                success: function(data) {
                    if(data.success) {
                        var redirectionUrl= "/hr/getDepartment";
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


 //Deactivate Department
   $('.deactiveDepartment').click(function () {
       var departmentId=$(this).data('departmentid');
        swal({
              title: "Are you sure?",
              text: "You want to deactivate Department!",
               type: "warning",
               showCancelButton: true,
               confirmButtonColor: "#0094db",
               confirmButtonText: "Yes, deactivate it!",
               closeOnConfirm: false
                  }, function () {

                   $.ajax({
                           url: '/hr/activateOrDeactivateDepartment',
                           type: 'GET',
                           dataType : 'json',
                            data: { 'departmentId': departmentId },
                            success: function(data){
                            if(data.success) {
                                      var redirectionUrl= "/hr/getDepartment";
                                       window.location.href = redirectionUrl;
                                     }
                                    },
                             error: function(jqXHR, textStatus) {
                              alert('Error Occured');
                                  }
                                 });
                           //swal("Activated!", "Item has been Activated.", "success");
          });
   });
    //End Deactive Department

      //Activate Salary Component

            $('.activatecomponent').click(function () {
                var salryComponentId=$(this).data('salrycomponentid');
                alert(salryComponentId);
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
                        url: '/hr/activateOrDeactivateSalaryComponent',
                        type: 'GET',
                        dataType : 'json',
                        data: { 'salryComponentId':salryComponentId },
                        success: function(data) {
                            if(data.success) {
                                var redirectionUrl= "/hr/getSalaryComponent";
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
    // End salry Component


     //Deactivate Salry Component
       $('.deactivecomponent').click(function () {
           var salryComponentId=$(this).data('salrycomponentid');

            swal({
                  title: "Are you sure?",
                  text: "You want to deactivate Salary component!",
                   type: "warning",
                   showCancelButton: true,
                   confirmButtonColor: "#0094db",
                   confirmButtonText: "Yes, deactivate it!",
                   closeOnConfirm: false
                      }, function () {

                       $.ajax({
                               url: '/hr/activateOrDeactivateSalaryComponent',
                               type: 'GET',
                               dataType : 'json',
                                data: { 'salryComponentId': salryComponentId },
                                success: function(data){
                                if(data.success) {
                                          var redirectionUrl= "/hr/getSalaryComponent";
                                           window.location.href = redirectionUrl;
                                         }
                                        },
                                 error: function(jqXHR, textStatus) {
                                  alert('Error Occured');
                                      }
                                     });
                               //swal("Activated!", "Item has been Activated.", "success");
              });
       });
        //End Deactive Salary Component

  // Add Branch start
    $(function(datepicker) {

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
        $('#gstEffectFrmDate .input-group.date').datepicker({
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

    // Remove Gate Pass Start
    $('.removeGatePass').click(function () {
       var gatepassIds=$(this).data('gatepassids');
        swal({
            title: "Are you sure?",
            text: "You will not be able to recover this Gate Pass!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#0094db",
            confirmButtonText: "Yes, delete it!",
            closeOnConfirm: false
        }, function () {

        $.ajax({
            url: '/inventory/deleteGatePass',
            type: 'POST',
            dataType : 'json',
            data: { 'gatepassids': gatepassIds },
            success: function(data){
                if(data.success) {
                      var redirectionUrl= "/inventory/getGatePass";
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
    // Remove Gate Pass End

   //Role access starts
   $('#roleAccess').change(function (e) {
   e.preventDefault();
    var rolesId=document.getElementById("roleAccess").value;

     $.ajax({
      url: '/admin/getRoleAccessEdit',
       type: 'GET',
       dataType : 'json',
       data: {'roleId': rolesId},
       success: function(data){
         if(data.success) {
               $("#moduleCheckId tbody").html("");
               $.each(data.data.rolemodules, function(id){
               var tblRow = "<tr class='gradeX'>"  + "<td>" + this['module'] + "</td>" + "<td>" + "<input type='checkbox' name='checkboxName' checked value=" +this['id'] +" '/> "+ "</td>";
               $(tblRow).appendTo("#moduleCheckId tbody");
               });
                $.each(data.data.remainingModules, function(id){
                 var tblRow = "<tr class='gradeX'>"  + "<td>" + this['module'] + "</td>" + "<td>" + "<input type='checkbox' name='checkboxName' value=" +this['id'] +"  '/> "+ "</td>";
                 $(tblRow).appendTo("#moduleCheckId tbody");
                });
          }
           },
       error: function(jqXHR, textStatus) {
       alert('Error Occured');
       }

      });
 });
   //Role access Ends

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

//Edit Department Start
   $("body").on('click','.departmentEdit',function (e) {
    e.preventDefault();
    var departmentId =$(this).data('departmentid');
    $.ajax({
        url: '/hr/getDepartmentForEdit',
        type: 'GET',
        dataType : 'json',
        data: { 'departmentId': departmentId },
        success: function(data){
            if(data.success) {
                $('#departmentId').val(data.data.departmentId);
                $('#departmentname').val(data.data.departmentName);
                $('#departmenthead').val(data.data.departmentHead);
            }
        },
        error: function(jqXHR, textStatus) {
            alert('Error Occured');
        }
    });
});
//Edit Department End

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
            $('#userRoles').append($('<option selected="selected">').val(this['id']).text(this['role']));
              });
               $.each(data.data.fullroles, function(i){
                  ﻿$('#userRoles').append($('<option>').val(this['id']).text(this['role']));
                  });
               $.each(data.data.branch, function(i){
                  ﻿$('#userBranch').append($('<option selected="selected">').val(this['id']).text(this['branchname']));
                  });
               $.each(data.data.fullbranch, function(i){
                var id=this['id'];
                ﻿$('#userBranch').append($('<option>').val(this['id']).text(this['branchname']));
                  });
            }
        },
        error: function(jqXHR, textStatus) {
            alert('Error Occured');
        }
    });
});
   //Edit User End

$(function() {
 //Select email Starts
    $(".selectEmail").select2({
    theme:'bootstrap4',
     dropdownParent: $('#addUser'),
    });
   //Select email Ends
   });

   $(function() {
   //SelectCountry Starts
    $(".selectCountry").select2({
     theme:'bootstrap4',
     });
    // Select Country Ends

    //Select State Starts
    $(".selectState").select2({
     theme:'bootstrap4',
     });
    // Select State Ends


     //Select City Starts
    $(".selectCity").select2({
     theme:'bootstrap4',
     });
    //Select City Ends

   });

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