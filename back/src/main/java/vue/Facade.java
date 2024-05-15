package vue;

import modele.*;
import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.security.SecureRandom;
import java.math.BigInteger;

/**
 * Facade
 */
@Singleton
public class Facade {

  @PersistenceContext
  private EntityManager em;

  private SecureRandom random = new SecureRandom();

  public void initialisation() {
    Etablissement new_etab1 = new Etablissement("4 rue test1", 1, "IKEA", true, "chat");
    em.persist(new_etab1);
    Utilisateur util = new Utilisateur("Fredo", "1234", "20", true, "@test.com", "06", new_etab1, "1");
    em.persist(util);
    Domain dom1 = new Domain("IA");
    em.persist(dom1);
    Evenement event = new Evenement("cool", LocalDateTime.of(2024, Month.MAY, 15, 14, 30, 0), new_etab1, 60,
        "Super");
    em.persist(event);
    Demande dem = new Demande(util, event);
    em.persist(dem);

  }

  // Partie Eleves
  // Cette fonction revoie un boolean qui dit si l'enregistrement s'est bien passé
  // ou pas
  public String Enregistrer(String nom, String mdp, String INE, String mdp_admin, String email, String telephone,
      String nom_etablissement) {
    // Le mec ne va pas cocher admin ou pas en s'inscrivant il doit rentré le bon
    // mot de passe pour s'inscrire en temps que admin d'ou les lignes suivante
    boolean admin = false;
    if (mdp_admin.equals("je_suis_admin")) {
      admin = true;
    }
    // Recherche l'établissement dont le nom à était rentré
    Etablissement etablissement_util = null;
    try {
      TypedQuery<Etablissement> query = em.createQuery("SELECT e FROM Etablissement e WHERE e.nom = :nom",
          Etablissement.class);
      query.setParameter("nom", nom);
      etablissement_util = query.getSingleResult();
      String token = new BigInteger(32 * 5, random).toString(32);
      Utilisateur user = new Utilisateur(nom, mdp, INE, admin, email, telephone, etablissement_util, token);
      em.persist(user);
      user.setEtablissement_util(etablissement_util);
      return token;
    } catch (IllegalArgumentException | PersistenceException e) {
      // Gérer le cas où aucun établissement n'a était trouvé avec ce nom n'est trouvé
      return "Error";
    }
  }

  public String seConnecter(String nom, String mdp) {
    Utilisateur user = null;
    try {
      TypedQuery<Utilisateur> query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.nom = :nom AND u.mdp = :mdp",
          Utilisateur.class);
      query.setParameter("eamil", nom);
      query.setParameter("motDePasse", mdp);
      user = query.getSingleResult();
      em.persist(user);
      String token = new BigInteger(32 * 5, random).toString(32);
      user.setToken(token);
      return token;
    } catch (IllegalArgumentException | PersistenceException e) {
      return "Error";
    }
  }

  // Rajouter un etablissement
  public String ajouterEtablissement(String adresse, int SIREN, String nom, boolean entreprise, String image) {
    Etablissement etablissement = em.find(Etablissement.class, SIREN);
    if (etablissement == null) {
      Etablissement new_etab = new Etablissement(adresse, SIREN, nom, entreprise, image);
      em.persist(new_etab);
      return "Success";
    } else {
      return "Error";
    }
  }

  public String accepterDemande(int id_demande) {
    Demande demande_en_cours = em.find(Demande.class, id_demande);
    if (demande_en_cours.isValide()) {
      return "Error";
    } else {
      demande_en_cours.setValide(true);
      return "demande_validé";
    }
  }

  public String refuserDemande(int id_demande) {
    Demande demande_en_cours = em.find(Demande.class, id_demande);
    if (demande_en_cours.isRefuse()) {
      return "Error";
    } else {
      demande_en_cours.setRefuse(true);
      return "demande_refusé";
    }
  }

  public Collection<Etablissement> listeEtablissements() {
    return em.createQuery("select e from Etablissement e", Etablissement.class).getResultList();
  }

  public Collection<Domain> listeDomain() {
    return em.createQuery("select d from Domain d", Domain.class).getResultList();
  }

  public Collection<Utilisateur> utilEvent(int id) {
    Evenement event = em.find(Evenement.class, id);
    return event.getUtilisateurs_event();
  }

  public Demande idDemande(int id_demande) {
    return em.find(Demande.class, id_demande);
  }

  public Collection<Evenement> trierEvenement(String jour, String heure, String mois, String annee, String minute,
      String nom) {
    if (jour == null || heure == null || mois == null || annee == null || minute == null) {
      if (nom == null) {
        return em.createQuery("select e from Evenement e", Evenement.class).getResultList();
      } else {
        return em.createQuery("select e from Evenement e where etablissement_event == " + nom, Evenement.class)
            .getResultList();
      }
    } else {
      String date = "" + annee + "-" + mois + "-" + jour + " " + heure + ":" + minute;
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
      LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
      if (nom == null) {
        return em.createQuery("select e from Evenement e where creneau => " + dateTime, Evenement.class)
            .getResultList();
      } else {
        return em.createQuery(
            "select e from Evenement e where creneau => " + dateTime + " and etablissement_event == " + nom,
            Evenement.class).getResultList();
      }
    }
  }

  // Ca marche pas est je peux pas le débugger je sais pas ce que fait cette
  // fonction
  /*
   * public List<Evenement,Int> ficheEvenement(int id_event){
   * Etablissement entreprise = em.find(Etablissement.class,id_event);
   * Collection<Evenement> event_entreprise = entreprise.getEvenements_etab();
   * int note_tot = 0;
   * for ( Evenement event: event_entreprise){
   * note_tot += event.getAvis_event().getNote();
   * }
   * return (em.find(Evenement.class, id), note_tot/length(event_entreprise));
   * }
   */

  public void demandeReservation(String token, int id_event) {
    Utilisateur utilisateur = em.createQuery("select m from Utilisateur m where token == " + token, Utilisateur.class)
        .getSingleResult();
    Demande new_demande = new Demande(utilisateur, em.find(Evenement.class, id_event));
    utilisateur.getDemandes_util().add(new_demande);
  }

  public void seDesinscrire(String token, int id_event) {
    Evenement event = em.find(Evenement.class, id_event);
    Utilisateur utilisateur = em.createQuery("select m from Utilisateur m where token == " + token, Utilisateur.class)
        .getSingleResult();
    event.getUtilisateurs_event().remove(utilisateur);
    em.merge(event);
  }

  public void donnerAvis(int id_utilisateur, int id_event, String titre, String contenu, int note) {
    Avis new_avis = new Avis(titre, note, contenu, em.find(Utilisateur.class, id_utilisateur),
        em.find(Evenement.class, id_event));
    em.persist(new_avis);
  }

  // Mettre un creneau sous la forme: yyyy-MM-dd HH:mm
  public void ajouterEvenement(String titre, String description, int duree, String creneau, int id_etablissement) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime dateTime = LocalDateTime.parse(creneau, formatter);
    Evenement new_event = new Evenement(description, dateTime, em.find(Etablissement.class, id_etablissement), duree,
        titre);
    em.persist(new_event);
  }

}