package fr.insalyon.dasi.proactif.metier.objetsmetier;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Employe
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEmploye;
	
    private String numeroTelephone;

    // L'agenda d'un employé est simplifié à une heure de début de service et une heure de fin de service
    // On considère que l'employé travaille toute la semaine (7/7)	
    private Integer heureDebut;
    private Integer heureFin;
    
    private String motDePasse;

    public Employe(String numeroTelephone, Integer heureDebut, Integer heureFin, String motDePasse) {
        this.numeroTelephone = numeroTelephone;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.motDePasse = motDePasse;
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

    @Override
    public String toString() {
        return "Employe{" + "idEmploye=" + idEmploye + ", numeroTelephone=" + numeroTelephone + ", heureDebut=" + heureDebut + ", heureFin=" + heureFin + ", motDePasse=" + motDePasse + '}';
    }
    
    
    
    
    
}


