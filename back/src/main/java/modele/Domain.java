package modele;

import java.util.Collection;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

public class Domain {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;   

    private String nom;

    @ManyToMany(mappedBy="domains_etab")
    private Collection<Etablissement> etablissements_dom;

    @ManyToMany(mappedBy="domains_event")
    private Collection<Evenement> evenement_dom;

    public Domain() {
        // Constructeur par défaut
    }

    public Domain(String nom) {
        this.nom = nom;
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

    public Collection<Etablissement> getEtablissements_dom() {
        return etablissements_dom;
    }

    public void setEtablissements_dom(Collection<Etablissement> etablissements_dom) {
        this.etablissements_dom = etablissements_dom;
    }

    public Collection<Evenement> getEvenement_dom() {
        return evenement_dom;
    }

    public void setEvenement_dom(Collection<Evenement> evenement_dom) {
        this.evenement_dom = evenement_dom;
    }

}
