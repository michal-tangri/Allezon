package pl.pjwstk.jaz.allezon.webapp.api;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationPath("/api")
public class RestAPI extends Application {

    @Path("/info")
    public static class InfoResource {

        private static final String API_VERSION = "Allezon public API v1.2.7";

        @GET
        @Path("/version")
        @Produces(MediaType.TEXT_PLAIN)
        public Response getAPIVersion() {
            return Response.ok().entity(API_VERSION).build();
        }

    }
}
