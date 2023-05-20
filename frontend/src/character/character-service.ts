import { Character, UpdateCharacter, CreateCharacter } from './character-types.ts'

export interface CharacterService {
  readonly get: {
    readonly all: () => Promise<Array<Character>>;
  };

  readonly create: {
    readonly one: (data: CreateCharacter) => Promise<Character>;
  }

  readonly update: {
    readonly one: (data: UpdateCharacter) => Promise<Character>;
  }

  readonly delete: {
    readonly one: (characterID: number) => Promise<void>;
  }
}