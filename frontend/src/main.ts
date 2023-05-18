import { createApp } from "vue";
import App from "./App.vue";

import { createPinia } from "pinia";

import router from "./router";

import "vuetify/styles";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import { VDataTable } from "vuetify/labs/VDataTable";

import { aliases, mdi } from "vuetify/iconsets/mdi";
import "@mdi/font/css/materialdesignicons.css";

import axios from "axios";

let apiUrl = "localhost:8081/api/v1/";
const apiUrlFromEnv = import.meta.env.VITE_API_URL + "/api/v1/";
if (apiUrlFromEnv) {
  apiUrl = apiUrlFromEnv;
}
axios.interceptors.request.use(function (config: any) {
  config.baseURL = apiUrl;
  return config;
});

const vuetify = createVuetify({
  components: { ...components, VDataTable },
  theme: {
    defaultTheme: "light",
  },
  icons: {
    defaultSet: "mdi",
    aliases,
    sets: {
      mdi,
    },
  },
  directives,
});

createApp(App).use(vuetify).use(router).use(createPinia()).mount("#app");
