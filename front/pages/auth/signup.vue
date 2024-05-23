<script setup lang="ts">
import {cn} from '~/lib/utils';
import { ref } from 'vue'
import { Check, ChevronsUpDown } from 'lucide-vue-next'

const open = ref(false)
const value = ref('')
const label = ref('')
const etu = ref(false)

const data = await $fetch('http://localhost:8080/PasserellePro/Serv?op=lister_etab_domain');
const companies = JSON.parse(data.split(";")[0])
const etablissements = companies.map((company) => ({ value: company.SIREN, label: company.nom }))
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
            <Input id="first-name" placeholder="Max" required />
          </div>
          <div class="grid gap-2">
            <Label for="last-name">Nom</Label>
            <Input id="last-name" placeholder="Robinson" required />
          </div>
        </div>
        <div class="grid gap-2">
          <Label for="email">Email</Label>
          <Input
            id="email"
            type="email"
            placeholder="m@example.com"
            required
          />
        </div>
        <div class="grid gap-2">
          <Label for="password">Mot de passe</Label>
          <Input id="password" type="password" />
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
                {{ value
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
                      v-for="framework in etablissements"
                      :key="framework.value"
                      :value="framework.value"
                      @select="(ev) => {
                        if (typeof ev.detail.value === 'number') {
                          value = ev.detail.value.toString()
                          label = framework.label
                          console.log(label)
                        }
                        open = false
                      }"
                    >
                      {{ framework.label }}
                      <Check
                        :class="cn(
                          'ml-auto h-4 w-4',
                          value === framework.value ? 'opacity-100' : 'opacity-0',
                        )"
                      />
                    </CommandItem>
                  </CommandGroup>
                </CommandList>
              </Command>
            </PopoverContent>
          </Popover>
        </div>

        <div class="gap-2 flex flex-row">
        <Switch v-model:checked="etu" />
        <div class="flex" v-if="etu">Etudiant</div>
        <div class="flex" v-else>Membre d'un établissement</div>
        </div>

        <Button type="submit" class="w-full">
          Créer un compte
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