package fr.insalyon.dasi.proactif.metier.objetsmetier;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;



@Entity
public class DemandeInterventionIncident extends DemandeIntervention implements Serializable
{

    public DemandeInterventionIncident(Date datePublication, Date dateFin, String commentaire, Integer heureFin, DemandeInterventionStatus status) {
        super(datePublication, dateFin, commentaire, heureFin, status);
    }

    public DemandeInterventionIncident() {
    }
	
    
}
