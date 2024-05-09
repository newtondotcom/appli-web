package vue;

import modele.*;
import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;


/**
 * Facade
 */
@Singleton
public class Facade {

  @PersistenceContext
  private EntityManager em;

 // Partie Eleves
  public void Enregistrer(String nom, String mdp, int INE, boolean admin, String email, String telephone, Etablissement etablissement_util, String token) {
    Utilisateur user = new Utilisateur(nom, mdp, INE, admin, email, telephone, etablissement_util, token);
    em.persist(user);
  } 
  
  public String seConnecter(String nom, String mdp) {
    Utilisateur utilisateur = em.createQuery("select m from Utilisateur m where nom == "+nom+" and mdp == "+mdp, Utilisateur.class).getSingleResult();
      if (utilisateur != null) {
        return utilisateur.getToken();
      } else {
        return null;
      }
  }
  public Collection<Evenement> trierEvenement(String jour, String heure, String mois, String annee, String minute, String nom){
    if (jour == null || heure == null || mois == null || annee == null || minute == null){
      if (nom == null) {
        return em.createQuery("select e from Evenement e",Evenement.class).getResultList();
      } else {
        return em.createQuery("select e from Evenement e where etablissement_event == " + nom, Evenement.class).getResultList();
      }
    } else{
      String date = "" + annee + "-" + mois + "-" + jour + " " + heure + ":" + minute;
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
      LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
      if (nom == null){
        return em.createQuery("select e from Evenement e where creneau => " + dateTime, Evenement.class).getResultList();
      } else {
        return em.createQuery("select e from Evenement e where creneau => " + dateTime + " and etablissement_event == " + nom, Evenement.class).getResultList();
      }
    }
  }

  // Ca marche pas est je peux pas le débugger je sais pas ce que fait cette fonction
 /*
  public List<Evenement,Int> ficheEvenement(int id_event){
    Etablissement entreprise = em.find(Etablissement.class,id_event);
    Collection<Evenement> event_entreprise = entreprise.getEvenements_etab();
    int note_tot = 0;
    for ( Evenement event: event_entreprise) { 
      note_tot += event.getAvis_event().getNote();
    }
    return (em.find(Evenement.class, id), note_tot/length(event_entreprise));
  }
 */

  public void demandeReservation(String token, int id_event ) {
    Utilisateur utilisateur = em.createQuery("select m from Utilisateur m where token == " + token, Utilisateur.class).getSingleResult();
    Demande new_demande = new Demande(false, false,utilisateur,em.find(Evenement.class, id_event));
    utilisateur.getDemandes_util().add(new_demande);
  }

  public void seDesinscrire(String token, int id_event) {
    Evenement event = em.find(Evenement.class, id_event);
    Utilisateur utilisateur = em.createQuery("select m from Utilisateur m where token == " + token, Utilisateur.class).getSingleResult();
    event.getUtilisateurs_event().remove(utilisateur);
    em.merge(event);
  }

  public Evenement returnEvent(int id_event){
    return em.find(Evenement.class,id_event);
  }

  public Collection<Evenement> listeTousEvent(){
    return em.createQuery("select e from Evenement e",Evenement.class).getResultList();
  }

  public void donnerAvis(int id_utilisateur, int id_event,String titre, String contenu, int note){
    Avis new_avis = new Avis(titre,note,contenu,em.find(Utilisateur.class, id_utilisateur), em.find(Evenement.class, id_event));
    em.persist(new_avis);
  }

  // Mettre un creneau sous la forme: yyyy-MM-dd HH:mm
  public void ajouterEvenement(String titre, String description, int duree, String creneau, int id_etablissement) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime dateTime = LocalDateTime.parse(creneau, formatter);
    Evenement new_event = new Evenement(description,dateTime,em.find(Etablissement.class, id_etablissement),duree,titre);
    em.persist(new_event);
  }

  // Je renvoie les evenements liés à l'établissement école. Je ne sais pas si c'est l'ensemble des visites possibles ou l'ensemble des visites
  // que les étudiants ont réservé. Ici je considère la deuxième option
  public Collection<Evenement> listeEventParEcole(int id_ecole) {
    Etablissement etab = em.find(Etablissement.class,id_ecole);
    return etab.getEvenements_etab();
  }

// Dans l'entity "Demande", je ne comprends pas pk valide et refuse sont dans le constructeur
// Sachant que de base une demande ets en attente. Et pourquoi les setValide ne change que un boolean et pas l'autre
  public void validerDemande(int id_demande){
    Demande demande_en_cours = em.find(Demande.class, id_demande);
    demande_en_cours.setValide(true);
    em.merge(demande_en_cours);
  }

  public void refuserDemande(int id_demande){
    Demande demande_en_cours = em.find(Demande.class, id_demande);
    demande_en_cours.setRefuse(true);
    em.merge(demande_en_cours);
  }
}
  