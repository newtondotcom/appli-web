<script setup lang="ts">
import { ComboboxAnchor, ComboboxInput, ComboboxPortal, ComboboxRoot } from 'radix-vue';
import { DateFormatter, type DateValue, getLocalTimeZone } from '@internationalized/date';
import { Calendar as CalendarIcon } from 'lucide-vue-next';
const df = new DateFormatter('fr-FR', {
    dateStyle: 'long',
});
const { toast } = useToast()
const domaines = [
    { value: 'next.js', label: 'Next.js' },
    { value: 'sveltekit', label: 'SvelteKit' },
    { value: 'nuxt.js', label: 'Nuxt.js' },
    { value: 'remix', label: 'Remix' },
    { value: 'astro', label: 'Astro' },
];
const eventDate = ref<DateValue>();
const eventName = ref('');
const eventDescription = ref('');
const eventDuration = ref(0);
const eventHour = ref(0);
const eventMinute = ref(0);
const modelValue = ref<string[]>([]);
const open = ref(false);
const loading = ref(false);
const description = ref('Description de l\'événement');
const searchTerm = ref('');
const route = useRoute();
let id = -1;
if (route.query.id) {
    id = route.query.id as number;
}
const filtereddomaines = computed(() => domaines.filter(i => !modelValue.value.includes(i.label)));

async function saveEvent() {
    loading.value = true;
    if (
        eventName.value === '' ||
        eventDescription.value === '' ||
        eventDuration.value === 0 ||
        eventDate === undefined ||
        eventHour.value === 0 ||
        eventMinute.value === 0 ||
        modelValue.value.length === 0
    ) {
        toast({
            title: 'Erreur',
            description: 'Veuillez remplir tous les champs',
        });
        loading.value = false;
        return;
    }
    if (id === -1) {
        await new Promise(resolve => setTimeout(resolve, 2000));
    } else {
        await new Promise(resolve => setTimeout(resolve, 2000));
    }
    loading.value = false;
}

</script>

<template>
    <div v-if="id === -1">
        <Titre title="Création d'un évènement"
            subtitle="Ici, vous pouvez créer un évènement pour votre établissement" />
    </div>
    <div v-else>
        <Titre title="Modification d'un évènement"
            subtitle="Ici, vous pouvez modifier un évènement de votre établissement" />
    </div>
    <div class="flex space-y-4 justify-center align-middle content-center mx-auto">
        <div class="gap-4 overflow-auto p-4 md:grid-cols-2 lg:grid-cols-3">
            <div class="relative hidden flex-col items-start gap-8 md:flex">
                <div class="grid w-full items-start gap-6">
                    <fieldset class="grid gap-6 rounded-lg border p-4">
                        <div class="grid gap-3">
                            <Label for="eventName">Nom de l'événement</Label>
                            <Input id="eventName" type="text" placeholder="Découverte IA" v-model="eventName" />
                        </div>
                        <div class="grid gap-3">
                            <Label for="eventDescription">Description de l'événement</Label>
                            <Textarea id="eventDescription" :placeholder="description" v-model="eventDescription" />
                        </div>
                        <div class="grid gap-3">
                            <Label for="eventDuration">Durée en minutes</Label>
                            <Input id="eventDuration" type="number" placeholder="60" v-model="eventDuration" />
                        </div>

                        <div class="grid gap-3">
                            <Label for="eventDate">Date de l'événement</Label>
                            <Popover>
                                <PopoverTrigger as-child>
                                    <div class="flex space-y-4 justify-center align-middle content-center mx-auto">
                                        <Button variant="outline" :class="cn(
        'flex w-[280px] justify-start text-left font-normal',
        !eventDate && 'text-muted-foreground',)">
                                            <CalendarIcon class="mr-2 h-4 w-4" />
                                            {{ eventDate ? df.format(eventDate.toDate(getLocalTimeZone())) : "Choisir une date"
                                            }}
                                        </Button>
                                    </div>
                                </PopoverTrigger>
                                <PopoverContent class="w-auto p-0">
                                    <Calendar v-model="eventDate" initial-focus />
                                </PopoverContent>
                            </Popover>
                        </div>

                        <div class="grid gap-3">
                            <Label for="eventHour">Heure de l'événement</Label>
                            <div class="flex space-x-4">
                                <Input id="eventHour" type="number" placeholder="14" v-model="eventHour" />
                                <Input id="eventMinute" type="number" placeholder="30" v-model="eventMinute" />
                            </div>
                        </div>
                        <div class="grid gap-3">
                            <Label for="eventDomain">Domaines concernés par l'événement</Label>
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
                                                :class="modelValue.length > 0 ? 'mt-1' : ''" @keydown.enter.prevent />
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
                                                <div @click="open = false; searchTerm = '';"
                                                    class="ml-2 text-sm text-semibold">
                                                    Fermer
                                                </div>
                                            </CommandGroup>
                                        </CommandList>
                                    </ComboboxPortal>
                                </ComboboxRoot>
                            </TagsInput>
                        </div>
                    </fieldset>
                    <div class="grid gap-3 mx-auto w-40 my-50">
                        <Button @click="saveEvent">
                            <div v-if="!loading">
                                Enregistrer
                            </div>
                            <div v-else class="py-2" role="status">
                                <svg aria-hidden="true"
                                    class="w-6 h-6 text-gray-200 animate-spin dark:text-gray-600 fill-primary/95"
                                    viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                                    <path
                                        d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z"
                                        fill="currentColor" />
                                    <path
                                        d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z"
                                        fill="currentFill" />
                                </svg>
                                <span class="sr-only">Loading...</span>
                            </div>
                        </Button>
                    </div>
                </div>
            </div>
        </div>
    </div>

</template>
