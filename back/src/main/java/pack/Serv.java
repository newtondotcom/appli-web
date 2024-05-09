package pack;

import java.io.IOException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vue.Facade;

@WebServlet("/Serv")
public class Serv extends HttpServlet {
  @EJB
  Facade facade;
  public Serv() {
    super();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      doPost(request,response);
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

      String op =  request.getParameter("op");
      if (op.equals("acceuil")) {
        String ac =  request.getParameter("action");
        if (ac.equals("enregistrer")){
          request.setAttribute("msg", "Bienvenue sur la page d'enregistrement");
          request.getRequestDispatcher("enregistrer.jsp").forward(request, response);
        }
        if (ac.equals("seconnecter")){
          request.setAttribute("msg", "Bienvenue sur la page de connexion");
          request.getRequestDispatcher("seconnecter.jsp").forward(request, response);
        }
      }
      if (op.equals("ajouteretab")) {
        String adresse = request.getParameter("adresse");
        String SIREN = request.getParameter("SIREN");
        String nom = request.getParameter("nom");
        String entrepriseParam = request.getParameter("entreprise");
        boolean estEntreprise = Boolean.parseBoolean(entrepriseParam);
        String image = request.getParameter("image");
        facade.ajouterEtablissement(adresse, SIREN, nom, estEntreprise, image);
        request.getRequestDispatcher("acceuil.html").forward(request, response);
      }
      if (op.equals("enregistrer")) {
        String nom = request.getParameter("nom");
        String mdp = request.getParameter("mdp");
        String INE = request.getParameter("ine");
        String mdp_admin = request.getParameter("mdp_admin");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        String nom_etablissement = request.getParameter("nom_etablissement");
        boolean reussi = facade.Enregistrer( nom, mdp, INE, mdp_admin, email, telephone, nom_etablissement) ;
        if (reussi) {
          request.setAttribute("msg", "Enregistrement réussi, veuillez vous connecter.");
          request.getRequestDispatcher("seconnecter.jsp").forward(request, response);
        } else {
          request.setAttribute("msg", "Vous avez rentré un établissement qui n'est pas connu");
          request.getRequestDispatcher("enregistrer.jsp").forward(request, response);
        }
      }
      if (op.equals("seconnecter")) {
        String nom = request.getParameter("nom");
        String mdp = request.getParameter("mdp");
        boolean reussi = facade.seConnecter(nom, mdp);
        if (reussi) {
          request.setAttribute("msg", "Connexion réussi");
          request.getRequestDispatcher("menu.jsp").forward(request, response);
        } else {
          request.setAttribute("msg", "Mauvais mot de passe ou mauvais identifiant");
          request.getRequestDispatcher("seconnecter.jsp").forward(request, response);
        }
      }
  }
}
