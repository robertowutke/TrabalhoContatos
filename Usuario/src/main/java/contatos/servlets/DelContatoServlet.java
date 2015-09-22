package contatos.servlets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/excluiContato")
public class DelContatoServlet extends HttpServlet
{
	private static final long	serialVersionUID = 1L;
	Mensagens men = new Mensagens();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{	
		PrintWriter writer = resp.getWriter();
		//recupero os dados do navegador
		String[] id = req.getParameterValues("contato");
		
		//valido se a senha ou login estão preenchidos
		if( id == null )
		{
			writer.write(men.mostraContatos( (String)req.getSession().getAttribute("usuario") ));
		}else{					    
			excluirContatos(id);
			writer.write(men.mostraContatos( (String)req.getSession().getAttribute("usuario") ));
		}
	}
	
	private void excluirContatos( String[] id ) throws IOException
	{
		File entrada = new File("D:\\Faculdade\\Desenvolvimento\\Usuario\\src\\contatos.txt");//pego o caminho do txt
		FileReader entradaFormatada = new FileReader(entrada);//leio o txt
		BufferedReader entradaString = new BufferedReader(entradaFormatada);//entro no txt
		
		int i = 0;
		
		String linha = entradaString.readLine();
		ArrayList<String> arrayContatos = new ArrayList<String>();
		
		while(linha != null)
		{
			if( i < id.length && linha.contains( "id="+id[i]) ) // se a minha linha do txt conter a string id=(algum numero) ele ignora a linha pegando a proxima
			{	
				linha = entradaString.readLine();
				i++;
			}else{
				arrayContatos.add(linha); // se nao contem id= com o numero que Ã© passado pelo html ele salva a linha em um array
				linha = entradaString.readLine();
			}
		}
		
		entradaFormatada.close(); // fecho o file reader
		entradaString.close(); // fecho o buffered reader
		FileWriter fw = new FileWriter(entrada, true);// seleciono o txt para escrita e passo o true para apagar tudo que tem dentro dele
		fw.close(); // fecho file writer
		
		FileWriter fw2 = new FileWriter(entrada);
		BufferedWriter bf = new BufferedWriter(fw2);
		
		for ( i = 0; i < arrayContatos.size(); i++ ) 
		{
			bf.write( arrayContatos.get(i) );
			bf.newLine();
		}
		
		bf.close();
		fw2.close();
	}
}
