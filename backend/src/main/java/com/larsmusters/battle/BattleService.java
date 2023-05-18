package com.larsmusters.battle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BattleService {

    private final BattleRepository battleRepository;

    @Autowired BattleService(BattleRepository battleRepository) { this.battleRepository = battleRepository;}

    public List<Battle> getBattle() { return battleRepository.findAll();}
}
