package pl.dk.soa.affordance.applyhyper;

import org.springframework.hateoas.*;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.afford;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
                .linkToSingleResource(ExistingApplication.class, application.getId())
                .andAffordance(afford(methodOn(ApplyHyperController.class).deleteApplication(application.getId())));

        Link allApplications = entityLinks
                .linkToCollectionResource(ExistingApplication.class)
                .withRel("all-applications");
        return new Resource<>(application, selfLink, allApplications);
    }

    @SuppressWarnings("unchecked")
    public Resources<ExistingApplication> toResource(Collection<ExistingApplication> applications) {
        return new Resources(
                applications.stream()
                .map(application -> {
                    Link selfLink = entityLinks
                            .linkToSingleResource(ExistingApplication.class, application.getId())
                            .andAffordance(afford(methodOn(ApplyHyperController.class).deleteApplication(application.getId())));
                    return new Resource<>(application, selfLink);
                })
                .collect(toList()),
                entityLinks
                        .linkToCollectionResource(ExistingApplication.class)
                        .andAffordance(afford(methodOn(ApplyHyperController.class).applyForJob(null)))
            );
    }
}
