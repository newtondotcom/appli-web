package pack;

import java.io.IOException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vue.Facade;
import modele.*;
import java.util.Collection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDateTime;

@WebServlet("/Serv")
public class Serv extends HttpServlet {
  @EJB
  Facade facade;

  public Serv() {
    super();
  }

  Gson gson = new GsonBuilder()
      .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
      .excludeFieldsWithoutExposeAnnotation()
      .create();

  public String getToken(Cookie[] cookies) {
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("token")) {
          return cookie.getValue();
        }
      }
    } else {
      return "mauvais";
    }
    return "mauvais";
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Cookie[] cookies = request.getCookies();
    String token = getToken(cookies);

    String op = request.getParameter("op");

    // Initialise la BD avec des entitées
    if (op.equals("init")) {
      facade.initialisation();
      String json = gson.toJson("OK");
      response.getWriter().write(json);
    }

    if (facade.verifierToken(token)) {
      // NULL -> Lister tout les établissements et domaines
      if (op.equals("lister_etab_domain")) {
        Collection<Etablissement> listeetab = facade.listeEtablissements();
        Collection<Domain> listedom = facade.listeDomain();
        String json1 = gson.toJson(listeetab);
        String json2 = gson.toJson(listedom);
        String json = json1 + json2;
        response.getWriter().write(json);
      }
      // NULL -> Lister tout les établissements et domaines
      if (op.equals("lister_event")) {
        Collection<Evenement> listEvent = facade.listEvent();
        String json = gson.toJson(listEvent);
        response.getWriter().write(json);
      }
      // ID_Event -> Utilisateur qui on envoyé des demandes
      if (op.equals("lister_util_event")) {
        int id = Integer.parseInt(request.getParameter("id"));
        Collection<Utilisateur> listeutil = facade.utilEvent(id);
        String json = gson.toJson(listeutil);
        response.getWriter().write(json);
      }
      // ID_Demande -> Demande
      if (op.equals("get_demande_from_id")) {
        int id = Integer.parseInt(request.getParameter("id"));
        Demande dem = facade.idDemande(id);
        String json = gson.toJson(dem);
        response.getWriter().write(json);
      }
      // id établissement -> La liste évenement de l'établissement
      if (op.equals("lister_event_etab")) {
        int id = Integer.parseInt(request.getParameter("id"));
        Collection<Evenement> coll_event = facade.lister_event_etab(id);
        String json = gson.toJson(coll_event);
        response.getWriter().write(json);
      }
      // Lister les stats d'un evenement
      if (op.equals("lister_stat_event")) {
        int id = Integer.parseInt(request.getParameter("id"));

        float[] stat = facade.liste_stat_event(id);
        String json = gson.toJson(stat);
        response.getWriter().write(json);
      }
      // id Evenement -> Evenement
      if (op.equals("get_evenement_from_id")) {
        int id = Integer.parseInt(request.getParameter("id"));

        Evenement event = facade.get_evenement_from_id(id);
        String json = gson.toJson(event);
        response.getWriter().write(json);
      }
      // Utilisateur id -> Liste_evenement
      if (op.equals("get_evenement_from_uid")) {
        int id = Integer.parseInt(request.getParameter("id"));

        Collection<Evenement> events = facade.get_evenement_from_uid(id);
        String json = gson.toJson(events);
        response.getWriter().write(json);
      }
    } else {
      String json = gson.toJson("Mauvais_Token");
      response.getWriter().write(json);
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Cookie[] cookies = request.getCookies();
    String token = getToken(cookies);

    boolean fct_sans_token = false;

    String op = request.getParameter("op");
    // Information_Utilisateur -> Si l'enregistemenent a était fait
    if (op.equals("enregistrer_util")) {
      String nom = request.getParameter("nom");
      String mdp = request.getParameter("mdp");
      String INE = request.getParameter("ine");
      String mdp_admin = request.getParameter("mdp_admin");
      String email = request.getParameter("email");
      String telephone = request.getParameter("telephone");
      String classe = request.getParameter("classe");
      String nom_etablissement = request.getParameter("nom_etablissement");
      String msg = facade.Enregistrer(nom, mdp, INE, mdp_admin, email, telephone, classe, nom_etablissement);
      String json = gson.toJson(msg);
      response.getWriter().write(json);
      fct_sans_token = true;
    }
    // Mail et Mdp -> Tokken si ok ou Error sinon
    if (op.equals("seconnecter")) {
      String email = request.getParameter("email");
      String mdp = request.getParameter("mdp");
      String msg = facade.seConnecter(email, mdp);
      String json = gson.toJson(msg);
      response.getWriter().write(json);
      fct_sans_token = true;
    }

    if (facade.verifierToken(token)) {
      // Information_etablissement -> Si l'enregistemenent a était fait
      if (op.equals("ajouter_etab")) {
        String adresse = request.getParameter("adresse");
        int SIREN = Integer.parseInt(request.getParameter("SIREN"));
        String nom = request.getParameter("nom");
        String entrepriseParam = request.getParameter("entreprise");
        boolean estEntreprise = Boolean.parseBoolean(entrepriseParam);
        String image = request.getParameter("image");
        String msg = facade.ajouterEtablissement(adresse, SIREN, nom, estEntreprise, image);
        String json = gson.toJson(msg);
        response.getWriter().write(json);
      }

      // NULL -> Success si validé ou Error sinon
      if (op.equals("validerdemande")) {
        int id = Integer.parseInt(request.getParameter("id"));
        String msg = facade.accepterDemande(id);
        String json = gson.toJson(msg);
        response.getWriter().write(json);
      }
      // NULL -> Success si refusé ou Error sinon
      if (op.equals("refuserdemande")) {
        int id = Integer.parseInt(request.getParameter("id"));
        String msg = facade.refuserDemande(id);
        String json = gson.toJson(msg);
        response.getWriter().write(json);
      }
      // Mettre présent
      if (op.equals("presentdemande")) {
        int id = Integer.parseInt(request.getParameter("id"));
        String msg = facade.presentDemande(id);
        String json = gson.toJson(msg);
        response.getWriter().write(json);
      }
      // token -> Création d'un évenement
      if (op.equals("créerEvent")) {
        String titre = request.getParameter("titre");
        String description = request.getParameter("description");
        String creneau = request.getParameter("creneau");
        String duree = request.getParameter("duree");
        String id_etablissement_event = request.getParameter("id_etablissement_event");
        String id_domains_event = request.getParameter("id_domains_event");
        String msg = facade.ajouterEvenement(titre, description, duree, creneau, id_etablissement_event,
            id_domains_event);
        String json = gson.toJson(msg);
        response.getWriter().write(json);
      }
      // Modification champs evenement
      if (op.equals("modifier_event")) {
        int id = Integer.parseInt(request.getParameter("id"));
        // Le type du champs de l'entité à modfier
        String type_champs = request.getParameter("type_champs");
        // Le champs de l'entité à modfier
        String champs = request.getParameter("champs");

        String msg = facade.modifer_event_attribut(id, type_champs, champs);
        String json = gson.toJson(msg);
        response.getWriter().write(json);
      }
      // Modification d'un evenement
      if (op.equals("modifier_etablissement")) {
        int id = Integer.parseInt(request.getParameter("id"));
        String type_champs = request.getParameter("type_champs");
        // Si le champs et une établissement on indiquera son id (SIREN)
        String champs = request.getParameter("champs");

        String msg = facade.modifier_etablissement_attribut(id, type_champs, champs);
        String json = gson.toJson(msg);
        response.getWriter().write(json);
      }
      // Création d'une demande
      if (op.equals("creer_demande")) {
        String motivation = request.getParameter("motivation");
        int id_etudiant = Integer.parseInt(request.getParameter("id_etudiant"));
        int id_evenement = Integer.parseInt(request.getParameter("id_evenement"));

        String msg = facade.creer_demande(motivation, id_etudiant, id_evenement);
        String json = gson.toJson(msg);
        response.getWriter().write(json);
      }
    } else {
      if (!fct_sans_token) {
        String json = gson.toJson("Mauvais_Token");
        response.getWriter().write(json);
      }
    }
  }

}
