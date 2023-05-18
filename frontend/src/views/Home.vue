<template>
  <v-container style="max-width: 40em">
    <v-card elevation="1">
      <v-card-title> Fill in your character(s):</v-card-title>
      <v-list style="max-width: 40em">
        <v-list-item>
          <v-row class="mt-1 mb-1">
            <v-col cols="8" sm="6">
              <v-text-field
                variant="outlined"
                label="Name"
                persistent-placeholder
                v-model="newCharacter.name"
                hide-details
              >
              </v-text-field
            ></v-col>
            <v-col cols="4">
              <v-text-field
                variant="outlined"
                label="Initiative"
                persistent-placeholder
                hide-details
                v-model.number="newCharacter.initiative"
                :rules="[(v) => !isNaN(v) || '']"
              />
            </v-col>
            <v-col class="btn-grow">
              <v-btn block color="blue-darken-2" @click="submitNewCharacter">
                <span v-if="!showAdded" class="text-h5"> Add </span>
                <v-icon v-else icon="mdi-check-bold" size="x-large" />
              </v-btn>
            </v-col>
          </v-row>
        </v-list-item>
        <v-divider />
      </v-list>

      <v-list class="ml-4">
        <v-list-item>
          <v-row>
            <v-col cols="4"> Name </v-col>
            <v-col cols="4" md="3"> Initiative</v-col>
          </v-row>
        </v-list-item>
        <v-list-item v-for="row in sortedCharacters" :key="row.id!">
          <v-row align="center">
            <v-col cols="4">
              <b>
                {{ row.name }}
              </b>
            </v-col>
            <v-col cols="3" sm="2">
              {{ row.initiative }}
            </v-col>
            <v-col cols="1">
              <v-btn
                variant="flat"
                icon="mdi-delete-outline"
                @click="deleteCharacter(row.id!)"
              />
            </v-col>
          </v-row>
        </v-list-item>
      </v-list>
    </v-card>
  </v-container>
</template>

<script setup lang="ts">
import { computed, ref, toRaw } from "vue";
import axios from "axios";
import { useAppStore } from "@/store";
import { Character } from "@/types";

const store = useAppStore();

const showAdded = false;

const newCharacter = ref<Character>({
  id: null,
  name: null,
  initiative: null,
  isUp: null,
  battleId: null,
});

const sortedCharacters = computed(() => {
  return store.data?.sort((a, b) => (b.initiative || 0) - (a.initiative || 0));
});

const submitNewCharacter = async () => {
  const config = {
    method: "POST",
    url: "character",
    headers: {
      "Content-type": "application/json",
    },
    data: newCharacter.value,
  };
  await axios(config)
    .then((response) => {
      console.log(response);
      let newRow = toRaw({ ...newCharacter.value, id: response.data });
      store.data?.push(structuredClone(newRow));
    })
    .catch((response) => {
      console.log(response);
    });
};

const deleteCharacter = async (id: number) => {
  const config = {
    method: "DELETE",
    url: "character/" + id,
  };
  await axios(config)
    .then((response) => {
      console.log(response);
      store.data = store.data!.filter((row) => row.id != id);
    })
    .catch((response) => console.log(response));
};
</script>

<style scoped>
.btn-grow {
  display: flex;
  flex-direction: column;
}
</style>
