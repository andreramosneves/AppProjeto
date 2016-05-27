package ejb.entities;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
public class PosicaoGPX {

    private int id;
    @Column(name = "login")
    private String login;
    @Column(name = "lat")
    private String lat;
    @Column(name = "timestamp")
    protected Timestamp timestamp;
    @Column(name = "long")
    private String lon;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    @XmlJavaTypeAdapter(TimestampAdapter.class)
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
