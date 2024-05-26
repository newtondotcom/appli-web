<script setup lang="ts">

const id = 1;
const postulants2 = [
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



const postulants = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=get_demandes_from_eventid&id=${id}`,
  {
    credentials : "include",
  }
);
console.log(postulants);

const postulants3 = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=get_liste_postulants_from_eventid&id=${id}`,
  {
    credentials : "include",
  }
);
console.log(postulants3);

</script>

<template>
<div>
      <Titre
        title="Postulants"
        subtitle="Découvrez les postulants à cet évenement"
      />
      <div class="grid grid-cols-3 gap-4">
        <div v-for="postulant in postulants" :key="postulant.id">
          <Card class="sm:col-span-2 w-[400px]">
            <CardHeader class="pb-3">
              <CardTitle>{{ postulant.nom }}</CardTitle>
              <CardDescription class="max-w-lg text-balance leading-relaxed">
                {{ postulant.email }} 2<br />
                {{ postulant.numero }}2
              </CardDescription>
            </CardHeader>
            <CardFooter>
              <a
                v-if="!postulant.valide && !postulant.refuse"
                :href="'/etablissement/demande/' + postulant.id"
              >
                <Button>Consulter la demande</Button>
              </a>
              <Button
                v-if="postulant.valide"
                disabled
                class="bg-green-500"
                >Accepté(e)</Button
              >
              <Button
                v-if="postulant.refuse"
                disabled
                class="bg-red-500"
                >Refusé(e)</Button
              >
            </CardFooter>
          </Card>
        </div>
      </div>
    </div>
</template>