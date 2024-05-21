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
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.security.SecureRandom;
import java.math.BigInteger;
import org.mindrot.jbcrypt.BCrypt;

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
    Utilisateur util = new Utilisateur("Fredo", "1234", "20", true, "@test.com", "06", "2A", new_etab1, "1");
    em.persist(util);
    Domain dom1 = new Domain("IA");
    em.persist(dom1);
    Collection<Domain> domains = new HashSet<>();
    domains.add(dom1);
    Evenement event = new Evenement("cool", LocalDateTime.of(2024, Month.MAY, 15, 14, 30, 0), new_etab1, 60,
        "Super", domains);
    em.persist(event);
    Avis avis = new Avis("Super", 5, "Génial", util, event);
    em.persist(avis);
    Demande dem = new Demande("oui", util, event);
    em.persist(dem);
    util.getEvenements_util().add(event);
  }

  public LocalDateTime StringToTime(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
    return dateTime;
  }

  public boolean verifierToken(String token) {
    try {
      TypedQuery<Utilisateur> query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.token = :token",
          Utilisateur.class);
      query.setParameter("token", token);
      Utilisateur utilisateur = query.getSingleResult();
      return true;
    } catch (IllegalArgumentException | PersistenceException e) {
      return false;
    }
  }

  // Partie Eleves
  // Cette fonction revoie un boolean qui dit si l'enregistrement s'est bien passé
  // ou pas
  public String Enregistrer(String nom, String mdp, String INE, String mdp_admin, String email, String telephone,
      String classe,
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
      TypedQuery<Etablissement> query = em.createQuery(
          "SELECT e FROM Etablissement e WHERE e.nom = :nom",
          Etablissement.class);
      query.setParameter("nom", nom_etablissement);
      etablissement_util = query.getSingleResult();
      String token = new BigInteger(32 * 5, random).toString(32);
      String sel = BCrypt.gensalt(12);
      String mdpHacher = BCrypt.hashpw(mdp, sel);
      Utilisateur user = new Utilisateur(nom, mdpHacher, INE, admin, email,
          telephone, classe, etablissement_util, token);
      em.persist(user);
      user.setEtablissement_util(etablissement_util);
      return token;
    } catch (IllegalArgumentException | PersistenceException e) {
      // Gérer le cas où aucun établissement n'a était trouvé avec ce nom n'est trouvé
      return "Error";
    }
  }

  public String seConnecter(String email, String mdp) {
    Utilisateur user = null;
    try {
      TypedQuery<Utilisateur> query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.email = :email",
          Utilisateur.class);
      query.setParameter("email", email);
      user = query.getSingleResult();
      em.persist(user);
      String mdpHacher = user.getMdp();
      if (BCrypt.checkpw(mdp, mdpHacher)) {
        String token = new BigInteger(32 * 5, random).toString(32);
        user.setToken(token);
        return token;
      } else {
        return "Le mot de passe est incorrect.";
      }

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

  // Accepter une demande
  public String accepterDemande(int id_demande) {
    Demande demande_en_cours = em.find(Demande.class, id_demande);
    if (demande_en_cours.isValide()) {
      return "Error";
    } else {
      demande_en_cours.setValide(true);
      demande_en_cours.setRefuse(false);
      return "demande_validé";
    }
  }

  // Mettre présent une demande
  public String presentDemande(int id_demande) {
    Demande demande_en_cours = em.find(Demande.class, id_demande);
    if (demande_en_cours.isPresent()) {
      return "Error";
    } else {
      demande_en_cours.setPresent(true);
      return "demande_noté_présent";
    }
  }

  // Refuser une demande
  public String refuserDemande(int id_demande) {
    Demande demande_en_cours = em.find(Demande.class, id_demande);
    if (demande_en_cours.isRefuse()) {
      return "Error";
    } else {
      demande_en_cours.setRefuse(true);
      demande_en_cours.setValide(false);
      return "demande_refusé";
    }
  }

  // Lister tout les établissements
  public Collection<Etablissement> listeEtablissements() {
    return em.createQuery("select e from Etablissement e", Etablissement.class).getResultList();
  }

  // Lister tout les event
  public Collection<Evenement> listEvent() {
    return em.createQuery("select e from Evenement e", Evenement.class).getResultList();
  }

  // Lister tout les domains
  public Collection<Domain> listeDomain() {
    return em.createQuery("select d from Domain d", Domain.class).getResultList();
  }

  // Lister les utilisateurs d'un évenement
  public Collection<Utilisateur> utilEvent(int id) {
    Evenement event = em.find(Evenement.class, id);
    return event.getUtilisateurs_event();
  }

  // Renvoie la demande connaissant son id
  public Demande idDemande(int id_demande) {
    return em.find(Demande.class, id_demande);
  }

  // Enregister un nouvel évenement (un domain obligatoire)
  public String ajouterEvenement(String titre, String description, String duree, String creneau,
      String id_etablissement, String id_domain) {
    try {
      Collection<Domain> domains_event = new HashSet<>();
      domains_event.add(em.find(Domain.class, Integer.parseInt(id_domain)));
      Evenement new_event = new Evenement(description, StringToTime(creneau),
          em.find(Etablissement.class, Integer.parseInt(id_etablissement)), Integer.parseInt(duree),
          titre, domains_event);
      em.persist(new_event);
      return "Success";
    } catch (IllegalArgumentException | PersistenceException | DateTimeParseException e) {
      return "Error";
    }
  }

  // Donne la liste des évenements d'un établissement
  public Collection<Evenement> lister_event_etab(int id_etab) {
    Etablissement etab = em.find(Etablissement.class, id_etab);
    return etab.getEvenements_etab();
  }

  public String modifer_event_attribut(int id, String type_champs, String champs) {
    try {
      Evenement event = em.find(Evenement.class, id);
      switch (type_champs) {
        case "titre":
          event.setTitre(champs);
          break;
        case "description":
          event.setDescription(champs);
          break;
        case "creneau":
          event.setCreneau(StringToTime(champs));
          break;
        case "duree":
          event.setDuree(Integer.parseInt(champs));
          break;
        case "etablissement":
          Etablissement etab = em.find(Etablissement.class, Integer.parseInt(champs));
          event.setEtablissement_event(etab);
          break;
        default:
          return "Invalid field type";
      }
      return "Success";
    } catch (IllegalArgumentException | PersistenceException e) {
      return "Error";
    }

  }

  public String modifier_etablissement_attribut(int id, String type_champs, String champs) {
    try {
      Etablissement etablissement = em.find(Etablissement.class, id);
      switch (type_champs) {
        case "adresse":
          etablissement.setAdresse(champs);
          break;
        case "nom":
          etablissement.setNom(champs);
          break;
        case "entreprise":
          etablissement.setEntreprise(Boolean.parseBoolean(champs));
          break;
        case "image":
          etablissement.setImage(champs);
          break;
        default:
          return "Invalid field type";
      }
      return "Success";
    } catch (IllegalArgumentException | PersistenceException e) {
      return "Error: " + e.getMessage();
    }
  }

  // Lister stat event

  public float[] liste_stat_event(int id) {
    Evenement event = em.find(Evenement.class, id);
    float[] stats = new float[4];
    Collection<Demande> demandes = event.getDemandes_event();
    stats[0] = demandes.size();
    int accepter = 0;
    int present = 0;
    for (Demande dem : demandes) {
      if (dem.isValide()) {
        accepter += 1;
      }
      if (dem.isPresent()) {
        present += 1;
      }
    }
    Collection<Avis> avis = event.getAvis_event();
    int somme_note = 0;
    for (Avis av : avis) {
      somme_note += av.getNote();
    }
    float moyenne = somme_note / avis.size();
    stats[1] = accepter;
    stats[2] = present;
    stats[3] = moyenne;
    return stats;
  }

  // Donne l'évenement suivant son id

  public Evenement get_evenement_from_id(int id) {
    return em.find(Evenement.class, id);
  }

  // Donne la liste des evenements d'un utilisateur
  public Collection<Evenement> get_evenement_from_uid(int id_util) {
    Utilisateur util = em.find(Utilisateur.class, id_util);
    return util.getEvenements_util();
  }

  public String creer_demande(String motivation, int id_etudiant, int id_evenement) {
    try {
      Utilisateur util = em.find(Utilisateur.class, id_etudiant);
      Evenement event = em.find(Evenement.class, id_evenement);
      Demande dem = new Demande(motivation, util, event);
      em.persist(dem);
      return "Success";
    } catch (IllegalArgumentException | PersistenceException e) {
      return "Error";
    }
  }
  // public Collection<Evenement> trierEvenement(String jour, String heure, String
  // mois, String annee, String minute,
  // String nom) {
  // if (jour == null || heure == null || mois == null || annee == null || minute
  // == null) {
  // if (nom == null) {
  // return em.createQuery("select e from Evenement e",
  // Evenement.class).getResultList();
  // } else {
  // return em.createQuery("select e from Evenement e where etablissement_event ==
  // " + nom, Evenement.class)
  // .getResultList();
  // }
  // } else {
  // String date = "" + annee + "-" + mois + "-" + jour + " " + heure + ":" +
  // minute;
  // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd
  // HH:mm");
  // LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
  // if (nom == null) {
  // return em.createQuery("select e from Evenement e where creneau => " +
  // dateTime, Evenement.class)
  // .getResultList();
  // } else {
  // return em.createQuery(
  // "select e from Evenement e where creneau => " + dateTime + " and
  // etablissement_event == " + nom,
  // Evenement.class).getResultList();
  // }
  // }
  // }

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

  // public void demandeReservation(String token, int id_event) {
  // Utilisateur utilisateur = em.createQuery("select m from Utilisateur m where
  // token == " + token, Utilisateur.class)
  // .getSingleResult();
  // Demande new_demande = new Demande(utilisateur, em.find(Evenement.class,
  // id_event));
  // utilisateur.getDemandes_util().add(new_demande);
  // }

  // public void seDesinscrire(String token, int id_event) {
  // Evenement event = em.find(Evenement.class, id_event);
  // Utilisateur utilisateur = em.createQuery("select m from Utilisateur m where
  // token == " + token, Utilisateur.class)
  // .getSingleResult();
  // event.getUtilisateurs_event().remove(utilisateur);
  // em.merge(event);
  // }

  // public void donnerAvis(int id_utilisateur, int id_event, String titre, String
  // contenu, int note) {
  // Avis new_avis = new Avis(titre, note, contenu, em.find(Utilisateur.class,
  // id_utilisateur),
  // em.find(Evenement.class, id_event));
  // em.persist(new_avis);
  // }

}