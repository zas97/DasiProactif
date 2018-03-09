/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.service;

import fr.insalyon.dasi.proactif.dao.DAOClient;
import fr.insalyon.dasi.proactif.dao.DAODemandeIntervention;
import fr.insalyon.dasi.proactif.dao.JpaUtil;
import fr.insalyon.dasi.proactif.metier.objetsmetier.Client;
import fr.insalyon.dasi.proactif.metier.objetsmetier.DemandeIntervention;
import fr.insalyon.dasi.proactif.metier.objetsmetier.Employe;
import java.util.List;

/**
 *
 * @author lsterner
 */
public class ServiceDemandeIntervention
{
    public void creerDemande(DemandeIntervention demandeIntervention)
    {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();

        DAODemandeIntervention.create(demandeIntervention);

        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
    }
    
    public void assignerDemande(DemandeIntervention demandeIntervention, Employe assignee)
    {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        DAODemandeIntervention.updateAssignee(demandeIntervention, assignee);
        
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
    }
    
    
    
    
    public List<DemandeIntervention> recupererDemandesAssigneesAEmploye(Employe e)
    {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        List<DemandeIntervention> demandes = DAODemandeIntervention.getDemandesAssignedToEmployee(e);
        
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        
        return demandes;
    }   
    
    public List<DemandeIntervention> recupererDemandesDuClient(Client c)
    {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        List<DemandeIntervention> demandes = DAODemandeIntervention.getDemandesOfClient(c);
        
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();

        return demandes;
    }
    
    public List<DemandeIntervention> recupererDemandesNonAssignees()
    {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        List<DemandeIntervention> demandes = DAODemandeIntervention.getUnnasignedDemandes();
        
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();

        return demandes;
    }

}
