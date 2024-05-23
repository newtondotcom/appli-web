<script setup lang="ts">
defineProps({
  evenement: {
    type: Object as () => EvenementEtablissement,
    required: true,
  },
});
import { Calendar, Star } from "lucide-vue-next";
</script>

<template>
  <div
    class="flex space-y-4 justify-center align-middle content-center mx-auto"
    :class="{ 'opacity-50': new Date(evenement.creneau) < new Date() }"
  >
    <Card class="w-[70vw]">
      <CardHeader
        class="flex flex-row items-center justify-between space-y-0 pb-2"
      >
        <CardTitle class="text-2xl font-medium">
          {{ evenement.titre }}
        </CardTitle>
        <div class="flex flex-row">
          <Calendar class="mr-2" />
          {{ new Date(evenement.creneau).toLocaleDateString() }} -
          {{ new Date(evenement.creneau).toLocaleTimeString() }}
        </div>
      </CardHeader>
      <CardContent>
        <div class="text-xl">
          {{ evenement.description }}
        </div>
        <div class="text-xs text-muted-foreground mt-4 flex-row">
          <div class="text-xs text-muted-foreground mt-4 flex-row">
            <div class="flex justify-between">
              <div>
                <Badge class="mr-2">{{ evenement.nom_etablissement }} </Badge>
                <Badge variant="secondary"
                  >{{ evenement.note_etablissement }} / 5
                  <Star class="ml-1" :size="14" />
                </Badge>
              </div>
              <div class="flex flex-row mt-2">
                <Badge class="mr-2" v-for="tag in evenement.tags" :key="tag">{{
                  tag
                }}</Badge>
              </div>
            </div>
          </div>
        </div>
      </CardContent>
    </Card>
  </div>
</template>
