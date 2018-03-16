package fr.insalyon.dasi.proactif.ihm;

import com.google.maps.model.LatLng;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import fr.insalyon.dasi.proactif.dao.JpaUtil;
import fr.insalyon.dasi.proactif.metier.objetsmetier.Client;
import fr.insalyon.dasi.proactif.metier.objetsmetier.DemandeIntervention;
import fr.insalyon.dasi.proactif.metier.objetsmetier.DemandeInterventionAnimal;
import fr.insalyon.dasi.proactif.metier.objetsmetier.DemandeInterventionIncident;
import fr.insalyon.dasi.proactif.metier.objetsmetier.DemandeInterventionLivraison;
import fr.insalyon.dasi.proactif.metier.objetsmetier.Employe;
import fr.insalyon.dasi.proactif.metier.service.Service;
import fr.insalyon.dasi.proactif.metier.service.ServiceException;
import fr.insalyon.dasi.proactif.metier.service.ServiceRecuperation;
import static fr.insalyon.dasi.proactif.util.GoogleMapUtil.getFlightDistanceInKm;
import static fr.insalyon.dasi.proactif.util.GoogleMapUtil.getLatLng;
import static fr.insalyon.dasi.proactif.util.GoogleMapUtil.*;
import fr.insalyon.dasi.proactif.util.Saisie;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;


// TODO : no binome dans compte rendu
// TODO : diagrammes
// TODO : patterns
// TODO : annotation


// TODO : cas ou aucune demande n'est en cours
/**
 *
 * @author lsterner
 */
public class Main {

    public static void main(String[] args) throws Exception {
        JpaUtil.init();

        Service.creerEmployes();
        
//        testInscriptionEmploye();
//        testInscriptionClient();
//        testConnexionClient();
//        testDemande();
//        testTableauDeBord();
//        testHistoriqueClient();

        console();

        JpaUtil.destroy();
    }

    private static void testInscriptionEmploye() {
        // test de la persistance d'un employe lors d'une inscription        
        Service.inscriptionEmploye(new Employe("0123456789", 8, 18, "mdp", "Les Poulettes 69100 Lyon", "leo.leo@leo.leo"));

        for (Employe e : ServiceRecuperation.recupererEmployees())
            System.out.println(e);
    }

    private static void testInscriptionClient() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

        try
        {
            Service.inscriptionClient(new Client("leo", "leo", "Mr", sdf.parse("06/06/1966"), "INSA LYON", "0011001100", "aa@bbbbbbaa", "aaa"));
        }
        catch (ServiceException.EmailDejaUtiliseException | ParseException e)
        {
            System.err.println(e.getMessage());
        }
        
        for (Client c : ServiceRecuperation.recupererClients())
            System.out.println(c);
    }

    private static void testDemande() throws Exception
    {

        // https://stackoverflow.com/questions/3581258/adding-n-hours-to-a-date-in-java



        Client c = new Client("leo", "leo", "Mr", new Date(), "insa Lyon", "000", "leo.leo", "mdp");
        Service.inscriptionClient(c);
//        Client c = ServiceConnexion.connexionClient("leo.leo", "mdp");
        Employe e = new Employe("000", 10, 22, "mdp", "bretagne", "loo.loo@loo.loo");
        Service.inscriptionEmploye(e);
        
        DemandeInterventionAnimal d = new DemandeInterventionAnimal("CHAT", "DEMANDE",   c);

        Service.gererDemande(d);
        
        System.out.println(d.getIdDemandeIntervention());
        
        System.out.println(d.getCharge());
        
//        Employe e = ServiceConnexion.connexionEmploye("e2@", "mdp");

        
        
        System.out.println("Demandes assignée à l'employé : " + e.getIdEmploye() + "\n");
        System.out.println(Service.recupererDemandeCouranteDeEmploye(e) + "\n");
        
        
        
        
        Service.cloturerDemande(Service.recupererDemandeCouranteDeEmploye(e), DemandeIntervention.DemandeInterventionStatus.TERMINEE, "FINI");
        
        System.out.println("Demandes assignée à l'employé : " + e.getIdEmploye() + "\n");
        System.out.println(Service.recupererDemandeCouranteDeEmploye(e) + "\n");

    }

    private static void testGoogleAPI()       
    {
        String adresse1 = "7 Avenue Jean Capelle Ouest, Villeurbanne";
        LatLng coords1 = getLatLng(adresse1);
        System.out.println("Lat/Lng de Adresse #1: " + coords1);

        String adresse2 = "37 Avenue Jean Capelle Est, Villeurbanne";
        LatLng coords2 = getLatLng(adresse2);
        String adresse3 = "61 Avenue Roger Salengro, Villeurbanne";
        LatLng coords3 = getLatLng(adresse3);
        
        Double duree = getTripDurationByBicycleInMinute(coords1, coords3, coords2);
        System.out.println("Durée de Trajet à Vélo de Adresse #1 à Adresse #3 en passant par Adresse #2: " + duree + " min");

        Double distance = getTripDistanceByCarInKm(coords1, coords3);
        System.out.println("Distance en Voiture de Adresse #1 à Adresse #3 (trajet direct par la route): " + distance + " km");

        Double distanceVolDOiseau = getFlightDistanceInKm(coords1, coords3);
        System.out.println("Distance à Vol d'Oiseau de Adresse #1 à Adresse #3 (distance géographique): " + distanceVolDOiseau + " km");
    }
    
    private static void testConnexionClient()
    {
        try
        {
            Service.connexionClient("aa@bbbbbbaa", "aaa");
        }
        catch (ServiceException.IdentifiantsInvalidesException e)
        {
            System.out.println(e.getMessage());
        }
        
        System.out.println("Client connecté : " + Service.getClientConnecte());
        
        
    }
    
    
    private static void testTableauDeBord()
    {        
        Calendar c = new GregorianCalendar();
        c.set(Calendar.DAY_OF_MONTH, 12);
        System.out.println(c.getTime());
 
        // TODO
        
//        ServiceDemandeIntervention.creerDemande(new DemandeInterventionAnimal("chat", c.getTime(), null, null,null, null, null));
//        ServiceDemandeIntervention.creerDemande(new DemandeInterventionAnimal("chat", new Date(), null, null,null, null, null));
     
        List<DemandeIntervention> demandesDuJour = Service.recupererDemandesDuJour();
        
        System.out.println("Demandes du jour :\n");
        for (DemandeIntervention demandeIntervention : demandesDuJour)
            System.out.println(demandeIntervention);
    }
    
    
    
    private static void testHistoriqueClient() throws Exception
    {
        Client c = Service.connexionClient("leo.leo", "mdp");
        
        
        List<DemandeIntervention> ds = Service.recupererDemandesDuClient(c);
        
        System.out.println("Demandes du client " + c.getPrenom());
        for (DemandeIntervention d : ds)
            System.out.println(d);
    }
    
    
    
    
    
    
    private static void console() throws Exception
    {
        int choix;
        
        
        System.out.println("Bienvenue sur Proact'IF !");
        
        do
        {
        
            System.out.println("Vous êtes ?");
            System.out.println("Employe [0]");
            System.out.println("Client [1]");
            System.out.println("Nouveau client [2]");

            choix = Saisie.lireInteger("> ", Arrays.asList(0,1,2));
            switch (choix)
            {
                case 0:
                {
                    System.out.println("Connectez-vous : ");
                    String mail = Saisie.lireChaine("Mail : ");
                    String pass = Saisie.lireChaine("Mot de passe: ");

                    Employe emplConnecte = Service.connexionEmploye(mail, pass);

                    while (true)
                    {

                        System.out.println("Voulez vous ?");
                        System.out.println("Cloturer votre demande courante (si elle existe) [0]");
                        System.out.println("Consulter le tableau de bord [1]");
                        System.out.println("Vous déconnecter [2]");

                        choix = Saisie.lireInteger("> ", Arrays.asList(0,1,2));
                        switch (choix)
                        {
                            case 0:
                                DemandeIntervention demandeCourant = Service.recupererDemandeCouranteDeEmploye(emplConnecte);
                                choix = Saisie.lireInteger("Terminée [0] - Incident [1] ", Arrays.asList(0,1,2));
                                DemandeIntervention.DemandeInterventionStatus status;
                                if (choix == 0)
                                    status = DemandeIntervention.DemandeInterventionStatus.TERMINEE;
                                else
                                    status = DemandeIntervention.DemandeInterventionStatus.INCIDENT;
                                    
                                String commentaire = Saisie.lireChaine("Commentaire : ");
                                Service.cloturerDemande(demandeCourant, status, commentaire);

                                System.out.println("La demande est maintenant clôturée !");
                                break;

                            case 1:
                                List<DemandeIntervention> demandeInterventions = Service.recupererDemandesDuJour();

                                System.out.println("Voici votre tableau de bord :");
                                for (DemandeIntervention d : demandeInterventions)
                                    System.out.println(d);
                                break;

                            case 2:
                                System.out.println("Au revoir !");
                                Service.deconnexion();
                                break;

                        }
                        
                        choix = Saisie.lireInteger("Continuer Oui[1] / Non[0] ", Arrays.asList(0,1));
                        if (choix == 0)
                            break;
                                
                    } 
                    
                    break;
                }
                case 1:
                {
                    System.out.println("Connectez-vous :");
                    String mail = Saisie.lireChaine("Mail : ");
                    String pass = Saisie.lireChaine("Mot de passe : ");

                    Client connecte = Service.connexionClient(mail, pass);

                    while (true)
                    {

                        System.out.println("Voulez vous ?");
                        System.out.println("Ajouter une demande [0]");
                        System.out.println("Consulter votre historique [1]");
                        System.out.println("Vous déconnecter [2]");

                        choix = Saisie.lireInteger("> ", Arrays.asList(0,1,2));
                        switch (choix)
                        {
                            case 0:
                                String description = Saisie.lireChaine("Description :");
                                DemandeIntervention di;
                                System.out.println("Animal [0] - Incident [1] - Livraison [2]");
                                choix = Saisie.lireInteger("> ", Arrays.asList(0,1,2));
                                switch (choix)
                                {
                                    case 0:
                                        String animal = Saisie.lireChaine("Animal : ");

                                        di = new DemandeInterventionAnimal(animal, description, connecte);
                                        Service.gererDemande(di);

                                        System.out.println("Votre demande est prise en compte !");

                                        break;

                                    case 1:
                                        di = new DemandeInterventionIncident(description, connecte);
                                        Service.gererDemande(di);

                                        System.out.println("Votre demande est prise en compte !");

                                        break;

                                    case 2:
                                        String objet = Saisie.lireChaine("Objet : ");
                                        String entreprise = Saisie.lireChaine("Entreprise : ");

                                        di = new DemandeInterventionLivraison(objet, entreprise, description, connecte);
                                        Service.gererDemande(di);

                                        System.out.println("Votre demande est prise en compte !");

                                        break;
                                }


                                break;

                            case 1:
                                List<DemandeIntervention> demandeInterventions = Service.recupererDemandesDuClient(connecte);

                                System.out.println("Voici votre historique : ");
                                for (DemandeIntervention d : demandeInterventions)
                                    System.out.println(d);
                                break;

                            case 2:
                                System.out.println("Au revoir !");
                                Service.deconnexion();
                                break;

                        }
                        
                        choix = Saisie.lireInteger("Continuer Oui[1] / Non[0] ", Arrays.asList(0,1));
                        if (choix == 0)
                            break;
                                
                    }
                    break;
                }
                case 2:
                {
                    System.out.println("Bienvenue nouveau client :");

                    String nom  = Saisie.lireChaine("Nom : ");
                    String prenom = Saisie.lireChaine("Prenom : ");
                    String civilite = Saisie.lireChaine("Civilité : ");
                    String dateNaissance = Saisie.lireChaine("Date de naissance : ");

                    SimpleDateFormat dateFormat = new SimpleDateFormat("DD/MM/YYYY");
                    Date naissance = dateFormat.parse(dateNaissance);

                    String adressePostale = Saisie.lireChaine("Adresse postale : ");
                    String numeroTelephone = Saisie.lireChaine("Tel : ");
                    String mail = Saisie.lireChaine("Mail : ");
                    String motDePasse = Saisie.lireChaine("Pass : ");

                    Service.inscriptionClient(new Client(nom, prenom, civilite, naissance, adressePostale, numeroTelephone, mail, motDePasse));

                    break;
                }
            }
        
        }
        while (true);
    }
}
