import { CharacterService } from '@/character/character-service.ts'
import { ApiService } from '@/api/api-service.ts'


export function createCharacterService(apiService: ApiService): CharacterService {
  return {
    get: {
      all: () => apiService.get('/character'),
    },
    create: {
      one: (data) => apiService.post('/character', data),
    },
    update: {
      one: (data) => apiService.put(`/character/${data.id}`, data),
    },
    delete: {
      one: (characterID) => apiService.delete(`character/${characterID}`),
    },
  }
}