package pl.pjwstk.jaz.allezon.webapp.sales.photos.api;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("photos")
public class PhotoResource {

    @Inject
    private PhotoApi photoApi;

    @GET
    @Path("/{auctionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuctionPhotos(@PathParam("auctionId") Long auctionId) {
        try {
            var photos = photoApi.getAuctionMiniature(auctionId);
            return Response.status(Response.Status.OK).entity(photos).build();
        }
        catch (IllegalArgumentException err0) {
            return Response.status(Response.Status.NOT_FOUND).entity(err0.getMessage()).build();
        }
    }

}
