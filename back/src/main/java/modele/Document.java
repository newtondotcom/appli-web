package modele;

import com.google.gson.annotations.Expose;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private int id;

    @ManyToOne
    private Utilisateur utilisateur_doc;

    @ManyToOne
    private Demande demande_doc;

    public Document() {
        // Constructeur par défaut
    }

    public Document(Utilisateur utilisateur_doc, Demande demande_doc) {
        this.utilisateur_doc = utilisateur_doc;
        this.demande_doc = demande_doc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur_doc() {
        return utilisateur_doc;
    }

    public void setUtilisateur_doc(Utilisateur utilisateur_doc) {
        this.utilisateur_doc = utilisateur_doc;
    }

    public Demande getDemande_doc() {
        return demande_doc;
    }

    public void setDemande_doc(Demande demande_doc) {
        this.demande_doc = demande_doc;
    }
}