<script setup lang="ts">
import { ref } from "vue";
import { useToast } from "@/components/ui/toast/use-toast";
import { Settings } from "lucide-vue-next";
import { useRoute } from "vue-router";
const { toast } = useToast();

const nomEtudiant = ref("");
const prenomEtudiant = ref("");
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
console.log("infos : ", infos);
console.log(infos.nom);
let nomPrenom = infos.nom.split("#");

nomEtudiant.value = nomPrenom[0];
prenomEtudiant.value = nomPrenom[1];
ineEtudiant.value = infos.INE;
emailEtudiant.value = infos.email;
telephoneEtudiant.value = infos.telephone;
classeEtudiant.value = infos.classe;
etablissementEtudiant.value = etab.nom;
/* Fonction qui permet de changer de mode en edit et non-edit */
async function Modifyprofil() {
  isModifying.value = !isModifying.value;
}
/* Réfrence pour les valeurs de validation */
const isNomValid = ref(true);
const isPrenomValid = ref(true);
const isINEValid = ref(false);
const isEmailValid = ref(true);
const isTelephoneValid = ref(false);
const isClassValid = ref(false);
/* Expression Régulière pour analyser les valeur rentrer */
const nomRegex = /^[a-zA-ZàâäéèêëëïîîööôùûûüçÀÂÄÉÈÊËËÏÎÎÖÖÔÙÛÛÜÇç\s-]{2,}$/;
const ineRegex = /^[a-zA-Z0-9]{11}$/;
const addressRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
const telephoneRegex = /^\d{10}$/;

/* Référence pour le texte d'erreur */
const errorText = ref("");

const validateNom = () => {
  if (!nomRegex.test(nomEtudiant.value)) {
    errorText.value += "Nom, ";
    isNomValid.value = false;
  } else {
    isNomValid.value = true;
  }
};
const validatePrenom = () => {
  if (!nomRegex.test(prenomEtudiant.value)) {
    errorText.value += "Prénom, ";
    isPrenomValid.value = false;
  } else {
    isPrenomValid.value = true;
  }
};
const validateINE = () => {
  if (!ineRegex.test(ineEtudiant.value)) {
    errorText.value += "INE, ";
    isINEValid.value = false;
  } else {
    isINEValid.value = true;
  }
};
const validateEmail = () => {
  if (!addressRegex.test(emailEtudiant.value)) {
    errorText.value += "Email, ";
    isEmailValid.value = false;
  } else {
    isEmailValid.value = true;
  }
};
const validateTelephone = () => {
  if (!telephoneRegex.test(telephoneEtudiant.value)) {
    errorText.value += "Téléphone, ";
    isTelephoneValid.value = false;
  } else {
    isTelephoneValid.value = true;
  }
};
const validateClass = () => {
  if (classeEtudiant.value === "") {
    errorText.value += "Classe, ";
    isClassValid.value = false;
  } else {
    isClassValid.value = true;
  }
};
async function saveChanges() {
  loading.value = true;
  validateNom();
  validateINE();
  validateEmail();
  validateTelephone();
  validateClass();
  validatePrenom;
  if (
    isNomValid.value &&
    isINEValid.value &&
    isEmailValid.value &&
    isTelephoneValid.value &&
    isClassValid.value &&
    isPrenomValid.value
  ) {
    try {
      const data = await $fetch(
        `http://localhost:8080/PasserellePro/Serv?op=modifier_util`,
        {
          method: "POST",
          credentials: "include",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            nom: nomEtudiant.value,
            prenom: prenomEtudiant.value,
            email: emailEtudiant.value,
            telephone: telephoneEtudiant.value,
            classe: classeEtudiant.value,
            id_util: id,
          }),
        }
      );
      const data2 = await $fetch(
        `http://localhost:8080/PasserellePro/Serv?op=modifier_ine_util`,
        {
          method: "POST",
          credentials: "include",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            ine: ineEtudiant.value,
            id_util: id,
          }),
        }
      );
      console.log(data);
      console.log(data2);
      await Modifyprofil();
    } catch (error) {
      console.error(error);
    }
  } else {
    toast({
      title: "Erreur",
      description: "Veuillez vérifier les champs suivants: " + errorText.value,
    });
  }
  errorText.value = "";
  loading.value = false;
}
const handleClasse = (classe: string) => {
  classeEtudiant.value = classe;
};
const handleEcole = (ecole: string) => {
  etablissementEtudiant.value = ecole;
};
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
        <Label for="prenomEtudiant">Prénom</Label>
        <Input
          id="prenomEtudiant"
          type="text"
          v-model="prenomEtudiant"
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
        <EtudiantClassSelect
          @classe="handleClasse"
          :class="classeEtudiant"
          :isModifying="isModifying"
        />
      </div>
      <div class="grid gap-3">
        <Label for="etablissementEtudiant">Établissement</Label>
        <EtudiantEtablissementComboBox
          @ecole="handleEcole"
          :etab="etablissementEtudiant"
          :isModifying="isModifying"
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
