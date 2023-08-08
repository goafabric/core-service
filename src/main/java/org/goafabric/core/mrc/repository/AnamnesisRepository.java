package org.goafabric.core.mrc.repository;


import org.goafabric.core.mrc.repository.entity.AnamnesisEo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// TODO: use gin index + computated field, check if extension is needed, @@ phraseto_tsquery
public interface AnamnesisRepository extends CrudRepository<AnamnesisEo, String> {
    List<AnamnesisEo> findByEncounterIdAndTextContainsIgnoreCase(String encounterId, String text);

    @Query("select a from AnamnesisEo a")
    List<AnamnesisEo> findAllByEncounterId(String encounterId, TextCriteria criteria);

    /*
    @Query(nativeQuery = true, value = "select text from observation WHERE to_tsvector('english', text) @@ to_tsquery('english', :criteria)")
    List<String> findByText(String criteria);
     */
}

