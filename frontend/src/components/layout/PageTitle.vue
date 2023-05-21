<template>
  <div class="pa-2 pl-6 bg-grey-lighten-3">
    <h1>
      {{ title }}
    </h1>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  title: string
}>()
</script>