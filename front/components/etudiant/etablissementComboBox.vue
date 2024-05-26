<script setup lang="ts">
import { ref, watchEffect } from "vue";
import { Check, ChevronsUpDown } from "lucide-vue-next";
import { cn } from "../../lib/utils";

/* Je récupère l'école de l'utilsateur */
const props = defineProps({
  etab: {
    type: String,
    required: true,
  },
  isModifying: {
    type: Boolean,
    required: true,
  },
});
/* Je récupère toutes les écoles */
const ecoles = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=lister_ecole`,
  {
    method: "GET",
    credentials: "include",
  }
);

const ecolesTab = [];

for (let i = 0; i < ecoles.length; i++) {
  const ecole = {
    value: ecoles[i].nom,
    label: ecoles[i].nom,
  };
  ecolesTab.push(ecole);
}
console.log("Ecole Tab :", ecolesTab);

const open = ref(false);
const value = ref("");
if (props.etab !== "") {
  value.value = props.etab;
}

const emit = defineEmits(["ecole"]);
watchEffect(() => {
  emit("ecole", value.value);
  console.log(value.value);
});
</script>

<template>
  <Popover v-model:open="open">
    <PopoverTrigger as-child>
      <Button
        variant="outline"
        role="combobox"
        :aria-expanded="open"
        class="w-[200px] justify-between"
        :disabled="!props.isModifying"
      >
        {{
          value
            ? ecolesTab.find((ecole) => ecole.value === value)?.label
            : "Choisir une école..."
        }}
        <ChevronsUpDown class="ml-2 h-4 w-4 shrink-0 opacity-50" />
      </Button>
    </PopoverTrigger>
    <PopoverContent class="w-[200px] p-0">
      <Command>
        <CommandInput class="h-9" placeholder="Nom École" />
        <CommandEmpty>No framework found.</CommandEmpty>
        <CommandList>
          <CommandGroup>
            <CommandItem
              v-for="ecole in ecolesTab"
              :key="ecole.value"
              :value="ecole.value"
              @select="
                (ev) => {
                  if (typeof ev.detail.value === 'string') {
                    value = ev.detail.value;
                  }
                  open = false;
                }
              "
            >
              {{ ecole.label }}
              <Check
                :class="
                  cn(
                    'ml-auto h-4 w-4',
                    value === ecole.value ? 'opacity-100' : 'opacity-0'
                  )
                "
              />
            </CommandItem>
          </CommandGroup>
        </CommandList>
      </Command>
    </PopoverContent>
  </Popover>
</template>
