<script setup lang="ts">
definePageMeta({
  layout: false,
})
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
          />
        </div>
        <div class="grid gap-2">
          <div class="flex items-center">
            <Label for="password">Mot de passe</Label>
            <a href="#" class="ml-auto inline-block text-sm underline">
              Oubli de votre mot de passe ?
            </a>
          </div>
          <Input id="password" type="password" required />
        </div>
        <Button type="submit" class="w-full">
          Se connecter
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