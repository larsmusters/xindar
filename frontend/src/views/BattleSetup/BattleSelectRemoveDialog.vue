<template>
  <v-dialog
    v-model="dialog"
    activator="parent"
    width="auto"
  >
    <v-card>
      <v-card-text class="d-flex flex-column align-center">
        <p>You are about to remove "{{ title }}".</p>
        <p>It cannot be restored.</p>
        <p>
          <u> Are you sure? </u>
        </p>
      </v-card-text>
      <v-card-actions align-end>
        <v-spacer />
        <v-btn @click="dialog = false">
          Cancel
        </v-btn>
        <v-btn @click="[emits('confirm'), (dialog = false)];">
          Remove
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import { ref } from 'vue'

defineProps<{
  title: string
}>()

const emits = defineEmits<{
  (e: 'confirm'): void
}>()

const dialog = ref<boolean>(false)
</script>