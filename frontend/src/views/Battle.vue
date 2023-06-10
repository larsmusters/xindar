<template>
  <div>
    <v-row v-if="route.query.type == 'host'" no-gutters>
      <v-col>
        <v-btn
          block
          rounded="0"
          color="warning"
          flat
          @click="goToPreviousTurn()"
        >
          Previous
        </v-btn>
      </v-col>
      <v-col>
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
    <v-container fluid>
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
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { BattleWithCharacters } from '@/battle/battle-types.ts'
import { useAppStore } from '@/store'
import { useRoute } from 'vue-router'
import SockJS from 'sockjs-client/dist/sockjs'
import Stomp, { Frame } from 'webstomp-client'

const store = useAppStore()
const route = useRoute()

const stompClient = ref()
const connectToBattle = (battleId: number) => {
  const url = import.meta.env.VITE_API_URL + '/websocket'
  const socket = new SockJS(url)
  stompClient.value = Stomp.over(socket)
  stompClient.value.debug = () => {}
  stompClient.value.connect({}, function () {
    stompClient?.value.subscribe(
      '/topic/battle-state/' + battleId,
      (message: Frame) => handleUpdate(message)
    )
  })
}

onMounted(() => {
  initiateBattle()
})

const battleData = ref<BattleWithCharacters>()
const initiateBattle = async () => {
  let battle: BattleWithCharacters
  try {
    battle = await store.battleService.get.one(
      route.query.battleId as unknown as number
    )
  } catch (error) {
    console.log(error)
    return
  }
  connectToBattle(battle.id)

  const sortedCharacters = store.sortCharacters(battle.characters)
  battleData.value = { ...battle, characters: sortedCharacters }

  const noStarter = !sortedCharacters.find((c) => c.up)
  if (noStarter) {
    sortedCharacters[0].up = true
    await store.characterService.update.one(sortedCharacters[0])
  }
}

const handleUpdate = async (message: Frame) => {
  const battle = JSON.parse(message.body)
  const sortedCharacters = store.sortCharacters(battle.characters)
  battleData.value = { ...battle, sortedCharacters }

  const noStarter = !sortedCharacters.find((c) => c.up)
  if (noStarter) {
    sortedCharacters[0].up = true
    await store.characterService.update.one(sortedCharacters[0])
  }
}

const goToNextTurn = (): void => {
  if (!battleData.value) return
  if (!currentCharacter.value) return

  const currentId = battleData.value!.characters.indexOf(
    currentCharacter.value!
  )
  const upNextId = battleData.value!.characters.indexOf(upNextCharacter.value!)
  battleData.value!.characters[currentId].up = false
  battleData.value!.characters[upNextId].up = true

  // Update through websocket
  store.battleService.update.one(battleData.value!)
}

const goToPreviousTurn = (): void => {
  if (!battleData.value) return
  if (!currentCharacter.value) return

  const currentId = battleData.value!.characters.indexOf(
    currentCharacter.value!
  )
  const previousId = battleData.value!.characters.indexOf(previousCharacter.value!)
  battleData.value!.characters[currentId].up = false
  battleData.value!.characters[previousId].up = true

  // Update through websocket
  store.battleService.update.one(battleData.value!)
}


const currentCharacter = computed(() => {
  if (!battleData.value) return null
  return battleData.value!.characters.find((c) => c.up)
})

const upNextCharacter = computed(() => {
  if (!battleData.value) return null
  if (!currentCharacter.value) return null

  const currentId = battleData.value!.characters.indexOf(
    currentCharacter.value!
  )
  if (currentId == null) return null

  const nCharacters = battleData.value!.characters.length
  return battleData.value!.characters.slice(currentId - nCharacters + 1)[0]
})

const previousCharacter = computed(() => {
  if (!battleData.value) return null
  if (!currentCharacter.value) return null

  const currentId = battleData.value!.characters.indexOf(
    currentCharacter.value!
  )
  if (currentId == null) return null

  return battleData.value!.characters.slice(currentId - 1)[0]
})
</script>