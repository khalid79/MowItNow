/*********** fonctions js du projet mowitnow **********/
	
	$(document).ready(function(){
		// placer le listener sur l'envois des instructions
		envoyerCommandes();
		// permet de r�cup�rer le token � partir du pseudo envoy� au service
		presentationForm();
		// d�sactiver le submit du form avec le bouton entrer
		desactiverSubmitConnexion();
	});
	
	/**
	 * d�sactiver le submit du form avec le bouton entrer
	 */
	function desactiverSubmitConnexion(){
		$("#connexionform").keypress(function(e) {
			if ( e.which == 13 ) e.preventDefault();
		});
	}
	
	/**
	 * Affiche la fenetre modale qui contient le formulaire
	 * R�cup�re le token de connection si le pseudo est bien saisi
	 */
	function presentationForm(){
		// cach� les �l�ments
		var $modal = $("#fenetreConnexion");
		var $pseudoInput = $("#pseudoInput");
		// afficher la modale qui contient le form
		$modal.modal({backdrop: 'static' ,  keyboard: false, show:true});
		$("#envoyerPseudo").click(function(){
			var pseudo = $pseudoInput.val();
			if(pseudo != ""){
				if(pseudo.indexOf(' ') == -1){
				// cach� la fenetre de pr�sentation
				$modal.modal("hide");
				$.ajax({
					url: "/init",
					type: "POST",
					data: {"pseudo" : pseudo},
					dataType: "json",
					success: function(token) {
						// r�cup�rer le token g�n�rer par le service
						initChannel(token);
					}
				});
				// afficher le pseudo
				$("#pseudo").text(pseudo);
				}else{
					erreurPseudo("Le pseudo ne doit pas contenir d'espace.");
				}
			}else{
				erreurPseudo("Veuillez saisir un pseudo.");
			}
		});
		
		/*
		 * Pour afficher les messages d'erreurs du form
		 */
		function erreurPseudo(texteErreur){
			// supprimer les erreurs d�ja pr�sente
			var $helpInline = $(".help-inline");
			// si un message est pr�sent
			if( $helpInline.length > 0 ){
				// le supprimer
				$helpInline.remove();
			}
			$(".control-group").addClass("error");
			$(".controls").append('<span class="help-inline">'+texteErreur+'</span>');
		}
	}
	
	/**
	 * Cr�ation et ouverture du channel
	 */
	function initChannel(token){
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
		var message = jQuery.parseJSON(msg.data);
		// si message d'erreur
		if(message.hasOwnProperty("Erreur")) {
			var $zoneErreur = $('#zoneErreur');
			// vider la zone d'erreur
			$zoneErreur.empty();
			var template = $('#erreur').html();
			var output = Mustache.render(template, {messageErreur: message.Erreur});
			$zoneErreur.append(output);
		}else{
			// parser le r�sultat envoyer en json
			var messageTondeuse = message;
		    console.log("messagePush : "+ messageTondeuse.degre);
		    var idGrille = "grile-"+messageTondeuse.position.y+"-"+messageTondeuse.position.x;
		    // r�cup�re la grille qui repr�sente la position de la tondeuse
		    var $grille = $("#"+idGrille);
		    $(".tondue"+messageTondeuse.idTondeuse).css("background-image", "");
		    $(".tondue"+messageTondeuse.idTondeuse).css("background-color", "green"); 
	    	$grille.css("background-image", "url(image/tondeuse.jpg)").css("background-size","100%");
	    	// g�rer la rotation
	    	messageTondeuse.degre = messageTondeuse.degre - 90;
	    	$grille.css('-webkit-transform', 'rotate('+messageTondeuse.degre+'deg)');
	    	$grille.css('-moz-transform', 'rotate('+messageTondeuse.degre+'deg)');
	    	$grille.attr("class","tondue"+messageTondeuse.idTondeuse);
		}
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
			var $zoneErreur = $('#zoneErreur');
			// vider la zone d'erreur
			$zoneErreur.empty();
			// Envoyer toutes les instructions vers le service
			$.ajax({
				url: "/pushPosition",
				type: "POST",
				data: {instructions : entreeInstruction}
			});
			// si le format des commandes est bon
			construirePelouse(pelouse);
		});
	}
	
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
		var pasX = 50;
		var pasY = 50;
		var left = 0;
		var top  = 0;
		for ( z = 0; z <= pelouse.hauteur; z++ ) {
			top = z * pasX; 
			for ( i=0 ; i <= pelouse.largeur; i++) {
				left = i * pasY;
				var idGrille = "grile-"+(pelouse.hauteur - z)+"-"+ i;
				var $grille = $("<div id='"+idGrille+"' style='left:"+left+"px;top:"+top+"px;border: solid 1px black;height:"+pasY+"px;width:"+pasX+"px;display:inline-block;position: absolute;'>");
				$grille.css("background-image", "url(image/gazon.jpg)"); 
				//$grille.appendTo(".grilleContainer");
				$(".grilleContainer").append($grille.show("slow"));
			}
		}
	};
	
    
	