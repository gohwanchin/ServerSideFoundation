package sg.edu.nus.workshop16.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.workshop16.repo.RedisRepo;

@Service
public class GameService {
    @Autowired
    RedisRepo redisRepo;

    public Set<String> searchKeys(String s){
        String pattern = "*%s*".formatted(s);
        return redisRepo.findKeys(pattern);
    }
}
