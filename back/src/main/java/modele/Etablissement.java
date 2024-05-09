package modele;

import java.util.Collection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Etablissement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String adresse;
    private String SIREN;
    private String nom;
    private boolean entreprise; // true si c'est une entreprise et false si c'est un étudiant
    private String image;

    // Les utilisateurs relié
    @OneToMany(mappedBy="etablissement_util",fetch = FetchType.EAGER)
    private Collection<Utilisateur> utilisateur_etab;

    // Les évenements organisé
    @OneToMany(mappedBy="etablissement_event",fetch = FetchType.EAGER)
    private Collection<Evenement> evenements_etab;

    // PB confirme
    // Les domains associé
    //@ManyToMany(mappedBy="etablissements_dom",fetch = FetchType.LAZY)
    //private Collection<Domain> domains_etab;

    public Etablissement() {
        // Constructeur par défaut
    }
}
