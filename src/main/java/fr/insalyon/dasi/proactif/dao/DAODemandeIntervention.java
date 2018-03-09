/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.dao;


import fr.insalyon.dasi.proactif.metier.objetsmetier.Client;
import fr.insalyon.dasi.proactif.metier.objetsmetier.DemandeIntervention;
import fr.insalyon.dasi.proactif.metier.objetsmetier.Employe;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author lsterner
 */
public abstract class DAODemandeIntervention
{
    public static void create(DemandeIntervention demandeIntervention)
    {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(demandeIntervention);
    }
    
    public static void updateAssignee(DemandeIntervention demandeIntervention, Employe assignee)
    {
        demandeIntervention.setCharge(assignee);
        
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.merge(demandeIntervention);
    }
    
    public static List<DemandeIntervention> getDemandesOfClient(Client c)
    {
        List<DemandeIntervention> demandeInterventions = new ArrayList<>();
        
        EntityManager em = JpaUtil.obtenirEntityManager();
        
        demandeInterventions.addAll(em.createQuery("SELECT dia FROM DemandeInterventionAnimal dia WHERE dia.demandeur = :c").setParameter("c", c).getResultList());
        demandeInterventions.addAll(em.createQuery("SELECT dii FROM DemandeInterventionIncident dii WHERE dii.demandeur = :c").setParameter("c", c).getResultList());
        demandeInterventions.addAll(em.createQuery("SELECT dil FROM DemandeInterventionLivraison dil WHERE dil.demandeur = :c").setParameter("c", c).getResultList());
        
        return demandeInterventions;
    }
    
    public static List<DemandeIntervention> getDemandesAssignedToEmployee(Employe e)
    {
        List<DemandeIntervention> demandeInterventions = new ArrayList<>();
        
        EntityManager em = JpaUtil.obtenirEntityManager();
        
        demandeInterventions.addAll(em.createQuery("SELECT dia FROM DemandeInterventionAnimal dia WHERE dia.charge = :e").setParameter("e", e).getResultList());
        demandeInterventions.addAll(em.createQuery("SELECT dii FROM DemandeInterventionIncident dii WHERE dii.charge = :e").setParameter("e", e).getResultList());
        demandeInterventions.addAll(em.createQuery("SELECT dil FROM DemandeInterventionLivraison dil WHERE dil.charge = :e").setParameter("e", e).getResultList());
        
        return demandeInterventions;
    }
    
    public static List<DemandeIntervention> getUnnasignedDemandes()
    {
        List<DemandeIntervention> demandeInterventions = new ArrayList<>();
        
        EntityManager em = JpaUtil.obtenirEntityManager();
        
        demandeInterventions.addAll(em.createQuery("SELECT dia FROM DemandeInterventionAnimal dia WHERE dia.charge = null").getResultList());
        demandeInterventions.addAll(em.createQuery("SELECT dii FROM DemandeInterventionIncident dii WHERE dii.charge = null").getResultList());
        demandeInterventions.addAll(em.createQuery("SELECT dil FROM DemandeInterventionLivraison dil WHERE dil.charge = null").getResultList());
        
        return demandeInterventions;
    }
    
    
    
}
