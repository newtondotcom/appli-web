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
console.log(route);
console.log(id);
console.log(bool);

const data = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=get_evenement_from_id&id=${id}`
);
const evenement = data;

/* Demande déja effectué */
const demandeEffectue = true;
const demandeValide = ref(false);
const demandeEnAttente = ref(true);
const demandeRefuse = ref(false);
if (demandeEffectue) {
  demandeEnvoyee.value = true;
  // Récupérer la demande du boug
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
    <EvenementCarte :key="evenement.id" :evenement="evenement" />
    <div class="grid w-full max-w-sm items-center gap-14 content-center">
      <div v-if="demandeEnvoyee" class="flex justify-center">
        <TicketCheck color="#3e9392" class="w-20 h-20" />
        <TicketSlash color="#d95000" class="w-20 h-20" />
        <TicketX color="#b51a00" class="w-20 h-20" />
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
          :disabled="demandeEnvoyee"
        />
      </div>
      <div>
        <Label for="picture">Papier d'identité</Label>
        <Input
          id="picture"
          type="file"
          @change="handleFileChange"
          :disabled="demandeEnvoyee"
        />
      </div>
      <Button
        @click="sendDemand"
        variant="secondary"
        class="w-[20%] place-self-center"
        :disabled="demandeEnvoyee"
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
