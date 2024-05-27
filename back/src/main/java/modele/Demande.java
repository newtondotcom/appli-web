package modele;

import com.google.gson.annotations.Expose;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Expose
    private int id;

    @Expose
    private boolean valide; // True veut dire validé et false en attente

    @Expose
    private boolean refuse; // True veut dire refusé et false en attente ou validé

    @Expose
    private boolean present;

    @Expose
    private String motivation;

    @ManyToOne
    private Utilisateur utilisateur_dem;

    @ManyToOne
    private Evenement evenement_dem;

    public Demande() {
        // Constructeur par défaut
    }

    public Demande(String motivation, Utilisateur utilisateurDem, Evenement evenementDem) {
        this.valide = false;
        this.refuse = false;
        this.present = false;
        this.motivation = motivation;
        this.utilisateur_dem = utilisateurDem;
        this.evenement_dem = evenementDem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isValide() {
        return valide;
    }

    public void setValide(boolean valide) {
        this.valide = valide;
    }

    public boolean isRefuse() {
        return refuse;
    }

    public void setRefuse(boolean refuse) {
        this.refuse = refuse;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public Utilisateur getUtilisateur_dem() {
        return this.utilisateur_dem;
    }

    public void setUtilisateurDem(Utilisateur utilisateurDem) {
        this.utilisateur_dem = utilisateurDem;
    }

    public Evenement getEvenement_dem() {
        return this.evenement_dem;
    }

    public void setEvenementDem(Evenement evenementDem) {
        this.evenement_dem = evenementDem;
    }
}
