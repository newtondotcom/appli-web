<script setup lang="ts">

const token = useCookie("token", { path: "/" });
token.value = "1";
const data = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=lister_event_etab`,
  {
    method: "POST",
    body: JSON.stringify({
      id,
    }),
    credentials: 'include'
  }
);
console.log(data);

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
</div>  
</template>