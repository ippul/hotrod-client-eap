package org.ippul.infinispan.example;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.ippul.infinispan.example.hotrod.cache.CacheProxy;
import org.ippul.infinispan.example.model.ObjectStoredInCache0;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;

@Path("/services")
@Produces({"application/json", "application/xml"})
public class ObjectStoredInCache0Controller implements Serializable {

    @Inject
    private CacheProxy<ObjectStoredInCache0> cache;

    @GET
    @Path("/{id}")
    @Operation(summary = "Get ObjectStoredInCache0 by id",
            tags = {"id"},
            responses = {
                    @ApiResponse(description = "An ObjectStoredInCache0 instance from the cache",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ObjectStoredInCache0.class))),
                    @ApiResponse(responseCode = "400", description = "Key not found")})
    public Response getCarById(
            @Parameter(description = "The ObjectStoredInCache0 id that needs to be fetched. ", required = true) @PathParam("id") String id) {

        ObjectStoredInCache0 instance = cache.getByKey(id);
        if (null != instance) {
            return Response.ok().entity(instance).build();
        } else {
            throw new NotFoundException(Response.status(404, "ObjectStoredInCache0 not found with the provided id").build());
        }
    }

    @GET
    @Path("/")
    @Operation(summary = "Get all ObjectStoredInCache0",
            tags = {"id"},
            responses = {
                    @ApiResponse(description = "All ObjectStoredInCache0 instance from the cache",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = List.class))),
                    @ApiResponse(responseCode = "400", description = "No element found")})
    public Response getAll() {
        List<ObjectStoredInCache0> instances = cache.getAll();
        if (null != instances) {
            return Response.ok().entity(instances).build();
        } else {
            throw new NotFoundException(Response.status(404, "No ObjectStoredInCache0 found").build());
        }
    }
}
