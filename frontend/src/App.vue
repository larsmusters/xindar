<template>
  <v-app>
    <v-app-bar
      color="white"
      flat
      border
    >
      <v-app-bar-title style="font-size: 2em">
        Xindar
      </v-app-bar-title>
      <v-app-bar-nav-icon :icon="showIcon" @click="startBattle" />
      <v-app-bar-nav-icon
        v-if="store.socketConnected"
        style="pointer-events: none"
        icon="mdi-access-point-check"
        color="green"
      />
      <v-app-bar-nav-icon
        v-else
        style="pointer-events: none"
        icon="mdi-access-point-remove"
        color="red"
      />
    </v-app-bar>
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script setup lang="ts">
import { useRoute, useRouter } from 'vue-router'
import { computed, onMounted } from 'vue'
import axios from 'axios'
import { Character } from '@/types'
import { useAppStore } from '@/store'
import SockJS from 'sockjs-client/dist/sockjs'
import Stomp from 'webstomp-client'

const store = useAppStore()

const router = useRouter()
const route = useRoute()

const startBattle = () => {
  if (route.name == 'Home') {
    router.push({ name: 'Battle' })
  } else {
    router.push({ name: 'Home' })
  }
}

const showIcon = computed(() =>
  route.name == 'Home' ? 'mdi-sword' : 'mdi-home'
)

const getCharacters = async () => {
  const config = { method: 'GET', url: 'character' }
  await axios(config).then((response) => {
    store.data = response.data as Character[]
  })
}

const connectToWebSocket = async () => {
  const url = import.meta.env.VITE_API_URL + '/websocket'
  const socket = new SockJS(url)
  store.stompClient = Stomp.over(socket)
  store.stompClient.connect({}, function (frame) {
    console.log('Connected: ' + frame)
    store.stompClient?.subscribe('/topic/greetings', (greeting) => {
      console.log(JSON.parse(greeting.body).content)
    })
    store.stompClient?.subscribe('/topic/next', () => {
      store.goToNextTurn()
    })
  })
}

onMounted(() => {
  getCharacters()
  connectToWebSocket()
})
</script>
