package fr.insalyon.dasi.proactif.metier.objetsmetier;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;

@Entity
public class DemandeInterventionLivraison extends DemandeIntervention implements Serializable
{
    private String objet;
    private String entreptrise;

    public DemandeInterventionLivraison(String objet, String entreptrise, Date datePublication, Date dateFin, String commentaire, Integer heureFin, DemandeInterventionStatus status) {
        super(datePublication, dateFin, commentaire, heureFin, status);
        this.objet = objet;
        this.entreptrise = entreptrise;
    }

    public DemandeInterventionLivraison() {}

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public String getEntreptrise() {
        return entreptrise;
    }

    public void setEntreptrise(String entreptrise) {
        this.entreptrise = entreptrise;
    }
}
