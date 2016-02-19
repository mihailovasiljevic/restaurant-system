function fn_loguj_se() {
		
		var uname = $("#korisnik").val();
		var pass = $("#lozinka").val();
		varallGood = false;
		
        if( uname == "" || uname == undefined || uname == null ) {
              $(".empty_username").css("display","block");
              allGood = false;
        } else {
        	$(".empty_username").css("display","none");
        	allGood = true;
        }  
        
        if( pass == "" || pass == undefined || pass == null ) {
              $(".empty_password").css("display","block");
              allGood = false;
        } else {
        	$(".empty_password").css("display","none");
      		if(allGood != false)
      			allGood = true;
        }  
       
        if(allGood == true){
        	$.ajaxSetup({async:false});
        	$.ajax({
        		  url: "../admin-login",
        		  type: 'post',

        		  data: {
        		   loginPodaci:JSON.stringify({
        		   username:uname,
        		   password:pass,
        		   }),    
        		   cache: false,
        		   dataType:'json'
        		},
        		  success: function (data, status) {
        		    console.log(data);
        		    console.log(status);
        		  },
        		  error: function (xhr, desc, err) {
        		    console.log(xhr);
        		  },
        		}).done(function(data){
             
					//provera odgovora servera:
                   	if(data == "\"vec_prijavljen\""){
                		$("#login_panel").css('display','none');
                		$("#header_info").hide();
                		$("#header_info").load("header.txt",function(){
                            $(this).fadeIn(2000);
                        });
                		$("#side_bar").hide();
                		$("#side_bar").load("side_bar.txt",function(){
                            $(this).fadeIn(2000);
                        });
                		$("#content").hide();
                		$("#content").load("pocetna.txt",function(){
                            $(this).fadeIn(2000);
                        });

                	}
                	else if(data == "\"prijavljen\""){
                   		 $("#login_panel").fadeOut();
                		$("#header_info").hide();
                		$("#header_info").load("header.txt",function(){
                            $(this).fadeIn(2000);
                        });
                		$("#side_bar").hide();
                		$("#side_bar").load("side_bar.txt",function(){
                            $(this).fadeIn(2000);
                        });
                		$("#content").hide();
                		$("#content").load("pocetna.txt",function(){
                            $(this).fadeIn(2000);
                        });
                		

                	}
                	else if(data == "\"greska\""){
                		$("#login_panel").css('display','block');
                		$(".user_doesnt_exist").css("display", "block");

                	}
                	else if(data == "\"-1\""){
                		$("#login_panel").css('display','block');
                	}
        		});
        	$.ajaxSetup({async:true});
		}
	}