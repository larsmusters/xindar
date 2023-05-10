<template>
  <v-app>
    <v-app-bar color="white" flat border>
      <v-app-bar-title style="font-size: 2em"> Xindar </v-app-bar-title>
      <v-app-bar-nav-icon :icon="showIcon" @click="startBattle" />
    </v-app-bar>
    <v-main style="max-width: 40em">
      <router-view />
    </v-main>
  </v-app>
</template>

<script setup lang="ts">
import { useRoute, useRouter } from "vue-router";
import { computed } from "vue";

const router = useRouter();
const route = useRoute();

const startBattle = () => {
  if (route.name == "Home") {
    router.push({ name: "Battle" });
  } else {
    router.push({ name: "Home" });
  }
};

const showIcon = computed(() =>
  route.name == "Home" ? "mdi-sword" : "mdi-home"
);
</script>
