/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.service;

import fr.insalyon.dasi.proactif.dao.DAOClient;
import fr.insalyon.dasi.proactif.dao.DAOEmploye;
import fr.insalyon.dasi.proactif.dao.JpaUtil;
import fr.insalyon.dasi.proactif.metier.objetsmetier.Employe;
import javax.persistence.EntityManager;

/**
 *
 * @author lsterner
 */
public class ServiceInscription
{    

    public ServiceInscription() {
    }
    
    
    public static void main(String[] args)
    {
        JpaUtil.init();
        
        
        
        // test de la persistance d'un employe lors d'une inscription
        
        ServiceInscription inscription = new ServiceInscription();
        
        inscription.inscriptionEmploye(new Employe("0123456789", 8,18, "mdp"));
        
        // que pour les tests :
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();

        for (Employe e : DAOEmploye.getEmployes())
            System.out.println(e);
        
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        // fin que pour les tests
        
        
        JpaUtil.destroy();
    }
    
    public void inscriptionEmploye(Employe e)
    {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        DAOEmploye.create(e);
        
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
    }
}
