/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.dasi.proactif.metier.service;

import com.google.maps.model.LatLng;
import fr.insalyon.dasi.proactif.dao.DAOClient;
import fr.insalyon.dasi.proactif.dao.DAODemandeIntervention;
import fr.insalyon.dasi.proactif.dao.DAOEmploye;
import fr.insalyon.dasi.proactif.dao.JpaUtil;
import fr.insalyon.dasi.proactif.metier.objetsmetier.Client;
import fr.insalyon.dasi.proactif.metier.objetsmetier.DemandeIntervention;
import fr.insalyon.dasi.proactif.metier.objetsmetier.Employe;
import fr.insalyon.dasi.proactif.metier.service.ServiceException.EmailDejaUtiliseException;
import fr.insalyon.dasi.proactif.metier.service.ServiceException.IdentifiantsInvalidesException;
import fr.insalyon.dasi.proactif.util.GoogleMapUtil;
import java.util.Date;
import java.util.List;
import javax.persistence.RollbackException;

/**
 *
 * @author lsterner
 */
public abstract class Service {
    
    // AFFECTATION
    
    public static DemandeIntervention recupererDemandeCouranteDeEmploye(Employe e)
    {
        DemandeIntervention demandeIntervention;
        
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        
        demandeIntervention = DAODemandeIntervention.getCurrentDemandeOfEmployee(e);
        
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        
        return demandeIntervention;
    }

    //-------------------------------

    // CONNEXION
    
    private static Client CLIENT_CONNECTE = null;
    private static Employe EMPLOYE_CONNECTE = null;

    public static Employe connexionEmploye(String mail, String motDePasse) throws IdentifiantsInvalidesException {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();

        EMPLOYE_CONNECTE = DAOEmploye.getEmployeFromCredentials(mail, motDePasse);

        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();


        if (EMPLOYE_CONNECTE == null)
            throw new IdentifiantsInvalidesException(mail);

        return EMPLOYE_CONNECTE;
    }
    
    public static Client connexionClient(String mail, String motDePasse) throws IdentifiantsInvalidesException {

        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();

        CLIENT_CONNECTE = DAOClient.getClientFromCredentials(mail, motDePasse);

        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();

        if (CLIENT_CONNECTE == null)
            throw new IdentifiantsInvalidesException(mail);

        return CLIENT_CONNECTE;
    }

    public static void deconnexion() {
        CLIENT_CONNECTE = null;
        EMPLOYE_CONNECTE = null;
    }

    public static Employe getEmployeConnecte() {
        return EMPLOYE_CONNECTE;
    }

    public static Client getClientConnecte() {
        return CLIENT_CONNECTE;
    }
    
    //-------------------------------

    // CREATION EMPLOYE
    
    public static void creerEmployes()
    {
        try
        {
            Employe e1 = new Employe("0000", 10, 18, "mdp", "rennes", "e1@");
            Employe e2 = new Employe("0000", 10, 18, "mdp", "lyon", "e2@");
            Employe e3 = new Employe("0000", 10, 18, "mdp", "paris", "e3@");
            inscriptionEmploye(e1);
            inscriptionEmploye(e2);
            inscriptionEmploye(e3);        
        }
        catch (Exception e)
        {}
    }

    
    //--------------------------------

    // DEMANDE INTERVENTION
    
    public static void gererDemande(DemandeIntervention demandeIntervention) throws ServiceException.AucunEmployeDisponibleException
    {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
                
        // on assigne la demande
        Employe charge = employeLePlusProcheParmi(DAOEmploye.getEmployesDisponibles(), demandeIntervention.getDemandeur().getPosition());

        if (charge == null) {    
            JpaUtil.annulerTransaction();
            JpaUtil.fermerEntityManager();
            throw new ServiceException.AucunEmployeDisponibleException(demandeIntervention.getDescription());
        }

        
        demandeIntervention.setCharge(charge);
        demandeIntervention.setStatus(DemandeIntervention.DemandeInterventionStatus.EN_COURS_DE_TRAITEMENT);
        charge.setDisponible(false);
        
        // FIXME : l'employé n'est jamais merge ??????
        try
        {
            DAOEmploye.update(charge);
            DAODemandeIntervention.create(demandeIntervention);
        }
        catch (RollbackException e)
        {
            JpaUtil.annulerTransaction();
            JpaUtil.fermerEntityManager();

            // TODO
            throw new ServiceException.AucunEmployeDisponibleException(demandeIntervention.getDescription());
        }
        
        DAODemandeIntervention.create(demandeIntervention);
        // TODO ici id intervention est null ??
        
        // on envoie la notification
        Service.envoyerNotificationEmploye(demandeIntervention);
        
        
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
    
    }
    
    
    public static void cloturerDemande(DemandeIntervention demandeIntervention, DemandeIntervention.DemandeInterventionStatus status, String commentaire) throws ServiceException.InvalidClosingStatusException
    {
        if (status != DemandeIntervention.DemandeInterventionStatus.TERMINEE && status != DemandeIntervention.DemandeInterventionStatus.INCIDENT)
            throw new ServiceException.InvalidClosingStatusException(status);
        
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();

        demandeIntervention.setCommentaire(commentaire);
        demandeIntervention.setStatus(status);
        demandeIntervention.setDateFin(new Date());   
        
        demandeIntervention.getCharge().setDisponible(true);
        
        DAODemandeIntervention.update(demandeIntervention);
        DAOEmploye.update(demandeIntervention.getCharge());
        
        
        envoyerNotificationClient(demandeIntervention);
        
        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
    }           

    public static List<DemandeIntervention> recupererDemandesDuClient(Client c) {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();

        List<DemandeIntervention> demandes = DAODemandeIntervention.getDemandesOfClient(c);

        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();

        return demandes;
    }
    
    public static List<DemandeIntervention> recupererDemandesDuJour()
    {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();

        List<DemandeIntervention> demandesDuJour = DAODemandeIntervention.getDemandesDuJour();

        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
        
        return demandesDuJour;    
    }
    
    
    
    
    private static Employe employeLePlusProcheParmi(List<Employe> employes, LatLng coord) {
        double minDistance = Double.MAX_VALUE;
        Employe plusProche = null;

        for (Employe employe : employes) {
            double distance = GoogleMapUtil.getTripDistanceByCarInKm(employe.getPosition(), coord);
            if (distance < minDistance) {
                minDistance = distance;
                plusProche = employe;
            }
        }

        return plusProche;
    }

    
    //--------------------------------

    // INSCRIPTION
    
    public static void inscriptionEmploye(Employe e) {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();

        DAOEmploye.create(e);

        JpaUtil.validerTransaction();
        JpaUtil.fermerEntityManager();
    }



    public static void inscriptionClient(Client c) throws EmailDejaUtiliseException {
        try {
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();

            DAOClient.create(c);

            JpaUtil.validerTransaction();
            JpaUtil.fermerEntityManager();
            
            envoyerConfirmationInscription(c);
        } catch (RollbackException e) {
            JpaUtil.annulerTransaction();
            JpaUtil.fermerEntityManager();
            
            envoyerErreurInscription(c);
            
            throw new EmailDejaUtiliseException(c.getMail());
        }
    }
    
    //--------------------------------

    // MAIL
    
    private static final String EXPEDITEUR_MAIL = "contact@proact.if";
    private static final String SUJET_MAIL = "Bienvenue chez PROACT'IF";

    private static final String SUCCES_MAIL = "Bonjour %s,\n"
            + "Nous vous confirmons votre inscription au service PROACT'IF.\n"
            + "Votre numéro de client est le : %d.";

    private static final String ECHEC_MAIL = "Bonjour %s,\n"
            + "Votre inscription au service PROACT'IF a malencontreusement échouée...\n"
            + "Merci de recommencer ultérieurement.";

    private static void envoyerEnTete(Client c) {
        System.out.println("Expéditeur : " + EXPEDITEUR_MAIL);
        System.out.println("Pour : " + c.getMail());
        System.out.println("Sujet : " + SUJET_MAIL);

    }

    private static void envoyerConfirmationInscription(Client c) {
        envoyerEnTete(c);

        System.out.println();

        System.out.println(String.format(SUCCES_MAIL, c.getPrenom(), c.getIdClient()));
    }

    private static void envoyerErreurInscription(Client c) {
        envoyerEnTete(c);

        System.out.println();

        System.out.println(String.format(ECHEC_MAIL, c.getPrenom()));
    }
    
    //--------------------------------

    // NOTIFICATION
    
    private static final String NOTIFICATION_EMPLOYE = "Intervention %s\n"
            + "Demandée le %s pour %s %s (Client #%d), %s, (%f km) : \n"
            + "%s.";

    private static final String NOTIFICATION_CLIENT = "Intervention %s\n"
            + "L'intervention que vous avez demandé le %s à été réalisée avec le status (%s)\n"
            + "(%s)";
    
    
    private static String typeIntervention(DemandeIntervention di) {
        return di.getClass().toString().substring(DemandeIntervention.class.getSimpleName().length() + di.getClass().toString().indexOf(DemandeIntervention.class.getSimpleName()));
    }

    private static void envoyerNotificationEmploye(DemandeIntervention di) {
        Client demandeur = di.getDemandeur();
        Employe charge = di.getCharge();

        System.out.println(String.format(NOTIFICATION_EMPLOYE,
                        typeIntervention(di),
                        di.getDatePublication(),
                        demandeur.getNom(),
                        demandeur.getPrenom(),
                        demandeur.getIdClient(),
                        demandeur.getAdressePostale(),
                        GoogleMapUtil.getTripDistanceByCarInKm(demandeur.getPosition(), charge.getPosition()),
                        di.getDescription()));
    }
    
    private static void envoyerNotificationClient(DemandeIntervention di)
    {
        System.out.println(String.format(NOTIFICATION_CLIENT,
                        typeIntervention(di),
                        di.getDatePublication(),
                        di.getStatus().name(),
                        di.getCommentaire()));
    }

    
    //--------------------------------

}
