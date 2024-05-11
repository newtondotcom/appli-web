<script setup lang="ts">
import { ref } from "vue";
import { useToast } from "@/components/ui/toast/use-toast";
import { Settings } from "lucide-vue-next";
const { toast } = useToast();

const studentFirstName = ref("");
const studentLastName = ref("");
const studentClass = ref("");
const studentEtablissement = ref("");
const studentDescription = ref("");
const loading = ref(false);
const isModifying = ref(false);

const route = useRoute();
let id = -1;
if (route.query.id) {
  id = route.query.id as number;
}

async function saveChanges() {
  loading.value = true;
  toast({
    title: "Succès",
    description: "Les modifications ont été enregistrées avec succès",
  });
  loading.value = false;
}

async function Modifyprofil() {
  isModifying.value = !isModifying.value;
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
        <Label for="studentLastName">Nom</Label>
        <Input
          id="studentLastName"
          type="text"
          v-model="studentLastName"
          :disabled="!isModifying"
        />
      </div>
      <div class="grid gap-3">
        <Label for="studentFirstName">Prénom</Label>
        <Input
          id="studentFirstName"
          type="text"
          v-model="studentFirstName"
          :disabled="!isModifying"
        />
      </div>
      <div class="grid gap-3">
        <Label for="studentClass">Classe</Label>
        <Input
          id="studentClass"
          type="text"
          v-model="studentClass"
          :disabled="!isModifying"
        />
      </div>
      <div class="grid gap-3">
        <Label for="studentEstalishment">Établissement</Label>
        <Input
          id="studentEstalishment"
          type="text"
          v-model="studentEtablissement"
          :disabled="!isModifying"
        />
      </div>
      <div class="grid gap-3">
        <Label for="studentDescription">Établissement</Label>
        <Textarea
          id="studentDescription"
          v-model="studentDescription"
          :disabled="!isModifying"
        />
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
  </div>
</template>
