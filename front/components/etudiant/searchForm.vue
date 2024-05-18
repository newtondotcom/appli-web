<template>
  <div class="relative w-72 max-w-sm items-center">
    <Input
      @input="search"
      type="text"
      placeholder="Search..."
      class="pl-10 h-full w-full"
    />
    <span class="absolute start-0 inset-y-0 px-2 flex items-center">
      <Search class="size-6 text-muted-foreground" />
    </span>
  </div>
</template>

<script lang="ts" setup>
const emit = defineEmits(["search"]);
const search = (e: Event) => {
  if (!(e.target instanceof HTMLInputElement)) return;
  emit("search", e.target.value);
};
</script>

<style></style>
