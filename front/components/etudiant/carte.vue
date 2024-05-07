<script setup lang="ts">
interface Evenement {
  id: number;
  title: string;
  nom: string;
  date: string;
  domaine: string;
}

defineProps({
  evenement: {
    type: Object as () => Evenement,
    required: true,
  },
});
</script>

<template>
  <div class="w-[40%]">
    <a :href="`${evenement.id}`">
      <Card>
        <CardHeader>
          <CardDescription class="flex justify-start">
            {{ evenement.nom }}
          </CardDescription>
          <CardTitle>{{ evenement.title }}</CardTitle>
        </CardHeader>
        <CardContent>
          <div class="flex w-16 place-content-center content-center">
            <CalendarDays />
            <p class="text-[9px] place-self-center">{{ evenement.date }}</p>
          </div>
        </CardContent>
        <CardFooter class="flex flex-wrap justify-start">
          <Card class="text-xs bg-stone-200 p-1">{{ evenement.domaine }}</Card>
        </CardFooter>
      </Card>
    </a>
  </div>
</template>

<style></style>
