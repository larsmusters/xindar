import { Character } from '@/character/character-types.ts'

export interface Battle {
  id: number;
  name: string;
}

export interface BattleWithCharacters extends Battle {
  characters: Character[];
}

export interface CreateBattle {
  name: string
}
