package pl.pjwstk.jaz.allezon.webapp.sales.photos.api;

import pl.pjwstk.jaz.allezon.webapp.sales.photos.entities.Photo;
import pl.pjwstk.jaz.allezon.webapp.sales.photos.repositories.PhotoRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PhotoApiImpl implements PhotoApi {

    @Inject
    private PhotoRepository photoRepository;

    @Override
    public Photo getAuctionMiniature(Long auctionId) throws IllegalArgumentException {
        var photos = photoRepository.findAllPhotosByAuctionId(auctionId);
        if (photos.isEmpty())
            throw new IllegalArgumentException("This auction has no photos");
        return photos.get(0);
    }
}