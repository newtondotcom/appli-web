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
const id = 1;
const nom = "Découverte IA";
const description =
  "Venez découvrir l'intelligence artificielle avec nos collaborateurs durant ce stage de 3 heures.";
let creneau: Date = new Date();
creneau.setDate(creneau.getDate() - 1);
const nom_etablissement = "Airbus";
const id_etablissement = 0;
const note_etablissement = 4.5;
const duree = 180;
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
  duree,
};

const evenementPassed = computed(() => {
  return creneau < new Date();
});

const listeAppels = [
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
];

const appelEffectue = ref(true);

async function marquerPersonnePresente(id: number) {
  console.log("Présent");
}

async function marquerPersonneAbsente(id: number) {
  console.log("Absent");
}

const statistiques = [
    {
        titre: "Nombre de participants ayant postulé",
        valeur: 45,
        sous_titre: "+20.1% from last month",
    },
    {
        titre: "Nombre de participants retenus",
        valeur: 30,
        sous_titre: "+20.1% from last month"
    },
    {
        titre: "Nombre de participants présents",
        valeur: 23,
        sous_titre: "+20.1% from last month"
    },
    {
        titre: "Note moyenne des participants",
        valeur: 2.5,
        sous_titre: "+20.1% from last month"
    },
];
</script>

<template>
<div>
  <EvenementCarte :key="evenement.id" :evenement="evenement" />
  <div
    class="flex space-y-4 justify-center align-middle content-center mx-auto my-4"
  >
    <a v-if="!evenementPassed" :href="'/etablissement/evenement/modifier?id=' + evenement.id">
      <Button >Modifier l'évenement</Button>
    </a>
  </div>

  <div v-if="!evenementPassed && !appelEffectue">
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
              {{ postulant.email }} <br />
              {{ postulant.numero }}
            </CardDescription>
          </CardHeader>
          <CardFooter>
            <a
              v-if="postulant.statut == 'en attente'"
              :href="'/etablissement/demande/' + postulant.id"
            >
              <Button>Consulter la demande</Button>
            </a>
            <Button
              v-if="postulant.statut == 'accepte'"
              disabled
              class="bg-green-500"
              >Accepté(e)</Button
            >
            <Button
              v-if="postulant.statut == 'refuse'"
              disabled
              class="bg-red-500"
              >Refusé(e)</Button
            >
          </CardFooter>
        </Card>
      </div>
    </div>
  </div>
  <div v-if="evenementPassed && !appelEffectue">
    <Titre title="Appel" subtitle="Faire l'appel pour cet évenement" />
    <div class="grid grid-cols-1 gap-4 justify-center mx-auto items-center">
      <div v-for="postulant in postulants" :key="postulant.id">
        <Card class="sm:col-span-2 w-[80%]">
          <CardHeader class="pb-3">
            <CardTitle>
              <div class="flex justify-between flex-row">
                <div>
                  {{ postulant.nom }}
                </div>
                <div>
                  <Button @click="marquerPersonnePresente(postulant.id)" class="mr-2">Présent</Button>
                  <Button @click="marquerPersonneAbsente(postulant.id)" class="bg-red-500">Absent</Button>
                </div>
              </div>
            </CardTitle>
            <CardDescription class="max-w-lg text-balance leading-relaxed">
              {{ postulant.email }} <br />
              {{ postulant.numero }}
            </CardDescription>
          </CardHeader>
        </Card>
      </div>
    </div>
  </div>
  <div v-else>
    <Titre title="Récapitulatif" subtitle="Récapitulatif de cet évènement" />
    <div class="grid gap-4 md:grid-cols-2 md:gap-8 lg:grid-cols-4">
        <Card v-for="statistique in statistiques" :key="statistique.index">
          <CardHeader class="flex flex-row items-center justify-between space-y-0 pb-2">
            <CardTitle class="text-sm font-medium">
                {{ statistique.titre }}
            </CardTitle>
            <DollarSign class="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div class="text-2xl font-bold">
                {{ statistique.valeur }}
            </div>
            <p class="text-xs text-muted-foreground">
                {{ statistique.sous_titre }}
            </p>
          </CardContent>
        </Card>
    </div>
  </div>
</div>
</template>