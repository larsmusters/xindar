<template>
  <v-container style="max-width: 1200px" class="pt-0 pb-0">
    {{ store.selectedBattle }}
    <PageTitle title="Battle builder" />
    <v-sheet min-height="70vh" rounded="lg">
      <BattleSelect
        :title="store.selectedBattle?.name || null"
        :battles="store.battles"
        @update:title="changeBattleName"
        @select:battle="changeBattle"
        @remove:battle="removeBattle"
        @add:battle="addBattle"
        @init:new-battle="handleNewBattle"
      />
      <v-divider />
      <character-adder />

      <character-overview />
    </v-sheet>
  </v-container>
</template>

<script setup lang="ts">
import PageTitle from '@/components/layout/PageTitle.vue'
import BattleSelect from '@/views/BattleSetup/BattleSelect.vue'
import CharacterAdder from '@/views/BattleSetup/CharacterAdder.vue'
import CharacterOverview from '@/views/BattleSetup/CharacterOverview.vue'
import { ref } from 'vue'
import { useAppStore } from '@/store'

const store = useAppStore()

const getBattles = () => {
  store.battleService?.get.all().then((response) => {
    store.battles = response
  })
}
getBattles()

const battleName = ref<string>('')
const changeBattle = (battle) => {
  store.selectedBattle = battle
  battleName.value = battle.name
}

const changeBattleName = (name: string) => {
  if (store.selectedBattle) {
  store.selectedBattle.name = name
  store.battleService?.update.one({ ...store.selectedBattle, 'name': name }).then((response) => {
    console.log(response)
  })
  }
}

const addBattle = (event: any) => {
  console.log('adding battle ' + event + '...')
}

const removeBattle = () => {
  console.log('removing current battle...')
}
const handleNewBattle = () => {
  console.log('new battle is being made, remove current data')
}
</script>
