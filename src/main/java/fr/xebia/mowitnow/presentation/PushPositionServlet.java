package fr.xebia.mowitnow.presentation;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.gson.Gson;

import fr.xebia.mowitnow.autres.IConstantes;
import fr.xebia.mowitnow.domaine.TondeuseFactory;

/**
 * Permet de notifier l'ihm de la position des tondeuses.
 * @author kbouaabd
 */
@SuppressWarnings("serial")
public class PushPositionServlet extends HttpServlet implements Observer {
	
	/**
	 * Récupérer les requests get.
	 * @param req request
	 * @param resp response
	 * @throws IOException IOException
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// Récupération des instructions
		String[] tableauInstructions = req.getParameterValues("instructions[]");
		// intialisation des tondeuses
		TondeuseFactory.initialisationTondeuses(tableauInstructions,this);
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
	
	
	@Override
	public void update(Observable o, Object arg) {
		pushPosition((MessageTondeuse) arg);
	}
	
	
	/**
	 * 
	 * @param messageTondeuse 
	 */
	private void pushPosition(MessageTondeuse messageTondeuse){
		ChannelService channelService = ChannelServiceFactory.getChannelService();

		// This channelKey needs to be the same as the one in the first section above.
		String channelKey = IConstantes.CLIENT_ID;

		// transformation Gson
		Gson gson = new Gson();
		channelService.sendMessage(new ChannelMessage(channelKey, gson.toJson(messageTondeuse)));
	}
	
}
