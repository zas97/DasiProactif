/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.dao;

import java.util.List;
import javax.persistence.EntityManager;
import fr.insalyon.dasi.proactif.metier.objetsmetier.Employe;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author lsterner
 */
public abstract class DAOEmploye {

    public static void create(Employe e) {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.persist(e);
    }
    
    public static void update(Employe e)
    {
        EntityManager em = JpaUtil.obtenirEntityManager();
        em.merge(e);
    }

    public static List<Employe> getEmployes() {
        EntityManager em = JpaUtil.obtenirEntityManager();
        return em.createQuery("SELECT e FROM Employe e").getResultList();
    }

    public static Employe getEmployeFromCredentials(String mail, String password) {
        EntityManager em = JpaUtil.obtenirEntityManager();

        List<Employe> result = em.createQuery("SELECT e FROM Employe e WHERE e.mail = :mail AND e.motDePasse = :p").setParameter("mail", mail).setParameter("p", password).getResultList();
        
        if (result.isEmpty())
            return null;
        
        return result.get(0);
    }

    public static List<Employe> getEmployesDisponibles()
    {        
        EntityManager em = JpaUtil.obtenirEntityManager();        
        
        Calendar c = new GregorianCalendar();
        
        int hour = c.get(Calendar.HOUR_OF_DAY);

        // TODO : l'affectation prend en compte l'heure de début (incluse) et l'heure de fin (exclue)
        List<Employe> result = em.createQuery("SELECT e FROM Employe e WHERE e.disponible = true AND e.heureDebut <= :heure AND e.heureFin > :heure").setParameter("heure", hour).getResultList();

        return result;
    }
}
