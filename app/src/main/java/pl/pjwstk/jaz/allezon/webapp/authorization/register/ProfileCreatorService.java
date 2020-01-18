package pl.pjwstk.jaz.allezon.webapp.authorization.register;

import pl.pjwstk.jaz.allezon.webapp.authorization.entities.ProfileEntity;
import pl.pjwstk.jaz.allezon.webapp.authorization.repositories.ProfileRepository;
import pl.pjwstk.jaz.allezon.webapp.sales.carts.CartRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ProfileCreatorService {

    @Inject
    private ProfileRepository profileRepository;

    @Inject
    private CartRepository cartRepository;

    public void saveProfile(ProfileEntity profileEntity) {
        profileRepository.save(profileEntity);
    }

    public void createCartForNewUser(String username) {
        cartRepository.createCartForUser(username);
    }
}
