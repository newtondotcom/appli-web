package modele;

import java.util.Collection;
import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Evenement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titre;
    private String description;
    private LocalDateTime creneau;
    private int duree;// Durée exprimer en min

    // PB confirme
    //@ManyToMany(mappedBy="evenements_util")
    //private Collection<Utilisateur> utilisateurs_event;

    @ManyToOne
    private Etablissement etablissement_event;

    @OneToMany(mappedBy="evenement_dem",fetch = FetchType.EAGER)
    private Collection<Demande> demandes_event;

    // PB confirme
    //@ManyToMany(mappedBy="evenement_dom",fetch = FetchType.LAZY)
    //private Collection<Domain> domains_event;

    @OneToMany(mappedBy="evenement_avis",fetch = FetchType.EAGER)
    private Collection<Avis> avis_event;

    // BOOLEEN POUR DIRE SI L EVENEMENT EST PASSE OU PAS

    public Evenement() {
        // Constructeur par défaut
    }
}