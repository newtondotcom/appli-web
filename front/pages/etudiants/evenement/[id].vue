<script setup lang="ts">
import { ref } from "vue";
import { Loader2 } from "lucide-vue-next";
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
const idd = 0;
const nom = "Découverte IA";
const description =
  "Venez découvrir l'intelligence artificielle avec nos collaborateurs durant ce stage de 3 heures.";
const creneau: Date = new Date();
const nom_etablissement = "Airbus";
const id_etablissement = 0;
const note_etablissement = 4.5;
const tags = ["IA", "Stage", "Airbus"];
const evenement: EvenementEtablissement = {
  idd,
  nom,
  description,
  creneau,
  nom_etablissement,
  id_etablissement,
  note_etablissement,
  tags,
};
const lettreDeMotiv = ref("");
const requeteFausse = ref(false);
const loading = ref(false);
const fileSelected = ref(false);
const fileName = ref("");
const demandeEnvoyee = ref(false);

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
