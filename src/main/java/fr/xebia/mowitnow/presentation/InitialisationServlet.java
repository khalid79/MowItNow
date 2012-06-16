package fr.xebia.mowitnow.presentation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.gson.Gson;
import fr.xebia.mowitnow.autres.IConstantes;

/**
 * Permet de fournir un token pour établir la connexion avec l'ihm.
 * @author kbouaabd
 *
 */
@SuppressWarnings("serial")
public class InitialisationServlet extends HttpServlet {
	
	/**
	 * Récupérer les requests get.
	 * @param req request
	 * @param resp response
	 * @throws IOException IOException
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// récupérer le pseudo de l'utilisateur
		String pseudo = req.getParameter("pseudo");
		// récupérer la session, créer une nouvelle si elle n'existe pas.
        HttpSession session = req.getSession(true);
        session.setAttribute(IConstantes.PSEUDO, pseudo);
		// récupérer une instance du channelService
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		// créer le token à partir du pseudo envoyer par l'ihm
		String token = channelService.createChannel(pseudo);
		// envoyer le token généré dans la réponse
		Gson gson = new Gson();
		resp.setContentType("text/json");
		resp.getWriter().write(gson.toJson(token));
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
}
