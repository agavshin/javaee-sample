package ru.agavshin.demo.boundary;

import ru.agavshin.demo.entity.Demo;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/demo")
@Produces(MediaType.APPLICATION_JSON)
public class DemoResource {

    @Inject
    private DemoService demoService;

    @GET
    public Response demo() {
        return Response.ok(demoService.createSample()).build();
    }

    @POST
    @Path("/send")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response send(Demo demo) throws IOException {
        demoService.send(demo);
        return Response.ok().build();
    }
}
