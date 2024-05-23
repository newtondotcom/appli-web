<script setup lang="ts">
import {MoveRight} from 'lucide-vue-next';

const data = await $fetch('http://localhost:8080/PasserellePro/Serv?op=lister_etab_domain');
const companies = JSON.parse(data.split(";")[0])
const domains = JSON.parse(data.split(";")[1])

const defaultValue = 'item-1'
const accordionItems = [
  { value: 'item-1', title: 'Is it accessible?', content: 'Yes. It adheres to the WAI-ARIA design pattern.' },
  { value: 'item-2', title: 'Is it unstyled?', content: 'Yes. It\'s unstyled by default, giving you freedom over the look and feel.' },
  { value: 'item-3', title: 'Can it be animated?', content: 'Yes! You can use the transition prop to configure the animation.' },
]

const words = ["entreprise.", "spécialisation.", "carrière.", "passion."];
let currentText = ref("");
let i = 0;
let j = 0;
let currentWord = "";
let isDeleting = false;

onMounted(() => {
  type();
});
function type() {
  currentWord = words[i];
  if (isDeleting) {
    currentText.value = currentWord.substring(0, j-1);
    j--;
    if (j == 0) {
      isDeleting = false;
      i++;
      if (i == words.length) {
        i = 0;
      }
    }
  } else {
    currentText.value = currentWord.substring(0, j+1);
    j++;
    if (j == currentWord.length) {
      isDeleting = true;
    }
  }
  setTimeout(type, 100);
}
</script>

<template>
<div class="flex flex-col justify-center items-center h-full">
  <div class="w-full h-20 flex justify-center items-center">
    <h1 class="text-4xl font-bold flex flex-row items-center">
      <div class="mr-2">Découvrez une</div>
      <div class="min-w-[250px] flex bg-gradient-to-r from-cyan-500 to-blue-500 text-transparent bg-clip-text">
        {{ currentText }}
      </div>
    </h1>
  </div>

<div class="flex flex-row justify-center items-center">
<ScrollArea class="h-[450px] w-[300px] rounded-md border mr-8  my-6">
    <div class="p-4">
      <h4 class="mb-4 text-md font-bold leading-none">
        Entreprises
      </h4>

      <div v-for="tag in domains" :key="tag">
        <div class="text-sm">
          {{ tag.nom }}
        </div>
        <Separator class="my-2" />
      </div>
      <a href="" class="font-medium flex">Voir plus <MoveRight class="ml-2" /></a>
    </div>
  </ScrollArea>

  <ScrollArea class="h-[450px] w-[300px] rounded-md border ml-8">
    <div class="p-4">
      <h4 class="mb-4 text-md font-bold leading-none">
        Domaines
      </h4>

      <div v-for="tag in companies" :key="tag">
        <div class="text-sm">
          {{ tag.nom }}
        </div>
        <Separator class="my-2" />
      </div>
      <a  class="font-medium flex">Voir plus <MoveRight class="ml-2" /></a>
    </div>
  </ScrollArea>
</div> 

<Accordion type="single" class="w-[60vw] border-2 rounded-lg border-dotted px-8 py-4" collapsible :default-value="defaultValue">
    <AccordionItem v-for="item in accordionItems" :key="item.value" :value="item.value">
      <AccordionTrigger>{{ item.title }}</AccordionTrigger>
      <AccordionContent>
        {{ item.content }}
      </AccordionContent>
    </AccordionItem>
  </Accordion>
</div>
</template>