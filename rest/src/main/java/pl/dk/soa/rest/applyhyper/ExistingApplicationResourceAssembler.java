package pl.dk.soa.rest.applyhyper;

import org.springframework.hateoas.*;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static java.util.stream.Collectors.toList;

@Service
public class ExistingApplicationResourceAssembler implements ResourceAssembler<ExistingApplication, Resource<ExistingApplication>> {

    private final EntityLinks entityLinks;

    public ExistingApplicationResourceAssembler(EntityLinks entityLinks) {
        this.entityLinks = entityLinks;
    }

    @Override
    public Resource<ExistingApplication> toResource(ExistingApplication application) {
       /* Link selfLink = ControllerLinkBuilder
                .linkTo(methodOn(ApplyHyperController.class).showApplication(application.getId()))
                .withSelfRel();*/
        Link selfLink = entityLinks
                .linkToSingleResource(ExistingApplication.class, application.getId());
        Link allApplications = entityLinks
                .linkToCollectionResource(ExistingApplication.class)
                .withRel("all-applications");
        return new Resource<>(application, selfLink, allApplications);
    }

    public Resources<ExistingApplication> toResource(Collection<ExistingApplication> applications) {
        return new Resources(applications.stream()
                .map(application -> {
                    Link selfLink = entityLinks
                            .linkToSingleResource(ExistingApplication.class, application.getId());
                    return new Resource<>(application, selfLink);
                })
                .collect(toList()));
    }
}
