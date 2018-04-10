package pl.dk.soa.rest.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Comparator.comparing;

@Service
public class ApplyService {

    private Map<String, StoredApplication> applications = new HashMap<>();

    public void addApplication(StoredApplication storedApplication) {
        applications.put(storedApplication.getId(), storedApplication);
    }

    public Optional<StoredApplication> getApplication(String id) {
        return Optional.ofNullable(applications.get(id));
    }

    public List<StoredApplication> getApplications() {
        List<StoredApplication> applicationList = new ArrayList<>(applications.values());
        applicationList.sort(comparing(StoredApplication::getCreatedTime));
        return applicationList;
    }

    public void remove(String id) {
        applications.remove(id);
    }

}
