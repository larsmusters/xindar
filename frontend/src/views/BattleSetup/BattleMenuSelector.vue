<template>
  <v-card>
    <v-card-title> Select a battle</v-card-title>
    <v-divider />
    <v-card-item
      v-for="battle in battles"
      :key="battle.id"
      class="battle-card-item pa-3 px-5"
      @click="$emit('select', battle)"
    >
      {{ battle.name }}
    </v-card-item>
  </v-card>
</template>

<script setup lang="ts">
import { Battle } from '@/battle/battle-types.ts'

defineProps<{
  battles: Battle[];
}>()

defineEmits<{
  (e: 'select', battle: Battle): void;
}>()
</script>

<style scoped lang="scss">
.battle-card-item {
  cursor: pointer;
  &:hover {
    background-color: rgba(128, 128, 128, 0.3);
  }
}
</style>