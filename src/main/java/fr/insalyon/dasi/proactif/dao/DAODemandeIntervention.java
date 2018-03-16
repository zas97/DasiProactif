/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.dao;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import fr.insalyon.dasi.proactif.metier.objetsmetier.Client;
import fr.insalyon.dasi.proactif.metier.objetsmetier.DemandeIntervention;
import fr.insalyon.dasi.proactif.metier.objetsmetier.Employe;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import javax.persistence.TemporalType;
/**
 *
 * @author lsterner
 */
public abstract class DAODemandeIntervention {

    public static void create(DemandeIntervention demandeIntervention) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(demandeIntervention);
    }
    
    
    public static void update(DemandeIntervention demandeIntervention)
    {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.merge(demandeIntervention);
    }

    public static List<DemandeIntervention> getDemandesOfClient(Client c) {
        List<DemandeIntervention> demandeInterventions = new ArrayList<>();

        EntityManager em = JpaUtil.obtenirEntityManager();

        demandeInterventions.addAll(em.createQuery("SELECT di FROM DemandeIntervention di WHERE di.demandeur = :c").setParameter("c", c).getResultList());

        return demandeInterventions;
    }

        // TODO not needed
//    public static List<DemandeIntervention> getDemandesAssignedToEmployee(Employe e) {
//        List<DemandeIntervention> demandeInterventions = new ArrayList<>();
//
//        EntityManager em = JpaUtil.obtenirEntityManager();
//
//
//        demandeInterventions.addAll(em.createQuery("SELECT di FROM DemandeIntervention di WHERE di.charge = :e").setParameter("e", e).getResultList());
//
//        return demandeInterventions;
//    }
    
    public static DemandeIntervention getCurrentDemandeOfEmployee(Employe e)
    {        
        List<DemandeIntervention> demandeInterventions = new ArrayList<>();

        EntityManager em = JpaUtil.obtenirEntityManager();
        
        demandeInterventions.addAll(em.createQuery("SELECT di FROM DemandeIntervention di WHERE di.charge = :e and di.status = :status").setParameter("e", e).setParameter("status", DemandeIntervention.DemandeInterventionStatus.EN_COURS_DE_TRAITEMENT).getResultList());
        
        if (demandeInterventions.isEmpty())
            return null;

        return demandeInterventions.get(0);
    }


    public static List<DemandeIntervention> getDemandesDuJour()
    {
        List<DemandeIntervention> demandeInterventions = new ArrayList<>();

        EntityManager em = JpaUtil.obtenirEntityManager();

        Calendar c = new GregorianCalendar();
        
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        Date sod = c.getTime();
        
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        Date eod = c.getTime();
        
        // TODO : between ?
        demandeInterventions.addAll(em.createQuery("SELECT di FROM DemandeIntervention di WHERE di.datePublication > :sod AND di.datePublication < :eod")
                .setParameter("sod", sod, TemporalType.TIMESTAMP)
                .setParameter("eod", eod, TemporalType.TIMESTAMP)
                .getResultList());
        
        return demandeInterventions;
    }
}