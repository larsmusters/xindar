<template>
  <v-app-bar flat>
    <v-container
      class="fill-height d-flex align-center"
      style="max-width: 1200px"
    >
      <v-avatar
        class="me-8 ms-6 v-card--hover"
        size="32"
        rounded="0"
        image="head-icon.svg"
        @click="toHome"
      />
      <div v-if="!xs">
        <v-btn
          v-for="link in links"
          :key="link.routeName"
          variant="plain"
          :to="{ name: link.routeName, query: link.query }"
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
                :to="{ name: link.routeName, query: link.query }"
                block
                variant="text"
              >
                {{ link.label }}
                {{ link.query }}
              </v-btn>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-btn>
    </v-container>
  </v-app-bar>
</template>

<script setup lang="ts">
import { useDisplay } from 'vuetify'
import { useRouter } from 'vue-router'
const { xs } = useDisplay()

interface Links {
  label: string;
  routeName: string;
  query?: { [key: string]: string };
}

const links: Links[] = [
  { label: 'Battles', routeName: 'Setup' },
  { label: 'Host a battle', routeName: 'Joiner', query: { type: 'host' } },
  { label: 'Join a battle', routeName: 'Joiner', query: { type: 'join' } },
  { label: 'Volaren map', routeName: 'Maps' },
]

const router = useRouter()

const toHome = () => {
  router.push({ name: 'Home' })
}
</script>