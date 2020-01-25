package pl.pjwstk.jaz.allezon.webapp.sales.carts;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/carts")
public class CartResource {

    @Inject
    private CartApi cartApi;

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCart(@PathParam("username") final String username) {
        try {
            var cart = cartApi.getCartWithProducts(username);
            return Response.status(Status.OK).entity(cart).build();
        }
        catch (Exception err0) {
            return Response.status(Status.NOT_FOUND).entity(err0.getMessage()).build();
        }
    }

    @POST
    @Path("/add-item")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addItemToCart(@Valid AddItemCommand addItemCommand) {
        try {
            cartApi.addItemToCart(addItemCommand);
            String message = "Added " + addItemCommand.getAmount() +" products No: " + addItemCommand.getAuctionId() +
                             " to " + addItemCommand.getUsername() + "'s cart";
            return Response.ok(message).build();
        }
        catch (IllegalArgumentException err0) {
            return Response.status(Status.NOT_FOUND).entity(err0.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{username}")
    public Response removeAllProductsFromUsersCart(@PathParam("username") final String username) {
        try {
            cartApi.deleteCart(username);
            return Response.ok().entity("Deleted products from " + username + "'s cart").build();
        }
        catch (IllegalArgumentException err0) {
            return Response.status(Status.NOT_FOUND).entity(err0.getMessage()).build();
        }
    }
}