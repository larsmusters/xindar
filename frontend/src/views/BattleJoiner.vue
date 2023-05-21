<template>
  <v-container style="max-width:1200px" class="pt-0 pb-0">
    <page-title :title="title" />
    <v-sheet min-height="70vh" rounded="lg">
      <v-list class="ml-4" style="max-width: 40em">
        <v-list-item>
          <v-row>
            <v-col cols="2">
              Name
            </v-col>
          </v-row>
        </v-list-item>
        <v-list-item v-for="row in store.battles" :key="row.id!">
          <v-row align="center">
            <v-col cols="2">
              <b>
                {{ row.name }}
              </b>
            </v-col>
            <v-col cols="1">
              <v-btn
                variant="flat"
                icon="mdi-chevron-right"
                @click="joinBattle(row.id!)"
              />
            </v-col>
          </v-row>
          <v-divider />
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