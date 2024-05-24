<script lang="ts" setup>
import { Search, Tag } from "lucide-vue-next";
import { ref, watch, onMounted, computed } from "vue";

/* Récupérer les Evenements */
const data = await $fetch(
  "http://localhost:8080/PasserellePro/Serv?op=lister_event"
);
const evenementsStart = data;

let events = [];

for (let i = 0; i < evenementsStart.length; i++) {
  const etab = await $fetch(
    `http://localhost:8080/PasserellePro/Serv?op=get_etab_from_eventid&id=${evenementsStart[i].id}`
  );
  const domains = await $fetch(
    `http://localhost:8080/PasserellePro/Serv?op=get_domains_from_eventid&id=${evenementsStart[i].id}`
  );
  const stats = await $fetch(
    `http://localhost:8080/PasserellePro/Serv?op=lister_stat_event&id=${evenementsStart[i].id}`
  );
  const doma = domains.map((d) => d.nom);
  const event = {
    id: evenementsStart[i].id,
    titre: evenementsStart[i].titre,
    description: evenementsStart[i].description,
    creneau: new Date(evenementsStart[i].creneau),
    nom_etablissement: etab.nom,
    id_etablissement: etab.SIREN,
    note_etablissement: stats[3],
    tags: doma,
  };
  events.push(event);
}

/* Gestion des filtres */
const searchFilter = ref("");
const domaineFilter = ref("");
const entreprisesFilter = ref("");

const filteredEvenements = computed(() => {
  let evenements = events;
  if (searchFilter.value !== "") {
    evenements = evenements.filter(
      (evenement: { titre: string; description: string }) =>
        evenement.titre.includes(searchFilter.value) ||
        evenement.description.includes(searchFilter.value)
    );
  }
  if (entreprisesFilter.value !== "") {
    evenements = evenements.filter(
      (evenement: { nom_etablissement: string }) =>
        evenement.nom_etablissement === entreprisesFilter.value
    );
  }
  return evenements;
});
const handleSearch = (search: string) => {
  searchFilter.value = search;
};
const handleEntrepriseFilter = (filter: string) => {
  entreprisesFilter.value = filter;
};
</script>

<template>
  <div class="flex flex-col justify-center">
    <div class="flex flex-row w-full align-center justify-center">
      <EtudiantSearchForm @search="handleSearch" />
      <EtudiantEntrepriseFilter @filter="handleEntrepriseFilter" />
    </div>
    <div class="flex flex-wrap justify-center">
      <div
        v-for="evenement in filteredEvenements"
        :key="evenement.id"
        class="w-[40%] mx-4"
      >
        <a :href="'./evenement/' + evenement.id" class="">
          <EvenementCarte class="my-4 mx-4" :evenement="evenement" />
        </a>
      </div>
    </div>
  </div>
</template>

<style></style>
