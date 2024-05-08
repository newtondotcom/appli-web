<script setup lang="ts">
const id_demande = 0;
const nom_evenement = "Découverte IA";
const id_evenement = 0;
const identite = "Jean Dupont";
const email = "jean.dupont@gmail.com";
const numero = "06 12 34 56 78";
const motivation = "Je suis très intéressé par l'intelligence artificielle et je souhaite en apprendre plus sur le sujet.";
    const creneau : string = new Date().toLocaleDateString() + " - " + new Date().toLocaleTimeString();
</script>

<template>
<div class="flex space-y-4 justify-center align-middle content-center mx-auto mb-2">
    <Card class="w-[70vw]">
      <CardHeader class="flex flex-row items-center justify-between space-y-0 pb-2">
        <CardTitle class="text-2xl font-medium">
          {{ nom_evenement }}
        </CardTitle>
        <div class="flex flex-row"><Calendar class="mr-2" /> {{ creneau }}</div>
      </CardHeader>
      <CardContent class="flex space-y-4 justify-center align-middle content-center">
        <Button>Modifier l'évenement</Button>
      </CardContent>
    </Card>
</div>
<Titre :title="identite" subtitle="Découvrez sa demande" />
        <div class="text-xs text-muted-foreground mb-6 ml-6">
        <Badge class="mr-2">{{ email }} </Badge> 
        <Badge  variant="secondary">{{ numero }}</Badge>
        </div>
<div class="flex space-y-4 justify-center align-middle content-center mx-auto">
<Textarea class="w-[60vw] h-[20vh]" :placeholder="motivation" disabled />
</div>

</template>