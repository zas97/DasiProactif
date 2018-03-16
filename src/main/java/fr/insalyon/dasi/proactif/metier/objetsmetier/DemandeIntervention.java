package fr.insalyon.dasi.proactif.metier.objetsmetier;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class DemandeIntervention implements Serializable {

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

    @Temporal(TemporalType.TIMESTAMP)
    protected Date datePublication;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date dateFin;

    protected String description;

    protected String commentaire;


    @Enumerated(EnumType.STRING)
    protected DemandeInterventionStatus status = DemandeInterventionStatus.NON_ASSIGNEE;

    @ManyToOne
    protected Employe charge;

    @ManyToOne
    protected Client demandeur;

    public DemandeIntervention(String description, Client demandeur) {
        this.datePublication = new Date();
        this.dateFin = null;
        this.description = description;
        this.commentaire = null;
        this.status = DemandeInterventionStatus.NON_ASSIGNEE;
        this.charge = null;
        this.demandeur = demandeur;
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

    public DemandeInterventionStatus getStatus() {
        return status;
    }

    public void setStatus(DemandeInterventionStatus status) {
        this.status = status;
    }

    public Employe getCharge() {
        return charge;
    }

    public void setCharge(Employe charge) {
        this.charge = charge;
    }

    public Client getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(Client demandeur) {
        this.demandeur = demandeur;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    

    @Override
    public String toString() {
        return "DemandeIntervention{" + "idDemandeIntervention=" + idDemandeIntervention + ", datePublication=" + datePublication + ", dateFin=" + dateFin + ", description=" + description + ", commentaire=" + commentaire + ", status=" + status + ", charge=" + charge + ", demandeur=" + demandeur + '}';
    }
}