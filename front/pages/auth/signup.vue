<script setup lang="ts">
import {cn} from '~/lib/utils'
import { ref } from 'vue'
import { Check, ChevronsUpDown } from 'lucide-vue-next'

const open = ref(false)
const value = ref('')

const etablissements = [
  { value: 'next.js', label: 'Next.js' },
  { value: 'sveltekit', label: 'SvelteKit' },
  { value: 'nuxt.js', label: 'Nuxt.js' },
  { value: 'remix', label: 'Remix' },
  { value: 'astro', label: 'Astro' },
]
</script>

<template>
  <Card class="mx-auto max-w-sm mt-[100px]">
    <CardHeader>
      <CardTitle class="text-xl">
        Sign Up
      </CardTitle>
      <CardDescription>
        Enter your information to create an account
      </CardDescription>
    </CardHeader>
    <CardContent>
      <div class="grid gap-4">
        <div class="grid grid-cols-2 gap-4">
          <div class="grid gap-2">
            <Label for="first-name">First name</Label>
            <Input id="first-name" placeholder="Max" required />
          </div>
          <div class="grid gap-2">
            <Label for="last-name">Last name</Label>
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
          <Label for="password">Password</Label>
          <Input id="password" type="password" />
        </div>


        <div class="grid gap-2">
          <Label for="password">Etablissement</Label>
          <Popover v-model:open="open">
            <PopoverTrigger as-child>
              <Button
                variant="outline"
                role="combobox"
                :aria-expanded="open"
                class="justify-between"
              >
                {{ value
                  ? etablissements.find((framework) => framework.value === value)?.label
                  : "Sélectionner votre établissement..." }}
                <ChevronsUpDown class="ml-2 h-4 w-4 shrink-0 opacity-50" />
              </Button>
            </PopoverTrigger>
            <PopoverContent class="p-0">
              <Command>
                <CommandInput class="h-9" placeholder="Chercher un établissement..." />
                <CommandEmpty>Pas d'établissement trouvé.</CommandEmpty>
                <CommandList>
                  <CommandGroup>
                    <CommandItem
                      v-for="framework in etablissements"
                      :key="framework.value"
                      :value="framework.value"
                      @select="(ev) => {
                        if (typeof ev.detail.value === 'string') {
                          value = ev.detail.value
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

        <Button type="submit" class="w-full">
          Create an account
        </Button>
      </div>
      <div class="mt-4 text-center text-sm">
        Already have an account?
        <a href="#" class="underline">
          Sign in
        </a>
      </div>
    </CardContent>
  </Card>
</template>