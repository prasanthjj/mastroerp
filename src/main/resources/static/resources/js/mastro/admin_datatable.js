$(document).ready(function(){

// User master table start
$('.dataTables-user').DataTable({
    pageLength: 25,
    responsive: true,
    dom: '<"html5buttons"B>lTfgitp',
    buttons: [
    ]
});
// User master table end

//Role master table start
$('.dataTables-role').DataTable({
    pageLength: 25,
    responsive: true,
    dom: '<"html5buttons"B>lTfgitp',
    buttons: [
    ]
});
//Role master table end

// Role Access Rights start
$('.dataTables-role-access').DataTable({
    pageLength: 25,
    responsive: true,
    dom: '<"html5buttons"B>lTfgitp',
    buttons: [
    ]
});
// Role Access Rights End

//Branch Master Table Start
$('.dataTables-Branch').DataTable({
    pageLength: 25,
    responsive: true,
    dom: '<"html5buttons"B>lTfgitp',
    buttons: [
    ]
});
//Branch Master Table End

});

