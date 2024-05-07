<script setup lang="ts">
interface Evenement {
  id: number;
  title: string;
  nom: string;
  date: string;
  domaine: string;
}
const evenements: Evenement[] = [
  {
    id: 1,
    title: "Conférence sur le développement durable",
    nom: "Hall Of Change",
    date: "2023/04/15",
    domaine: "Environnement",
  },
  {
    id: 2,
    title: "Atelier sur la programmation fonctionnelle",
    nom: "Microsoft",
    date: "2023/05/01",
    domaine: "Informatique",
  },
  {
    id: 3,
    title: "Concert de musique classique",
    nom: "Opéra de Toulouse",
    date: "2023/06/10",
    domaine: "Culture",
  },
];
</script>

<template>
  <div class="relative w-full max-w-sm items-center mx-[40%]">
    <Input
      id="search"
      type="text"
      placeholder="Search..."
      class="pl-10 w-[50%] justify-center content-center align-center"
    />
    <span
      class="absolute start-0 inset-y-0 flex items-center justify-center px-2"
    >
      <Search class="size-6 text-muted-foreground" />
    </span>
  </div>
  <div class="flex justify-center flex-wrap gap-8 p-4">
    <EtudiantCarte
      v-for="evenement in evenements"
      :key="evenement.id"
      :evenement="evenement"
    />
  </div>
</template>

<style></style>
