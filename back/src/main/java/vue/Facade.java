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
  public String SeConnecter(String nom, String mdp) {
    Utilisateur utilisateur = em.createQuery("select m from Utilisateur m where nom == "+nom+" and mdp == "+mdp, Utilisateur.class).getSingleResult();
    return utilisateur.getId();
  }
}