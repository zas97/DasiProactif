package fr.insalyon.dasi.proactif.metier.objetsmetier;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@MappedSuperclass // enables subclasses to be stored in DB
public abstract class DemandeIntervention
{
    
    public enum DemandeInterventionStatus
    {
            NON_ASSIGNEE,
            EN_COURS_DE_TRAITEMENT,
            TERMINEE,
            INCIDENT
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idDemandeIntervention;
	
    @Temporal(TemporalType.DATE)
    protected Date datePublication;

    @Temporal(TemporalType.DATE)
    protected Date dateFin;

    protected String commentaire;
    protected Integer heureFin;

    protected DemandeInterventionStatus status;

    public DemandeIntervention(Date datePublication, Date dateFin, String commentaire, Integer heureFin, DemandeInterventionStatus status) {
        this.datePublication = datePublication;
        this.dateFin = dateFin;
        this.commentaire = commentaire;
        this.heureFin = heureFin;
        this.status = status;
    }

    public DemandeIntervention() {
    }

    public Integer getIdDemandeIntervention() {
        return idDemandeIntervention;
    }

    public void setIdDemandeIntervention(Integer idDemandeIntervention) {
        this.idDemandeIntervention = idDemandeIntervention;
    }

    public Date getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(Date datePublication) {
        this.datePublication = datePublication;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Integer getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(Integer heureFin) {
        this.heureFin = heureFin;
    }

    public DemandeInterventionStatus getStatus() {
        return status;
    }

    public void setStatus(DemandeInterventionStatus status) {
        this.status = status;
    }
}