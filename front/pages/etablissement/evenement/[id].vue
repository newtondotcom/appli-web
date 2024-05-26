<script setup lang="ts">
import { computed } from "vue";
import type { EvenementEtablissement } from "~/types";
import { useRoute } from "vue-router";

const id = useRoute().params.id;

const data = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=get_evenement_from_id&id=${id}`,
  {
    credentials : "include",
  }
);
const evenement = data;
const duree = evenement.duree;
const creneau = new Date(evenement.creneau);

const etab = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=get_etab_from_eventid&id=${id}`,
  {
    credentials : "include",
  }
);
const domains = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=get_domains_from_eventid&id=${id}`,
  {
    credentials : "include",
  }
);
const stats = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=lister_stat_event&id=${id}`,
  {
    credentials : "include",
  }
);

const doma = domains.map((d) => d.nom);
const event = {
  id: evenement.id,
  titre: evenement.titre,
  description: evenement.description,
  creneau: new Date(evenement.creneau),
  nom_etablissement: etab.nom,
  id_etablissement: etab.SIREN,
  note_etablissement: stats[3],
  tags: doma,
};

const dateActuelle = new Date();
dateActuelle.setDate(dateActuelle.getDate() - 20);

const evenementPassed = computed(() => {
  return new Date(evenement.creneau).getTime() + duree * 60000 < dateActuelle.getTime()
});

const evenementEnCours = computed(() => {
  return (
    new Date(evenement.creneau) < dateActuelle &&
    new Date(evenement.creneau).getTime() + duree * 60000 > dateActuelle.getTime()
  );
});
</script>

<template>
  <div>
    <EvenementCarte :key="event.id" :evenement="event" />
    <div
      class="flex space-y-4 justify-center align-middle content-center mx-auto my-4"
    >
      <a
        v-if="!evenementPassed"
        :href="'/etablissement/evenement/modifier?id=' + evenement.id"
      >
        <Button>Modifier l'évenement</Button>
      </a>
    </div>

    <div v-if="!evenementPassed && !evenementEnCours">
      <EvenementAVenir />
    </div>
    <div v-if="evenementEnCours">
      <EvenementEnCours />
    </div>
    <div v-if="evenementPassed">
      <EvenementPasse :stats="stats" />
  </div>
  </div>
</template>