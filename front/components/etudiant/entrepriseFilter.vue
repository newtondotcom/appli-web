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
              v-for="(entreprise, index) in entreprises"
              :key="index"
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
const data2 = await $fetch(
  "http://localhost:8080/PasserellePro/Serv?op=lister_entreprise"
);
const entreprises = data2;
const emit = defineEmits(["filter"]);
watchEffect(() => {
  emit("filter", selectedEntreprise.value);
});
</script>

<style></style>
