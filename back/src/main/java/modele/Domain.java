package modele;

import java.util.Collection;

import com.google.gson.annotations.Expose;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private int id;

    @Expose
    private String nom;

    @ManyToMany(mappedBy = "domains_etab", fetch = FetchType.EAGER)
    private Collection<Etablissement> etablissements_dom;

    @ManyToMany(mappedBy = "domains_event", fetch = FetchType.EAGER)
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
