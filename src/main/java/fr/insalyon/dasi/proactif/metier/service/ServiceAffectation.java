/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.service;

import com.google.maps.model.LatLng;
import fr.insalyon.dasi.proactif.dao.DAODemandeIntervention;
import fr.insalyon.dasi.proactif.dao.DAOEmploye;
import fr.insalyon.dasi.proactif.dao.JpaUtil;
import fr.insalyon.dasi.proactif.metier.objetsmetier.Employe;
import java.util.List;

/**
 *
 * @author lsterner
 */
public abstract class ServiceAffectation
{
    public List<Employe> trouverEmployesLibres()
    {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();


        List<Employe> employes = DAOEmploye.getEmployes();

        
        
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        
        
        return null;
    }
    
    
    public Employe employeLePlusProcheParmi(Employe e, LatLng coord)
    {
        return null;
    }
}
