import { defineStore } from 'pinia'
import { Character } from '@/character/character-types.ts'
import { Client } from 'webstomp-client'
import { CharacterService } from '@/character/character-service.ts'
import { BattleService } from '@/battle/battle-service.ts'
import { BattleWithCharacters } from '@/battle/battle-types.ts'
import { createBattleService } from '@/battle/create-battle-service.ts'
import { createCharacterService } from '@/character/create-character-service.ts'
import { AxiosApiService } from '@/api/api-service.ts'

const apiUrl = import.meta.env.VITE_API_URL + '/api/v1/'
const apiService = new AxiosApiService(apiUrl)

export const useAppStore = defineStore('app', {
  state: () => ({
    characterService: createCharacterService(apiService) as CharacterService,
    battleService: createBattleService(apiService) as BattleService,
    selectedBattle: null as BattleWithCharacters | null,
    stompClient: null as Client | null,
    data: [] as Character[],
    battleData: [] as Character[],
    battles: [] as BattleWithCharacters[],
  }),
  getters: {
    exampleGetter(): boolean {
      return true
    },
  },
  actions: {
    sortCharacters(characters: Character[]): Character[] {
      return characters.sort((a,b) => {
        return  (b.initiative || 0) - (a.initiative || 0)
      })
    },
  },
})
