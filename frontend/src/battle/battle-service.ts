import { Battle, BattleWithCharacters, CreateBattle } from '@/battle/battle-types.ts'


export interface BattleService {
  readonly get: {
    readonly all: () => Promise<Array<BattleWithCharacters>>;
  };

  readonly create: {
    readonly one: (data: CreateBattle) => Promise<Battle>;
  };

  readonly update: {
    readonly one: (data: Battle)  => Promise<Battle>;
  };

  readonly delete: {
    readonly one: (battleID: number) => Promise<void>;
  }

}