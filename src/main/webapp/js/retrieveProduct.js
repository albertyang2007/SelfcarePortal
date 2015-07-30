/**
 * Select & de-select all products
 */
$(document).ready(function() {

	$("#selectAll").click(function() {
		$("[name=productID]").prop("checked", true);
		$("#purchaseItems").button('reset'); 
	});

	$("#deselectAll").click(function() {
		$("[name=productID]").prop("checked", false);
		$("#purchaseItems").button('loading');
	});
	
	$("[name=productID]").click(function(){
		if ($("[name='productID']:checked").length == 0) {
			$("#purchaseItems").button('loading');
	      }else{
	    	  $("#purchaseItems").button('reset'); 
	      }
	});
});