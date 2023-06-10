<template>
  <v-app>
    <AppBar />
    <v-main class="bg-grey-lighten-3">
      <router-view />
    </v-main>
  </v-app>
</template>

<script setup lang="ts">
import AppBar from '@/components/layout/AppBar.vue'
</script>
