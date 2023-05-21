<template>
  <div class="pa-3 px-3 d-flex justify-lg-space-between">
    <div>
      <div
        v-if="editMode == 'off' && battles.length != 0"
        class="d-flex align-center"
      >
        <v-btn
          v-if="battles.length != 0"
          icon
          variant="text"
        >
          <v-icon icon="mdi-dots-horizontal" />
          <v-menu activator="parent">
            <battle-menu-selector
              :battles="battles"
              @select="(e) => $emit('select:battle', e)"
            />
          </v-menu>
        </v-btn>
        <h1 :class="!!title ? '' : 'text-grey'" class="pr-2 ml-3">
          {{ title || "Select a battle" }}
        </h1>
        <v-btn
          v-if="!!title"
          variant="text"
          class="rounded-lg"
          icon="mdi-pencil"
          @click="editBattleName"
        />
      </div>

      <div v-else-if="editMode != 'off'" class="d-flex align-center">
        <v-text-field
          v-model="editModeTitle"
          class="pr-3 ml-3"
          variant="outlined"
          style="min-width: 15em"
          hide-details
          density="comfortable"
          placeholder="Your battle name"
          @keyup.enter="handleSaveButton"
        />
        <v-btn
          variant="text"
          class="rounded-lg"
          @click="handleSaveButton"
        >
          Save
          <v-icon icon="mdi-content-save" class="pl-2" />
        </v-btn>
        <v-btn
          variant="text"
          class="rounded-lg ml-1"
          @click="editMode = 'off'"
        >
          Cancel
          <v-icon icon="mdi-cancel" class="pl-2" />
        </v-btn>
      </div>
    </div>

    <div class="d-flex align-center">
      <v-btn
        v-if="!!title"
        variant="text"
        class="d-flex flex-column"
      >
        remove battle
        <v-dialog
          v-model="dialog"
          activator="parent"
          :fullscreen="xs"
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
              <v-btn @click="[emits('remove:battle'), (dialog = false)];">
                Remove
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-btn>
      <v-divider
        v-if="title"
        vertical
        class="mx-2"
      />
      <v-btn variant="text" @click="initNewBattle">
        new battle
      </v-btn>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useDisplay } from 'vuetify'
import BattleMenuSelector from '@/views/BattleSetup/BattleMenuSelector.vue'
import { Battle } from '@/battle/battle-types.ts'

const { xs } = useDisplay()

const dialog = ref<boolean>()

const props = defineProps<{
  title: string | null;
  battles: Battle[];
}>()

const emits = defineEmits<{
  (e: 'update:title', title: string): void;
  (e: 'remove:battle'): void;
  (e: 'add:battle', battleName: string): void;
  (e: 'init:new-battle'): void;
  (e: 'select:battle', battle: Battle): void;
}>()

const editMode = ref<'off' | 'editing' | 'adding'>('off')
const editModeTitle = ref<string>('')

const editBattleName = () => {
  editMode.value = 'editing'
  editModeTitle.value = props.title || ''
}

const initNewBattle = () => {
  emits('init:new-battle')
  editMode.value = 'adding'
  editModeTitle.value = ''
}

const handleSaveButton = () => {
  if (!editModeTitle.value) {
    return
  }
  if (editMode.value == 'editing') {
    emits('update:title', editModeTitle.value)
  } else if (editMode.value == 'adding') {
    console.log('called once')
    emits('add:battle', editModeTitle.value)
  }
  editMode.value = 'off'
}
</script>