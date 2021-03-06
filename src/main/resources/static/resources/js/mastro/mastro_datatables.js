$(document).ready(function(){
//Brand Start
$('.dataTables-brand').DataTable({
    pageLength: 25,
    responsive: true,
    dom: '<"html5buttons"B>lTfgitp',
    buttons: [
        { extend: 'copy'},
        {extend: 'csv'},
        {extend: 'excel', title: 'ExampleFile'},
        {extend: 'pdf', title: 'ExampleFile'},

        {extend: 'print',
         customize: function (win){
                $(win.document.body).addClass('white-bg');
                $(win.document.body).css('font-size', '10px');

                $(win.document.body).find('table')
                        .addClass('compact')
                        .css('font-size', 'inherit');
        }
        }
    ]

});
//Brand End
//Price List Start
$('.dataTables-priceList').DataTable({
    pageLength: 25,
    responsive: true,
    dom: '<"html5buttons"B>lTfgitp',
    buttons: [
        { extend: 'copy'},
        {extend: 'csv'},
        {extend: 'excel', title: 'ExampleFile'},
        {extend: 'pdf', title: 'ExampleFile'},

        {extend: 'print',
         customize: function (win){
                $(win.document.body).addClass('white-bg');
                $(win.document.body).css('font-size', '10px');

                $(win.document.body).find('table')
                        .addClass('compact')
                        .css('font-size', 'inherit');
        }
        }
    ]

});
//Price List Data-table End

//asset datatable start
$('.dataTables-assets').DataTable({
    pageLength: 25,
    responsive: true,
    dom: '<"html5buttons"B>lTfgitp',
    buttons: [
        { extend: 'copy'},
        {extend: 'csv'},
        {extend: 'excel', title: 'ExampleFile'},
        {extend: 'pdf', title: 'ExampleFile'},

        {extend: 'print',
         customize: function (win){
                $(win.document.body).addClass('white-bg');
                $(win.document.body).css('font-size', '10px');
                $(win.document.body).find('table')
                        .addClass('compact')
                        .css('font-size', 'inherit');
        }
        }
    ]
});
//Assets Data-table end

//HSN Data-table start
$('.dataTables-hsn').DataTable({
pageLength:25,
responsive:true,
dom:'<"html5buttons"B>lTfgitp',
buttons: [
        { extend:'copy'},
        {extend:'csv'},
        {extend:'excel', title:'ExampleFile'},
        {extend:'pdf', title:'ExampleFile'},

        {extend:'print',
customize:function (win){
$(win.document.body).addClass('white-bg');
$(win.document.body).css('font-size', '10px');

$(win.document.body).find('table')
                        .addClass('compact')
                        .css('font-size', 'inherit');
        }
        }
    ]

});
//HSN Data-table end

//Item Data-table start
$('.dataTables-item').DataTable({
    pageLength: 25,
    responsive: true,
    dom: '<"html5buttons"B>lTfgitp',
    buttons: [
        { extend: 'copy'},
        {extend: 'csv'},
        {extend: 'excel', title: 'ExampleFile'},
        {extend: 'pdf', title: 'ExampleFile'},

        {extend: 'print',
         customize: function (win){
                $(win.document.body).addClass('white-bg');
                $(win.document.body).css('font-size', '10px');

                $(win.document.body).find('table')
                        .addClass('compact')
                        .css('font-size', 'inherit');
        }
        }
    ]

});
//Item Data-table End
//Party Data-table Start
$('.dataTables-party').DataTable({
    pageLength: 25,
    responsive: true,
    dom: '<"html5buttons"B>lTfgitp',
    buttons: [
        { extend: 'copy'},
        {extend: 'csv'},
        {extend: 'excel', title: 'ExampleFile'},
        {extend: 'pdf', title: 'ExampleFile'},

        {extend: 'print',
         customize: function (win){
                $(win.document.body).addClass('white-bg');
                $(win.document.body).css('font-size', '10px');

                $(win.document.body).find('table')
                        .addClass('compact')
                        .css('font-size', 'inherit');
        }
        }
    ]

});
//Party Data-table End

//item party rate  Data-table start
$('.dataTables-itemParty').DataTable({
    pageLength: 25,
    responsive: true,
    dom: '<"html5buttons"B>lTfgitp',
    buttons: [    ]
});
//item party rate  Data-table End
//party item rate  Data-table start
$('.dataTables-partyItem').DataTable({
    pageLength: 25,
    responsive: true,
    dom: '<"html5buttons"B>lTfgitp',
    buttons: [    ]
});
//party item rate  Data-table End

//Department  Data-table start
$('.dataTables-department').DataTable({
    pageLength: 25,
    responsive: true,
    dom: '<"html5buttons"B>lTfgitp',
    buttons: [    ]
});
//Department   Data-table End

//Salary  Data-table start
$('.dataTables-salaryComponents').DataTable({
    pageLength: 25,
    responsive: true,
    dom: '<"html5buttons"B>lTfgitp',
    buttons: [    ]
});
//Salary   Data-table End
//Salary  Data-table start
$('.dataTables-gatepass').DataTable({
    pageLength: 25,
    responsive: true,
    dom: '<"html5buttons"B>lTfgitp',
    buttons: [    ]
});
//Salary   Data-table End


//stock details Data-table start
$('.dataTables-stockDetails').DataTable({
    pageLength: 25,
    responsive: true,
    dom: '<"html5buttons"B>lTfgitp',
    buttons: [    ]
});
//stock details  Data-table End

//GRN Data-table start
$('.dataTables-grn').DataTable({
    pageLength: 25,
    responsive: true,
    dom: '<"html5buttons"B>lTfgitp',
    buttons: [    ]
});
//GRN Data-table End
});

