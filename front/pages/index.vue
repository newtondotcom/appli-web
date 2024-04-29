<script setup lang="ts">
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
]
</script>

<template>
<div class="flex flex-row justify-center items-center h-screen">
<ScrollArea class="h-72 w-48 rounded-md border">
    <div class="p-4">
      <h4 class="mb-4 text-sm font-medium leading-none">
        Entreprises
      </h4>

      <div v-for="tag in domains" :key="tag">
        <div class="text-sm">
          {{ tag }}
        </div>
        <Separator class="my-2" />
      </div>
    </div>
  </ScrollArea>

  <ScrollArea class="h-72 w-48 rounded-md border">
    <div class="p-4">
      <h4 class="mb-4 text-sm font-medium leading-none">
        Domaines
      </h4>

      <div v-for="tag in companies" :key="tag">
        <div class="text-sm">
          {{ tag }}
        </div>
        <Separator class="my-2" />
      </div>
    </div>
  </ScrollArea>
</div> 

</template>