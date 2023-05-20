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
import SockJS from 'sockjs-client/dist/sockjs'
import Stomp from 'webstomp-client'
import AppBar from '@/components/layout/AppBar.vue'
import { AxiosApiService } from '@/api/api-service.ts'
import { createCharacterService } from '@/character/create-character-service.ts'
import { createBattleService } from '@/battle/create-battle-service.ts'

const store = useAppStore()

const connectToWebSocket = async () => {
  const url = import.meta.env.VITE_API_URL + '/websocket'
  const socket = new SockJS(url)
  store.stompClient = Stomp.over(socket)
  store.stompClient.debug = () => {}
  store.stompClient.connect({}, async function () {
    store.stompClient?.subscribe('/topic/greetings', (greeting) => {
      console.log(JSON.parse(greeting.body).content)
    })
    store.stompClient?.subscribe('/topic/next', () => {
      store.goToNextTurn()
    })
  })
}
connectToWebSocket()

const apiUrl = import.meta.env.VITE_API_URL + '/api/v1/'

const apiService = new AxiosApiService(apiUrl)
store.characterService = createCharacterService(apiService)
store.battleService = createBattleService(apiService)
</script>
