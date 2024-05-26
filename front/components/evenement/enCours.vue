<script setup lang="ts">
const props = defineProps({
  id: {
    type: String,
    required: true,
  },
});
import { useToast } from "@/components/ui/toast/use-toast";
const { toast } = useToast();
const id = props.id;
const postulants2 = ref([]);
const postulants = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=get_demandes_from_eventid&id=${id}`,
  {
    credentials: "include",
  }
);
for (let i = 0; i < postulants.length; i++) {
  console.log(postulants[i].id);
  const postulants3 = await $fetch(
    `http://localhost:8080/PasserellePro/Serv?op=get_util_from_id_dem&id_dem=${postulants[i].id}`,
    {
      credentials: "include",
    }
  );
  postulants2.value.push({
    id: postulants[i].id,
    nom: postulants3.nom.replace("#", " "), // "nom" : "nom#prenom"
    email: postulants3.email,
    numero: postulants3.numero,
    valide: postulants[i].valide,
    refuse: postulants[i].refuse,
    present: postulants[i].present,
    presenceverifiee: false,
  });
}

async function mettreElevePresent(id: number) {
  const data = await $fetch(
    "http://localhost:8080/PasserellePro/Serv?op=presentdemande",
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        id: id,
      }),
      credentials: "include",
    }
  );
  if (data != "Error") {
    toast({
      title: "Succès",
      description: "L'élève a été marqué comme présent",
    });
    postulants2.value = postulants2.value.map((appel) => {
      if (appel.id == id) {
        appel.present = true;
        appel.presenceverifiee = true;
      }
      return appel;
    });
  } else {
    toast({
      title: "Erreur",
      description:
        "Une erreur est survenue lors de la mise à jour de la présence",
    });
  }
}

async function mettreEleveAbsent(id: number) {
  const data = await $fetch(
    "http://localhost:8080/PasserellePro/Serv?op=absentdemande",
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        id: id,
      }),
      credentials: "include",
    }
  );
  if (data != "Error") {
    toast({
      title: "Succès",
      description: "L'élève a été marqué comme absent",
    });
    postulants2.value = postulants2.value.map((appel) => {
      if (appel.id == id) {
        appel.present = false;
        appel.presenceverifiee = true;
      }
      return appel;
    });
  } else {
    toast({
      title: "Erreur",
      description:
        "Une erreur est survenue lors de la mise à jour de la présence",
    });
  }
}
</script>

<template>
  <div>
    <Titre title="Appel" subtitle="Faire l'appel pour cet évenement" />
    <div class="grid grid-cols-1 gap-4 justify-center mx-auto items-center">
      <div v-for="postulant in postulants2" :key="postulant.id">
        <Card class="sm:col-span-2 w-[80%]">
          <CardHeader class="pb-3">
            <CardTitle>
              <div class="flex justify-between flex-row">
                <div>
                  {{ postulant.nom }}
                </div>
                <div v-if="!postulant.presenceverifiee">
                  <Button @click="mettreElevePresent(postulant.id)" class="mr-2"
                    >Présent</Button
                  >
                  <Button
                    @click="mettreEleveAbsent(postulant.id)"
                    class="bg-red-400"
                    >Absent</Button
                  >
                </div>
                <div
                  v-else-if="
                    postulant.present == true && postulant.presenceverifiee
                  "
                >
                  <Badge variant="primary">Présent</Badge>
                </div>
                <div
                  v-else-if="
                    postulant.presence == false && postulant.presenceverifiee
                  "
                >
                  <Badge variant="secondary">Absent</Badge>
                </div>
              </div>
            </CardTitle>
            <CardDescription class="max-w-lg text-balance leading-relaxed">
              {{ postulant.email }} <br />
              {{ postulant.numero }}
            </CardDescription>
          </CardHeader>
        </Card>
      </div>
    </div>
  </div>
</template>