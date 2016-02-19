      function initialize() {
        var position = new google.maps.LatLng(45.251667,  19.836944);
        var myOptions = {
          zoom: 10,
          center: position,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        var map = new google.maps.Map(
            document.getElementById("map_canvas"),
            myOptions);
/*
        var marker = new google.maps.Marker({
            position: position,
            map: map,
            title:"This is the place."
        });  

        var contentString = 'Hello <strong>World</strong>!';
        var infowindow = new google.maps.InfoWindow({
            content: contentString
        });

        google.maps.event.addListener(marker, 'click', function() {
          infowindow.open(map,marker);
        });
        */
       geocoder = new google.maps.Geocoder();   
       var gotData;
      $.ajaxSetup({async:false});
      	$.ajax({
   		  url: "./api/restaurant/restaurants",
   		  type: 'post',

   		  data: {
   		   mapData:JSON.stringify({}),    
   		   cache: false,
   		   dataType:'json'
   		},
      		  success: function (data, status) {
      		    console.log(data);
      		    console.log(status);
      		    },
      		  error: function (xhr, desc, err) {
      		    console.log(xhr);
      		  }
      		}).done(function(data){
      			
      			var getLatLng = function(addresses, callback) {
      				
        		    var marker = [];
    	   		    var infowindow = [];
    	   		    var contentString = [];
    	   		    
      	            addresses.forEach(function(address) {

      	                var geocoder = new google.maps.Geocoder();
      	                geocoder.geocode({'address': address}, function (results, status) {

      	                    if (status == google.maps.GeocoderStatus.OK) {
								
  		   	                  var tmpMark= new google.maps.Marker({
		   	                      map: map,
		   	                      position: results[0].geometry.location,
		   	                      title:gotData[i][0]
		   	                  });
		   	                  marker.push(tmpMark);
		   	                  
		   	                  var tmpContString = "Restoran: " + data[i][0] + "\nTip restorana: " + data[i][5];
		   	                  var tmpInfoWind = new google.maps.InfoWindow({
		   	                      content: contentString[i]
		   	                  });
		   	                  contentString.push(tmpContString);
		   	                  infowindow.push(tmpInfoWind);
		   	                  
		   	                  google.maps.event.addListener(marker[i], 'click', function() {
		   	                    infowindow[i].open(map,marker[i]);
		   	                  });
      	                        
      	                        
      	                        
      	                        // all addresses have been processed
      	                        if (map.length === addresses.length)
      	                            callback(map);
      	                    }
      	                });
      	            });
      	        }
      			
				var addresses = [];
	   		    for(var i = 0; i < data.length; i++){
	   		    	addresses.push(data[i][2] + " " +data[i][1] + ", " + data[i][3] + ", " + data[i][4]);
	   		    }
      			
      	        getLatLng(data, function (results) {
      	            console.log("received all addresses:", results);
      	        });
      			
      	
      			
      		});
      	 $.ajaxSetup({async:true});  
      }