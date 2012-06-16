package fr.xebia.mowitnow.presentation;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import fr.xebia.mowitnow.autres.IConstantes;
import fr.xebia.mowitnow.autres.TondeuseException;
import fr.xebia.mowitnow.domaine.TondeuseFactory;
import fr.xebia.mowitnow.domaine.TondeuseMediator;

/**
 * Permet de notifier l'ihm de la position des tondeuses.
 * @author kbouaabd
 */
@SuppressWarnings("serial")
public class PushPositionServlet extends HttpServlet implements Observer {
	
	/**
	 * Logger.
	 */
	private static final Logger log = Logger.getLogger(PushPositionServlet.class.getName());
	
	/**
	 * Récupérer les requests get.
	 * @param req request
	 * @param resp response
	 * @throws IOException IOException
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// récupérer le token à partir de la session
		String pseudo = (String) req.getSession().getAttribute(IConstantes.PSEUDO);
		// Récupération des instructions
		String instructions = req.getParameter("instructions");
		try{
			if(!instructions.isEmpty()){
				// intialisation des tondeuses
				TondeuseFactory.initialisationTondeuses(instructions,pseudo, this);
			}else{
				pushErreur(pseudo, "Veuillez saisir les instructions.");
			}
		}catch (TondeuseException tondeuseException) {
			String messageErreur = tondeuseException.getMessage();
			log.severe(messageErreur);
			pushErreur(pseudo, messageErreur);
		}
	}
	
	/**
	 * Récupérer les requests post.
	 * @param req request
	 * @param resp response
	 * @throws ServletException ServletException
	 * @throws IOException IOException
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Invoquer la méthode doGet avec les paramètres reçu par la méthode doPost
		doGet(req, resp);
	}
	
	
	/**
	 * Permet de récupérer toutes les changements notifier par TondeuseMediator 'Observable'.
	 * @param observable TondeuseMediator 
	 * @param message message envoyer par TondeuseMediator
	 */
	@Override
	public void update(Observable observable, Object message) {
		TondeuseMediator mediator = (TondeuseMediator) observable;
		pushPosition(mediator.getPseudo(),(MessageTondeuse) message);
		// TODO : temporaire à externaliser 
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Permet d'envoyer les messages vers l'ihm.
	 * @param messageTondeuse messageTondeuse à envoyer
	 * @param pseudo permet de pousser le message vers le bon consomateur 
	 */
	private void pushPosition(String pseudo,MessageTondeuse messageTondeuse){
		// récupérer une instance du channelService
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		// transformation Gson
		Gson gson = new Gson();
		channelService.sendMessage(new ChannelMessage(pseudo, gson.toJson(messageTondeuse)));
	}
	
	/**
	 * Permet d'envoyer les messages d'erreurs vers l'ihm.
	 * @param messageErreur messageErreur
	 * @param pseudo permet de pousser le message vers le bon consomateur 
	 */
	private void pushErreur(String pseudo,String messageErreur){
		// récupérer une instance du channelService
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		// transformation Gson
		Gson gson = new Gson();
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("Erreur", messageErreur);
		channelService.sendMessage(new ChannelMessage(pseudo, gson.toJson(jsonObject)));
	}
	
}
