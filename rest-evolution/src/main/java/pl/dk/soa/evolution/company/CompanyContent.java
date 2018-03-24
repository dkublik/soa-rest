package pl.dk.soa.evolution.company;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CompanyContent {

    private String name;
    private List<String> benefits = new ArrayList<>();
    private List<String> gallery = new ArrayList<>();

    CompanyContent(String name) {
        this.name = name;
    }

}
