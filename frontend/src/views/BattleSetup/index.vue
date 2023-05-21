<template>
  <v-container style="max-width: 1200px" class="pt-0 pb-0">
    <PageTitle title="Battle builder" />
    <v-row>
      <v-col>
        <v-sheet min-height="70vh" rounded="lg">
          <BattleSelect
            v-model:title="battleName"
            @remove-battle="removeBattle"
            @add-battle="addBattle"
            @init:new-battle="handleNewBattle"
          />
          <v-list style="max-width: 40em">
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
        </v-sheet>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup lang="ts">
import { computed, ref, toRaw } from 'vue'
import { useAppStore } from '@/store'
import { CreateCharacter } from '@/character/character-types.ts'
import PageTitle from '@/components/layout/PageTitle.vue'
import BattleSelect from '@/views/BattleSetup/BattleSelect.vue'

const store = useAppStore()

const getBattles = () => {
  store.battleService?.get.all().then((response) => {
    store.battles = response
    battleName.value = response[0].name
  })
}
getBattles()

const addBattle = (event: any) => {
  console.log('adding battle ' + event + '...')
}

const removeBattle = () => {
  console.log('removing current battle...')
}
const handleNewBattle = () => {
  console.log('new battle is being made, remove current data')
}
const showAdded = false

const battleName = ref<string>('')

const newCharacter = ref<CreateCharacter>({
  name: '',
  initiative: null,
  battleId: null,
})

const sortedCharacters = computed(() => {
  return store.data?.sort((a, b) => (b.initiative || 0) - (a.initiative || 0))
})

const submitNewCharacter = async () => {
  await store.characterService?.create
    .one(newCharacter.value)
    .then((response) => {
      let newRow = toRaw(response)
      store.data?.push(structuredClone(newRow))
    })
    .catch((response) => {
      console.log(response)
    })
}

const deleteCharacter = async (id: number) => {
  await store.characterService?.delete
    .one(id)
    .then((response) => {
      console.log(response)
      store.data = store.data!.filter((row) => row.id != id)
    })
    .catch((response) => console.log(response))
}
</script>

<style scoped>
.btn-grow {
  display: flex;
  flex-direction: column;
}
</style>