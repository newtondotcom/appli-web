# Semaine du 22 avril au 26 avril

## Initialisation du projet 

Front-end en Vue.js (Nuxt.js) et back-end (JBoss)

Ce projet utilise le fork [Wildfly](https://github.com/wildfly/wildfly) maintenu de JBoss qui permet de faire marcher le projet sans utiliser Eclipse.

## Création du "thème du site" sur [Figma](https://www.figma.com/file/HR0hwzph2oeCbt7P2aPQkL/appli_web?type=design&node-id=0%3A1&mode=design&t=iQpcZF4FbKFyWKLn-1)

## Spécifications : 

### Élèves 

Se connecter et s'enregistrer comme les entreprises
Parcourir les événements
- Par date / thème / entreprise
- En réserver un
Voir ses évènements
- Se désinscrire
(- Retourner la convention signée
	- Un document spécial demandé par l’entreprise ( ID, passport ) ) faire plus tard
Pouvoir donner une avis sur un événement passé

Enchaînement
Connexion (→ inscription si première ) →  Page de récapitulatif des visites (Accueil) :
→ Recherche d’une visite par domaine ou entreprise
→ Cliquer sur un événement pour voir les détails et les documents ( convention et document spécial )


### Etablissement


Se connecter
- Champ identifiant
- Champ mot de passe
S'enregistrer pour une première connexion
- De nombreux champs d'information (SIREN, email, id, mdp ...)
Ajouter une visite
- Date / Heure
- Informations sur l'entreprise et la visite
- Nombre d'élèves
Voir le récapitulatif des visites et des élèves inscrits
- Supprimer l'un ou l'autre
- Bouton qui indique à l'école l'absence d'un étudiant à sa visite
Programmer l'envoi automatique de mails à des utilisateurs quand la date de la visite se rapproche
Demander que l’étudiant dépose un document spécifique ( ex : ID pour Airbus )

Se connecter comme les élèves
Voir tous les créneaux réservé par des élèves de son établissement
- Valider une demande d’élève pour participer un évènement
Même envoie de mail que les entreprises

Enchaînement 
Connexion (→ inscription si première ) → Page récapitulatif des évènements  (Accueil)
→ Un évènement envoie à sa page d’envoie de mail et de validation

## Entities produits pour ces besoins

**Utilisateur** (étudiant, admin, responsable dans une école ou une entreprise) 

**Établissement** (école ou entreprise) 

**Événement** organisé par l’entreprise 

**Demande** de participation d’un étudiant à un événement 

**Domaine** de l’entreprise ( IA, web, logiciel …)

**Document** demandé par l'entreprise ( ID ) et convention

**Avis** sur un établissement donné par un élève après un évènement

## Enumeration des pages

### Utilisateurs

- Page d’accueil
- Page de connexion
- Page de recherche d’évèenemnt
- Page d’évènement
- Page utilisateur

### Entreprise

- Page d’accueil
- Page de connexion
- Création d’évènement
- Page de compte
- Page liste de ses évènement
- Page Liste étudiant pour un évènement

### Vue Admin ( fonctionnalité implantée en dernier )

Voir tous les évènements créés par les entreprises, les élèves qui ont réservé et leurs établissements et avoir tout contrôle dessus.
Mêmes options que les Entreprises, Élèves et Établissement pour faire les choses à leur place s'il y a un problème de connexion
Accéder aux informations rentrées par les utilisateurs (retrouver un mot de passe)

# Semaine du 29 avril au 3 mai

## Création des entitiées et leurs associations

- [Entitées et Associations](back/src/main/java/modele/)

## Commencement des fonctionnalitées dans la Facade

- [Facade](back/src/main/java/vue/)

## Commencement de la vue Utilisateur

- [Listing des évènements visible par les étudiant](front/pages/etudiants/evenement/evenement.vue)
- [Page d'un évenement avec le détail de l'entreprise](front/pages/etudiants/evenement/[id].vue)
- [Page de compte d'un utilisateur](front/pages/etudiants/[id].vue)

## Vue Etablissement

- [Listing des événements créés par l'entreprise](front/pages/etablissement/evenements.vue)
- [Page d'un évènement avec la liste des postulants](front/pages/etablissement/evenement/[id].vue)
- [Demande d'un postulant pour un évènement](front/pages/etablissement/demande/[id].vue)

## Autres

- [Page d'inscription](front/pages/auth/login.vue)
- [Page d'authentification](front/pages/auth/signup.vue)

# Semaine du 19 mai au 24 mai

## Vue Etablissement

- [Création de la page de modification du compte d'un personnel d'un établissement](front/pages/etablissement/demande/compte.vue)
- [Création de la page pour créer et modifier un évènement](front/pages/etablissement/evenement/modifier.vue)
- [Modification de la page d'un évènement avec la liste des postulants si l'évènement n'est pas passé, si l'évènement est en cours possibilité de faire l'appel, sinon le personnel voit des statistiques sur l'évènement](front/pages/etablissement/evenement/[id].vue)
- Commencement de l'intégration de l'API Wildlfy

## Vue Etudiant
- [Création de la page de visualisation & modification du compte d'un étudiant](front/pages/etudiants/[id].vue)
- [Création de la page qui permet de visualiser tous les évènement sauquel un etudiant à postuler](front/pages/etudiants/mesEvenement.vue)
- [AJout de filtre pour l'affichage de tous les évèenements](front/pages/etudiants/evenements.vue)
- Commencement de l'intégration de l'API Wildlfy

## Facade

- Génération des tokens pour les utilisateurs après l'inscription et la connexion.
- Hachage du mot de passe à l'inscription avant de le rentrer dans la BD et comparaison avec le hachage du mot de passe rentré à la connexion.
- Création des fonctions nécessaires pour extraire les éléments de la BD pour le front.

## Serveur

- Extraction du token des cookies et vérification pour les requêtes qui le nécessitent.
- Implémentation d'un builderJson pour envoyer les réponses de la facade sous format JSON pour le front.
- Création des conditions pour que le champ opérateur des requêtes utilise les bonnes fonctions de la facade.

## BDD

- Modification des entités pour que le builderJson ne sélectionne que les champs voulus et ne boucle pas.

# Semaine du 19 mai au 24 mai
## Vue Etudiant
- Création des avis pour commenter un évènement
- Ajout d'un Icone pour suivre l'avancé de la demande
- Ajout de toutes les requêtes à la base de donnée
## Back-end

- Rajout de la plupart des fonctions renvoyant les attrubuts @ManytoOne, @ManytoMany et @OnetoMany des entitys
- Nouvelles fonctions : Savoir si un utilisateur est étudiant, des foncions ne prenant que le token en paramêtre etc ...
