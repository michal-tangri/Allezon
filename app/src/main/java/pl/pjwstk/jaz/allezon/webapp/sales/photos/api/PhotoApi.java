package pl.pjwstk.jaz.allezon.webapp.sales.photos.api;

import pl.pjwstk.jaz.allezon.webapp.sales.photos.entities.Photo;

public interface PhotoApi {

    Photo getAuctionMiniature(Long auctionId);
}
