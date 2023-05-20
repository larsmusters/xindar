<template>
  <v-app-bar flat>
    <v-container class="fill-height d-flex align-center">
      <v-avatar
        class="me-10 ms-4"
        size="32"
        rounded="0"
        image="head-icon.svg"
      />

      <v-btn
        v-for="link in links"
        :key="link.routeName"
        variant="plain"
        :to="{ name: link.routeName }"
      >
        {{ link.label }}
      </v-btn>
    </v-container>
  </v-app-bar>
</template>

<script setup lang="ts">
import { useAppStore } from '@/store/index.ts'

const store = useAppStore()

const links = [{ label: 'Battles', routeName: 'Setup' }, { label: 'Join a battle', routeName: 'Battle' }]
</script>