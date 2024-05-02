<template>
    <div class="mt-4 ml-6 mb-6">
      <h1 class="text-2xl font-bold text-primary sm:text-3xl">
        {{ title }}
      </h1>
  
      <p class="mt-1.5 text-sm text-gray-500">
        {{ subtitle }}
      </p>
    </div>
  </template>
  
  <script lang="ts">
  export default {
    props: {
      title: {
        type: String,
        required: true,
      },
      subtitle: {
        type: String,
        required: true,
      },
    },
  };
  </script>