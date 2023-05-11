<template>
  <div>{{ store.data }}</div>
</template>

<script setup lang="ts">
import { useAppStore } from "@/store";

const store = useAppStore();
</script>
