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
        <v-list-item>
          <v-row>
            <v-col cols="3">
              <v-text-field v-model="newCharacter.name"> </v-text-field
            ></v-col>
            <v-col cols="1">
              <v-text-field v-model.number="newCharacter.health" />
            </v-col>
            <v-col cols="1">
              <v-text-field v-model.number="newCharacter.size" />
            </v-col>
            <v-col cols="2">
              <v-btn @click="submitNewCharacter"> Submit </v-btn></v-col
            >
          </v-row>
        </v-list-item>
        <v-divider />
        <v-list-item v-for="row in databaseOutput" :key="row.id">
          <v-row>
            <v-col cols="1">{{ row.id }}</v-col>
            <v-col cols="3">
              {{ row.name }}
            </v-col>
            <v-col cols="1">
              {{ row.health }}
            </v-col>
            <v-col cols="1">
              {{ row.size }}
            </v-col>
            <v-col cols="1">
              <v-btn
                variant="flat"
                icon="mdi-delete-outline"
                @click="deleteCharacter(row.id)"
              />
            </v-col>
          </v-row>
        </v-list-item>
      </v-list>
    </v-card>
  </v-container>
</template>

<script setup lang="ts">
import { onMounted, ref, toRaw } from "vue";
import axios from "axios";

interface Character {
  id: number;
  name: string | null;
  health: number | null;
  size: number | null;
}

const newCharacter = ref<Character>({
  id: -1,
  name: null,
  health: null,
  size: null,
});

const databaseOutput = ref<Character[] | null>(null);

const makeCall = async () => {
  const config = { method: "GET", url: "character" };
  await axios(config).then((response) => {
    console.log(response);
    databaseOutput.value = response.data as Character[];
    return response;
  });
};

const submitNewCharacter = async () => {
  const config = {
    method: "POST",
    url: "character",
    headers: { "Content-type": "application/json" },
    data: newCharacter.value,
  };
  await axios(config)
    .then((response) => {
      console.log(response);
      let newRow = toRaw({ ...newCharacter.value, id: response.data });
      databaseOutput.value?.push(structuredClone(newRow));
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
      databaseOutput.value = databaseOutput.value!.filter(
        (row) => row.id != id
      );
    })
    .catch((response) => console.log(response));
};
onMounted(() => {
  makeCall();
});
</script>
