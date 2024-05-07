package vue;

import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import modele.Personne;
import modele.Adresse;

/**
 * Facade
 */
@Singleton
public class Facade {

  @PersistenceContext
  private EntityManager em;

 // Partie Eleves
  public Utilisateur Enregistrer(String nom, String mdp, String SIREN, boolean entreprise, int INE, boolean admin, String email, String telephone, Etablissement etablissement_util) {
    Utilisateur user = new Utilisateur(String nom, String mdp, String SIREN, boolean entreprise, int INE, boolean admin, String email, String telephone, Etablissement etablissement_util);
    em.persist(user);
  } 
  public String seConnecter(String nom, String mdp) {
    Utilisateur utilisateur = em.createQuery("select m from Utilisateur m where nom == "+nom+" and mdp == "+mdp, Utilisateur.class).getSingleResult();
    return utilisateur.getToken();
  }
  public Collection<Evenement> trierEvenement(String jour, String heure, String mois, String annee, String minute, String nom){
    if (jour == null || heure == null || mois == null || annee == null || minute == null){
      if (nom == null) {
        return em.createQuery("select e from Evenement e").getListResult();
      } else {
        return em.createQuery("select e from Evenement e where etablissement_event == " + nom, Evenement.class).getListResult();
      }
    } else{
      String date = "" + annee + "-" + mois + "-" + jour + " " + heure + ":" + minute;
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
      LocalDateTime dateTime = LocalDateTime.parse(date, formatter)
      if (nom == null){
        return em.createQuery("select e from Evenement e where creneau => " + dateTime, Evenement.class).getListResult();
      } else {
        return em.createQuery("select e from Evenement e where creneau => " + dateTime + " and etablissement_event == " + nom, Evenement.class).getListResult();
      }
    }
  }

  public List<Evenement,Int> ficheEvenement(int id_event){
    Etablissement entreprise = em.find(Etablissement.class,id_event);
    Collection<Evenement> event_entreprise = entreprise.getEvenements_etab();
    int note_tot = 0;
    for ( Evenelent event: event_entreprise) {
      note_tot += event.getAvis_event();
    }
    return (em.find(Evenement.class, id), note_tot/length(event_entreprise));
  }
}

  public void demandeReservation(String token, int id_event ) {
    Utilisateur utilisateur = em.createQuery("select m from Utilisateur m where token == " + token, Utilisateur.class).getSingleResult();
    Demande new_demande = Demande(false, false,utilisateur,em.find(Evenement.class, id_event))
    utilisateur.getDemandes_util.add(Demandde)
  }

  public void seDesinscrire(String token, int id_event) {
    Evenement event = em.
  }

  public void returnEvent(int id_event){
    return em.find(Evenement.class,id_event);
  }

  public Collection<Evenement> listeTousEvent(){
    return em.createQuery("select e from Evenement e").getListResult();
  }