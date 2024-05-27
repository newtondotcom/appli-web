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

    @Expose
    private String name;

    public Document() {
        // Default constructor
    }

    public Document(Utilisateur utilisateurDoc, String name) {
        this.utilisateur_doc = utilisateurDoc;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utilisateur getUtilisateurDoc() {
        return this.utilisateur_doc;
    }

    public void setUtilisateurDoc(Utilisateur utilisateurDoc) {
        this.utilisateur_doc = utilisateurDoc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
