import { defineStore } from "pinia";
import { Character } from "@/types";

export const useAppStore = defineStore("app", {
  state: () => ({
    data: [] as Character[],
  }),
});
