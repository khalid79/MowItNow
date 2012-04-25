package fr.xebia.mowitnow.presentation;

import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

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

		ChannelService channelService = ChannelServiceFactory.getChannelService();

		String token = channelService.createChannel(IConstantes.CLIENT_ID);

		FileReader reader = new FileReader("html/accueil.html");
		CharBuffer buffer = CharBuffer.allocate(16384);
		reader.read(buffer);
		String index = new String(buffer.array());
		index = index.replaceAll("\\{\\{ token \\}\\}", token);

		resp.setContentType("text/html; charset=iso-8859-1");
		resp.getWriter().write(index);
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
