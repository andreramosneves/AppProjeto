package ejb.beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import ejb.entities.Posicao;
import ejb.entities.PosicaoGPX;
import java.util.ArrayList;

@Stateless
@LocalBean
public class PosicaoBeanGPX  {

    @PersistenceContext(unitName = "DerbyPU")
    private EntityManager em;



    public List<PosicaoGPX> list(String login) {
        Query query = em.createQuery("FROM Posicao p where p.login='" + login + "'");
        List<Posicao> list = query.getResultList();
        List<PosicaoGPX> lista = new ArrayList<>();
        while(list.iterator().hasNext()){
            PosicaoGPX e = new PosicaoGPX();
            Posicao pos = list.iterator().next();
            e.setId(pos.getId());
            e.setLat(pos.getLat());
            e.setLogin(pos.getLogin());
            e.setLon(pos.getLon());
            e.setTimestamp(pos.getTimestamp());
            lista.add(e);
        }
        return lista;
    }
}
