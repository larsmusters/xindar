export interface Character {
  id: number | null;
  name: string | null;
  initiative: number | null;
  isUp: number | null;
  battleId: number | null;
}

export interface Battle {
  id: number | null;
  name: string | null;
}

export interface BattleWithCharacters extends Battle {
  characters: Character[];
}