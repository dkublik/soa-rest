package pl.dk.soa.evolution.company;

import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Arrays.asList;

@Service
public class CompanyService {

    private final Map<String, CompanyContent> companyContent = new HashMap<>();

    CompanyService() {
        CompanyContent googleContent = new CompanyContent("google");
        googleContent.getBenefits().addAll(asList("free snacks", "workshops", "shower"));
        googleContent.getGallery().addAll(asList(
                "https://i.pinimg.com/236x/28/eb/54/28eb54d70a2bbfcd677509c46ae6aba2--office-graphics-google-office.jpg",
                "https://i.pinimg.com/236x/79/97/62/7997624be81c95c12cd4dc11ea0fc03c--modern-office-design-modern-offices.jpg"
        ));
        companyContent.put("google", googleContent);
        CompanyContent spotifyContent = new CompanyContent("spotify");
        spotifyContent.getBenefits().addAll(asList("gym", "trainings", "free parking"));
        spotifyContent.getGallery().addAll(asList(
                "https://i.pinimg.com/236x/63/f6/1a/63f61adcf855dd6ebf35f7e0bfb944d1--office-pictures-office-interiors.jpg",
                "https://i.pinimg.com/236x/55/3e/8a/553e8a9fe9ea3bd4c911490b270c259d--offices.jpg"
        ));
        companyContent.put("spotify", spotifyContent);
    }

    public  Optional<CompanyContent> getCompanyContent(String companyName) {
        return Optional.ofNullable(companyContent.get(companyName));
    }

}
