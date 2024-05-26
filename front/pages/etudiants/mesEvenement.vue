<script setup lang="ts">
const route = useRoute();

/* Récupérer l'id de l'utilisateur */

const uid = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=get_uid_from_token`,
  {
    method: "GET",
    credentials: "include",
  }
);

/* Récupérer les evenement */
const data = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=get_evenement_from_uid&id=${uid}`,
  {
    method: "GET",
    credentials: "include",
  }
);
console.log(data);
const evenementsStart = data;

let events = [];

for (let i = 0; i < evenementsStart.length; i++) {
  const etab = await $fetch(
    `http://localhost:8080/PasserellePro/Serv?op=get_etab_from_eventid&id=${evenementsStart[i].id}`,
    {
      method: "GET",
      credentials: "include",
    }
  );
  const domains = await $fetch(
    `http://localhost:8080/PasserellePro/Serv?op=get_domains_from_eventid&id=${evenementsStart[i].id}`,
    {
      method: "GET",
      credentials: "include",
    }
  );
  const stats = await $fetch(
    `http://localhost:8080/PasserellePro/Serv?op=lister_stat_event&id=${evenementsStart[i].id}`,
    {
      method: "GET",
      credentials: "include",
    }
  );
  const doma = domains.map((d) => d.nom);
  const event = {
    id: evenementsStart[i].id,
    titre: evenementsStart[i].titre,
    description: evenementsStart[i].description,
    creneau: new Date(evenementsStart[i].creneau),
    nom_etablissement: etab.nom,
    id_etablissement: etab.SIREN,
    note_etablissement: stats[3],
    tags: doma,
  };
  events.push(event);
}
</script>

<template>
  <div>
    <div class="flex flex-col justify-center">
      <div class="flex flex-row w-full align-center justify-center">
        <Titre
          title="Evènements"
          subtitle="Voici les évènements auquel vous avez postulé"
        />
      </div>
      <div class="flex flex-wrap justify-center">
        <div
          v-for="evenement in events"
          :key="evenement.id"
          class="w-[40%] mx-4"
        >
          <a :href="'./evenement/' + evenement.id" class="">
            <EtudiantEvenementCarte class="my-4 mx-4" :evenement="evenement" />
          </a>
        </div>
      </div>
    </div>
  </div>
</template>
