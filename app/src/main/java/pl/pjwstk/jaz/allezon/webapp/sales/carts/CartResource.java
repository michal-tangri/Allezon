package pl.pjwstk.jaz.allezon.webapp.sales.carts;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.time.LocalDate;

@Path("/carts")
public class CartResource {

    @Inject
    private CartManagerService cartManagerService;

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCart(@PathParam("username") final String username) {
        var cart = cartManagerService.getCartByUsername(username);
        if(cart == null)
            return Response.status(Status.NOT_FOUND).build();
        return Response.status(Response.Status.OK).entity(cart).build();
    }

    @POST
    @Path("/{username}/add/{id}")
    public Response addItemToCart(@PathParam("username") final String username, @PathParam("id") final Long id,
                                  @QueryParam("amount") String amountString) {

        Cart cart = cartManagerService.getCartByUsername(username);
        Long amount = amountString == null ? 1L : Long.parseLong(amountString);

        CartProduct product = new CartProduct(LocalDate.now(), cartManagerService.getAuctionById(id), cart, amount);

        if(product.getAuction() == null)
            return Response.status(Status.NOT_FOUND).entity("Auction with this ID does not exist").build();
        cartManagerService.saveProduct(product);
        return Response.ok("Added " + amount +" products No: " + id + " to " + username + "'s cart").build();
    }

    @DELETE
    @Path("/{username}")
    public Response removeAllProductsInUsersCart(@PathParam("username") final String username) {
        if(cartManagerService.getCartByUsername(username) == null)
            return Response.status(Status.NOT_FOUND).entity("This user does not exist or does not have a cart!").build();
        cartManagerService.removeAllProductsInUsersCart(username);
        return Response.ok().entity("Deleted products from " + username + "'s cart").build();
    }
}