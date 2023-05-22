<template>
  <div class="pa-3 px-3 d-flex justify-space-between">
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
          class="pr-2 ml-3"
          variant="outlined"
          style="min-width: 15em"
          hide-details
          density="comfortable"
          placeholder="Your battle name"
          @keyup.enter="handleSaveButton"
        />
        <v-btn
          variant="text"
          :class="{'rounded-lg': true, 'pl-0': xs}"
          @click="handleSaveButton"
        >
          {{ !xs ? "Save" : null }}
          <v-icon icon="mdi-content-save" class="pl-2" />
        </v-btn>
        <v-btn
          v-if="!xs"
          variant="text"
          class="rounded-lg ml-1"
          @click="handleCancelButton"
        >
          Cancel
          <v-icon icon="mdi-cancel" class="pl-2" />
        </v-btn>
      </div>
    </div>

    <div v-if="editMode == 'off'">
      <v-btn
        v-if="battles.length != 0"
        icon
        variant="text"
      >
        <v-icon :icon="actionMenu ? 'mdi-chevron-up' : 'mdi-chevron-down'" />
        <v-menu v-model="actionMenu" activator="parent">
          <v-card>
            <v-card-title> Actions </v-card-title>
            <v-divider />
            <v-card-item v-if="!!title" class="battle-card-item pa-3 px-5">
              Remove this battle
              <battle-select-remove-dialog
                :title="title"
                @confirm="emits('remove:battle')"
              />
            </v-card-item>
            <v-card-item
              class="battle-card-item pa-3 px-5"
              @click="initNewBattle"
            >
              Create new battle
            </v-card-item>
          </v-card>
        </v-menu>
      </v-btn>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useDisplay } from 'vuetify'
import BattleMenuSelector from '@/views/BattleSetup/BattleMenuSelector.vue'
import { Battle } from '@/battle/battle-types.ts'
import BattleSelectRemoveDialog from '@/views/BattleSetup/BattleSelectRemoveDialog.vue'

const { xs } = useDisplay()

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
    emits('add:battle', editModeTitle.value)
  }
  editMode.value = 'off'
}

const handleCancelButton = () => {
  editMode.value = 'off'
  editModeTitle.value = props.title || ''
}

const actionMenu = ref<boolean>(false)
</script>

<style scoped lang="scss">
.battle-card-item {
  cursor: pointer;
  &:hover {
    background-color: rgba(128, 128, 128, 0.3);
  }
}
</style>