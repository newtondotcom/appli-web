package modele;

import java.util.Collection;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

public class Demande {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean valide; // True veut dire validé et false en attente
    private boolean refuse; // True veut dire refusé et false en attente ou validé

    @ManyToOne
    Utilisateur utilisateur_dem;

    @ManyToOne
    Evenement evenement_dem;

    // Les documents attaché à la demande
    @OneToMany(mappedBy="demande_doc",fetch = FetchType.EAGER)
    private Collection<Document> documents_dem;

    public Demande() {
        // Constructeur par défaut
    }

    public Demande(boolean valide, boolean refuse, Utilisateur utilisateur_dem, Evenement evenement_dem) {
        this.valide = valide;
        this.refuse = refuse;
        this.utilisateur_dem = utilisateur_dem;
        this.evenement_dem = evenement_dem;
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
