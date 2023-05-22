<template>
  <v-container style="max-width:1200px" class="pt-0 pb-0">
    <page-title :title="title" />
    <v-sheet min-height="70vh" rounded="lg">
      <v-list class="pa-4" style="max-width: 40em">
        <v-list-item
          v-for="row in store.battles"
          :key="row.id!"
          class="rounded-lg"
          @click="joinBattle(row.id)"
        >
          <div class="d-flex">
            <b class="pr-5">
              {{ row.name }}
            </b>
            <v-icon icon="mdi-chevron-right" />
          </div>
        </v-list-item>
      </v-list>
    </v-sheet>
  </v-container>
</template>

<script setup lang="ts">
import PageTitle from '@/components/layout/PageTitle.vue'
import { useRoute, useRouter } from 'vue-router'
import { useAppStore } from '@/store'
import { computed } from 'vue'

const store = useAppStore()

const title = computed(() => {
  if (route.query.type == 'host'){
    return 'Host a battle'
  } else if (route.query.type == 'join') {
    return 'Join a battle'
  }
  return ''
})

const getBattles = () => {
  store.battleService?.get.all().then((response) => {
    store.battles = response.sort(
      (b1, b2) => new Date(b2.updateDt).valueOf() - new Date(b1.updateDt).valueOf()
    )
    store.selectedBattle = store.battles[0]
  })
}

if (store.battles.length == 0) {
  getBattles()
}

const route = useRoute()
const router = useRouter()

const joinBattle = (battleId: number) => {
  store.selectedBattle = store.battles.find((battle) => battle.id == battleId) || null
  router.push({ name: 'Battle', query: {
    battleId: battleId, type: route.query.type } })
}
</script>