package ru.agavshin.boundary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/demo")
public class DemoResource {

    @GET
    public String demo() {
        return "It works!";
    }
}
