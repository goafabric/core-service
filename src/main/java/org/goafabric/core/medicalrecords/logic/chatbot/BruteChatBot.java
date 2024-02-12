package org.goafabric.core.medicalrecords.logic.chatbot;

import org.goafabric.core.medicalrecords.controller.dto.Encounter;
import org.goafabric.core.medicalrecords.controller.dto.MedicalRecordType;
import org.goafabric.core.organization.repository.entity.PatientNamesOnly;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;


@Component
public class BruteChatBot {
    public record SearchResult(PatientNamesOnly patientName, List<MedicalRecordType> medicalRecordTypes, String displayText) {
        public boolean nothingFound() {
            return (patientName == null && medicalRecordTypes().isEmpty() && displayText.isEmpty());
        }
    }

    private final BruteChatTool bruteChatTool;

    public BruteChatBot(BruteChatTool bruteChatTool) {
        this.bruteChatTool = bruteChatTool;
    }

    public List<Encounter> chat(String text, String prevPatientId) {
        var searchResult = createSearchResult(text);
        var patientId = searchResult.patientName == null ? prevPatientId : searchResult.patientName.getId();
        return searchResult.nothingFound()
                ? new ArrayList<>()
                : bruteChatTool.findByPatientIdAndDisplayAndType(patientId, searchResult.displayText(), searchResult.medicalRecordTypes());
    }

    public SearchResult createSearchResult(String text) {
        var tokens = tokenizeText(text);
        var medicalRecordTypes = searchMedicalRecordType(tokens);
        var displayText = searchDisplayText(tokens);
        var patient = searchPatient(tokens);

        return new SearchResult(patient, medicalRecordTypes, displayText);
    }

    public List<String> tokenizeText(String input) {
        var text = input.replaceAll(",", "").toLowerCase();
        return Arrays.stream(text.split(" "))
                .filter(token -> token.length() > 3).toList();
    }

    //search record types with the tokens, from a static switched list of matching keywords
    private List<MedicalRecordType> searchMedicalRecordType(List<String> tokens) {
        return tokens.stream()
                .map(bruteChatTool::findMedicalRecordTypeViaKeyords)
                .filter(Objects::nonNull)
                .toList();
    }

    //brute force search with the tokens, for patient names inside the db, returns first hit and only via GivenName OR FamilyName
    private PatientNamesOnly searchPatient(List<String> tokens) {
        return tokens.stream()
                .map(bruteChatTool::findPatientViaDatabaseBruteForce)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    //search display text via Keywords
    private String searchDisplayText(List<String> tokens) {
        var keywords = Arrays.asList("text", "contain", "contains");
        return IntStream.range(0, tokens.size() - 1)
                .filter(i -> keywords.contains(tokens.get(i)) && !keywords.contains(tokens.get(i + 1)))
                .mapToObj(i -> tokens.get(i + 1))
                //.mapToObj(index -> String.join(" ", tokens.subList(index + 1, tokens.size())))
                .findFirst()
                .orElse("");
    }

}
