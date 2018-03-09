package fr.insalyon.dasi.proactif.metier.objetsmetier;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;



@Entity
public class DemandeInterventionIncident extends DemandeIntervention implements Serializable
{

    public DemandeInterventionIncident(Date datePublication, Date dateFin, String commentaire, Employe charge, Client demandeur) {
        super(datePublication, dateFin, commentaire, charge, demandeur);
    }


    public DemandeInterventionIncident() {
    }
	
    
}
