<script setup lang="ts">
const postulants = [
    {
        id: 0,
        nom: "Jean Dupont",
        email: "jean.dupont@gmail.com",
        numero: "06 12 34 56 78",
        statut : "accepte"
    },
    {
        id: 1,
        nom: "Marie Dupont",
        email: "marie.dupont@gmail.com",
        numero: "06 12 34 56 78",
        statut : "refuse"
    },
    {
        id: 2,
        nom: "Paul Dupont",
        email: "paul.dupont@gmail.com",
        numero: "06 12 34 56 78",
        statut : "en attente"
    }
];
const id = 0;
const nom = "Découverte IA";
const description = "Venez découvrir l'intelligence artificielle avec nos collaborateurs durant ce stage de 3 heures.";
const creneau : Date = new Date();
const nom_etablissement = "Airbus";
const id_etablissement = 0;
const note_etablissement = 4.5;
const tags = ["IA", "Stage", "Airbus"];
const evenement : EvenementEtablissement = {id, nom, description, creneau, nom_etablissement, id_etablissement, note_etablissement, tags};
</script>

<template>
<EvenementCarte :key="evenement.id" :evenement="evenement"/>
<div class="flex space-y-4 justify-center align-middle content-center mx-auto my-4">
<a :href="'/etablissement/evenement/modifier?id=' + evenement.id" >
<Button>Modifier l'évenement</Button>
</a>
</div>
<Titre title="Postulants" subtitle="Découvrez les postulants à cet évenement" />
<div class="grid grid-cols-3 gap-4">
<div v-for="postulant in postulants" :key="postulant.id">
    <Card class="sm:col-span-2 w-[400px]">
              <CardHeader class="pb-3">
                <CardTitle>{{ postulant.nom }}</CardTitle>
                <CardDescription class="max-w-lg text-balance leading-relaxed">
                    {{ postulant.email }} <br> {{ postulant.numero }}
                </CardDescription>
              </CardHeader>
              <CardFooter>
                <a v-if="postulant.statut=='en attente'" :href="'/etablissement/demande/' + postulant.id" >
                <Button>Consulter la demande</Button>
                </a>
                <Button v-if="postulant.statut=='accepte'" disabled class="bg-green-500">Accepté(e)</Button>
                <Button v-if="postulant.statut=='refuse'" disabled class="bg-red-500">Refusé(e)</Button>
              </CardFooter>
            </Card>
</div>
</div>

</template>