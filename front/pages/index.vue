<script setup lang="ts">
import {MoveRight} from 'lucide-vue-next';

const tags = Array.from({ length: 50 }).map(
  (_, i, a) => `v1.2.0-beta.${a.length - i}`,
)
const domains = [
  'Développement logiciel',
  'Développement web',
  'Développement mobile',
  'Conception de produits',
  'Marketing numérique',
  'Analyse de données',
  'Science des données',
  'Ingénierie des données',
  'Sécurité informatique',
  'Réseaux informatiques',
  'Administration système',
  'Gestion de projet',
    'Gestion de produit',
    'Gestion de projet',
]

const companies = [
"SAP",
"Salesforce",
"VMware",
"Dell",
"HP",
"Intuit",
"Uber",
"Airbnb",
"Dropbox",
"Slack",
"Twilio",
"Stripe",
"Shopify",
"Zoom",
]

const defaultValue = 'item-1'
const accordionItems = [
  { value: 'item-1', title: 'Is it accessible?', content: 'Yes. It adheres to the WAI-ARIA design pattern.' },
  { value: 'item-2', title: 'Is it unstyled?', content: 'Yes. It\'s unstyled by default, giving you freedom over the look and feel.' },
  { value: 'item-3', title: 'Can it be animated?', content: 'Yes! You can use the transition prop to configure the animation.' },
]
</script>

<template>
<div class="flex flex-col justify-center items-center h-full">
    <Alert class="w-[60%] my-6">
    <AlertTitle>Heads up!</AlertTitle>
    <AlertDescription>
      You can add components to your app using the cli.
    </AlertDescription>
  </Alert>
<div class="flex flex-row justify-center items-center">
<ScrollArea class="h-[450px] w-[300px] rounded-md border mr-8  my-6">
    <div class="p-4">
      <h4 class="mb-4 text-md font-bold leading-none">
        Entreprises
      </h4>

      <div v-for="tag in domains" :key="tag">
        <div class="text-sm">
          {{ tag }}
        </div>
        <Separator class="my-2" />
      </div>
      <div class="font-medium flex">Voir plus <MoveRight class="ml-2" /></div>
    </div>
  </ScrollArea>

  <ScrollArea class="h-[450px] w-[300px] rounded-md border ml-8">
    <div class="p-4">
      <h4 class="mb-4 text-md font-bold leading-none">
        Domaines
      </h4>

      <div v-for="tag in companies" :key="tag">
        <div class="text-sm">
          {{ tag }}
        </div>
        <Separator class="my-2" />
      </div>
      <div class="font-medium flex">Voir plus <MoveRight class="ml-2" /></div>
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