<template>
  <v-app>
    <AppBar />
    <v-main class="bg-grey-lighten-3">
      <router-view />
    </v-main>
  </v-app>
</template>

<script setup lang="ts">
import { useAppStore } from '@/store'
import AppBar from '@/components/layout/AppBar.vue'
import { AxiosApiService } from '@/api/api-service.ts'
import { createCharacterService } from '@/character/create-character-service.ts'
import { createBattleService } from '@/battle/create-battle-service.ts'

const store = useAppStore()

const apiUrl = import.meta.env.VITE_API_URL + '/api/v1/'

const apiService = new AxiosApiService(apiUrl)
store.characterService = createCharacterService(apiService)
store.battleService = createBattleService(apiService)
</script>
