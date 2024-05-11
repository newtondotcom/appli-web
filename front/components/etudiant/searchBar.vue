<script lang="ts" setup>
import { Search } from "lucide-vue-next";
import { ref, watch, onMounted } from "vue";

interface Entreprise {
  id: number;
  nom: string;
}
const entreprises: Entreprise[] = [
  { id: 1, nom: "Google" },
  { id: 2, nom: "Microsoft" },
  { id: 3, nom: "Apple" },
  { id: 4, nom: "Amazon" },
  { id: 5, nom: "Facebook" },
  { id: 6, nom: "Tesla" },
  { id: 7, nom: "Alibaba" },
  { id: 8, nom: "Tencent" },
  { id: 9, nom: "Samsung" },
  { id: 10, nom: "IBM" },
];

interface Domaine {
  id: number;
  nom: string;
}
const domaines: Domaine[] = [
  { id: 1, nom: "Informatique et technologie" },
  { id: 2, nom: "Santé et médecine" },
  { id: 3, nom: "Éducation et formation" },
  { id: 4, nom: "Finance et banque" },
  { id: 5, nom: "Marketing et publicité" },
  { id: 6, nom: "Vente et commerce" },
  { id: 7, nom: "Ressources humaines et gestion de personnel" },
  { id: 8, nom: "Droit et justice" },
  { id: 9, nom: "Immobilier et construction" },
  { id: 10, nom: "Transport et logistique" },
  { id: 11, nom: "Tourisme et hôtellerie" },
  { id: 12, nom: "Agriculture et alimentation" },
  { id: 13, nom: "Environnement et développement durable" },
  { id: 14, nom: "Art et culture" },
  { id: 15, nom: "Sport et loisirs" },
];

const selectedEntreprise = ref("");
const selectedDomaine = ref("");
</script>

<template>
  <div class="flex justify-center">
    <div class="relative w-72 max-w-sm items-center">
      <Input id="search" type="text" placeholder="Search..." class="pl-10" />
      <span
        class="absolute start-0 inset-y-0 flex items-center justify-center px-2"
      >
        <Search class="size-6 text-muted-foreground" />
      </span>
    </div>

    <div class="mx-4">
      <DropdownMenu>
        <DropdownMenuTrigger as-child>
          <Button variant="outline">
            {{ selectedEntreprise === "" ? "Entreprise" : selectedEntreprise }}
          </Button>
        </DropdownMenuTrigger>
        <DropdownMenuContent class="w-56">
          <DropdownMenuLabel>Choisis une entreprise</DropdownMenuLabel>
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

    <div class="mx-4">
      <DropdownMenu>
        <DropdownMenuTrigger as-child>
          <Button variant="outline">
            {{ selectedDomaine === "" ? "Domaine" : selectedDomaine }}
          </Button>
        </DropdownMenuTrigger>
        <DropdownMenuContent class="w-56">
          <DropdownMenuLabel>Choisis un Domainne</DropdownMenuLabel>
          <DropdownMenuSeparator />
          <DropdownMenuRadioGroup v-model="selectedDomaine">
            <ScrollArea class="h-40 w-48 rounded-md">
              <DropdownMenuRadioItem
                v-for="domaine in domaines"
                :key="domaine.id"
                :value="domaine.nom"
              >
                {{ domaine.nom }}
              </DropdownMenuRadioItem>
            </ScrollArea>
          </DropdownMenuRadioGroup>
        </DropdownMenuContent>
      </DropdownMenu>
    </div>
  </div>
</template>

<style></style>
