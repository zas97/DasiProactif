package fr.insalyon.dasi.proactif.ihm;


import com.google.maps.model.LatLng;
import fr.insalyon.dasi.proactif.dao.DAOClient;
import fr.insalyon.dasi.proactif.dao.DAOEmploye;
import fr.insalyon.dasi.proactif.dao.JpaUtil;
import fr.insalyon.dasi.proactif.metier.objetsmetier.Client;
import fr.insalyon.dasi.proactif.metier.objetsmetier.DemandeIntervention;
import fr.insalyon.dasi.proactif.metier.objetsmetier.DemandeInterventionAnimal;
import fr.insalyon.dasi.proactif.metier.objetsmetier.Employe;
import fr.insalyon.dasi.proactif.metier.service.ServiceDemandeIntervention;
import fr.insalyon.dasi.proactif.metier.service.ServiceInscription;
import fr.insalyon.dasi.proactif.metier.service.ServiceInscription.EmailDejaUtiliseException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import static util.GoogleMapUtil.getFlightDistanceInKm;
import static util.GoogleMapUtil.getLatLng;
import static util.GoogleMapUtil.getTripDistanceByCarInKm;
import static util.GoogleMapUtil.getTripDurationByBicycleInMinute;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


// TODO : no binome dans compte rendu
// TODO : diagrammes
// TODO : patterns

/**
 *
 * @author lsterner
 */
public class Main
{
    public static void main(String[] args) throws Exception
    {
        JpaUtil.init();
        
        testDemande();
        
        JpaUtil.destroy();
    }
    
    
    private static void testInscriptionEmploye()
    {
        ServiceInscription inscription = new ServiceInscription();

        
        // test de la persistance d'un employe lors d'une inscription        
        inscription.inscriptionEmploye(new Employe("0123456789", 8,18, "mdp"));

        
//        for (Employe e : DAOEmploye.getEmployes())
//            System.out.println(e);
    }
    
    private static void testInscriptionClient()
    {
        ServiceInscription inscription = new ServiceInscription();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        
        try
        {
            inscription.inscriptionClient(new Client("leo", "leo", "Mr", sdf.parse("06/06/1966"), "INSA LYON", "0011001100", "aa@bbbbbbaa", "aaa"));
        }
        catch (EmailDejaUtiliseException | ParseException e)
        {
            System.err.println(e.getMessage());
        }
    }
    
    private static void testDemande()
    {
        
        // https://stackoverflow.com/questions/3581258/adding-n-hours-to-a-date-in-java
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(new Date()); // sets calendar time/date
        Date now = cal.getTime();
        //cal.add(Calendar.HOUR_OF_DAY, 1); // adds one hour
        
        ServiceDemandeIntervention demandeIntervention = new ServiceDemandeIntervention();
        ServiceInscription inscription = new ServiceInscription();
        
        
        Employe e = new Employe("000", 8, 20, "mdp");
        inscription.inscriptionEmploye(e);

        
        
        DemandeInterventionAnimal d = new DemandeInterventionAnimal("CHAT", now, null, "DEMANDE", null, null);
        
        demandeIntervention.creerDemande(d);
//        demandeIntervention.assignerDemande(d, e);
//        
//
//        System.out.println("Demandes assignées à l'employé : " + e.getIdEmploye() + "\n");
//        for (DemandeIntervention di : demandeIntervention.recupererDemandesAssigneesAEmploye(e))
//            System.out.println(di.getIdDemandeIntervention() + "\n");

           for (DemandeIntervention di : demandeIntervention.recupererDemandesNonAssignees())
               System.out.println(di.getIdDemandeIntervention());
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
}
