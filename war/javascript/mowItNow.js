/** * fonctions js du projet mowitnow ** */

	
	function init(){
		// récupération du token
		var token = $(".token").text();
		channel = new goog.appengine.Channel(token);
	    socket = channel.open();
	    socket.onopen = onOpened;
	    socket.onmessage = onMessage;
	    socket.onerror = onError;
	    socket.onclose = onClose;
	}
	
	
	function onOpened() {
	    console.log("Channel opened!");
	}
	
	function onMessage(msg) {
		var messageTondeuse = jQuery.parseJSON(msg.data);
	    console.log("messagePush : "+ messageTondeuse);
	    var idGrille = "grile-"+messageTondeuse.position.x+"-"+messageTondeuse.position.y;
	    $("#"+idGrille).css("background-image", "url(image/gazon-apres.jpg)");
	    $("#"+idGrille).css("background-image", "url(image/tondeuse.jpg)").css("background-size","100%");
	}
	
	function onClose() {
	    console.log("Channel closed!");
	}
	
	function onError() {
	    console.log("Channel error!");
	}
			    
			    
	function clicker() {
		console.log("okkkkkkkkkk");
		envoyer($("#zoneInstruction").val());
	}
    
	function envoyer(instructions) {
		var request = $.ajax({
			url: "/pushPosition",
			type: "Post",
			data: {instructions : instructions},
			dataType: "json",
			success: function(data) {
				console.log(data);
			}
		});
	};
    
	$(document).ready(function(){
		// ouvrir le channel
		init();
  
	function Position(positionnement) {
		this.x = positionnement[0];
		this.y = positionnement[1];
		this.log = function(){console.log("x: "+this.x +" y: "+this.y)};
	};
			  

	function construireGrille(position){
		var pasX = 60;
		var pasY = 60;
		var left = 0;
		var top  = 0;
		for (z=0;z<position.y;z++) {
			top = z * pasX; 
			for (i=0;i<position.x;i++) {
				left = i * pasY;
				var idGrille = "grile-"+(position.y -1 - z)+"-"+ i;
				var $grille = $("<div id='"+idGrille+"' style='left:"+left+";top:"+top+";border: solid 1px black;height:"+pasY+"px;width:"+pasX+"px;display:inline-block;position: absolute;'>");
				$grille.css("background-image", "url(image/gazon.jpg)"); 
				$grille.appendTo(".grilleContainer");
			}
		}
	};
			  
	// récupérer les insctruction
	$("[name='envoyer']").click(function(){
		$('.grilleContainer').find('div').remove();
		var entreeInstruction = $("#zoneInstruction").val();
		var instructions = entreeInstruction.split(/\r\n|\r|\n/);
		console.log(instructions);
		// la première ligne pour récupérer la largeur et la hauteur
		var position = new Position(instructions[0].split(/\s/));
		position.log();
		construireGrille(position);
		envoyer(instructions);
	});
			  
	});