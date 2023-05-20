<template>
  <v-app-bar flat>
    <v-container class="fill-height d-flex align-center">
      <v-avatar
        class="me-10 ms-4"
        size="32"
        rounded="0"
        image="head-icon.svg"
      />

      <div v-if="!xs">
        <v-btn
          v-for="link in links"
          :key="link.routeName"
          variant="plain"
          :to="{ name: link.routeName }"
        >
          {{ link.label }}
        </v-btn>
      </div>
      <v-spacer />
      <v-btn
        v-if="xs"
        icon
        size="small"
        variant="text"
      >
        <v-icon icon="mdi-menu" />
        <v-menu location="bottom" activator="parent">
          <v-list>
            <v-list-item>
              <v-btn
                v-for="link in links"
                :key="link.routeName"
                :to="{name: link.routeName}"
                block
                variant="text"
              >
                {{ link.label }}
              </v-btn>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-btn>
    </v-container>
  </v-app-bar>
</template>

<script setup lang="ts">
import { useAppStore } from '@/store/index.ts'
import { useDisplay } from 'vuetify'
import { ref } from 'vue'

const store = useAppStore()

const dialog = ref<boolean>()

const { xs } = useDisplay()

const links = [{ label: 'Battles', routeName: 'Setup' }, { label: 'Join a battle', routeName: 'Battle' }]
</script>