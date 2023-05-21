export interface Character {
  id: number;
  name: string | null;
  initiative: number | null;
  up: boolean;
  battleId: number | null;
}


export interface CreateCharacter {
  name: string;
  initiative: number | null;
  battleId: number | null;
}

export interface UpdateCharacter {
  id: number;
  name: string;
  initiative: number;
  battleId: number;
  up: boolean;
}