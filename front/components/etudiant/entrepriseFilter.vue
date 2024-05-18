<template>
  <div class="mx-4">
    <DropdownMenu>
      <DropdownMenuTrigger as-child>
        <Button variant="outline" class="h-full w-full">
          {{ selectedEntreprise === "" ? "Entreprise" : selectedEntreprise }}
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent class="w-56">
        <DropdownMenuLabel>Choisis une entreprise</DropdownMenuLabel>
        <DropdownMenuSeparator />
        <DropdownMenuRadioGroup v-model="selectedEntreprise">
          <DropdownMenuRadioItem value="">
            Aucune Selection
          </DropdownMenuRadioItem>
        </DropdownMenuRadioGroup>
        <DropdownMenuSeparator />
        <DropdownMenuRadioGroup v-model="selectedEntreprise">
          <ScrollArea class="h-40 w-48 rounded-md">
            <DropdownMenuRadioItem
              v-for="entreprise in entreprises"
              :key="entreprise.id"
              :value="entreprise.nom"
            >
              {{ entreprise.nom }}
            </DropdownMenuRadioItem>
          </ScrollArea>
        </DropdownMenuRadioGroup>
      </DropdownMenuContent>
    </DropdownMenu>
  </div>
</template>

<script lang="ts" setup>
import { ref, watchEffect } from "vue";
const selectedEntreprise = ref("");
interface Entreprise {
  id: number;
  nom: string;
}
const entreprises: Entreprise[] = [
  { id: 1, nom: "Google" },
  { id: 2, nom: "Microsoft" },
  { id: 3, nom: "Airbus" },
];
const emit = defineEmits(["filter"]);
watchEffect(() => {
  console.log(selectedEntreprise.value);
  emit("filter", selectedEntreprise.value);
});
</script>

<style></style>
