<script setup lang="ts">
const props = defineProps({
    id : {
        type : String,
        required : true
    }
});
const id = props.id;
const listeAppels = [
  {
    id: 0,
    nom: "Jean Dupont",
    email: "jean.dupont@gmail.com",
    numero: "06 12 34 56 78",
    statut: "accepte",
  },
  {
    id: 1,
    nom: "Marie Dupont",
    email: "marie.dupont@gmail.com",
    numero: "06 12 34 56 78",
    statut: "refuse",
  },
];

const avancementAppel = ref([]);
avancementAppel.value = listeAppels.map((appel) => {
  return {
    id: appel.id,
    nom: appel.nom,
    email: appel.email,
    numero: appel.numero,
    statut: appel.statut,
    presence: null,
  };
});

async function mettreElevePresent(id: number) {
  const data = await $fetch('http://localhost:8080/PasserellePro/Serv?op=presentdemande', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      id: id,
    }),
    credentials : "include",
  });
  console.log(data);
  avancementAppel.value = avancementAppel.value.map((appel) => {
    if (appel.id == id) {
      appel.presence = true;
    }
    return appel;
  });
}

async function mettreEleveAbsent(id: number) {
  const data = await $fetch('http://localhost:8080/PasserellePro/Serv?op=absentdemande', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({
      id: id,
    }),
    credentials : "include",
  });
  console.log(data);
  
  avancementAppel.value = avancementAppel.value.map((appel) => {
    if (appel.id == id) {
      appel.presence = false;
    }
    return appel;
  });
}

</script>

<template>
    <div>
      <Titre title="Appel" subtitle="Faire l'appel pour cet évenement" />
      <div class="grid grid-cols-1 gap-4 justify-center mx-auto items-center">
        <div v-for="postulant in avancementAppel" :key="postulant.id">
          <Card class="sm:col-span-2 w-[80%]">
            <CardHeader class="pb-3">
              <CardTitle>
                <div class="flex justify-between flex-row">
                  <div>
                    {{ postulant.nom }}
                  </div>
                  <div v-if="postulant.presence == null">
                    <Button
                      @click="mettreElevePresent(postulant.id)"
                      class="mr-2"
                      >Présent</Button
                    >
                    <Button
                      @click="mettreEleveAbsent(postulant.id)"
                      class="bg-red-400"
                      >Absent</Button
                    >
                  </div>
                  <div v-else-if="postulant.presence == true">
                    <Badge variant="primary">Présent</Badge>
                  </div>
                  <div v-else-if="postulant.presence == false">
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