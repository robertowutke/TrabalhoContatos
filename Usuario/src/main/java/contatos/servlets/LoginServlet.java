package contatos.servlets;

import java.io.*;
import contatos.servlets.Mensagens;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet 
{
	private static final long	serialVersionUID = 1L;
	Mensagens mem = new Mensagens();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{	
		PrintWriter writer = resp.getWriter();
		//recupero os dados do navegador
		String nomeLogin = req.getParameter("user");
		String senhaLogin = req.getParameter("pass");
		
		//valido se a senha ou login estão preenchidos
		if( nomeLogin == "" || senhaLogin == "" )
		{
			writer.write(mem.mensagemLoginInvalido());			
		}else{		
			//comparo se tenho o nomeLogin cadastrado		
			FileInputStream entrada = new FileInputStream("D:\\Faculdade\\Desenvolvimento\\Usuario\\src\\users.txt");
			InputStreamReader entradaFormatada = new InputStreamReader(entrada);
			BufferedReader entradaString = new BufferedReader(entradaFormatada);
		    
			boolean bool = false;
			String linha = entradaString.readLine();
			
			while(linha != null) {
				if( linha.contains(nomeLogin) )
				{
					if( linha.contains(senhaLogin) )
					{
						HttpSession session = req.getSession();
						
						session.setAttribute("usuario", nomeLogin);
						writer.write(mem.mostraContatos( nomeLogin ));
						entrada.close();
						bool = true;
						break;
					}
				writer.write(mem.mensagemLoginInvalido());
				bool = true;
				break;
				}
				linha = entradaString.readLine();	
			}
			
			
			if( !bool )
				writer.write(mem.mensagemLoginInvalido());
		}
	}
}