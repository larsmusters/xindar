<template>
  <v-container style="max-width: 1200px" class="pt-0 pb-0">
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
    store.battles = response.sort(
      (b1, b2) => new Date(b2.updateDt).valueOf() - new Date(b1.updateDt).valueOf()
    )
    store.selectedBattle = store.battles[0]
  })
}
getBattles()

const battleName = ref<string>('')
const changeBattle = (battle: any) => {
  store.selectedBattle = battle
  battleName.value = battle.name
}

const changeBattleName = (name: string) => {
  if (store.selectedBattle) {
    store.selectedBattle.name = name
    store.battleService?.update
      .one({ ...store.selectedBattle, name: name })
      .then((response) => {
        console.log(response)
      })
  }
}

const addBattle = (name: any) => {
  store.battleService?.create.one({ name: name }).then((response) => {
    store.selectedBattle = { ...response, characters: [] }
    store.battles.push(store.selectedBattle)
  })
}

const removeBattle = () => {
  if (store.selectedBattle!.id){
    store.battleService?.delete.one(store.selectedBattle!.id)
  }
  store.battles = store.battles.filter((battle) => battle.id != store.selectedBattle!.id)
  store.selectedBattle = store.battles[0]
}

const handleNewBattle = () => {
  store.selectedBattle = null
}
</script>