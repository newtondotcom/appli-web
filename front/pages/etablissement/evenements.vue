<script setup lang="ts">
const route = useRoute();
const id = 0;
const nom = "Découverte IA";
const description =
  "Venez découvrir l'intelligence artificielle avec nos collaborateurs durant ce stage de 3 heures.";
const creneau: Date = new Date();
const nom_etablissement = "Airbus";
const id_etablissement = 0;
const note_etablissement = 4.5;
const tags = ["IA", "Stage", "Airbus"];
const evenement: EvenementEtablissement = {
  id,
  nom,
  description,
  creneau,
  nom_etablissement,
  id_etablissement,
  note_etablissement,
  tags,
};
let creneau2: Date = new Date();
creneau2.setDate(creneau2.getDate() - 1);
const evenement2: EvenementEtablissement = {
  id: 1,
  nom: "Découverte IA",
  description:
    "Venez découvrir l'intelligence artificielle avec nos collaborateurs durant ce stage de 3 heures.",
  creneau: creneau2,
  nom_etablissement: "Airbus",
  id_etablissement: 0,
  note_etablissement: 4.5,
  tags: ["IA", "Stage", "Airbus"],
};
</script>

<template>
<div>
  <Titre
    title="Evènements"
    subtitle="Voici les évènements proposés par votre établissement"
  />
  <a :href="'/etablissement/evenement/' + evenement.id">
    <EvenementCarte class="my-4" :evenement="evenement" :key="evenement.id" />
  </a>
  <a href="/etablissement/evenement/1">
    <EvenementCarte :evenement="evenement2" :key="evenement2.id" />
  </a>
</div>  
</template>