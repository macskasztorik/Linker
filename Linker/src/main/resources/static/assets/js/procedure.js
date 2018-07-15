$(document).ready(function() {
	toastr.options.timeOut = 6000;
	toastr.options.positionClass = 'toast-bottom-right';
	init();
});



function init() {
	drawLinks();
	
	$(document).keypress(function(e) {
	    if(e.which == 13) {
	    	$("#linkGeneratorButton").click();
	    }
	});
	
	function drawLinks() {
		var baseLink = window.location.href; 
		var pathname = window.location.pathname;
		baseLink = baseLink.substring(0, baseLink.length - pathname.length);
		var links = JSON.parse(localStorage.getItem("linkerLinks"));
		
		if (links != null){
			$("#links").html('');
			links.reverse();
			
			$.each(links, function(i, obj) {
				 $("#links").append('<p style="color:#000;text-decoration: none;font-size: 13px;">' + (obj.originalLink.length > 30 ? obj.originalLink.substring(0,29)+'...' : obj.originalLink.substring() )  +' </p>');
				 $("#links").append('<a target="_BLANK" style="color:#000;font-weight: bold;font-size: 15px;" href="' + baseLink + '/' + obj.shortLink + '">' + baseLink + '/' + obj.shortLink +' </a> <hr/> </br>');
			});
		}
		
	 }
	
	$("#linkGeneratorButton").on("click", function() {
		if(/^(http|https|ftp):\/\/[a-z0-9]+([\-\.]{1}[a-z0-9]+)*\.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?$/i.test($("#originalLink").val()) && $('#originalLink').val().length > 5 && $('#originalLink').val().length < 256 ){
			console.log('Input is ok');
		}else{
			toastr.error('The link should be valid, and between 5 and 256 characters!','Information');
			return;
		}
		
		if($('#originalLink').val().length < 5 || $('#originalLink').val().length > 256){
			toastr.error('The link should be valid, and between 5 and 256 characters!','Information');
			return;
		}
		
		var formData = {
           	originalLink: $("#originalLink").val(),
        };
        generateLink(formData);
	});
	
	
	function generateLink(formData) {
		 $.ajax({
	            url: "/generateLink",
	            type: "POST",
	            data: JSON.stringify(formData),
	            contentType: "application/json; charset=utf-8",
	            dataType: "json",
	            success: function (data, textStatus, jqXHR) {
	            	
	            	if (localStorage.getItem("linkerLinks") === null) {
	            		var linkerLinks = [];
	            		linkerLinks.push(data);
	            		localStorage.setItem("linkerLinks", JSON.stringify(linkerLinks));
	            	}
	            	
	            	var storedLinks = JSON.parse(localStorage.getItem("linkerLinks"));
	            	
	            	var onTheList = false;
	            	
	            	 $.each(storedLinks, function(i, obj) {
	            		 if (obj.shortLink === data.shortLink) { 
	            			 onTheList = true; 
	            		}
	            	 });
	            	 
	            	 
	            	if (!onTheList){
	            		storedLinks.push(data);
	            		localStorage.setItem("linkerLinks", JSON.stringify(storedLinks));
		           }
	            	toastr.success('Your link is on your list!','Information');
	            	
	            	$("#originalLink").val('');
	            	drawLinks();
	            },	
	            error: function (jqXHR, textStatus, errorThrown) {
	            	 window.location.replace("/");
	            }
	        });
	 }
	
	
}
