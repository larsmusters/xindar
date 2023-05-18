<template>
  <v-container fluid style="min-height: 100%">
    <v-row>
      <v-col class="px-0 pt-0">
        <v-btn
          block
          rounded="0"
          color="green"
          flat
          @click="goToNextTurn()"
        >
          Volgende huts!
        </v-btn>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12" md="8">
        <v-row>
          <v-col align="center">
            <h1 style="font-size: 5em">
              {{ store.currentCharacter?.name }}
            </h1>
            <h1 style="font-size: 5em">
              {{ store.currentCharacter?.initiative }}
            </h1>
          </v-col>
        </v-row>
        <v-row>
          <v-col align="center">
            <h2>Up next</h2>
            <h1>{{ store.upNextCharacter?.name }}</h1>
          </v-col>
        </v-row>
      </v-col>
      <v-divider vertical />
      <v-col cols="12" md="4">
        <v-card elevation="0">
          <v-list class="ml-4">
            <v-list-item
              v-for="character in store.battleData"
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
</template>

<script setup lang="ts">
import { useAppStore } from '@/store'
const store = useAppStore()
import { onMounted } from 'vue'

onMounted(() => {
  store.battleData = store.sortedCharacters
})

const goToNextTurn = () => {
  if (store.stompClient) {
    store.stompClient.send('/ws/next')
  }
}
</script>
