/*********** fonctions js du projet mowitnow **********/
	
	$(document).ready(function(){
		// ouvrir le channel
		initChannel();
		// placer le listener sur l'envois des instructions
		envoyerCommandes();
	});
	
	/**
	 * Cr�ation et ouverture du channel
	 */
	function initChannel(){
		// r�cup�ration du token
		var token = $(".token").text();
		channel = new goog.appengine.Channel(token);
	    socket = channel.open();
	    socket.onopen = onOpened;
	    socket.onmessage = onMessage;
	    socket.onerror = onError;
	    socket.onclose = onClose;
	}
	
	/**
	 * Fonction appel�e � l'ouverture du channel
	 */
	function onOpened() {
	    console.log("Channel opened!");
	}
	
	/**
	 * Fonction appel�e � la fermeture du channel
	 */
	function onClose() {
		console.log("Channel closed!");
	}
	
	/**
	 * Fonction appel�e lors d'une erreur survenue sur la socket
	 */
	function onError() {
		console.log("Channel error!");
	}
	
	/**
	 * Fonction appel�e � la r�ception d'un message par la socket
	 */
	function onMessage(msg) {
		// parser le r�sultat envoyer en json
		var messageTondeuse = jQuery.parseJSON(msg.data);
	    console.log("messagePush : "+ messageTondeuse);
	    var idGrille = "grile-"+messageTondeuse.position.y+"-"+messageTondeuse.position.x;
	    var $grille = $("#"+idGrille);
    	$(".tondue").css("background-image", "url(image/gazon-apres.jpg)");
    	$grille.css("background-image", "url(image/tondeuse.jpg)").css("background-size","100%");
    	$grille.attr("class","tondue");
	}
	
	/**
	 * Permet d'envoyer les commandes vers le service
	 */
	function envoyerCommandes() {
		$("[name='envoyer']").click(function(){
			// supprimer le conteneur de grille
			$('.grilleContainer').find('div').remove();
			var entreeInstruction = $("#zoneInstruction").val();
			var instructions = entreeInstruction.split(/\r\n|\r|\n/);
			// la premi�re ligne pour r�cup�rer la largeur et la hauteur
			var pelouse = new Pelouse(instructions[0].split(/\s/));
			construirePelouse(pelouse);
			envoyerInstructionsAjax(instructions);
		});
	}
	
	/**
	 * Permet d'envoyer toutes les instructions vers le service
	 * @param instructions
	 */
	function envoyerInstructionsAjax(instructions) {
		$.ajax({
			url: "/pushPosition",
			type: "POST",
			data: {instructions : instructions},
			dataType: "json"
		});
	};
	
	/**
	 * Repr�sente une pelouse, poss�de une largeur et une hauteur
	 * @param descriptionPelouse tableau qui repr�sente la largeur et la hauteur
	 */
	function Pelouse(descriptionPelouse) {
		this.largeur = descriptionPelouse[0];
		this.hauteur = descriptionPelouse[1];
	};
	
	/**
	 * Permet de construire la pelouse � partie de sa description
	 * @param pelouse pelouse
	 */
	function construirePelouse(pelouse){
		var pasX = 60;
		var pasY = 60;
		var left = 0;
		var top  = 0;
		for ( z = 0; z <= pelouse.hauteur; z++ ) {
			top = z * pasX; 
			for ( i=0 ; i <= pelouse.largeur; i++) {
				left = i * pasY;
				var idGrille = "grile-"+(pelouse.hauteur - z)+"-"+ i;
				var $grille = $("<div id='"+idGrille+"' style='left:"+left+";top:"+top+";border: solid 1px black;height:"+pasY+"px;width:"+pasX+"px;display:inline-block;position: absolute;'>");
				$grille.css("background-image", "url(image/gazon.jpg)"); 
				$grille.appendTo(".grilleContainer");
			}
		}
	};
	
    
	