<script setup lang="ts">
    import {Calendar, Star} from 'lucide-vue-next';
    defineProps({
        id: Number
    });

    const nom = "Découverte IA";
    const description = "Venez découvrir l'intelligence artificielle avec nos collaborateurs durant ce stage de 3 semaines.";
    const creneau : string = new Date().toLocaleDateString() + " - " + new Date().toLocaleTimeString();
    const nom_etablissement = "Airbus";
    const id_etablissement = 0;
    const note_etablissement = 4.5;
</script>

<template>
<div class="flex space-y-4 justify-center align-middle content-center mx-auto">
    <Card class="w-[70vw]">
      <CardHeader class="flex flex-row items-center justify-between space-y-0 pb-2">
        <CardTitle class="text-2xl font-medium">
          {{ nom }}
        </CardTitle>
        <div class="flex flex-row"><Calendar class="mr-2" /> {{ creneau }}</div>
      </CardHeader>
      <CardContent>
        <div class="text-xl">
            {{ description }}
        </div>
        <div class="text-xs text-muted-foreground mt-4">
        <Badge class="mr-2">{{ nom_etablissement }} </Badge> 
        <Badge  variant="secondary">{{ note_etablissement }} / 5 <Star class="ml-1" size="14"/></Badge>
        </div>
      </CardContent>
    </Card>
</div>
</template>