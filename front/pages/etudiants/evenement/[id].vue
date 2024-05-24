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

const id = route.params.id;
const bool = route.query.bool === "true";

/* Récupération de l'Evenement */
const data = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=get_evenement_from_id&id=${id}`
);
const evenement = data;

const etab = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=get_etab_from_eventid&id=${id}`
);
const domains = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=get_domains_from_eventid&id=${id}`
);
const stats = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=lister_stat_event&id=${id}`
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
  `http://localhost:8080/PasserellePro/Serv?op=get_bool_demande_from_eventid_utilid&id_util=${id}&id_event=${event.id}`
);
if (demandeEffectue) {
  const demande = await $fetch(
    `http://localhost:8080/PasserellePro/Serv?op=get_demande_from_eventid_utilid&id_util=${id}&id_event=${event.id}`
  );
  console.log(demande);
  demandeValide.value = demande.valide;
  demandeRefuse.value = demande.refuse;
  if (!demande.valide && !demande.refuse) {
    demandeEnAttente.value = true;
  }
  lettreDeMotiv.value = demande.motivation;
}

async function sendDemand() {
  console.log("sendDemand");
  loading.value = true;
  if (lettreDeMotiv.value === "" || !fileSelected.value) {
    requeteFausse.value = true;
    loading.value = false;
    return;
  }
  requeteFausse.value = false;
  loading.value = false;
  demandeEnvoyee.value = true;
}

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
  <div class="flex flex-wrap justify-center gap-10">
    <EvenementCarte :key="event.id" :evenement="event" />
    <div class="grid w-full max-w-sm items-center gap-14 content-center">
      <div v-if="demandeEffectue" class="flex justify-center grid">
        <h1 class="w-full">Demade Effectué</h1>
        <TicketCheck
          color="#3e9392"
          class="w-20 h-20 justify-self-center"
          v-if="demandeValide"
        />
        <TicketSlash
          color="#c4bc00"
          class="w-20 h-20 justify-self-center"
          v-if="demandeEnAttente"
        />
        <TicketX
          color="#b51a00"
          class="w-20 h-20 justify-self-center"
          v-if="demandeRefuse"
        />
      </div>
      <div>
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
      </div>
      <Button
        @click="sendDemand"
        variant="secondary"
        class="w-[20%] place-self-center"
        :disabled="demandeEffectue"
      >
        <div v-if="loading">
          <Loader2 class="w-4 h-4 mr-2 animate-spin" />
          Please wait
        </div>
        Postuler
      </Button>
    </div>
    <Alert v-if="requeteFausse" variant="destructive" class="w-[70%]">
      <AlertTitle>Erreur</AlertTitle>
      <AlertDescription> Vous devez remplir tous les champs </AlertDescription>
    </Alert>
  </div>
</template>
