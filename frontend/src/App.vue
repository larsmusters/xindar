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
import { computed, onMounted } from "vue";
import axios from "axios";
import { Character } from "@/types";
import { useAppStore } from "@/store";

const store = useAppStore();

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

const getCharacters = async () => {
  const config = { method: "GET", url: "character" };
  await axios(config).then((response) => {
    console.log(response);
    store.data = response.data as Character[];
  });
};

onMounted(() => {
  getCharacters();
});
</script>
