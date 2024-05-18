<template>
  <div>
    <TagsInput class="w-[70vw]" :model-value="modelValue">
      <div class="flex gap-2 flex-wrap items-center px-3">
        <TagsInputItem v-for="item in modelValue" :key="item" :value="item">
          <TagsInputItemText />
          <TagsInputItemDelete />
        </TagsInputItem>
      </div>

      <ComboboxRoot
        v-model="modelValue"
        v-model:open="open"
        v-model:searchTerm="searchTerm"
        class="w-full"
      >
        <ComboboxAnchor as-child>
          <ComboboxInput placeholder="Domaine..." as-child>
            <TagsInputInput
              class="w-full px-3"
              :class="modelValue.length > 0 ? 'mt-1' : ''"
              @keydown.enter.prevent
            />
          </ComboboxInput>
        </ComboboxAnchor>

        <ComboboxPortal>
          <CommandList
            position="popper"
            class="w-[--radix-popper-anchor-width] rounded-md mt-2 border bg-popover text-popover-foreground shadow-md outline-none data-[state=open]:animate-in data-[state=closed]:animate-out data-[state=closed]:fade-out-0 data-[state=open]:fade-in-0 data-[state=closed]:zoom-out-95 data-[state=open]:zoom-in-95 data-[side=bottom]:slide-in-from-top-2 data-[side=left]:slide-in-from-right-2 data-[side=right]:slide-in-from-left-2 data-[side=top]:slide-in-from-bottom-2"
          >
            <CommandEmpty />
            <CommandGroup>
              <CommandItem
                v-for="domaine in domaines"
                :key="domaine.id"
                :value="domaine.nom"
                @select.prevent="
                  (ev) => {
                    if (typeof ev.detail.value === 'string') {
                      searchTerm = '';
                      modelValue.push(ev.detail.value);
                    }

                    if (domaine.length === 0) {
                      open = false;
                    }
                  }
                "
              >
                {{ domaine.nom }}
              </CommandItem>
              <div
                @click="
                  open = false;
                  searchTerm = '';
                "
                class="ml-2 text-sm text-semibold"
              >
                Fermer
              </div>
            </CommandGroup>
          </CommandList>
        </ComboboxPortal>
      </ComboboxRoot>
    </TagsInput>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import {
  ComboboxAnchor,
  ComboboxInput,
  ComboboxPortal,
  ComboboxRoot,
} from "radix-vue";
const open = ref(false);
const modelValue = ref<string[]>([]);
const searchTerm = ref("");
const selectedDomaine = ref("");
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
</script>

<style></style>
