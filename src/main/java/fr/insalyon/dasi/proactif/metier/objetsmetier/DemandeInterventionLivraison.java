package fr.insalyon.dasi.proactif.metier.objetsmetier;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
public class DemandeInterventionLivraison extends DemandeIntervention implements Serializable {

    private String objet;
    private String entreptrise;

    public DemandeInterventionLivraison(String objet, String entreptrise, String description, Client demandeur) {
        super(description, demandeur);
        this.objet = objet;
        this.entreptrise = entreptrise;
    }

    public DemandeInterventionLivraison() {
    }

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
