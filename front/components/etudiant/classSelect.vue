<template>
  <div>
    <Select v-model="classeChosis" :disabled="!props.isModifying">
      <SelectTrigger class="w-[280px]">
        <SelectValue placeholder="Choisir une classe" />
      </SelectTrigger>
      <SelectContent>
        <SelectGroup>
          <SelectLabel>Collège</SelectLabel>
          <SelectItem value="6 ème"> 6 ème </SelectItem>
          <SelectItem value="5 ème"> 5 ème </SelectItem>
          <SelectItem value="4 ème"> 4 ème </SelectItem>
          <SelectItem value="3 ème"> 3 ème </SelectItem>
        </SelectGroup>
        <SelectGroup>
          <SelectLabel>Lycée</SelectLabel>
          <SelectItem value="Seconde"> Seconde </SelectItem>
          <SelectItem value="Première"> Première </SelectItem>
          <SelectItem value="Terminale"> Terminale </SelectItem>
        </SelectGroup>
        <SelectGroup>
          <SelectLabel>Post Bac</SelectLabel>
          <SelectItem value="BAC +1"> BAC +1 </SelectItem>
          <SelectItem value="BAC +2"> BAC +2 </SelectItem>
          <SelectItem value="BAC +3"> BAC +3 </SelectItem>
          <SelectItem value="BAC +4"> BAC +4 </SelectItem>
          <SelectItem value="BAC +5"> BAC +5 </SelectItem>
        </SelectGroup>
      </SelectContent>
    </Select>
  </div>
</template>

<script lang="ts" setup>
import { ref, watchEffect } from "vue";
const props = defineProps({
  class: {
    type: String,
    required: true,
  },
  isModifying: {
    type: Boolean,
    required: true,
  },
});
const classeChosis = ref("");
if (props.class !== "") {
  classeChosis.value = props.class;
}
const emit = defineEmits(["classe"]);
watchEffect(() => {
  emit("classe", classeChosis.value);
  console.log(classeChosis.value);
});
</script>

<style></style>
