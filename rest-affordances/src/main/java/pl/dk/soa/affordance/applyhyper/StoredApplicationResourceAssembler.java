package pl.dk.soa.affordance.applyhyper;

import org.springframework.hateoas.*;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.afford;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class StoredApplicationResourceAssembler implements ResourceAssembler<StoredApplication, Resource<StoredApplication>> {

    private final EntityLinks entityLinks;

    public StoredApplicationResourceAssembler(EntityLinks entityLinks) {
        this.entityLinks = entityLinks;
    }

    @Override
    public Resource<StoredApplication> toResource(StoredApplication application) {
       /* Link selfLink = ControllerLinkBuilder
                .linkTo(methodOn(ApplyHyperController.class).showApplication(application.getId()))
                .withSelfRel();*/
        Link selfLink = entityLinks
                .linkToSingleResource(StoredApplication.class, application.getId())
                .andAffordance(afford(methodOn(ApplyHyperController.class).deleteApplication(application.getId())));

        Link allApplications = entityLinks
                .linkToCollectionResource(StoredApplication.class)
                .withRel("all-applications");
        return new Resource<>(application, selfLink, allApplications);
    }

    @SuppressWarnings("unchecked")
    public Resources<StoredApplication> toResource(Collection<StoredApplication> applications) {
        return new Resources(
                applications.stream()
                .map(application -> {
                    Link selfLink = entityLinks
                            .linkToSingleResource(StoredApplication.class, application.getId())
                            .andAffordance(afford(methodOn(ApplyHyperController.class).deleteApplication(application.getId())));
                    return new Resource<>(application, selfLink);
                })
                .collect(toList()),
                entityLinks
                        .linkToCollectionResource(StoredApplication.class)
                        .andAffordance(afford(methodOn(ApplyHyperController.class).applyForJob(null)))
            );
    }
}
