<script setup lang="ts">
import { ref } from "vue";
import { useToast } from "@/components/ui/toast/use-toast";
import { Settings } from "lucide-vue-next";
import { useRoute } from "vue-router";
const { toast } = useToast();

const nomEtudiant = ref("");
const ineEtudiant = ref("");
const emailEtudiant = ref("");
const telephoneEtudiant = ref("");
const classeEtudiant = ref("");
const etablissementEtudiant = ref("");

const loading = ref(false);
const isModifying = ref(false);

const route = useRoute();
let id = -1;
if (route.params.id) {
  id = route.params.id as number;
}
/* Récupere les infos de l'utilisateur */
const infos = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=get_util_from_uid&id=${id}`,
  {
    method: "GET",
    credentials: "include",
  }
);
const etab = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=get_etab_from_uid&id=${id}`,
  {
    method: "GET",
    credentials: "include",
  }
);
console.log(infos);
nomEtudiant.value = infos.nom;
ineEtudiant.value = infos.INE;
emailEtudiant.value = infos.email;
telephoneEtudiant.value = infos.telephone;
classeEtudiant.value = infos.classe;
etablissementEtudiant.value = etab.nom;
/* Fonction qui permet de changer de mode en edit et non-edit */
async function Modifyprofil() {
  isModifying.value = !isModifying.value;
}
/* Fonction qui permet de sauvegarder les modifications */
async function saveChanges() {
  loading.value = true;
  toast({
    title: "Succès",
    description: "Les modifications ont été enregistrées avec succès",
  });
  loading.value = false;
}
</script>

<template>
  <div class="flex flex-col">
    <div class="absolute right-4">
      <Button
        @click="Modifyprofil"
        :variant="isModifying ? undefined : 'secondary'"
        size="icon"
      >
        <Settings class="w-4 h-4" />
      </Button>
    </div>
    <Titre
      title="Profil"
      subtitle="Voici les informations que voit les entreprises."
      class="place-self-center"
    />
    <div class="grid gap-4 px-4">
      <div class="grid gap-3">
        <Label for="nomEtudiant">Nom</Label>
        <Input
          id="nomEtudiant"
          type="text"
          v-model="nomEtudiant"
          :disabled="!isModifying"
        />
      </div>
      <div class="grid gap-3">
        <Label for="ineEtudiant">INE</Label>
        <Input
          id="ineEtudiant"
          type="text"
          v-model="ineEtudiant"
          :disabled="!isModifying"
        />
      </div>
      <div class="grid gap-3">
        <Label for="emailEtudiant">Email</Label>
        <Input
          id="emailEtudiant"
          type="text"
          v-model="emailEtudiant"
          :disabled="!isModifying"
        />
      </div>
      <div class="grid gap-3">
        <Label for="telephoneEtudiant">Téléphone</Label>
        <Input
          id="telephoneEtudiant"
          type="text"
          v-model="telephoneEtudiant"
          :disabled="!isModifying"
        />
      </div>
      <div class="grid gap-3">
        <Label for="classeEtudiant">Classe</Label>
        <Input
          id="classeEtudiant"
          type="text"
          v-model="classeEtudiant"
          :disabled="!isModifying"
        />
      </div>
      <div class="grid gap-3">
        <Label for="etablissementEtudiant">Établissement</Label>
        <Input
          id="etablissementEtudiant"
          type="text"
          v-model="etablissementEtudiant"
          :disabled="!isModifying"
        />
      </div>
      <Button
        @click="saveChanges"
        :disabled="isModifying ? undefined : 'secondary'"
      >
        <div v-if="!loading" class="flex items-center space-x-2">
          Enregistrer les modifications
        </div>
      </Button>
    </div>
  </div>
</template>
