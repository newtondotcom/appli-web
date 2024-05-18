<script lang="ts" setup>
import { Search } from "lucide-vue-next";
import { ref, watch, onMounted, computed } from "vue";
interface EvenementEtablissement {
  id: number;
  nom: string;
  description: string;
  creneau: Date;
  nom_etablissement: string;
  id_etablissement: number;
  note_etablissement: number;
  tags: string[];
}

const evenementsStart: EvenementEtablissement[] = [
  {
    id: 0,
    nom: "Découverte IA",
    description:
      "Venez découvrir l'intelligence artificielle avec nos collaborateurs durant ce stage de 3 heures.",
    creneau: new Date(),
    nom_etablissement: "Airbus",
    id_etablissement: 0,
    note_etablissement: 4.5,
    tags: ["IA", "Stage", "Airbus"],
  },
  {
    id: 1,
    nom: "Formation Python",
    description:
      "Apprenez les bases du langage de programmation Python avec nos experts.",
    creneau: new Date(),
    nom_etablissement: "Microsoft",
    id_etablissement: 1,
    note_etablissement: 4.8,
    tags: ["Python", "Formation", "Microsoft"],
  },
  {
    id: 2,
    nom: "Atelier Design Thinking",
    description:
      "Découvrez la méthode de Design Thinking pour résoudre des problèmes complexes en équipe.",
    creneau: new Date(),
    nom_etablissement: "Google",
    id_etablissement: 2,
    note_etablissement: 4.7,
    tags: ["Design Thinking", "Atelier", "Google"],
  },
];

const searchFilter = ref("");
const domaineFilter = ref("");
const entreprisesFilter = ref("");

const filteredEvenements = computed(() => {
  let evenements = evenementsStart;
  if (searchFilter.value !== "") {
    evenements = evenements.filter(
      (evenement: { nom: string; description: string }) =>
        evenement.nom.includes(searchFilter.value) ||
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
computed(() => {
  console.log(entreprisesFilter.value);
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
