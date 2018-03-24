package pl.dk.soa.evolution.alloperations;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "addCandidate", value = AddCandidateOperation.class),
        @JsonSubTypes.Type(name = "getCandidate", value = GetCandidateOperation.class),
        @JsonSubTypes.Type(name = "getCompanyContent", value = GetCompanyContentOperation.class),
})
public interface Operation {
}
