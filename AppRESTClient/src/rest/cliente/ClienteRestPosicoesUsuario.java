package rest.cliente;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

public class ClienteRestPosicoesUsuario {

    public static void main(String[] args) throws IOException, TransformerException {
        HttpClient cliente = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://localhost:8081/AppFrontController/LP3Rest/lp3/posicoesXML/denis");
        HttpResponse response = cliente.execute(httpget);
        System.out.println("----------------------------------------");
        System.out.println(response.getStatusLine());
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream instream = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            PrintWriter w = new PrintWriter("/temp/posicao.xml");
            w.print(out.toString());
            w.flush();
            w.close();
            Source xmlSource = new StreamSource("/temp/posicao.xml");
            File xslFile = new File("C:/Users/linolica1/Documents/NetBeansProjects/AppProjeto/App/Dados/gpx.xsl");
            TransformerFactory transFact = TransformerFactory.newInstance();
            Transformer trans = transFact.newTransformer(new StreamSource(xslFile));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            trans.transform(xmlSource, new StreamResult(bos));
            PrintWriter writer = new PrintWriter("C:/Users/linolica1/Documents/NetBeansProjects/AppProjeto/App/Dados/posicao_proc.gpx", "UTF-8");
            writer.print(bos);
            String s = new String(bos.toByteArray());
            System.out.println(s);
            writer.close();
            reader.close();
            instream.close();
            reader.close();
            instream.close();
        }
    }
}
