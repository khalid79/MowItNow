package fr.xebia.mowitnow.presentation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.gson.Gson;

import fr.xebia.mowitnow.domaine.Position;
import fr.xebia.mowitnow.domaine.TondeuseMediator;

/**
 * Permet de notifier l'ihm de la position des tondeuses.
 * @author kbouaabd
 */
@SuppressWarnings("serial")
public class PushPositionServlet extends HttpServlet {
	
	/**
	 * Récupérer les requests get.
	 * @param req request
	 * @param resp response
	 * @throws IOException IOException
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		// Récupération des instructions
		String[] instructionsTab = req.getParameterValues("instructions[]");
		
		String bigInstruction = formaterInstruction(instructionsTab);
		System.out.println("instcurtions : " +bigInstruction);
		
		TondeuseMediator tondeuseMediator = new TondeuseMediator();
		
		tondeuseMediator.setPushServlet(this);
		try {
			tondeuseMediator.construireInstructions(bigInstruction);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 
	 * @param messageTondeuse
	 */
	public void pushPosition(MessageTondeuse messageTondeuse){
		ChannelService channelService = ChannelServiceFactory.getChannelService();

		// This channelKey needs to be the same as the one in the first section above.
		String channelKey = "kaka";

		// This is what actually sends the message.
		
		// transformation Gson
		Gson gson = new Gson();
		channelService.sendMessage(new ChannelMessage(channelKey, gson.toJson(messageTondeuse)));
	}
	
	
	/**
	 * Construire instruction avec saut de ligne.
	 * TODO: faire un Test unitaire
	 * @param instructionsTab instructionTab
	 * @return instruction sous forme de string
	 */
	private String formaterInstruction(String[] instructionsTab){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < instructionsTab.length; i++) {
			String information = instructionsTab[i];
			if(!information.isEmpty()){
				sb.append(information);
				sb.append("\n");
			}
		}
		return sb.toString();
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
