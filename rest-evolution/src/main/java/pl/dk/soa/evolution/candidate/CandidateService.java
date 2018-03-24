package pl.dk.soa.evolution.candidate;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CandidateService {

    private final Map<String, Candidate> candidates = new HashMap<>();

    public Optional<Candidate> getCandidate(String login) {
        return Optional.ofNullable(candidates.get(login));
    }

    public void addCandidate(Candidate candidate) {
        candidates.put(candidate.getLogin(), candidate);
    }

}
