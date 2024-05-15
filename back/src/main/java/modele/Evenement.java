package modele;

import java.time.LocalDateTime;
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
public class Evenement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titre;
    private String description;
    private LocalDateTime creneau;
    private int duree;// Durée exprimer en min

    @ManyToMany(mappedBy = "evenements_util", fetch = FetchType.EAGER)
    private Collection<Utilisateur> utilisateurs_event;

    @ManyToOne
    private Etablissement etablissement_event;

    @OneToMany(mappedBy = "evenement_dem", fetch = FetchType.EAGER)
    private Collection<Demande> demandes_event;

    @ManyToMany(mappedBy = "evenement_dom", fetch = FetchType.EAGER)
    private Collection<Domain> domains_event;

    @OneToMany(mappedBy = "evenement_avis", fetch = FetchType.EAGER)
    private Collection<Avis> avis_event;

    // BOOLEEN POUR DIRE SI L EVENEMENT EST PASSE OU PAS

    public Evenement() {
        // Constructeur par défaut
    }

    public Evenement(String description, LocalDateTime creneau, Etablissement etablissement_event, int duree,
            String titre) {
        this.description = description;
        this.creneau = creneau;
        this.etablissement_event = etablissement_event;
        this.duree = duree;
        this.titre = titre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreneau() {
        return creneau;
    }

    public void setCreneau(LocalDateTime creneau) {
        this.creneau = creneau;
    }

    public Collection<Utilisateur> getUtilisateurs_event() {
        return utilisateurs_event;
    }

    public void setUtilisateurs_event(Collection<Utilisateur> utilisateurs_event) {
        this.utilisateurs_event = utilisateurs_event;
    }

    public Etablissement getEtablissement_event() {
        return etablissement_event;
    }

    public void setEtablissement_event(Etablissement etablissement_event) {
        this.etablissement_event = etablissement_event;
    }

    public Collection<Demande> getDemandes_event() {
        return demandes_event;
    }

    public void setDemandes_event(Collection<Demande> demandes_event) {
        this.demandes_event = demandes_event;
    }

    public Collection<Domain> getDomains_event() {
        return domains_event;
    }

    public void setDomains_event(Collection<Domain> domains_event) {
        this.domains_event = domains_event;
    }

    public Collection<Avis> getAvis_event() {
        return avis_event;
    }

    public void setAvis_event(Collection<Avis> avis_event) {
        this.avis_event = avis_event;
    }

    public int getDuree() {
        return this.duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public void setTitre(String title) {
        this.titre = title;
    }

    public String getTitre() {
        return this.titre;
    }
}