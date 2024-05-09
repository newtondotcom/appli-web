package modele;

import java.util.Collection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Domain{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;

    // PB confirme
    //@ManyToMany(mappedBy="domains_etab",fetch = FetchType.LAZY)
    //private Collection<Etablissement> etablissements_dom;

    // PB confirme
    //@ManyToMany(mappedBy="domains_event",fetch = FetchType.LAZY)
    //private Collection<Evenement> evenement_dom;

    public Domain() {
        // Constructeur par défaut
    }

}
