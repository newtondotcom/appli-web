<script setup lang="ts">
const props = defineProps({
  id: {
    type: String,
    required: true,
  },
});
const id = props.id;
const postulants2 = ref([]);
const postulants = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=get_demandes_from_eventid&id=${id}`,
  {
    credentials: "include",
  }
);
for (let i = 0; i < postulants.length; i++) {
  console.log(postulants[i].id);
  const postulants3 = await $fetch(
    `http://localhost:8080/PasserellePro/Serv?op=get_util_from_id_dem&id_dem=${postulants[i].id}`,
    {
      credentials: "include",
    }
  );
  postulants2.value.push({
    id: postulants[i].id,
    nom: postulants3.nom.replace("#", " "), // "nom" : "nom#prenom"
    email: postulants3.email,
    numero: postulants3.numero,
    valide: postulants[i].valide,
    refuse: postulants[i].refuse,
  });
}
</script>

<template>
  <div>
    <Titre
      title="Postulants"
      subtitle="Découvrez les postulants à cet évenement"
    />
    <div class="grid grid-cols-3 gap-4">
      <div v-for="postulant in postulants2" :key="postulant.id">
        <NuxtLink :href="'/etablissement/demande/' + postulant.id">
        <Card class="sm:col-span-2 w-[400px]">
          <CardHeader class="pb-3">
            <CardTitle>{{ postulant.nom }}</CardTitle>
            <CardDescription class="max-w-lg text-balance leading-relaxed">
              {{ postulant.email }}<br />
              {{ postulant.numero }}
            </CardDescription>
          </CardHeader>
          <CardFooter>
            <NuxtLink
              v-if="!postulant.valide && !postulant.refuse"
              :href="'/etablissement/demande/' + postulant.id"
            >
              <Button>Consulter la demande</Button>
            </NuxtLink>
            <Button v-if="postulant.valide" disabled class="bg-green-500"
              >Accepté(e)</Button
            >
            <Button v-if="postulant.refuse" disabled class="bg-red-500"
              >Refusé(e)</Button
            >
          </CardFooter>
        </Card>
        </NuxtLink>
      </div>
    </div>
  </div>
</template>