<script setup lang="ts">
const email = ref('f@test.com')
const password = ref('1234')
const loading = ref(false)

async function seConnecter(){
  loading.value = true
  try {
  const data = await $fetch(`http://localhost:8080/PasserellePro/Serv?op=seconnecter`,
  {
    method: 'POST',
    body: JSON.stringify({
      email: email.value,
      password: password.value
    }),
    server : false
  }
  );
  console.log(data)
  } catch (error) {
    console.error(error)
  }
  loading.value = false
}
</script>

<template>
  <Card class="mx-auto max-w-sm mt-[100px]">
    <CardHeader>
      <CardTitle class="text-2xl">
        Se connecter
      </CardTitle>
      <CardDescription>
        Entrez vos informations pour vous connecter
      </CardDescription>
    </CardHeader>
    <CardContent>
      <div class="grid gap-4">
        <div class="grid gap-2">
          <Label for="email">Email</Label>
          <Input
            id="email"
            type="email"
            placeholder="m@example.com"
            required
            v-model="email"
          />
        </div>
        <div class="grid gap-2">
          <div class="flex items-center">
            <Label for="password">Mot de passe</Label>
            <a href="#" class="ml-auto inline-block text-sm underline">
              Oubli de votre mot de passe ?
            </a>
          </div>
          <Input v-model="password" id="password" type="password" required />
        </div>
        <Button @click="seConnecter" type="submit" class="w-full">
          <div v-if="loading" ><Loading/></div>
          <div v-else>Se connecter</div>
        </Button>
      </div>
      <div class="mt-4 text-center text-sm">
        Vous n'avez pas de compte ?&nbsp;
        <a href="#" class="underline">
          Créer un compte
        </a>
      </div>
    </CardContent>
  </Card>
</template>