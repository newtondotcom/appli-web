package modele;

import java.util.Collection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
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
}
