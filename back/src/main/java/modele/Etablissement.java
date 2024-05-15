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

    // // Les utilisateurs relié
    @OneToMany(mappedBy = "etablissement_util", fetch = FetchType.EAGER)
    private Collection<Utilisateur> utilisateur_etab;

    // Les évenements organisé
    @OneToMany(mappedBy = "etablissement_event", fetch = FetchType.EAGER)
    private Collection<Evenement> evenements_etab;

    // Les domains associé
    @ManyToMany(mappedBy = "etablissements_dom", fetch = FetchType.EAGER)
    private Collection<Domain> domains_etab;

    public Etablissement() {
        // Constructeur par défaut
    }

    public Etablissement(String adresse, String SIREN, String nom, boolean entreprise, String image) {
        this.adresse = adresse;
        this.SIREN = SIREN;
        this.nom = nom;
        this.entreprise = entreprise;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getSIREN() {
        return SIREN;
    }

    public void setSIREN(String SIREN) {
        this.SIREN = SIREN;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isEntreprise() {
        return entreprise;
    }

    public void setEntreprise(boolean entreprise) {
        this.entreprise = entreprise;
    }

    public Collection<Utilisateur> getUtilisateur_etab() {
        return utilisateur_etab;
    }

    public void setUtilisateur_etab(Collection<Utilisateur> utilisateur_etab) {
        this.utilisateur_etab = utilisateur_etab;
    }

    public Collection<Evenement> getEvenements_etab() {
        return evenements_etab;
    }

    public void setEvenements_etab(Collection<Evenement> evenements_etab) {
        this.evenements_etab = evenements_etab;
    }

    public Collection<Domain> getDomains_etab() {
        return domains_etab;
    }

    public void setDomains_etab(Collection<Domain> domains_etab) {
        this.domains_etab = domains_etab;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String im) {
        this.image = im;
    }
}
