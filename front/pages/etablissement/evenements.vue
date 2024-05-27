<script setup lang="ts">
import { onMounted } from "vue";

//const token = useCookie("token");
//token.value = 1;
let events = ref([]);

onMounted(async () => {
const evenemnents = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=lister_event_etab`,
  {
    method: "GET",
    credentials: 'include'
  }
);
for (let i = 0; i < evenemnents.length; i++) {
  const etab = await $fetch(
    `http://localhost:8080/PasserellePro/Serv?op=get_etab_from_eventid&id=${evenemnents[i].id}`,
    {
      method: "GET",
      credentials: "include",
    }
  );
  const domains = await $fetch(
    `http://localhost:8080/PasserellePro/Serv?op=get_domains_from_eventid&id=${evenemnents[i].id}`,
    {
      method: "GET",
      credentials: "include",
    }
  );
  const stats = await $fetch(
    `http://localhost:8080/PasserellePro/Serv?op=lister_stat_event&id=${evenemnents[i].id}`,
    {
      method: "GET",
      credentials: "include",
    }
  );
  const doma = domains.map((d) => d.nom);
  const event = {
    id: evenemnents[i].id,
    titre: evenemnents[i].titre,
    description: evenemnents[i].description,
    creneau: new Date(evenemnents[i].creneau),
    nom_etablissement: etab.nom,
    id_etablissement: etab.SIREN,
    note_etablissement: stats[3],
    tags: doma,
  };
  events.value.push(event);
}
});
</script>

<template>
<div>
    <div
      class="flex space-y-4 justify-center align-middle content-center mx-auto my-4"
    >
      <a
        href="/etablissement/evenement/modifier"
      >
        <Button>Créer un évènement</Button>
      </a>
    </div>
  <Titre
    title="Evènements"
    subtitle="Voici les évènements proposés par votre établissement"
  />
  <a v-for="event in events" :key="event.id" :href="'/etablissement/evenement/' + event.id">
    <EvenementCarte class="my-4" :evenement="event" :key="event.id" />
  </a>
</div>  
</template>