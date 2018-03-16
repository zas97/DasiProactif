package fr.insalyon.dasi.proactif.metier.objetsmetier;

import fr.insalyon.dasi.proactif.util.GoogleMapUtil;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import com.google.maps.model.LatLng;
import java.io.Serializable;
import javax.persistence.TemporalType;

/**
 *
 * @author lsterner
 */
@Entity
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idClient;

    private String nom;
    private String prenom;
    private String civilite;

    @Temporal(TemporalType.DATE)
    private Date dateNaissance;

    private String adressePostale;

    private String numeroTelephone;

    @Column(unique = true)
    private String mail;

    private String motDePasse;

    private LatLng position;

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getCivilite() {
        return civilite;
    }

    public void setCivilite(String civilite) {
        this.civilite = civilite;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getAdressePostale() {
        return adressePostale;
    }

    public void setAdressePostale(String adressePostale) {
        this.adressePostale = adressePostale;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    
    public Client(String nom, String prenom, String civilite, Date dateNaissance, String adressePostale, String numeroTelephone, String mail, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.civilite = civilite;
        this.dateNaissance = dateNaissance;
        this.adressePostale = adressePostale;
        this.numeroTelephone = numeroTelephone;
        this.mail = mail;
        this.motDePasse = motDePasse;

        this.position = GoogleMapUtil.getLatLng(adressePostale);
        if (position == null)
            throw new RuntimeException("Adress doesn't exists !");
    }

    public Client() {
    }

    @Override
    public String toString() {
        return "Client{" + "idClient=" + idClient + ", nom=" + nom + ", prenom=" + prenom + ", civilite=" + civilite + ", dateNaissance=" + dateNaissance + ", adressePostale=" + adressePostale + ", numeroTelephone=" + numeroTelephone + ", mail=" + mail + ", motDePasse=" + motDePasse + ", position=" + position + '}';
    }

    
    
}
