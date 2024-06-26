<script setup lang="ts">
import { ref } from "vue";
import { Loader2, TicketCheck, TicketSlash, TicketX } from "lucide-vue-next";

const lettreDeMotiv = ref("");
const requeteFausse = ref(false);
const loading = ref(false);
const fileSelected = ref(false);
const fileName = ref("");
const demandeEnvoyee = ref(false);

const route = useRoute();

const eid = route.params.id;
const bool = route.query.bool === "true";

const uid = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=get_uid_from_token`,
  {
    method: "GET",
    credentials: "include",
  }
);

/* Récupération de l'Evenement */
const data = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=get_evenement_from_id&id=${eid}`,
  {
    method: "GET",
    credentials: "include",
  }
);
const evenement = data;

const etab = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=get_etab_from_eventid&id=${eid}`,
  {
    method: "GET",
    credentials: "include",
  }
);
const domains = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=get_domains_from_eventid&id=${eid}`,
  {
    method: "GET",
    credentials: "include",
  }
);
const stats = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=lister_stat_event&id=${eid}`,
  {
    method: "GET",
    credentials: "include",
  }
);
const doma = domains.map((d) => d.nom);
const event = {
  id: evenement.id,
  titre: evenement.titre,
  description: evenement.description,
  creneau: new Date(evenement.creneau),
  nom_etablissement: etab.nom,
  id_etablissement: etab.SIREN,
  note_etablissement: stats[3],
  tags: doma,
};
/* Traitement de la demande */
const demandeValide = ref(false);
const demandeEnAttente = ref(true);
const demandeRefuse = ref(false);
const demandeEffectue = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=get_bool_demande_from_eventid_utilid&id_util=${uid}&id_event=${event.id}`,
  {
    method: "GET",
    credentials: "include",
  }
);
if (demandeEffectue) {
  const demande = await $fetch(
    `http://localhost:8080/PasserellePro/Serv?op=get_demande_from_eventid_utilid&id_util=${uid}&id_event=${event.id}`,
    {
      method: "GET",
      credentials: "include",
    }
  );
  demandeValide.value = demande.valide;
  demandeRefuse.value = demande.refuse;
  if (!demande.valide && !demande.refuse) {
    demandeEnAttente.value = true;
  }
  lettreDeMotiv.value = demande.motivation;
}

/* Création de la demande */

async function sendDemand() {
  try {
    console.log("sendDemand");
    const data = await $fetch(
      `http://localhost:8080/PasserellePro/Serv?op=creer_demande`,
      {
        method: "POST",
        credentials: "include",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          id_etudiant: uid,
          id_evenement: eid,
          motivation: lettreDeMotiv.value,
        }),
      }
    );
    navigateTo(`/etudiants/evenements`);
  } catch (error) {
    console.error(error);
  }
}
/* Gestion des avis */
const avis = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=get_avis_from_eventid&id=${eid}`,
  {
    method: "GET",
    credentials: "include",
  }
);
const tabAvis = ref(avis);

const duree = evenement.duree;
const evenementPassed = computed(() => {
  return new Date(event.creneau).getTime() + duree * 60000 < new Date().getTime()
});

const handleFileChange = (event: Event) => {
  const target = event.target as HTMLInputElement;
  if (target.files && target.files.length > 0) {
    fileSelected.value = true;
    fileName.value = target.files[0].name;
  } else {
    fileSelected.value = false;
    fileName.value = "";
  }
};
</script>

<template>
  <div>
    <div class="flex flex-wrap justify-center gap-10">
      <EtudiantEvenementCarte :key="event.id" :evenement="event" />
      <div v-if="!evenementPassed" class="grid w-full items-center gap-14 content-center justify-center">
        <fieldset class="grid w-[70vw] gap-6 rounded-lg border p-4 justify">
          <Label for="lettreDeMotiv" class="place-self-center"
            >Lettre de Motivation</Label
          >
          <Input
            id="lettreDeMotiv"
            type="text"
            placeholder="Cette visite m'intéresse car …"
            class="h-48"
            v-model="lettreDeMotiv"
            :disabled="demandeEffectue"
          />
        </fieldset>
        <Button
          @click="sendDemand"
          class="w-[20%] place-self-center"
          :disabled="demandeEffectue"
        >
          Postuler
        </Button>
      </div>
      <Alert v-if="requeteFausse" variant="destructive" class="w-[70%]">
        <AlertTitle>Erreur</AlertTitle>
        <AlertDescription>
          Vous devez remplir tous les champs
        </AlertDescription>
      </Alert>
    </div>
    <div class="flex justify-cente mt-8">
      <Titre title="Avis sur l'évènement" subtitle="Voici les avis postés sur l'évènement" />
    </div>
    <ScrollArea class="h-96 w-100 rounded-md border">
      <div class="px-4 py-4 grid grid-cols-3 gap-4 justify-center">
        <div v-for="a in tabAvis" :key="a.id" class="w-full">
          <EtudiantAvisCard :avis="a" />
        </div>
      </div>
    </ScrollArea>
    <div class="mt-20">
      <div class="flex justify-center">
        <Titre title="Partager votre avis" subtitle="" />
      </div>
      <div class="flex justify-center">
        <EtudiantCreerAvis :tabAvis="tabAvis" :id_util="uid" :id_event="eid" />
      </div>
    </div>
  </div>
</template>
