package modele;

import java.util.Collection;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;
    private String mdp;
    private int INE;
    private boolean admin; // True si c'est un administrateur
    private String email;
    private String telephone;


    // L'établissement auquel il appartient
    @ManyToOne
    private Etablissement etablissement_util;
    // Mise en place du token de la session
    @OneToOne
    private String token;
    // Les évenements auquels il a participé / participera
    @ManyToMany(mappedBy="utilisateurs_event",fetch = FetchType.EAGER)
    private Collection<Evenement> evenements_util;

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

    public Utilisateur(String nom, String mdp, String SIREN, boolean entreprise, int INE, boolean admin, String email, String telephone, Etablissement etablissement_util) {
        this.nom = nom;
        this.mdp = mdp;
        this.INE = INE;
        this.admin = admin;
        this.email = email;
        this.telephone = telephone;
        this.etablissement_util = etablissement_util;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public int getINE() {
        return INE;
    }

    public void setINE(int INE) {
        this.INE = INE;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Etablissement getEtablissement_util() {
        return etablissement_util;
    }

    public void setEtablissement_util(Etablissement etablissement_util) {
        this.etablissement_util = etablissement_util;
    }

    public Collection<Evenement> getEvenements_util() {
        return evenements_util;
    }

    public void setEvenements_util(Collection<Evenement> evenements_util) {
        this.evenements_util = evenements_util;
    }

    public Collection<Demande> getDemandes_util() {
        return demandes_util;
    }

    public void setDemandes_util(Collection<Demande> demandes_util) {
        this.demandes_util = demandes_util;
    }

    public Collection<Document> getDocuments_util() {
        return documents_util;
    }

    public void setDocuments_util(Collection<Document> documents_util) {
        this.documents_util = documents_util;
    }

    public Collection<Avis> getAvis_util() {
        return avis_util;
    }

    public void setAvis_util(Collection<Avis> avis_util) {
        this.avis_util = avis_util;
    }

}
