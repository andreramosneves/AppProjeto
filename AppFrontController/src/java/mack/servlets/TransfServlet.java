/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mack.servlets;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

/**
 *
 * @author linolica1
 */
public class TransfServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private void geraXML() throws FileNotFoundException, IOException {
        HttpClient cliente = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://localhost:8081/AppFrontController/LP3Rest/lp3/posicoesXML/denis");
        HttpResponse resposta = cliente.execute(httpget);
        System.out.println("----------------------------------------");
        System.out.println(resposta.getStatusLine());
        HttpEntity entity = resposta.getEntity();
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
            reader.close();
            instream.close();
        }


    }

    private void geraXSL() throws IOException {
        HttpClient cliente = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://localhost:8081/AppFrontController/dados/gpx.xsl");
        HttpResponse resposta = cliente.execute(httpget);
        System.out.println("----------------------------------------");
        System.out.println(resposta.getStatusLine());
        HttpEntity entity = resposta.getEntity();
        if (entity != null) {
            InputStream instream = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            PrintWriter w = new PrintWriter("/temp/gpx.xsl");
            w.print(out.toString());
            w.flush();
            w.close();
            reader.close();
            instream.close();
        }

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, TransformerException {
        geraXSL();
        geraXML();

        Source xmlSource = new StreamSource("/temp/posicao.xml");
        File xslFile = new File("/temp/gpx.xsl");
        TransformerFactory transFact = TransformerFactory.newInstance();
        Transformer trans = transFact.newTransformer(new StreamSource(xslFile));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        trans.transform(xmlSource, new StreamResult(bos));
        PrintWriter w = new PrintWriter("/temp/posicao_proc.gpx", "UTF-8");
        w.print(bos);
        w.close();
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println(new String(bos.toByteArray()));

        }        
//        response.getWriter().print(bos);
//        request.getRequestDispatcher("mapaTeste.html").forward(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);

        } catch (TransformerException ex) {
            Logger.getLogger(TransfServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);

        } catch (TransformerException ex) {
            Logger.getLogger(TransfServlet.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
