package pl.dk.soa.evolution.alloperations;

import lombok.Getter;
import lombok.Setter;
import pl.dk.soa.evolution.candidate.Candidate;

@Getter
@Setter
public class AddCandidateOperation implements Operation {

    private Candidate candidate;

}
