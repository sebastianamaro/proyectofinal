$(function() {
  function add(){
    var datosIncompletos = false;
      $('table tbody tr td input').each(function() {
        if ($(this).val()===""){
          alert("Completa la palabra anterior antes de crear una nueva.");
          datosIncompletos = true;
          return false;
        }
      });
    if (datosIncompletos){
      return;
    }
    var index = $('table tbody tr').length;
    var row = $('table tbody tr:first').html();
    row = row.replace(/\_\d+\_/g, "_"+index+"_"); //cambio ids, reemplaza todos los nros por index
    row = row.replace(/\[\d+\]/g, "["+index+"]"); //cambio ids, reemplaza todos los nros por index
    
    row = row.replace(/value="*"/g, 'value=""'); //limpio values
    $('table tbody').append("<tr>"+row+"</tr>");   
  }
 
  if ($('table tbody tr').length === 0) {
      add();
  }
 
  $('table tbody a').on('click', function(event) {
      event.preventDefault();
      if ($('table tbody tr').length > 0) {
          if ($('table tbody tr').length === 1) {
            add();
          }
          $(this).parent().parent().remove();
          return 1;
      }
  });
  $('#add_word_link').on('click', function(event) {
      event.preventDefault();
      add();
  });

  
});
