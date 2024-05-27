<script setup lang="ts">
import { Star } from "lucide-vue-next";
const props = defineProps({
  avis: {
    type: Object as () => any,
    required: true,
  },
});
console.log("Props", props.avis);
const data = props.avis;
</script>

<template>
  <Card class="w-[350px]">
    <CardHeader>
      <CardTitle>{{ data.titre }}</CardTitle>
    </CardHeader>
    <CardContent>
      <p>{{ data.contenu }}</p>
    </CardContent>
    <CardFooter class="flex justify-between px-6 pb-6">
      <Badge variant="secondary"
        >{{ data.note }} / 5
        <Star class="ml-1" :size="14" />
      </Badge>
    </CardFooter>
  </Card>
</template>
