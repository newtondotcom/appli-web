<script setup lang="ts">
import { ComboboxAnchor, ComboboxInput, ComboboxPortal, ComboboxRoot } from 'radix-vue'
import {
    DateFormatter,
    type DateValue,
    getLocalTimeZone,
} from '@internationalized/date';
import { Calendar as CalendarIcon } from 'lucide-vue-next';
import { cn } from '@/lib/utils'
const description = "";
const df = new DateFormatter('fr-FR', {
    dateStyle: 'long',
})
const domaines = [
    { value: 'next.js', label: 'Next.js' },
    { value: 'sveltekit', label: 'SvelteKit' },
    { value: 'nuxt.js', label: 'Nuxt.js' },
    { value: 'remix', label: 'Remix' },
    { value: 'astro', label: 'Astro' },
]
const value = ref<DateValue>()
const modelValue = ref<string[]>([])
const open = ref(false)
const searchTerm = ref('')

const filtereddomaines = computed(() => domaines.filter(i => !modelValue.value.includes(i.label)))
</script>

<template>
    <Titre title="Modification d'un évènement" subtitle="Création ou modification" />
    <div class="flex space-y-4 justify-center align-middle content-center mx-auto">
        <div class="gap-4 overflow-auto p-4 md:grid-cols-2 lg:grid-cols-3">
            <div class="relative hidden flex-col items-start gap-8 md:flex">
                <div class="grid w-full items-start gap-6">
                    <fieldset class="grid gap-6 rounded-lg border p-4">
                        <div class="grid gap-3">
                            <Label for="temperature">Nom de l'évenement</Label>
                            <Input id="temperature" type="text" placeholder="Découverte IA" />
                        </div>
                        <div class="grid gap-3">
                            <Label for="temperature">Description de l'évenement</Label>
                            <Textarea id="description" :placeholder="description" />
                        </div>
                        <div class="grid gap-3">
                            <Label for="temperature">Durée en minutes</Label>
                            <Input id="temperature" type="number" placeholder="60" />
                        </div>

                        <div class="grid gap-3">
                            <Label for="temperature">Date de l'évenement</Label>
                            <Popover>
                                <PopoverTrigger as-child>
                                    <div class="flex space-y-4 justify-center align-middle content-center mx-auto">
                                        <Button variant="outline" :class="cn(
                                'flex w-[280px] justify-start text-left font-normal',
                                !value && 'text-muted-foreground',)">
                                            <CalendarIcon class="mr-2 h-4 w-4" />
                                            {{ value ? df.format(value.toDate(getLocalTimeZone())) : "Choisir une date"
                                            }}
                                        </Button>
                                    </div>
                                </PopoverTrigger>
                                <PopoverContent class="w-auto p-0">
                                    <Calendar v-model="value" initial-focus />
                                </PopoverContent>
                            </Popover>
                        </div>

                        <div class="grid gap-3">
                            <Label for="temperature">Heure de l'évenement</Label>
                            <div class="flex space-x-4">
                                <Input id="temperature" type="number" placeholder="14" />
                                <Input id="temperature" type="number" placeholder="30" />
                            </div>
                        </div>
                        <div class="grid gap-3">
                            <Label for="temperature">Domaines concernés par l'évenement</Label>
                            <TagsInput class="w-[70vw]" :model-value="modelValue">
                                <div class="flex gap-2 flex-wrap items-center px-3">
                                    <TagsInputItem v-for="item in modelValue" :key="item" :value="item">
                                        <TagsInputItemText />
                                        <TagsInputItemDelete />
                                    </TagsInputItem>
                                </div>

                                <ComboboxRoot v-model="modelValue" v-model:open="open" v-model:searchTerm="searchTerm"
                                    class="w-full">
                                    <ComboboxAnchor as-child>
                                        <ComboboxInput placeholder="Domaine..." as-child>
                                            <TagsInputInput class="w-full px-3"
                                                :class="modelValue.length > 0 ? 'mt-2' : ''" @keydown.enter.prevent />
                                        </ComboboxInput>
                                    </ComboboxAnchor>

                                    <ComboboxPortal>
                                        <CommandList position="popper"
                                            class="w-[--radix-popper-anchor-width] rounded-md mt-2 border bg-popover text-popover-foreground shadow-md outline-none data-[state=open]:animate-in data-[state=closed]:animate-out data-[state=closed]:fade-out-0 data-[state=open]:fade-in-0 data-[state=closed]:zoom-out-95 data-[state=open]:zoom-in-95 data-[side=bottom]:slide-in-from-top-2 data-[side=left]:slide-in-from-right-2 data-[side=right]:slide-in-from-left-2 data-[side=top]:slide-in-from-bottom-2">
                                            <CommandEmpty />
                                            <CommandGroup>
                                                <CommandItem v-for="domaine in filtereddomaines" :key="domaine.value"
                                                    :value="domaine.label" @select.prevent="(ev) => {
                                if (typeof ev.detail.value === 'string') {
                                    searchTerm = ''
                                    modelValue.push(ev.detail.value)
                                }

                                if (filtereddomaines.length === 0) {
                                    open = false
                                }
                            }">
                                                    {{ domaine.label }}
                                                </CommandItem>
                                            </CommandGroup>
                                        </CommandList>
                                    </ComboboxPortal>
                                </ComboboxRoot>
                            </TagsInput>
                        </div>
                    </fieldset>
                    <div class="grid gap-3 mx-auto w-40">
                        <Button>Enregistrer</Button>
                    </div>
                </div>
            </div>
        </div>
    </div>

</template>