package contatos.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/novoUsuario")
public class AddUsuarioServlet extends HttpServlet
{
	private static final long	serialVersionUID = 1L;
	Mensagens men = new Mensagens();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{	
		//recupero os dados do navegador
		String loginUsuario = req.getParameter("user");
		String senhaUsuario = req.getParameter("pass");
		
		if( loginUsuario == "" || senhaUsuario == "" )
		{
			men.mensagemLoginInvalido();
		}else{
			salvaUsuario( loginUsuario, senhaUsuario );
			req.getRequestDispatcher("/index.html").forward(req, resp);
		}		
	}	
	
	private void salvaUsuario( String login, String senha ) throws IOException
	{
		Path path = Paths.get( "D:\\Faculdade\\Desenvolvimento\\Usuario\\src\\users.txt" );
		String conteudo = login+";"+senha+"\n";
		Files.write( path, conteudo.getBytes(), StandardOpenOption.APPEND );
	}
}
