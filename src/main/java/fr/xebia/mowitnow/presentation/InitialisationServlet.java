package fr.xebia.mowitnow.presentation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.gson.Gson;
import fr.xebia.mowitnow.autres.IConstantes;

/**
 * Permet de fournir un token pour �tablir la connexion avec l'ihm.
 * @author kbouaabd
 *
 */
@SuppressWarnings("serial")
public class InitialisationServlet extends HttpServlet {
	
	/**
	 * R�cup�rer les requests get.
	 * @param req request
	 * @param resp response
	 * @throws IOException IOException
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// r�cup�rer le pseudo de l'utilisateur
		String pseudo = req.getParameter("pseudo");
		// r�cup�rer la session, cr�er une nouvelle si elle n'existe pas.
        HttpSession session = req.getSession(true);
        session.setAttribute(IConstantes.PSEUDO, pseudo);
		// r�cup�rer une instance du channelService
		ChannelService channelService = ChannelServiceFactory.getChannelService();
		// cr�er le token � partir du pseudo envoyer par l'ihm
		String token = channelService.createChannel(pseudo);
		// envoyer le token g�n�r� dans la r�ponse
		Gson gson = new Gson();
		resp.setContentType("text/json");
		resp.getWriter().write(gson.toJson(token));
	}
	
	/**
	 * R�cup�rer les requests post.
	 * @param req request
	 * @param resp response
	 * @throws ServletException ServletException
	 * @throws IOException IOException
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Invoquer la m�thode doGet avec les param�tres re�u par la m�thode doPost
		doGet(req, resp);
	}
}
