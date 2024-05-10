<script setup lang="ts">
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

const evenements: EvenementEtablissement[] = [
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
</script>

<template>
  <div class="">
    <EtudiantSearchBar />
    <div class="flex flex-wrap justify-center">
      <div
        v-for="evenement in evenements"
        :key="evenement.id"
        class="w-[40%] mx-4"
      >
        <a :href="'' + evenement.id" class="">
          <EvenementCarte class="my-4 mx-4" :evenement="evenement" />
        </a>
      </div>
    </div>
  </div>
</template>

<style></style>
