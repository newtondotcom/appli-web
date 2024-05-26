<script setup lang="ts">
import { DollarSign } from "lucide-vue-next";
const props = defineProps({
  stats: Array,
});

const stats = props.stats;

const statistiques = [
  {
    titre: "Nombre de participants ayant postulé",
    valeur: stats[0]
  },
  {
    titre: "Nombre de participants retenus",
    valeur: stats[1]
  },
  {
    titre: "Nombre de participants présents",
    valeur: stats[2]
  },
  {
    titre: "Note moyenne des participants",
    valeur: stats[3]
  },
];

</script>

<template>
    <div>
      <Titre title="Récapitulatif" subtitle="Récapitulatif de cet évènement" />
      <div class="grid gap-4 md:grid-cols-2 md:gap-8 lg:grid-cols-4">
        <Card v-for="statistique in statistiques" :key="statistique.index">
          <CardHeader
            class="flex flex-row items-center justify-between space-y-0 pb-2"
          >
            <CardTitle class="text-sm font-medium">
              {{ statistique.titre }}
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div class="text-2xl font-bold">
              {{ statistique.valeur }}
            </div>
          </CardContent>
        </Card>
      </div>
</div>
</template>