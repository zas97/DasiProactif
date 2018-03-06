package fr.insalyon.dasi.proactif.metier.objetsmetier;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lsterner
 */
@Entity
public class Client
{
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
    
    private Integer latitude;
    private Integer longitude;
    
    @Column(unique=true)
    private String mail;
    
    private String motDePasse;

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

    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
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

	// we do not set id ourselves
    public Client(String nom, String prenom, String civilite, Date dateNaissance, String adressePostale, String numeroTelephone, String mail, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.civilite = civilite;
        this.dateNaissance = dateNaissance;
        this.adressePostale = adressePostale;
        this.numeroTelephone = numeroTelephone;
        this.mail = mail;
        this.motDePasse = motDePasse;
    }
    
    public Client()
    {}
    
}
