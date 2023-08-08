package org.goafabric.core.mrc.repository;


import org.goafabric.core.mrc.repository.entity.ConditionEo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ConditionRepository extends CrudRepository<ConditionEo, String> {
    List<ConditionEo> findByEncounterIdAndDisplayContains(String encounterId, String display);

}

