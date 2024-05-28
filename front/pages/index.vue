<script setup lang="ts">
import { MoveRight } from "lucide-vue-next";

const companies = ref([]);
const domains = ref([]);

const accordionItems = [
  {
    value: "item-1",
    title: "Qu'est-ce que PasserellePro ?",
    content:
      "PasserellePro est une plateforme qui met en relation des élèves et des établissements (écoles et entreprises) pour des immersions de courte durée, facilitant ainsi la découverte de nouvelles carrières et spécialisations.",
  },
  {
    value: "item-2",
    title: "Comment fonctionne PasserellePro ?",
    content:
      "Les élèves peuvent rechercher et postuler à des opportunités d'immersion proposées par divers établissements. Les établissements peuvent accepter et refuser les élèves pour des immersions spécifiques, facilitant ainsi une connexion bénéfique pour les deux parties.",
  },
  {
    value: "item-3",
    title:
      "Quels types d'établissements peuvent s'inscrire sur PasserellePro ?",
    content:
      "PasserellePro est ouvert aux écoles, universités, entreprises et tout autre type d'établissement offrant des opportunités d'immersion pour les élèves.",
  },
  {
    value: "item-4",
    title: "Quels sont les avantages pour les élèves utilisant PasserellePro ?",
    content:
      "Les élèves peuvent découvrir différentes carrières et spécialisations, acquérir une expérience précieuse dans un environnement réel et établir des contacts professionnels qui pourraient être bénéfiques pour leur future carrière.",
  },
  {
    value: "item-5",
    title:
      "Quels sont les avantages pour les établissements utilisant PasserellePro ?",
    content:
      "Les établissements peuvent attirer des talents prometteurs, contribuer à l'éducation et au développement professionnel des élèves, et éventuellement découvrir de futurs employés potentiels.",
  },
  {
    value: "item-6",
    title: "Comment postuler à une opportunité d'immersion ?",
    content:
      "Une fois connecté à votre compte, vous pouvez parcourir les opportunités disponibles, cliquer sur celles qui vous intéressent et suivre les instructions pour postuler.",
  },
];

const words = ["entreprise.", "spécialisation.", "carrière.", "passion."];
let currentText = ref("");
let i = 0;
let j = 0;
let currentWord = "";
let isDeleting = false;

onMounted(async () => {
  const data = await $fetch(
    "http://localhost:8080/PasserellePro/Serv?op=lister_etab_domain"
  );
  companies.value = JSON.parse(data.split(";")[0]);
  domains.value = JSON.parse(data.split(";")[1]);
  type();
  /*
  const data2 = await $fetch(
    "http://localhost:8080/PasserellePro/Serv?op=lister_entreprise"
  );
  companies.value = data2;
  console.log(companies.value);
  */
  type();
});

function type() {
  currentWord = words[i];
  if (isDeleting) {
    currentText.value = currentWord.substring(0, j - 1);
    j--;
    if (j == 0) {
      isDeleting = false;
      i++;
      if (i == words.length) {
        i = 0;
      }
    }
  } else {
    currentText.value = currentWord.substring(0, j + 1);
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
        <div
          class="min-w-[250px] flex bg-gradient-to-r from-cyan-500 to-blue-500 text-transparent bg-clip-text"
        >
          {{ currentText }}
        </div>
      </h1>
    </div>

    <div class="flex flex-row justify-center items-center">
      <ScrollArea class="h-[450px] w-[300px] rounded-md border mr-8 my-6">
        <div class="p-4">
          <h4 class="mb-4 text-md font-bold leading-none">Domaines</h4>

          <div v-for="tag in domains" :key="tag">
            <div class="text-sm">
              {{ tag.nom }}
            </div>
            <Separator class="my-2" />
          </div>
        </div>
      </ScrollArea>

      <ScrollArea class="h-[450px] w-[300px] rounded-md border ml-8">
        <div class="p-4">
          <h4 class="mb-4 text-md font-bold leading-none">Entreprises</h4>

          <div v-for="tag in companies" :key="tag">
            <div class="text-sm">
              {{ tag.nom }}
            </div>
            <Separator class="my-2" />
          </div>
        </div>
      </ScrollArea>
    </div>

    <Accordion
      type="single"
      class="w-[60vw] border-2 rounded-lg border-dotted px-8 py-4"
      collapsible
    >
      <AccordionItem
        v-for="item in accordionItems"
        :key="item.value"
        :value="item.value"
      >
        <AccordionTrigger>{{ item.title }}</AccordionTrigger>
        <AccordionContent>
          {{ item.content }}
        </AccordionContent>
      </AccordionItem>
    </Accordion>
  </div>
</template>
