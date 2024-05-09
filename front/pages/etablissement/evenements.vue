<script setup lang="ts">
const route = useRoute()
const id = route.params.id as number

const nom = "Découverte IA";
const description = "Venez découvrir l'intelligence artificielle avec nos collaborateurs durant ce stage de 3 semaines.";
const creneau : string = new Date().toLocaleDateString() + " - " + new Date().toLocaleTimeString();
const nom_etablissement = "Airbus";
const id_etablissement = 0;
const note_etablissement = 4.5;
const tags = ["IA", "Stage", "Airbus"];
const evenement : EvenementEtablissement = {id, nom, description, creneau, nom_etablissement, id_etablissement, note_etablissement, tags};
  
</script>

<template>
<Titre title="Evènements" subtitle="Voici les évènements proposés par votre établissement" />
<EvenementCarte :evenement="evenement" :key="evenement.id" />
</template>