<template>
  <v-container fluid style="min-height: 100%">
    <v-row v-if="route.query.type == 'host'">
      <v-col class="px-0 pt-0">
        <v-btn
          block
          rounded="0"
          color="green"
          flat
          @click="goToNextTurn()"
        >
          Next
        </v-btn>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" md="8">
        <v-row>
          <v-col align="center">
            <h1 style="font-size: 5em">
              {{ currentCharacter?.name }}
            </h1>
            <h1 style="font-size: 5em">
              {{ currentCharacter?.initiative }}
            </h1>
          </v-col>
        </v-row>
        <v-row>
          <v-col align="center">
            <h2>Up next</h2>
            <h1>{{ upNextCharacter?.name }}</h1>
          </v-col>
        </v-row>
      </v-col>
      <v-divider vertical />
      <v-col cols="12" md="4">
        <v-card elevation="0">
          <v-list class="ml-4">
            <v-list-item
              v-for="character in battleData?.characters"
              :key="character.id!"
            >
              <v-row align="center">
                <v-col cols="9">
                  <b style="font-size: 2em">
                    {{ character.name }}
                  </b>
                </v-col>
                <v-col style="font-size: 2em">
                  {{ character.initiative }}
                </v-col>
              </v-row>
            </v-list-item>
          </v-list>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
  {{ battleData }}
</template>

<script setup lang="ts">
import { useAppStore } from '@/store'
const store = useAppStore()
import { computed, onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import { BattleWithCharacters } from '@/battle/battle-types.ts'
import SockJS from 'sockjs-client/dist/sockjs'
import Stomp from 'webstomp-client'

const stompClient = ref()
const connectToWebSocket = async () => {
  const url = import.meta.env.VITE_API_URL + '/websocket'
  const socket = new SockJS(url)
  stompClient.value = Stomp.over(socket)
  stompClient.value.debug = () => {}
  stompClient.value.connect({}, async function () {
    stompClient?.value.subscribe('/topic/next', () => {
      moveWhoIsUp()
    })
  })
}
connectToWebSocket()


const route = useRoute()

onMounted(() => {
  store.battleService?.get
    .one(route.query.battleId as unknown as number)
    .then((response) => {
      const sortedCharacters = response.characters.sort((a,b) => {
        return  (b.initiative || 0) - (a.initiative || 0)
      }) || []
      const noStarter = !sortedCharacters.find((c) => c.up)
      if (noStarter) {
        sortedCharacters[0].up = true
      }
      battleData.value = { ...response, characters: sortedCharacters }
    })
})

const battleData = ref<BattleWithCharacters>()

const moveWhoIsUp = () => {
  // todo:
  console.log('check')
  const currentIndex = battleData.value?.characters.indexOf(currentCharacter.value!)
  console.log(currentIndex )
  if (currentIndex != null && battleData.value ) {
    battleData.value!.characters[currentIndex].up = false
    if (currentIndex == battleData.value?.characters.length - 1) {
      battleData.value!.characters[0].up = true
    } else {
      battleData.value!.characters[currentIndex+1].up = true
    }
  }
}
const goToNextTurn = () => {
  if (stompClient.value) {
    stompClient.value.send('/ws/next')
  }
}

const upNextCharacter = computed(() => {
  if (!currentCharacter.value) {
    return null
  }
  const currentIndex = battleData.value?.characters.indexOf(currentCharacter.value!)
  if (currentIndex && battleData.value) {
    if (currentIndex == battleData.value?.characters.length - 1) {
      return battleData.value?.characters[0]
    } else {
      return battleData.value!.characters[currentIndex+1]
    }
  }
})

const currentCharacter = computed(() => {
  if (battleData.value) {
    return battleData.value!.characters.find((c) => c.up)
  }
  return null
})
</script>