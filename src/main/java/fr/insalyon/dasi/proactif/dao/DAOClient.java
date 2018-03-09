/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.dao;

import fr.insalyon.dasi.proactif.metier.objetsmetier.Client;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author lsterner
 */
public abstract class DAOClient
{
    public static void create(Client e)
    {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(e);
    }
    
    public static List<Client> getClients()
    {
        EntityManager em = JpaUtil.obtenirEntityManager();
        return em.createQuery("SELECT c FROM Client c").getResultList();
    }
}
