$(document).ready(function(){

// add assets form start
$("#createAssetsForm").steps({
    bodyTag: "fieldset",
    onStepChanging: function (event, currentIndex, newIndex)
    {
        // Always allow going backward even if the current step contains invalid fields!
        if (currentIndex > newIndex)
        {
            return true;
        }

        // Forbid suppressing "Warning" step if the user is to young
        if (newIndex === 3 && Number($("#age").val()) < 18)
        {
            return false;
        }

        var form = $(this);

        // Clean up if user went backward before
        if (currentIndex < newIndex)
        {
            // To remove error styles
            $(".body:eq(" + newIndex + ") label.error", form).remove();
            $(".body:eq(" + newIndex + ") .error", form).removeClass("error");
        }

        // Disable validation on fields that are disabled or hidden.
        form.validate().settings.ignore = ":disabled,:hidden";

        // Start validation; Prevent going forward if false
        return form.valid();
    },
    onStepChanged: function (event, currentIndex, priorIndex)
    {
        // Suppress (skip) "Warning" step if the user is old enough.
        if (currentIndex === 2 && Number($("#age").val()) >= 18)
        {
            $(this).steps("next");
        }

        // Suppress (skip) "Warning" step if the user is old enough and wants to the previous step.
        if (currentIndex === 2 && priorIndex === 3)
        {
            $(this).steps("previous");
        }
    },
    onFinishing: function (event, currentIndex)
    {
        var form = $(this);

        // Disable validation on fields that are disabled.
        // At this point it's recommended to do an overall check (mean ignoring only disabled fields)
        form.validate().settings.ignore = ":disabled";

        // Start validation; Prevent form submission if false
        return form.valid();
    },
    onFinished: function (event, currentIndex)
    {
        var form = $(this);

        // Submit form input
        form.submit();
    }
}).validate({
            errorPlacement: function (error, element)
            {
                element.before(error);
            },
            rules: {
                 assetName: {
                    required:true
                 },
                 assetType: {
                    required:true
                 },
                 assetLocation: {
                    required:true
                 },
                 installationDate: {
                    required:true
                  },
                 effectiveDate: {
                                      required:true
                                    },
                    hoursUtilized: {
                              number:true
                                 },
                 maintenancePriority: {
                    required:true
                 },

            }
        });
// add assets form end

 // edit assets form start
       $("#editAssetsForm").steps({
           bodyTag: "fieldset",
           onStepChanging: function (event, currentIndex, newIndex)
           {
               // Always allow going backward even if the current step contains invalid fields!
               if (currentIndex > newIndex)
               {
                   return true;
               }

               // Forbid suppressing "Warning" step if the user is to young
               if (newIndex === 3 && Number($("#age").val()) < 18)
               {
                   return false;
               }

               var form = $(this);

               // Clean up if user went backward before
               if (currentIndex < newIndex)
               {
                   // To remove error styles
                   $(".body:eq(" + newIndex + ") label.error", form).remove();
                   $(".body:eq(" + newIndex + ") .error", form).removeClass("error");
               }

               // Disable validation on fields that are disabled or hidden.
               form.validate().settings.ignore = ":disabled,:hidden";

               // Start validation; Prevent going forward if false
               return form.valid();
           },
           onStepChanged: function (event, currentIndex, priorIndex)
           {
               // Suppress (skip) "Warning" step if the user is old enough.
               if (currentIndex === 2 && Number($("#age").val()) >= 18)
               {
                   $(this).steps("next");
               }

               // Suppress (skip) "Warning" step if the user is old enough and wants to the previous step.
               if (currentIndex === 2 && priorIndex === 3)
               {
                   $(this).steps("previous");
               }
           },
           onFinishing: function (event, currentIndex)
           {
               var form = $(this);

               // Disable validation on fields that are disabled.
               // At this point it's recommended to do an overall check (mean ignoring only disabled fields)
               form.validate().settings.ignore = ":disabled";

               // Start validation; Prevent form submission if false
               return form.valid();
           },
           onFinished: function (event, currentIndex)
           {
               var form = $(this);

               // Submit form input
               form.submit();
           }
       }).validate({
                   errorPlacement: function (error, element)
                   {
                       element.before(error);
                   },
                   rules: {
                        assetName: {
                           required:true
                        },
                        assetType: {
                           required:true
                        },
                        assetLocation: {
                           required:true
                        },
                        installationDate: {
                           required:true
                         },
                          effectiveDate: {
                            required:true
                               },
                         hoursUtilized: {
                           number:true
                                   },
                        maintenancePriority: {
                           required:true
                        },
                   }
               });
       // edit assets form end

       // view assets form start
       $("#viewAssetsForm").steps({
           bodyTag: "fieldset",
           onStepChanging: function (event, currentIndex, newIndex)
           {
               // Always allow going backward even if the current step contains invalid fields!
               if (currentIndex > newIndex)
               {
                   return true;
               }

               // Forbid suppressing "Warning" step if the user is to young
               if (newIndex === 3 && Number($("#age").val()) < 18)
               {
                   return false;
               }

               var form = $(this);

               // Clean up if user went backward before
               if (currentIndex < newIndex)
               {
                   // To remove error styles
                   $(".body:eq(" + newIndex + ") label.error", form).remove();
                   $(".body:eq(" + newIndex + ") .error", form).removeClass("error");
               }

               // Disable validation on fields that are disabled or hidden.
               form.validate().settings.ignore = ":disabled,:hidden";

               // Start validation; Prevent going forward if false
               return form.valid();
           },
           onStepChanged: function (event, currentIndex, priorIndex)
           {
               // Suppress (skip) "Warning" step if the user is old enough.
               if (currentIndex === 2 && Number($("#age").val()) >= 18)
               {
                   $(this).steps("next");
               }

               // Suppress (skip) "Warning" step if the user is old enough and wants to the previous step.
               if (currentIndex === 2 && priorIndex === 3)
               {
                   $(this).steps("previous");
               }
           },
           onFinishing: function (event, currentIndex)
           {
               var form = $(this);

               // Disable validation on fields that are disabled.
               // At this point it's recommended to do an overall check (mean ignoring only disabled fields)
               form.validate().settings.ignore = ":disabled";

               // Start validation; Prevent form submission if false
               return form.valid();
           },
           onFinished: function (event, currentIndex)
           {
                var redirectionUrl= "/master/getAssetList";
                 window.location.href = redirectionUrl;
           }
       }).validate({
                   errorPlacement: function (error, element)
                   {
                       element.before(error);
                   },
                   rules: {

                   }
               });
       // view assets form end

       // add party form start

       $("#createPartyForm").steps({
           bodyTag: "fieldset",
           onStepChanging: function (event, currentIndex, newIndex)
           {
               // Always allow going backward even if the current step contains invalid fields!
               if (currentIndex > newIndex)
               {
                   return true;
               }

               // Forbid suppressing "Warning" step if the user is to young
               if (newIndex === 3 && Number($("#age").val()) < 18)
               {
                   return false;
               }

               var form = $(this);

               // Clean up if user went backward before
               if (currentIndex < newIndex)
               {
                   // To remove error styles
                   $(".body:eq(" + newIndex + ") label.error", form).remove();
                   $(".body:eq(" + newIndex + ") .error", form).removeClass("error");
               }

               // Disable validation on fields that are disabled or hidden.
               form.validate().settings.ignore = ":disabled,:hidden";

               // Start validation; Prevent going forward if false
               return form.valid();
           },
           onStepChanged: function (event, currentIndex, priorIndex)
           {
               // Suppress (skip) "Warning" step if the user is old enough.
               if (currentIndex === 2 && Number($("#age").val()) >= 18)
               {
                   $(this).steps("next");
               }

               // Suppress (skip) "Warning" step if the user is old enough and wants to the previous step.
               if (currentIndex === 2 && priorIndex === 3)
               {
                   $(this).steps("previous");
               }
           },
           onFinishing: function (event, currentIndex)
           {
               var form = $(this);

               // Disable validation on fields that are disabled.
               // At this point it's recommended to do an overall check (mean ignoring only disabled fields)
               form.validate().settings.ignore = ":disabled";

               // Start validation; Prevent form submission if false
               return form.valid();
           },
           onFinished: function (event, currentIndex)
           {
               var form = $(this);

               // Submit form input
               form.submit();
           }
       }).validate({
                   errorPlacement: function (error, element)
                   {
                       element.before(error);
                   },
                   rules: {
                        partyType: {
                            required:true
                         },
                         partyName: {
                            required:true
                         },
                          categoryType: {
                                 required:true
                                     },
                     type:{
                     required:true,
                    },
                contactPersonName:{
                    required:true,
                    },
                telephoneNo:{
                    required:true,
                    number: true,
                    maxlength:10,
                    },
                emailId:{
                    number: true,
                    maxlength:10,
                    },
                country:{
                    required:true,
                    },
                state:{
                    required:true,
                    },
                city:{
                    required:true,
                    },
                pinCode:{
                    required:true,
                    },

                }
               });
       // add party form end
 // edit party form start

              $("#editPartyForm").steps({
                  bodyTag: "fieldset",
                  onStepChanging: function (event, currentIndex, newIndex)
                  {
                      // Always allow going backward even if the current step contains invalid fields!
                      if (currentIndex > newIndex)
                      {
                          return true;
                      }

                      // Forbid suppressing "Warning" step if the user is to young
                      if (newIndex === 3 && Number($("#age").val()) < 18)
                      {
                          return false;
                      }

                      var form = $(this);

                      // Clean up if user went backward before
                      if (currentIndex < newIndex)
                      {
                          // To remove error styles
                          $(".body:eq(" + newIndex + ") label.error", form).remove();
                          $(".body:eq(" + newIndex + ") .error", form).removeClass("error");
                      }

                      // Disable validation on fields that are disabled or hidden.
                      form.validate().settings.ignore = ":disabled,:hidden";

                      // Start validation; Prevent going forward if false
                      return form.valid();
                  },
                  onStepChanged: function (event, currentIndex, priorIndex)
                  {
                      // Suppress (skip) "Warning" step if the user is old enough.
                      if (currentIndex === 2 && Number($("#age").val()) >= 18)
                      {
                          $(this).steps("next");
                      }

                      // Suppress (skip) "Warning" step if the user is old enough and wants to the previous step.
                      if (currentIndex === 2 && priorIndex === 3)
                      {
                          $(this).steps("previous");
                      }
                  },
                  onFinishing: function (event, currentIndex)
                  {
                      var form = $(this);

                      // Disable validation on fields that are disabled.
                      // At this point it's recommended to do an overall check (mean ignoring only disabled fields)
                      form.validate().settings.ignore = ":disabled";

                      // Start validation; Prevent form submission if false
                      return form.valid();
                  },
                  onFinished: function (event, currentIndex)
                  {
                      var form = $(this);

                      // Submit form input
                      form.submit();
                  }
              }).validate({
                          errorPlacement: function (error, element)
                          {
                              element.before(error);
                          },
                          rules: {

                       }
                      });
              // edit party form end
       // view party form start

              $("#viewPartyForm").steps({
                  bodyTag: "fieldset",
                  onStepChanging: function (event, currentIndex, newIndex)
                  {
                      // Always allow going backward even if the current step contains invalid fields!
                      if (currentIndex > newIndex)
                      {
                          return true;
                      }

                      // Forbid suppressing "Warning" step if the user is to young
                      if (newIndex === 3 && Number($("#age").val()) < 18)
                      {
                          return false;
                      }

                      var form = $(this);

                      // Clean up if user went backward before
                      if (currentIndex < newIndex)
                      {
                          // To remove error styles
                          $(".body:eq(" + newIndex + ") label.error", form).remove();
                          $(".body:eq(" + newIndex + ") .error", form).removeClass("error");
                      }

                      // Disable validation on fields that are disabled or hidden.
                      form.validate().settings.ignore = ":disabled,:hidden";

                      // Start validation; Prevent going forward if false
                      return form.valid();
                  },
                  onStepChanged: function (event, currentIndex, priorIndex)
                  {
                      // Suppress (skip) "Warning" step if the user is old enough.
                      if (currentIndex === 2 && Number($("#age").val()) >= 18)
                      {
                          $(this).steps("next");
                      }

                      // Suppress (skip) "Warning" step if the user is old enough and wants to the previous step.
                      if (currentIndex === 2 && priorIndex === 3)
                      {
                          $(this).steps("previous");
                      }
                  },
                  onFinishing: function (event, currentIndex)
                  {
                      var form = $(this);

                      // Disable validation on fields that are disabled.
                      // At this point it's recommended to do an overall check (mean ignoring only disabled fields)
                      form.validate().settings.ignore = ":disabled";

                      // Start validation; Prevent form submission if false
                      return form.valid();
                  },
                  onFinished: function (event, currentIndex)
                  {
                      var form = $(this);

                      // Submit form input
                      form.submit();
                  }
              }).validate({
                          errorPlacement: function (error, element)
                          {
                              element.before(error);
                          },
                            rules: {
                                                 partyType: {
                                                     required:true
                                                  },
                                                  partyName: {
                                                     required:true
                                                  },
                                                   categoryType: {
                                                          required:true
                                                              },
                                              type:{
                                              required:true,
                                             },
                                         contactPersonName:{
                                             required:true,
                                             },
                                         telephoneNo:{
                                             required:true,
                                             number: true,
                                             maxlength:10,
                                             },
                                         emailId:{
                                             number: true,
                                             maxlength:10,
                                             },
                                         country:{
                                             required:true,
                                             },
                                         state:{
                                             required:true,
                                             },
                                         city:{
                                             required:true,
                                             },
                                         pinCode:{
                                             required:true,
                                             },

                                         }
                      });
              // view party form end

});





