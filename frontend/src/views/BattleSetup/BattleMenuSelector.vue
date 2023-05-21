<template>
  <v-card>
    <v-card-title> Select a battle</v-card-title>
    <v-divider />
    <v-card-item
      v-for="battle in battles"
      :key="battle.id"
      class="v-card--hover pa-3 px-5"
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