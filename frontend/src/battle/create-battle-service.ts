import { ApiService } from '@/api/api-service.ts'
import { BattleService } from '@/battle/battle-service.ts'


export function createBattleService(apiService: ApiService): BattleService {
  return {
    get: {
      all: () => apiService.get('/battle'),
      one: (battleID) => apiService.get(`/battle/${battleID}`),
    },
    create: {
      one: (data) => apiService.post('/battle', data),
    },
    update: {
      one: (data) => apiService.put(`/battle/${data.id}`, data),
    },
    delete: {
      one: (battleID) => apiService.delete(`battle/${battleID}`),
    },
  }
}