package pl.pjwstk.jaz.allezon.webapp.sales.photos.api;

import pl.pjwstk.jaz.allezon.webapp.sales.photos.entities.Photo;

import java.util.List;

public interface PhotoApi {

    Photo getAuctionMiniature(Long auctionId);
}
