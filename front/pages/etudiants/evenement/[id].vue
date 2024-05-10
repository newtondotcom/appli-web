<script setup lang="ts">
const postulants = [
  {
    id: 0,
    nom: "Jean Dupont",
    email: "jean.dupont@gmail.com",
    numero: "06 12 34 56 78",
    statut: "accepte",
  },
  {
    id: 1,
    nom: "Marie Dupont",
    email: "marie.dupont@gmail.com",
    numero: "06 12 34 56 78",
    statut: "refuse",
  },
  {
    id: 2,
    nom: "Paul Dupont",
    email: "paul.dupont@gmail.com",
    numero: "06 12 34 56 78",
    statut: "en attente",
  },
];
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
  <EvenementCarte :key="evenement.id" :evenement="evenement" />
</template>
