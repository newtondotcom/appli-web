<script setup lang="ts">
import { CircleUser } from "lucide-vue-next";
import { computed } from "vue";

const token = useCookie("token");
const userAuth = computed(() => token.value);
console.log("userAuth", userAuth.value);
const isConnected = computed(() => userAuth.value !== undefined);
console.log("isConnected", isConnected.value);
function deconnexion() {
  token.value = null;
  console.log(token.value);
  navigateTo("/");
}
/* On récupère l'id de l'utilisateur */
const isEntreprise = ref(false);
const lienCompte = ref("");
const lienEvenements = ref("");
if (isConnected.value) {
  const uid = await $fetch(
    `http://localhost:8080/PasserellePro/Serv?op=get_uid_from_token`,
    {
      method: "GET",
      credentials: "include",
    }
  );
  const isEntreprise = await $fetch(
    `http://localhost:8080/PasserellePro/Serv?op=get_bool_type_util_from_uid&uid=${uid}`,
    {
      method: "GET",
      credentials: "include",
    }
  );
  if (isEntreprise == "true") {
    lienCompte.value = "/etablissement/compte";
    lienEvenements.value = "/etablissement/evenements";
  } else {
    lienCompte.value = `/etudiants/${uid}`;
    lienEvenements.value = "/etudiants/mesEvenement";
  }
}
</script>
<template>
  <div>
    <Toaster />
    <header>
      <div class="mx-auto max-w-screen-xl px-4 sm:px-6 lg:px-8">
        <div class="flex h-16 items-center justify-between">
          <div class="md:flex md:items-center md:gap-12">
            <a class="block text-primary" href="/">
              <span class="sr-only">Home</span>
              <div class="underline text-2xl font-bold">PasserellePro</div>
            </a>
          </div>

          <div class="md:block" v-if="isConnected && !isEntreprise">
            <nav aria-label="Global">
              <ul class="flex items-center gap-6 text-sm">
                <li>
                  <a
                    class="text-gray-500 transition hover:text-gray-500/75"
                    href="/etudiants/evenements"
                  >
                    Rechercher un évènement
                  </a>
                </li>
              </ul>
            </nav>
          </div>

          <div class="flex items-center gap-4">
            <div v-if="!userAuth" class="sm:flex sm:gap-4">
              <a href="/auth/login">
                <Button> Se connecter</Button>
              </a>

              <a href="/auth/signup">
                <div class="hidden sm:flex">
                  <Button variant="secondary">Créer un compte </Button>
                </div>
              </a>
            </div>

            <DropdownMenu v-else>
              <DropdownMenuTrigger><CircleUser /></DropdownMenuTrigger>
              <DropdownMenuContent>
                <DropdownMenuLabel>Mon compte</DropdownMenuLabel>
                <DropdownMenuSeparator />
                <a :href="lienCompte">
                  <DropdownMenuItem>Paramètres</DropdownMenuItem>
                </a>
                <a :href="lienEvenements">
                  <DropdownMenuItem>Mes évènements</DropdownMenuItem>
                </a>
                <DropdownMenuItem @click="deconnexion"
                  >Se déconnecter</DropdownMenuItem
                >
              </DropdownMenuContent>
            </DropdownMenu>

            <div class="block md:hidden">
              <Button>
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  class="h-5 w-5"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                  stroke-width="2"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    d="M4 6h16M4 12h16M4 18h16"
                  /></svg
              ></Button>
            </div>
          </div>
        </div>
      </div>
    </header>

    <div class="mx-4 my-6">
      <NuxtPage />
    </div>

    <footer>
      <div class="mx-auto max-w-5xl px-4 py-16 sm:px-6 lg:px-8">
        <div class="flex justify-center text-primary">
          <div class="underline text-2xl font-bold">PasserellePro</div>
        </div>

        <p
          class="mx-auto mt-6 max-w-md text-center leading-relaxed text-gray-500"
        >
          PasserellePro est une plateforme permettant de mettre des relations
          des éléves et des établissements (écoles / entreprises) pour des
          immersions de courte durée.
        </p>

        <ul
          class="mt-12 flex flex-wrap justify-center gap-6 md:gap-8 lg:gap-12"
        >
          <li>
            <a class="text-gray-700 transition hover:text-gray-700/75" href="#">
              Mentions légales
            </a>
          </li>
        </ul>

        <ul class="mt-12 flex justify-center gap-6 md:gap-8">
          <li>
            <a
              href="https://github.com/newtondotcom/passerelle-pro"
              rel="noreferrer"
              target="_blank"
              class="text-gray-700 transition hover:text-gray-700/75"
            >
              <span class="sr-only">GitHub</span>
              <svg
                class="h-6 w-6"
                fill="currentColor"
                viewBox="0 0 24 24"
                aria-hidden="true"
              >
                <path
                  fill-rule="evenodd"
                  d="M12 2C6.477 2 2 6.484 2 12.017c0 4.425 2.865 8.18 6.839 9.504.5.092.682-.217.682-.483 0-.237-.008-.868-.013-1.703-2.782.605-3.369-1.343-3.369-1.343-.454-1.158-1.11-1.466-1.11-1.466-.908-.62.069-.608.069-.608 1.003.07 1.531 1.032 1.531 1.032.892 1.53 2.341 1.088 2.91.832.092-.647.35-1.088.636-1.338-2.22-.253-4.555-1.113-4.555-4.951 0-1.093.39-1.988 1.029-2.688-.103-.253-.446-1.272.098-2.65 0 0 .84-.27 2.75 1.026A9.564 9.564 0 0112 6.844c.85.004 1.705.115 2.504.337 1.909-1.296 2.747-1.027 2.747-1.027.546 1.379.202 2.398.1 2.651.64.7 1.028 1.595 1.028 2.688 0 3.848-2.339 4.695-4.566 4.943.359.309.678.92.678 1.855 0 1.338-.012 2.419-.012 2.747 0 .268.18.58.688.482A10.019 10.019 0 0022 12.017C22 6.484 17.522 2 12 2z"
                  clip-rule="evenodd"
                />
              </svg>
            </a>
          </li>
        </ul>
      </div>
    </footer>
  </div>
</template>
