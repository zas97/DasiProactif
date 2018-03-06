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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.persistence.RollbackException;
//import java.util.Date;
//import javax.persistence.EntityManager;

/**
 *
 * @author lsterner
 */
public class ServiceInscription
{    

    public ServiceInscription() {
    }
    
    
    public static void main(String[] args) throws ParseException
    {
        JpaUtil.init();
        
        System.err.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        
        // test de la persistance d'un employe lors d'une inscription
        
        ServiceInscription inscription = new ServiceInscription();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
//        
//        inscription.inscriptionEmploye(new Employe("0123456789", 8,18, "mdp"));
//        
//        // que pour les tests :
//        JpaUtil.creerEntityManager();
//        JpaUtil.ouvrirTransaction();
//
//        for (Employe e : DAOEmploye.getEmployes())
//            System.out.println(e);
//        
//        JpaUtil.validerTransaction();
//        JpaUtil.fermerEntityManager();
//        // fin que pour les tests
        
        try
        {
            inscription.inscriptionClient(new Client("leo", "leo", "Mr", sdf.parse("06/06/1966"), "INSA LYON", "0011001100", "aa@bbbbbbaa", "aaa"));
        }
        catch (EmailDejaUtiliseException e)
        {
            System.err.println(e.getMessage());
        }
        
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