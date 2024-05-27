package vue;

import modele.*;
import jakarta.ejb.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.print.Doc;
import javax.print.DocFlavor;

import java.security.SecureRandom;
import java.math.BigInteger;
import org.mindrot.jbcrypt.BCrypt;
import java.util.concurrent.TimeUnit;
import io.minio.MinioClient;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.http.Method;
import io.minio.MinioClient;



/**
 * Facade
 */
@Singleton
public class Facade {

  @PersistenceContext
  private EntityManager em;

  private SecureRandom random = new SecureRandom();

  private MinioClient minioClient = MinioClient.builder().endpoint("localhost", 9000, false).credentials("minioadmin", "minioadmin123").build();

  public void initialisation() {
    // Utilisateur 1 Entreprise
    Etablissement new_etab1 = new Etablissement("4 rue test1", 1, "IKEA", "meuble", true, "chat");
    em.persist(new_etab1);
    String sel = BCrypt.gensalt(12);
    String mdpHacher = BCrypt.hashpw("1234", sel);
    Utilisateur util = new Utilisateur("Fredo", mdpHacher, "f@test.com", new_etab1, "1");
    em.persist(util);
    // Création d'une école
    Etablissement new_etab2 = new Etablissement("4 rue test2", 2, "ENSEEIHT", "Ecole", false, "ecole");
    em.persist(new_etab2);
    // Création d'un évenement
    Domain dom1 = new Domain("IA");
    Domain dom2 = new Domain("tech");
    em.persist(dom1);
    em.persist(dom2);
    Evenement event = new Evenement("cool", LocalDateTime.of(2024, Month.MAY, 15, 14, 30, 0), new_etab1, 60,
        "Super");
    em.persist(event);
    event.getDomains_event().add(dom1);
    Avis avis = new Avis("Super", 5, "Génial", util, event);
    em.persist(avis);
    Demande dem = new Demande("oui", util, event);
    em.persist(dem);
    util.getEvenements_util().add(event);
    String name = new BigInteger(32 * 5, random).toString(32);
    Document doc = new Document(util, name);
    em.persist(doc);
  }

  public LocalDateTime StringToTime(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
    return dateTime;
  }

  public boolean verifierToken(String token) {
    System.out.println("Vérification du token : " + token);
    try {
      TypedQuery<Utilisateur> query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.token = :token",
          Utilisateur.class);
      query.setParameter("token", token);
      Utilisateur utilisateur = query.getSingleResult();
      System.out.println("Token valide");
      return true;
    } catch (IllegalArgumentException | PersistenceException e) {
      return false;
    }
  }

  // Partie Eleves
  // Cette fonction revoie un boolean qui dit si l'enregistrement s'est bien passé
  // ou pas
  public String Enregistrer(String nomComplet, String mdp, String email, String siren) {
    // Recherche l'établissement suivant le siren
    Etablissement etablissement_util = null;
    try {
      etablissement_util = em.find(Etablissement.class, Integer.parseInt(siren));
      String token = new BigInteger(32 * 5, random).toString(32);
      String sel = BCrypt.gensalt(12);
      String mdpHacher = BCrypt.hashpw(mdp, sel);
      Utilisateur user = new Utilisateur(nomComplet, mdpHacher, email, etablissement_util, token);
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
  public String ajouterEtablissement(String adresse, int SIREN, String nom, String description, boolean entreprise,
      String image) {
    Etablissement etablissement = em.find(Etablissement.class, SIREN);
    if (etablissement == null) {
      Etablissement new_etab = new Etablissement(adresse, SIREN, nom, description, entreprise, image);
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

  // Mettre absent une demande
  public String absentDemande(int id_demande) {
    Demande demande_en_cours = em.find(Demande.class, id_demande);
    if (demande_en_cours.isPresent()) {
      return "Error";
    } else {
      demande_en_cours.setPresent(false);
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
      int year = Integer.parseInt(creneau.substring(0, 4));
      int month = Integer.parseInt(creneau.substring(5, 7));
      int day = Integer.parseInt(creneau.substring(8, 10));
      int hour = Integer.parseInt(creneau.substring(11, 13));
      int minute = Integer.parseInt(creneau.substring(14, 16));
      LocalDateTime date = LocalDateTime.of(year, month, day, hour, minute);
      Evenement new_event = new Evenement(description, date,
          em.find(Etablissement.class, Integer.parseInt(id_etablissement)), Integer.parseInt(duree),
          titre);
      em.persist(new_event);
      String[] doms = id_domain.split(",");
      // Convertir chaque nombre en entier et les parcourir
      for (String numberAsString : doms) {
        int id_dom = Integer.parseInt(numberAsString);
        Domain domain_event = em.find(Domain.class, id_dom);
        new_event.getDomains_event().add(domain_event);
      }
      return "Success";
    } catch (IllegalArgumentException | PersistenceException | DateTimeParseException e) {
      return e.toString();
    }
  }

  // Donne la liste des évenements d'un établissement
  public Collection<Evenement> lister_event_etab(String token) {
    TypedQuery<Utilisateur> query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.token = :token",
        Utilisateur.class);
    query.setParameter("token", token);
    Utilisateur user = query.getSingleResult();
    Etablissement etab = user.getEtablissement_util();
    return etab.getEvenements_etab();
  }

  public String modifer_event_attribut(String titre, String description, String creneau,
      String duree, String id_domain_event, String id_event, String id_etablissement_event) {
    try {
      int year = Integer.parseInt(creneau.substring(0, 4));
      int month = Integer.parseInt(creneau.substring(5, 7));
      int day = Integer.parseInt(creneau.substring(8, 10));
      int hour = Integer.parseInt(creneau.substring(11, 13));
      int minute = Integer.parseInt(creneau.substring(14, 16));
      LocalDateTime date = LocalDateTime.of(year, month, day, hour, minute);
      Evenement event = em.find(Evenement.class, Integer.parseInt(id_event));
      if (event != null) {
        event.setTitre(titre);
        event.setDescription(description);
        event.setCreneau(date);
        event.setDuree(Integer.parseInt(duree));
        Etablissement etab = em.find(Etablissement.class,
            Integer.parseInt(id_etablissement_event));
        event.setEtablissement_event(etab);
        String[] doms = id_domain_event.split(",");
        // Convertir chaque nombre en entier et les parcourir
        Collection<Domain> domains_event = new HashSet<>();
        for (String dom : doms) {
          int id_dom = Integer.parseInt(dom);
          Domain domain_event = em.find(Domain.class, id_dom);
          domains_event.add(domain_event);
        }
        event.setDomains_event(domains_event);
        return "Modifier";
      } else {
        ajouterEvenement(titre, description, duree, creneau, id_etablissement_event, id_domain_event);
        return "Créer";
      }
    } catch (IllegalArgumentException | PersistenceException e) {
      ajouterEvenement(titre, description, duree, creneau, id_etablissement_event, id_domain_event);
      return "Créer";
    }

  }

  public String modifier_etablissement_attribut(String adresse, int SIREN, String nom, String description,
      boolean entreprise,
      String image) {
    try {
      Etablissement etablissement = em.find(Etablissement.class, SIREN);
      etablissement.setAdresse(adresse);
      etablissement.setDescription(description);
      etablissement.setEntreprise(entreprise);
      etablissement.setImage(image);
      etablissement.setNom(nom);
      return "Modifier";
    } catch (IllegalArgumentException | PersistenceException e) {
      ajouterEtablissement(adresse, SIREN, nom, description, entreprise, image);
      return "Ajouter";
    }
  }

  public String modifier_utilisateur(String nomComplet, String email, String telephone,
      String classe, String id_util) {
    Utilisateur util = em.find(Utilisateur.class, Integer.parseInt(id_util));
    if (util != null) {
      util.setEmail(email);
      util.setNom(nomComplet);
      util.setTelephone(telephone);
      util.setClasse(classe);
      return "Modifier";
    } else {
      return "Erreur";
    }
  }

  public String modifier_ine(String INE, String id_util) {
    Utilisateur util = em.find(Utilisateur.class, Integer.parseInt(id_util));
    if (util != null) {
      util.setINE(INE);
      return "Modifier";
    } else {
      return "Erreur";
    }
  }

  // Lister stat event
  // Renvoie
  // 0 : Nombre de demandes
  // 1 : Nombre de demandes acceptées
  // 2 : Nombre de demandes présentes
  // 3 : Moyenne des notes laisées
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
    float moyenne;
    if (avis.size() != 0){
      moyenne = somme_note / avis.size();
    }
    moyenne = -1;
    stats[1] = accepter;
    stats[2] = present;
    stats[3] = moyenne;
    return stats;
  }

  // Donne l'évenement suivant son id

  public Evenement get_evenement_from_id(int id) {
    return em.find(Evenement.class, id);
  }

  public Evenement get_evenement_from_id_demande(int id){
    Demande dem = em.find(Demande.class,id);
    return dem.getEvenement_dem();
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

  // Donne l'établissement d'un évenement
  public Etablissement get_etab_from_eventid(int id_event) {
    Evenement event = em.find(Evenement.class, id_event);
    return event.getEtablissement_event();
  }

  // note moyenne d'un établissement

  // Donne les domains d'un évenement
  public Collection<Domain> get_domains_from_eventid(int id_event) {
    Evenement event = em.find(Evenement.class, id_event);
    return event.getDomains_event();
  }

  // Donne les avis d'un évenement
  public Collection<Avis> get_avis_from_eventid(int id_event) {
    Evenement event = em.find(Evenement.class, id_event);
    return event.getAvis_event();
  }

  // Dit si une demande existe avec un évenement et un utilisateur
  public boolean get_bool_demande_from_eventid_utilid(int id_event, int id_util) {
    try {

      Utilisateur utilisateur = em.find(Utilisateur.class, id_util);
      Evenement evenement = em.find(Evenement.class, id_event);

      TypedQuery<Demande> query = em.createQuery(
          "SELECT d FROM Demande d WHERE d.utilisateur_dem = :utilisateur AND d.evenement_dem = :evenement",
          Demande.class);
      query.setParameter("utilisateur", utilisateur);
      query.setParameter("evenement", evenement);

      Demande demande = query.getSingleResult();
      return true;
    } catch (IllegalArgumentException | PersistenceException e) {
      return false;
    }
  }

  // Dit si une demande existe avec un évenement et un utilisateur
  public Demande get_demande_from_eventid_utilid(int id_event, int id_util) {
    try {

      Utilisateur utilisateur = em.find(Utilisateur.class, id_util);
      Evenement evenement = em.find(Evenement.class, id_event);

      TypedQuery<Demande> query = em.createQuery(
          "SELECT d FROM Demande d WHERE d.utilisateur_dem = :utilisateur AND d.evenement_dem = :evenement",
          Demande.class);
      query.setParameter("utilisateur", utilisateur);
      query.setParameter("evenement", evenement);

      Demande demande = query.getSingleResult();
      return demande;
    } catch (IllegalArgumentException | PersistenceException e) {
      return null;
    }
  }

  public Collection<Demande> get_demandes_from_eventid(int id_event) {
    Evenement event = em.find(Evenement.class, id_event);
    return event.getDemandes_event();
  }

  public Collection<Utilisateur> get_liste_postulants_from_eventid(int id_event) {
    Evenement event = em.find(Evenement.class, id_event);
    Collection<Demande> demandes = event.getDemandes_event();
    Collection<Utilisateur> utilisateurs = new HashSet<Utilisateur>();
    for (Demande dem : demandes) {
      utilisateurs.add(dem.getUtilisateur_dem());
    }
    return utilisateurs;
  }

  // Donne les attributs d'un utilisateurs
  public Utilisateur get_util_from_uid(int id_util) {
    return em.find(Utilisateur.class, id_util);
  }

  // Donne l' Etablissement d'un utilisateurs
  public Etablissement get_etab_from_uid(int id_util) {
    Utilisateur util = em.find(Utilisateur.class, id_util);
    return util.getEtablissement_util();
  }

  public Etablissement get_etab_from_token(String token) {
    TypedQuery<Utilisateur> query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.token = :token",
        Utilisateur.class);
    query.setParameter("token", token);
    Utilisateur utilisateur = query.getSingleResult();
    return utilisateur.getEtablissement_util();
  }

  // Donne les Demandes d'un utilisateurs
  public Collection<Demande> get_demande_from_uid(int id_util) {
    Utilisateur util = em.find(Utilisateur.class, id_util);
    return util.getDemandes_util();
  }

  // Donne les Documents d'un utilisateurs
  public Collection<Document> get_doc_from_uid(int id_util) {
    Utilisateur util = em.find(Utilisateur.class, id_util);
    return util.getDocuments_util();
  }

  // Donne les Documents d'un utilisateurs
  public Collection<Avis> get_avis_from_uid(int id_util) {
    Utilisateur util = em.find(Utilisateur.class, id_util);
    return util.getAvis_util();
  }

  // Donne la liste des evenements d'un utilisateur
  public Collection<Evenement> get_evenement_from_uid(int id_util) {
    Utilisateur util = em.find(Utilisateur.class, id_util);
    return util.getEvenements_util();
  }

  // Donne l'id de l'utilisateur avec le token
  public int get_uid_from_token(String token) {
    TypedQuery<Utilisateur> query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.token = :token",
        Utilisateur.class);
    query.setParameter("token", token);
    Utilisateur user = query.getSingleResult();
    return user.getId();
  }

  public Collection<Etablissement> lister_ecole() {
    TypedQuery<Etablissement> query = em.createQuery("SELECT e FROM Etablissement e WHERE e.entreprise = :entreprise",
        Etablissement.class);
    query.setParameter("entreprise", false);
    List<Etablissement> etabs = query.getResultList();
    return etabs;
  }

  public Collection<Etablissement> lister_entreprise() {
    TypedQuery<Etablissement> query = em.createQuery("SELECT e FROM Etablissement e WHERE e.entreprise = :entreprise",
        Etablissement.class);
    query.setParameter("entreprise", true);
    List<Etablissement> etabs = query.getResultList();
    return etabs;
  }

  public Utilisateur get_util_from_id_dem(String idem) {
    Demande dem = em.find(Demande.class, Integer.parseInt(idem));
    return dem.getUtilisateur_dem();
  }

  public boolean get_bool_type_util_from_uid(String uid) {
    Utilisateur util = em.find(Utilisateur.class, Integer.parseInt(uid));
    return util.getEtablissement_util().isEntreprise();
  }

  public String register_document(String  token){
    TypedQuery<Utilisateur> query = em.createQuery("SELECT u FROM Utilisateur u WHERE u.token = :token",Utilisateur.class);
    query.setParameter("token", token);
    Utilisateur utilisateur = query.getSingleResult();
    String nomAleatoire = new BigInteger(32 * 5, random).toString(32);
    Document doc = new Document(utilisateur, nomAleatoire);
    em.persist(doc);
    String path = "";
    try {
    path =
    minioClient.getPresignedObjectUrl(
       GetPresignedObjectUrlArgs.builder()
           .method(Method.PUT)
           .bucket("docs")
           .object(nomAleatoire)
           .expiry(1, TimeUnit.DAYS)
           .build());
    } catch (Exception e) {
      path = e.toString();
    }
    return path;
  }

  public String read_document_demande(int id_dem){
    Demande dem = em.find(Demande.class, id_dem);
    Utilisateur util = dem.getUtilisateur_dem();
    String querystring = "SELECT d FROM Document d WHERE d.utilisateur_doc = :utilisateur";
    TypedQuery<Document> query = em.createQuery(querystring,Document.class);
    query.setParameter("utilisateur", util);
    Document doc  = query.getSingleResult();
    String nameS3 = doc.getName();
    String url_access = "";
    try{
    url_access = minioClient.getPresignedObjectUrl(
        GetPresignedObjectUrlArgs.builder()
            .method(Method.GET)
            .bucket("docs")
            .object(nameS3)
            .expiry(1, TimeUnit.DAYS)
            .build());
    } catch (Exception e) {
      url_access = e.toString();
    }
    return url_access;
  }

}
