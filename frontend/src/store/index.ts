import { defineStore } from 'pinia'
import { Character } from '@/character/character-types.ts'
import { Client } from 'webstomp-client'
import { CharacterService } from '@/character/character-service.ts'
import { BattleService } from '@/battle/battle-service.ts'
import { BattleWithCharacters } from '@/battle/battle-types.ts'

export const useAppStore = defineStore('app', {
  state: () => ({
    selectedBattle: null as BattleWithCharacters | null,
    characterService: null as CharacterService | null,
    battleService: null as BattleService | null,
    stompClient: null as Client | null,
    data: [] as Character[],
    battleData: [] as Character[],
    battles: [] as BattleWithCharacters[],
  }),
  getters: {
    socketConnected(): boolean {
      return this.stompClient?.connected || false
    },
    sortedCharacters(): Character[] {
      return this.selectedBattle?.characters.sort(
        (a, b) => (b.initiative || 0) - (a.initiative || 0)
      ) || []
    },
  },
  actions: {
    goToNextTurn() {
      const nowLast = Object.assign(this.battleData.slice(0, 1)[0])
      const someArray = this.battleData.slice(1)
      someArray.push(nowLast)
      console.log(someArray, nowLast)
      this.battleData = someArray
    },
  },
})
