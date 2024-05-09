package modele;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
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
}
