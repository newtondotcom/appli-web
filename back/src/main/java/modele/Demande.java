package modele;

import java.util.Collection;

import com.google.gson.annotations.Expose;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Demande {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    Utilisateur utilisateur_dem;

    @ManyToOne
    Evenement evenement_dem;

    // Les documents attaché à la demande
    @OneToMany(mappedBy = "demande_doc", fetch = FetchType.EAGER)
    private Collection<Document> documents_dem;

    public Demande() {
        // Constructeur par défaut
    }

    public Demande(String motivation, Utilisateur utilisateur_dem, Evenement evenement_dem) {
        this.valide = false;
        this.refuse = false;
        this.present = false;
        this.motivation = motivation;
        this.utilisateur_dem = utilisateur_dem;
        this.evenement_dem = evenement_dem;
        this.documents_dem = null;
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
        return utilisateur_dem;
    }

    public void setUtilisateur_dem(Utilisateur utilisateur_dem) {
        this.utilisateur_dem = utilisateur_dem;
    }

    public Evenement getEvenement_dem() {
        return evenement_dem;
    }

    public void setEvenement_dem(Evenement evenement_dem) {
        this.evenement_dem = evenement_dem;
    }

    public Collection<Document> getDocuments_dem() {
        return documents_dem;
    }

    public void setDocuments_dem(Collection<Document> documents_dem) {
        this.documents_dem = documents_dem;
    }
}
