<script setup lang="ts">
import {cn} from '~/lib/utils';
import { ref, watch } from 'vue'
import { Check, ChevronsUpDown } from 'lucide-vue-next'

import { useToast } from '@/components/ui/toast/use-toast'
const { toast } = useToast()

const open = ref(false)
const idEtab = ref('')
const label = ref('')
const etu = ref(false)
const email = ref('f@test.com')
const password = ref('1234')
const nom = ref('Cyril')
const prenom = ref('Lzdq')

const loading = ref(false)

const data = await $fetch('http://localhost:8080/PasserellePro/Serv?op=lister_etab_domain');
const companies = JSON.parse(data.split(";")[0])
const etablissements = companies.map((company) => ({ value: company.SIREN, label: company.nom, isEntreprise : company.entreprise }))
const etablissementsAffiches = ref([])
etablissementsAffiches.value = etablissements.filter((company) => company.isEntreprise === true)

watch(etu, (value) => {
  if (value) {
    etablissementsAffiches.value = etablissements.filter((company) => company.isEntreprise === false)
  } else {
    etablissementsAffiches.value = etablissements.filter((company) => company.isEntreprise === true)
  }
})

async function enregistrer() {
  loading.value = true
  try {
    const data2 = $fetch(`http://localhost:8080/PasserellePro/Serv?op=body`, {
      method: 'POST',
      body: JSON.stringify({
        nom: nom.value,
        prenom: prenom.value,
        email: email.value,
        mdp: password.value,
        siren : etu ? 0 : idEtab.value,
      }),
    });

    console.log(nom.value);
    console.log(prenom.value);
    console.log(email.value);
    console.log(password.value);
    console.log(etu ? 0 : idEtab.value);
    
    const data = await $fetch(`http://localhost:8080/PasserellePro/Serv?op=enregistrer_util`, {
      method: 'POST',
      body: JSON.stringify({
        nom: nom.value,
        prenom: prenom.value,
        email: email.value,
        mdp: password.value,
        siren : etu ? 0 : idEtab.value,
      }),
    });
    const token = data;
    const token_cookie = useCookie('token', { path: '/' });
    token_cookie.value = token;
  } catch (error) {
    console.error(error);
    toast({
      title: 'Error',
      description: 'An error occurred while registering.',
      status: 'error',
    });
  }
  loading.value = false
}


</script>

<template>
  <Card class="mx-auto max-w-sm mt-[100px]">
    <CardHeader>
      <CardTitle class="text-xl">
        Créer un compte
      </CardTitle>
      <CardDescription>
        Entrez vos informations pour créer un compte
      </CardDescription>
    </CardHeader>
    <CardContent>
      <div class="grid gap-4">
        <div class="grid grid-cols-2 gap-4">
          <div class="grid gap-2">
            <Label for="first-name">Prénom</Label>
            <Input v-model="prenom" id="first-name" placeholder="Max" required />
          </div>
          <div class="grid gap-2">
            <Label for="last-name">Nom</Label>
            <Input v-model="nom" id="last-name" placeholder="Robinson" required />
          </div>
        </div>
        <div class="grid gap-2">
          <Label for="email">Email</Label>
          <Input
            id="email"
            type="email"
            placeholder="m@example.com"
            v-model="email"
            required
          />
        </div>
        <div class="grid gap-2">
          <Label for="password">Mot de passe</Label>
          <Input v-model="password" id="password" type="password" />
        </div>

        <div class="gap-2 flex flex-row">
        <Switch v-model:checked="etu" />
        <div class="flex" v-if="etu">Etudiant</div>
        <div class="flex" v-else>Membre d'un établissement</div>
        </div>


        <div class="grid gap-2">
          <Label for="password">Établissement</Label>
          <Popover v-model:open="open">
            <PopoverTrigger as-child>
              <Button
                variant="outline"
                role="combobox"
                :aria-expanded="open"
                class="justify-between"
              >
                {{ idEtab
                  ? label
                  : "Sélectionner votre établissement..." }}
                <ChevronsUpDown class="ml-2 h-4 w-4 shrink-0 opacity-50" />
              </Button>
            </PopoverTrigger>
            <PopoverContent class="p-0">
              <Command>
                <CommandInput class="h-9" placeholder="Chercher un établissement..."/>
                <CommandEmpty>Pas d'établissement trouvé.</CommandEmpty>
                <CommandList>
                  <CommandGroup>
                    <CommandItem
                      v-for="framework in etablissementsAffiches"
                      :key="framework.value"
                      :value="framework.value"
                      @select="(ev) => {
                        if (typeof ev.detail.value === 'number') {
                          idEtab = ev.detail.value.toString()
                          label = framework.label
                        }
                        open = false
                      }"
                    >
                      {{ framework.label }}
                      <Check
                        :class="cn(
                          'ml-auto h-4 w-4',
                          idEtab === framework.value ? 'opacity-100' : 'opacity-0',
                        )"
                      />
                    </CommandItem>
                  </CommandGroup>
                </CommandList>
              </Command>
            </PopoverContent>
          </Popover>
        </div>

        <Button @click="enregistrer" type="submit" class="w-full">
          <div v-if="loading" class="flex items-center justify-center">
            <Spinner class="mr-2" />
          </div>
          <div v-else>
            Créer un compte
          </div>
        </Button>
      </div>
      <div class="mt-4 text-center text-sm">
        Vous avez déjà un compte ?
        <a href="/auth/login" class="underline">
          Se connecter
        </a>
      </div>
    </CardContent>
  </Card>
</template>