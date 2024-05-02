<script setup lang="ts">
const postulants = [
    {
        id: 0,
        nom: "Jean Dupont",
        email: "jean.dupont@gmail.com",
        numero: "06 12 34 56 78"
    },
    {
        id: 1,
        nom: "Marie Dupont",
        email: "marie.dupont@gmail.com",
        numero: "06 12 34 56 78"
    },
    {
        id: 2,
        nom: "Paul Dupont",
        email: "paul.dupont@gmail.com",
        numero: "06 12 34 56 78"
    }
];
</script>

<template>
<EvenementCarte />
<div class="flex space-y-4 justify-center align-middle content-center mx-auto my-4">
<Button>Modifier l'évenement</Button>
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
                <Button>Consulter la demande</Button>
              </CardFooter>
            </Card>
</div>
</div>

</template>