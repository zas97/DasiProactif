package fr.insalyon.dasi.proactif.metier.objetsmetier;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;


@Entity
public class DemandeInterventionAnimal extends DemandeIntervention implements Serializable
{
    private String animal;

    public DemandeInterventionAnimal(String animal, Date datePublication, Date dateFin, String commentaire, Integer heureFin, DemandeInterventionStatus status) {
        super(datePublication, dateFin, commentaire, heureFin, status);
        this.animal = animal;
    }

    public DemandeInterventionAnimal() {
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }
        
        
}
