/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.service;

import fr.insalyon.dasi.proactif.dao.DAOClient;
import fr.insalyon.dasi.proactif.dao.DAOEmploye;
import fr.insalyon.dasi.proactif.dao.JpaUtil;
import fr.insalyon.dasi.proactif.metier.objetsmetier.Client;
import fr.insalyon.dasi.proactif.metier.objetsmetier.Employe;
import javax.persistence.RollbackException;


/**
 *
 * @author lsterner
 */
public class ServiceInscription
{    

    public ServiceInscription() {}
    
    
    public void inscriptionEmploye(Employe e)
    {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        DAOEmploye.create(e);
        
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
    }
    
    
    public class EmailDejaUtiliseException extends Exception
    {
        public EmailDejaUtiliseException(String mail)
        {
            super("Le mail " + mail + " est déjà utilisé");
        }
    }
    
    public void inscriptionClient(Client c) throws EmailDejaUtiliseException
    {
        try
        {
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
        
            DAOClient.create(c);

            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
        }
        catch (RollbackException e)
        {
            JpaUtil.annulerTransaction();
            JpaUtil.fermerEntityManager();       
            throw new EmailDejaUtiliseException(c.getMail());
        }
    }
}