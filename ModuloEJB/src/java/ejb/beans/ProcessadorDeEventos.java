package ejb.beans;


import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import ejb.entities.Log;

@MessageDriven(name = "EventMDB", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue
            = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destination", propertyValue
            = "java:/jms/queue/eventQueue"),
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Autoacknowledge")})
public class ProcessadorDeEventos implements MessageListener {


    @PersistenceContext(unitName = "DerbyPU")
    private EntityManager em;
    private final static Logger LOGGER
            = Logger.getLogger(ProcessadorDeEventos.class.toString());

    public ProcessadorDeEventos() {
    }

    @Override
    public void onMessage(Message message) {
        TextMessage msg = null;
        try {
            if (message instanceof TextMessage) {
                msg = (TextMessage) message;
                Log log = new Log();
                log.setTimestamp(new Timestamp(new Date().getTime()));
                log.setEvento(msg.getText());
                em.persist(log);
                
                System.out.println("Mensagem recebida da fila: " + msg.getText());
            } else {
                System.out.println("Mensagem de tipo não esperado: "
                        + message.getClass().getName());
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
