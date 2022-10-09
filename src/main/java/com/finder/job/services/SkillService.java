package com.finder.job.services;

import com.finder.job.models.Skill;
import com.finder.job.repositories.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {
    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public List<Skill> getSkillsById(Long id){
        return skillRepository.findAllByPersonId(id);
    }
}
