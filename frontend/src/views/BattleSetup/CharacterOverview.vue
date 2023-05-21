<template>
  <v-list class="ml-4" style="max-width: 40em">
    <v-list-item>
      <v-row>
        <v-col cols="4">
          Name
        </v-col>
        <v-col cols="4" md="3">
          Initiative
        </v-col>
      </v-row>
    </v-list-item>
    <v-list-item v-for="row in sortedCharacters" :key="row.id!">
      <v-row align="center">
        <v-col cols="4">
          <b>
            {{ row.name }}
          </b>
        </v-col>
        <v-col cols="3" sm="2">
          {{ row.initiative }}
        </v-col>
        <v-col cols="1">
          <v-btn
            variant="flat"
            icon="mdi-delete-outline"
            @click="deleteCharacter(row.id!)"
          />
        </v-col>
      </v-row>
      <v-divider />
    </v-list-item>
  </v-list>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useAppStore } from '@/store'

const store = useAppStore()

const sortedCharacters = computed(() => {
  return store.selectedBattle?.characters.sort((a, b) => (b.initiative || 0) - (a.initiative || 0))
})

const deleteCharacter = async (id: number) => {
  await store.characterService?.delete
    .one(id)
    .then(() => {
      store.selectedBattle!.characters = store.selectedBattle!.characters.filter((row) => row.id != id)
    })
    .catch((response) => console.log(response))
}
</script>