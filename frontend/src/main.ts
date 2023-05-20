import { createApp } from 'vue'
import App from './App.vue'

import { createPinia } from 'pinia'

import router from './router'

import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import { VDataTable } from 'vuetify/labs/VDataTable'

import { aliases, mdi } from 'vuetify/iconsets/mdi'
import '@mdi/font/css/materialdesignicons.css'


const vuetify = createVuetify({
  components: { ...components, VDataTable },
  theme: {
    defaultTheme: 'light',
  },
  icons: {
    defaultSet: 'mdi',
    aliases,
    sets: {
      mdi,
    },
  },
  directives,
})

createApp(App).use(vuetify).use(router).use(createPinia()).mount('#app')


