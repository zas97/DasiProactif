package fr.insalyon.dasi.proactif.metier.objetsmetier;

import java.util.Date;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@MappedSuperclass // enables subclasses to be stored in DB
public abstract class DemandeIntervention
{
    public enum DemandeInterventionStatus // vraiment nécessaire ???
    {
//            NON_ASSIGNEE, // charge = null
//            EN_COURS_DE_TRAITEMENT, // charge != null
            TERMINEE, // OK
            INCIDENT // OK
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer idDemandeIntervention;
	
    @Temporal(TemporalType.DATE)
    protected Date datePublication;

    @Temporal(TemporalType.DATE)
    protected Date dateFin;

    protected String commentaire;

    @Enumerated(EnumType.STRING)
    protected DemandeInterventionStatus status;

    @ManyToOne
    protected Employe charge;
    
    
    @ManyToOne
    protected Client demandeur;

    
    public DemandeIntervention(Date datePublication, Date dateFin, String commentaire, Employe charge, Client demandeur) {
        this.datePublication = datePublication;
        this.dateFin = dateFin;
        this.commentaire = commentaire;
        this.status = null;
        this.charge = charge;
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
    
}

    /* REFERENCE
    
    @Entity
    public class Department {
    @Id
    private long id;
    @OneToOne
    private Employee manager;
    @OneToMany(mappedBy="department")
    private List<Employee> employees;
    ...
    }

    @Entity
    public class Employee {
    @Id
    private long id;
    @ManyToOne
    @JoinColumn(name="DEPT_ID")
    private Department department;
    }
    
    */