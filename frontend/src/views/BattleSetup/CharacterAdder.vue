<template>
  <v-list>
    <v-list-item>
      <v-row class="mt-1 mb-1">
        <v-col cols="8" sm="6">
          <v-text-field
            v-model="newCharacter.name"
            variant="outlined"
            label="Name"
            persistent-placeholder
            hide-details
          />
        </v-col>
        <v-col cols="4">
          <v-text-field
            v-model.number="newCharacter.initiative"
            variant="outlined"
            label="Initiative"
            persistent-placeholder
            hide-details
            :rules="[(v) => !isNaN(v) || '']"
            @keyup.enter="submitNewCharacter"
          />
        </v-col>
        <v-col class="btn-grow">
          <v-btn
            block
            color="blue-darken-2"
            @click="submitNewCharacter"
          >
            <span v-if="!showAdded" class="text-h5"> Add </span>
            <v-icon
              v-else
              icon="mdi-check-bold"
              size="x-large"
            />
          </v-btn>
        </v-col>
      </v-row>
    </v-list-item>
    <v-divider />
  </v-list>
</template>

<script setup lang="ts">
import { ref, toRaw } from 'vue'
import { CreateCharacter } from '@/character/character-types.ts'
import { useAppStore } from '@/store'

const store = useAppStore()

const showAdded = false

const newCharacter = ref<CreateCharacter>({
  name: '',
  initiative: null,
  battleId: null,
})

const submitNewCharacter = async () => {
  if (!newCharacter.value.name) {
    return
  }
  await store.characterService?.create
    .one({ ...newCharacter.value, battleId: store.selectedBattle!.id })
    .then((response) => {
      let newRow = toRaw(response)
      store.selectedBattle!.characters.push(structuredClone(newRow))
    })
    .catch((response) => {
      console.log(response)
    })
}
</script>

<style scoped>
.btn-grow {
  display: flex;
  flex-direction: column;
}
</style>