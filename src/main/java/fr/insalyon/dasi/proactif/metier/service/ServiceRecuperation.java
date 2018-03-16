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
import java.util.List;

/**
 *
 * @author lsterner
 */
public abstract class ServiceRecuperation
{
    public static List<Employe> recupererEmployees()
    {        
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();

        List<Employe> employes = DAOEmploye.getEmployes();

        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
       
        return employes;
    }
    
    public static List<Client> recupererClients()
    {        
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();

        List<Client> clients = DAOClient.getClients();

        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
       
        return clients;
    }
    
    
}
