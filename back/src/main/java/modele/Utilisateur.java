package modele;

import java.util.Collection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String mdp;
    private String INE;
    private boolean admin; // True si c'est un administrateur
    private String email;
    private String telephone;
    private boolean token; // Mise en place du token de la session

    // L'établissement auquel il appartient
    @ManyToOne
    private Etablissement etablissement_util;

    // PB confirme
    // Les évenements auquels il a participé / participera
    //@ManyToMany(mappedBy="utilisateurs_event",fetch = FetchType.LAZY)
    //private Collection<Evenement> evenements_util;

    // Les demandes qu'il a envoyé
    @OneToMany(mappedBy="utilisateur_dem",fetch = FetchType.EAGER)
    private Collection<Demande> demandes_util;

    // Les documents qu'il a envoyé ou qu'il a reçu
    @OneToMany(mappedBy="utilisateur_doc",fetch = FetchType.EAGER)
    private Collection<Document> documents_util;

    // Les avis qu'il a laissé sur des évenements
    @OneToMany(mappedBy="utilisateur_avis",fetch = FetchType.EAGER)
    private Collection<Avis> avis_util;

    public Utilisateur() {}
}
