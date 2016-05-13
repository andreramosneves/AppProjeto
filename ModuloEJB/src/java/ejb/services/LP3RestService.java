package ejb.services;


import ejb.beans.PosicaoBean;
import ejb.beans.UsuarioBean;
import java.io.StringReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import shared.entities.Posicao;
import shared.entities.Usuario;

@Stateless
@LocalBean
@Path("/lp3/")
public class LP3RestService {

    @EJB
    UsuarioBean usuarioBean;
    @EJB
    PosicaoBean posicaoBean;

    @Path("/novousuario")
    @PUT
    @Consumes(MediaType.TEXT_XML)
    public void novoUsuario(String usuarioXml) {
        System.out.println(usuarioXml);
        JAXBContext jc;
        try {
            jc = JAXBContext.newInstance(Usuario.class);
            Unmarshaller u = jc.createUnmarshaller();
            StringReader reader = new StringReader(usuarioXml);
            Usuario usuario = (Usuario) u.unmarshal(reader);
            usuarioBean.save(usuario);
        } catch (JAXBException ex) {
            Logger.getLogger(LP3RestService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Path("/novaposicao")
    @PUT
    @Consumes(MediaType.TEXT_XML)
    public void novaPosicao(String posicaoXml) {
        System.out.println(posicaoXml);
        JAXBContext jc;
        try {
            jc = JAXBContext.newInstance(Posicao.class);
            Unmarshaller u = jc.createUnmarshaller();
            StringReader reader = new StringReader(posicaoXml);
            Posicao posicao = (Posicao) u.unmarshal(reader);
            posicaoBean.save(posicao);
        } catch (JAXBException ex) {
            Logger.getLogger(LP3RestService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @GET
    @Path("/posicoes/{login}")
    @Produces({"application/xml"})
    public List<Posicao> listaPosicoes(@PathParam("login") final String login) {
        return posicaoBean.list(login);
    }
}
