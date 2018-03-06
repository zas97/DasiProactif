/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.dao;

import fr.insalyon.dasi.proactif.metier.objetsmetier.Employe;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author lsterner
 */
public abstract class DAOEmploye
{
    public static void create(Employe e)
    {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(e);
    }
    
    public static List<Employe> getEmployes()
    {
        EntityManager em = JpaUtil.obtenirEntityManager();
        return em.createQuery("SELECT c FROM Employe c").getResultList();
    }
}
