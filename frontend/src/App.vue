<template>
  <v-container>
    <h1>
      Welkom op de website! Hier onder een demo van data trekken uit de database
    </h1>
    <v-card>
      <v-list>
        <v-list-item>
          <v-row>
            <v-col cols="3"> Name </v-col>
            <v-col cols="1"> Health</v-col>
            <v-col cols="1"> Size</v-col>
          </v-row>
        </v-list-item>
        <v-divider />
        <v-list-item v-for="row in databaseOutput" :key="row.id">
          <v-row>
            <v-col cols="3">
              {{ row.name }}
            </v-col>
            <v-col cols="1">
              {{ row.health }}
            </v-col>
            <v-col cols="1">
              {{ row.size }}
            </v-col>
          </v-row>
        </v-list-item>
      </v-list>
    </v-card>
  </v-container>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";
import axios from "axios";

interface Character {
  id: number;
  name: string;
  health: number;
  size: number;
}

const databaseOutput = ref<Character[] | null>(null);

const makeCall = async () => {
  const config = { method: "GET", url: "character" };
  await axios(config).then((response) => {
    databaseOutput.value = response.data as Character[];
    return response;
  });
};

onMounted(() => {
  makeCall();
});
</script>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
