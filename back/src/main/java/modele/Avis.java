package modele;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class Avis {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;   

    private String titre;
    private int note;
    private String contenu;

    // A quel utilisateur appartient il
    @ManyToOne
    private Utilisateur utilisateur_avis;

    // A quel evenement fait il référance
    @ManyToOne
    private Evenement evenement_avis;

    public Avis() {
        // Constructeur par défaut
    }

    public Avis(String titre, int note, String contenu, Utilisateur utilisateur_avis, Evenement evenement_avis) {
        this.titre = titre;
        this.note = note;
        this.contenu = contenu;
        this.utilisateur_avis = utilisateur_avis;
        this.evenement_avis = evenement_avis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Utilisateur getUtilisateur_avis() {
        return utilisateur_avis;
    }

    public void setUtilisateur_avis(Utilisateur utilisateur_avis) {
        this.utilisateur_avis = utilisateur_avis;
    }

    public Evenement getEvenement_avis() {
        return evenement_avis;
    }

    public void setEvenement_avis(Evenement evenement_avis) {
        this.evenement_avis = evenement_avis;
    }

}
