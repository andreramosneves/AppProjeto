package ejb.beans;

import ejb.interceptor.LogInterceptor;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import ejb.entities.Usuario;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

@Stateless
@LocalBean
public class UsuarioBean {
//    private final static String apikey = "8a362aa3872e40564830a7564af63707";
//    private static String privatekey = "cf64ae1ac91ecb74a75f6ee47571d6d244409d6c";

    private final static String apikey =   "f33eb15f5b1bf8d7da85f6d1d206f83c";
    private static String privatekey =   "c2a9c817c3b293d067a7f59406abde0b0a1e3e78";
    private static String urlbase = "http://gateway.marvel.com/v1/public/characters";

    @PersistenceContext(unitName = "DerbyPU")
    private EntityManager em;

    public void save(Usuario u) {

        em.persist(u);
    }

    public List<Usuario> list() {
        Query query = em.createQuery("FROM Usuario u");
        List<Usuario> list = query.getResultList();
        return list;
    }
    public static String MD5(String md5) {
        try {
           java.security.MessageDigest md =      java.security.MessageDigest.getInstance("MD5");
           byte[] array = md.digest(md5.getBytes());
           StringBuilder sb = new StringBuilder();
           for (int i = 0; i < array.length; ++i) {
               sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
           }
           return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
    public boolean buscaPorNomeSenha(final String nome, String senha) {
        try {
            Query query = em.createQuery("FROM Usuario u where u.nome = :username");
            query.setParameter("username", nome);
            return Hash.validaSenha(senha, ((Usuario) query.getResultList().get(0)).getSenha());
        } catch (IndexOutOfBoundsException ex) {
            return false;
        } catch (Exception ecc) {
            ecc.printStackTrace();
            return false;
        }
    }

    public void acessaAPIMarvel(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyyhhmmss");
        String ts = sdf.format(date);

        //Criação do HASH
        String hashStr = MD5( ts+privatekey+apikey );
        String uri;
        String name="Captain%20America";
        //url de consulta
        uri = urlbase + "?nameStartsWith=" + name + "&ts=" + ts + "&apikey=" + apikey + "&hash=" + hashStr;
        System.out.println(uri);
        try{
        HttpClient cliente = HttpClients.createDefault();

//        HttpHost proxy = new HttpHost("localhost", 8080, "http");
//        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
        HttpGet httpget = new HttpGet(uri);
//        httpget.setConfig(config);
        HttpResponse response = cliente.execute(httpget);
        System.out.println("----------------------------------------");
        System.out.println(response.getStatusLine());
        Header[] h = response.getAllHeaders();

        for (Header head:h){
            System.out.println(head.getValue());
        }

        HttpEntity entity = response.getEntity();

        if (entity != null) {
            InputStream instream = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            System.out.println(out.toString());
            reader.close();
            instream.close();
        }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    @Interceptors(LogInterceptor.class)
    public void sucesso() {

    }

    @Interceptors(LogInterceptor.class)
    public void falha() {

    }
}
