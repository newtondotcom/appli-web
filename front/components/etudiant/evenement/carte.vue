<script setup lang="ts">
import {
  Calendar,
  Star,
  TicketCheck,
  TicketSlash,
  TicketX,
} from "lucide-vue-next";
import { ref } from "vue";
const props = defineProps({
  evenement: {
    type: Object as () => EvenementEtablissement,
    required: true,
  },
});
/* Traitement de la demande */
const uid = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=get_uid_from_token`,
  {
    method: "GET",
    credentials: "include",
  }
);
const demandeValide = ref(false);
const demandeEnAttente = ref(true);
const demandeRefuse = ref(false);
const demandeEffectue = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=get_bool_demande_from_eventid_utilid&id_util=${uid}&id_event=${props.evenement.id}`,
  {
    method: "GET",
    credentials: "include",
  }
);
if (demandeEffectue) {
  const demande = await $fetch(
    `http://localhost:8080/PasserellePro/Serv?op=get_demande_from_eventid_utilid&id_util=${uid}&id_event=${props.evenement.id}`,
    {
      method: "GET",
      credentials: "include",
    }
  );
  console.log(demande);
  demandeValide.value = demande.valide;
  demandeRefuse.value = demande.refuse;
  if (!demande.valide && !demande.refuse) {
    demandeEnAttente.value = true;
  }
}
</script>

<template>
  <div
    class="flex space-y-4 justify-center align-middle content-center mx-auto"
    :class="{ 'opacity-50': new Date(props.evenement.creneau) < new Date() }"
  >
    <Card class="w-[70vw]">
      <CardHeader
        class="flex flex-row items-center justify-between space-y-0 pb-2"
      >
        <CardTitle class="text-2xl font-medium">
          {{ props.evenement.titre }}
        </CardTitle>
        <div class="flex flex-row">
          <Calendar class="mr-2" />
          {{ new Date(props.evenement.creneau).toLocaleDateString() }} -
          {{ new Date(props.evenement.creneau).toLocaleTimeString() }}
        </div>
      </CardHeader>
      <CardContent>
        <div class="text-xl flex justify-between">
          <div>{{ props.evenement.description }}</div>
          <div v-if="demandeEffectue" class="flex justify-center grid">
            <TicketCheck
              color="#3e9392"
              class="w-12 h-12 justify-self-center"
              v-if="demandeValide"
            />
            <TicketSlash
              color="#c4bc00"
              class="w-8 h-8 justify-self-center"
              v-if="demandeEnAttente"
            />
            <TicketX
              color="#b51a00"
              class="w-12 h-12 justify-self-center"
              v-if="demandeRefuse"
            />
          </div>
        </div>
        <div class="text-xs text-muted-foreground mt-4 flex-row">
          <div class="text-xs text-muted-foreground mt-4 flex-row">
            <div class="flex justify-between">
              <div>
                <Badge class="mr-2"
                  >{{ props.evenement.nom_etablissement }}
                </Badge>
                <Badge variant="secondary"
                  >{{ props.evenement.note_etablissement }} / 5
                  <Star class="ml-1" :size="14" />
                </Badge>
              </div>
              <div class="flex flex-row mt-2">
                <Badge
                  class="mr-2"
                  v-for="tag in props.evenement.tags"
                  :key="tag"
                  >{{ tag }}</Badge
                >
              </div>
            </div>
          </div>
        </div>
      </CardContent>
    </Card>
  </div>
</template>
