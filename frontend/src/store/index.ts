import { defineStore } from 'pinia'
import { Character, BattleWithCharacters } from '@/types'
import { Client } from 'webstomp-client'

export const useAppStore = defineStore('app', {
  state: () => ({
    stompClient: null as Client | null,
    data: [] as Character[],
    battleData: [] as Character[],
    battles: [] as BattleWithCharacters[],
  }),
  getters: {
    socketConnected(): boolean {
      return this.stompClient?.connected || false
    },
    upNextCharacter(): Character | null {
      return this.battleData[1]
    },
    currentCharacter(): Character | null {
      return this.battleData[0]
    },
    sortedCharacters(): Character[] {
      return this.data?.sort(
        (a, b) => (b.initiative || 0) - (a.initiative || 0)
      )
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
