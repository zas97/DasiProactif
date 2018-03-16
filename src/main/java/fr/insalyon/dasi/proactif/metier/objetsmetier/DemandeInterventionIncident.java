package fr.insalyon.dasi.proactif.metier.objetsmetier;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;

@Entity
public class DemandeInterventionIncident extends DemandeIntervention implements Serializable {

    public DemandeInterventionIncident(String description, Client demandeur) {
        super(description, demandeur);
    }

    public DemandeInterventionIncident() {
    }
    
}
