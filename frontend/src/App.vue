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
import { useRoute, useRouter } from 'vue-router'
import { computed, onMounted, ref } from 'vue'
import axios from 'axios'
import { BattleWithCharacters, Character } from '@/types'
import SockJS from 'sockjs-client/dist/sockjs'
import Stomp from 'webstomp-client'
import AppBar from '@/components/layout/AppBar.vue'

const drawer = ref()

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

const getBattles = async () => {
  const config = { method: 'GET', url: 'battle' }
  await axios(config).then((response) => {
    store.battles = response.data as BattleWithCharacters[]
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
  getBattles()
})
</script>
