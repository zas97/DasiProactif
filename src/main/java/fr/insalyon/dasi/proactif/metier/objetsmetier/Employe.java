package fr.insalyon.dasi.proactif.metier.objetsmetier;

import com.google.maps.model.LatLng;
import fr.insalyon.dasi.proactif.util.GoogleMapUtil;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmploye;

    private String numeroTelephone;

    // L'agenda d'un employé est simplifié à une heure de début de service et une heure de fin de service
    // On considère que l'employé travaille toute la semaine (7/7)	
    private Integer heureDebut;
    private Integer heureFin;

    private String motDePasse;

    private String adresseDomicile;

    @Column(unique = true)
    private String mail;

    private LatLng position;

    @Version
    private Long version;
    
    
    private boolean disponible = true;
    
    public Employe(String numeroTelephone, Integer heureDebut, Integer heureFin, String motDePasse, String adresseDomicile, String mail) {
        this.numeroTelephone = numeroTelephone;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.motDePasse = motDePasse;
        this.adresseDomicile = adresseDomicile;
        this.mail = mail;

        this.position = GoogleMapUtil.getLatLng(adresseDomicile);
        if (position == null)
            throw new RuntimeException("Adress doesn't exists !");

    }

    public Employe() {
    }

    public Integer getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(Integer idEmploye) {
        this.idEmploye = idEmploye;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public Integer getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Integer heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Integer getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(Integer heureFin) {
        this.heureFin = heureFin;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getAdresseDomicile() {
        return adresseDomicile;
    }

    public void setAdresseDomicile(String adresseDomicile) {
        this.adresseDomicile = adresseDomicile;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    
    
    
    
    
    @Override
    public String toString() {
        return "Employe [idEmploye=" + idEmploye + ", numeroTelephone=" + numeroTelephone + ", heureDebut=" + heureDebut + ", heureFin=" + heureFin + ", motDePasse=" + motDePasse + ", adresseDomicile=" + adresseDomicile + "]";
    }
}
