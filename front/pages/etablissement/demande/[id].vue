<script setup lang="ts">
const id = useRoute().params.id;

const data = await $fetch(`http://localhost:8080/PasserellePro/Serv?op=get_evenement_from_id_demande&id=${id}`,
    {
      credentials: "include",
    }
  );
const nom_evenement = data.titre;
const creneau = new Date(data.creneau);
const id_evenement = data.id;


const postulants = await $fetch(
  `http://localhost:8080/PasserellePro/Serv?op=get_demande_from_id&id=${id}`,
  {
    credentials : "include",
  }
);
const motivation = postulants.motivation;
const demandeRepondue = ref(postulants.refuse || postulants.valide);


const postulants3 = await $fetch(
    `http://localhost:8080/PasserellePro/Serv?op=get_util_from_id_dem&id_dem=${id}`,
    {
      credentials: "include",
    }
);
const identite = postulants3.nom.replace("#", " ");
const email = postulants3.email;
const numero = postulants3.numero;
const classe =  postulants3.classe;


async function accepterDemande() {
  console.log(id);
  const data = await $fetch(
    "http://localhost:8080/PasserellePro/Serv?op=validerdemande",
    {
      method: "POST",
      body: JSON.stringify({
        id: id
      }),
      credentials : "include",
    }
  );
  postulants.valide = true;
  demandeRepondue.value = true;
  console.log(data);
}

async function refuserDemande() {
  const data = await $fetch(
    "http://localhost:8080/PasserellePro/Serv?op=refuserdemande",
    {
      method: "POST",
      body: JSON.stringify({
        id: id,
      }),
      credentials : "include",
    }
  );
  postulants.refuse = true;
  demandeRepondue.value = true;
  console.log(data);
}
</script>

<template>
<div>
  <div
    class="flex space-y-4 justify-center align-middle content-center mx-auto mb-2"
  >
    <Card class="w-[70vw]">
      <CardHeader
        class="flex flex-row items-center justify-between space-y-0 pb-2"
      >
        <CardTitle class="text-2xl font-medium">
          {{ nom_evenement }}
        </CardTitle>
        <div class="flex flex-row">          
          {{ creneau.toLocaleDateString() }} -
          {{ creneau.toLocaleTimeString() }}</div>
      </CardHeader>
      <CardContent
        class="flex space-y-4 justify-center align-middle content-center"
      >
        <a :href="'/etablissement/evenement/' + id_evenement">
          <Button>Voir l'évenement</Button>
        </a>
      </CardContent>
    </Card>
  </div>
  <Titre :title="identite" subtitle="Découvrez sa demande" />
  <div class="text-xs text-muted-foreground mb-6 ml-6">
    <Badge class="mr-2">{{ email }} </Badge>
    <Badge v-if="numero!=null" variant="secondary">{{ numero }}</Badge>
    <Badge v-if="numero!=null" class="ml-2" variant="outline">{{ classe }}</Badge>
  </div>
  <div
    class="flex space-y-4 justify-center align-middle content-center mx-auto"
  >
    <Textarea class="w-[60vw] h-[20vh]" :placeholder="motivation" disabled />
  </div>
  <div
    v-if="!demandeRepondue"
    class="flex flex-row space-x-4 justify-center align-middle content-center mx-auto mt-8"
  >
    <Button @click="accepterDemande">Accepter</Button>
    <Button @click="refuserDemande" variant="secondary">Refuser</Button>
  </div>
  <div
    v-else
    class="flex flex-row space-x-4 justify-center align-middle content-center mx-auto mt-8"
  >
    <Badge variant="primary">Demande traitée</Badge>
  </div>
</div>
</template>