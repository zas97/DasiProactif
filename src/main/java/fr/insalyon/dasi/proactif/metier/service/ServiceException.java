/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.service;

import fr.insalyon.dasi.proactif.metier.objetsmetier.DemandeIntervention;

/**
 *
 * @author lsterner
 */
public abstract class ServiceException extends Exception
{

    public ServiceException(String s)
    {
        super(s);
    }
    
    
    public static class AucunEmployeDisponibleException extends ServiceException {

    public AucunEmployeDisponibleException(String description) {
            super("Aucun employé n'est disponible pour traiter la demande : " + description);
        }
    }
    
    public static class IdentifiantsInvalidesException extends ServiceException {

        public IdentifiantsInvalidesException(String mail) {
            super("Les identifiants fournis pour le compte (" + mail + ") sont incorrects !");
        }
    }
    
    
    public static class InvalidClosingStatusException extends Exception
    {
        public InvalidClosingStatusException(DemandeIntervention.DemandeInterventionStatus status) {
            super("Le status " + status.name() + " n'est pas utilisable pour la clotûre d'une demande !");
        }
    }

    
    public static class EmailDejaUtiliseException extends Exception {

        public EmailDejaUtiliseException(String mail) {
            super("Le mail " + mail + " est déjà utilisé");
        }
    }
}
