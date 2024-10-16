package com.flipkart.restController;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.flipkart.business.FlipFitGymCustomerBusiness;
import com.flipkart.business.interfaces.IFlipFitGymCustomer;
import com.flipkart.exceptions.InvalidChoiceException;
import com.flipkart.model.FlipFitBooking;
import com.flipkart.model.FlipFitGymCentre;
import com.flipkart.model.FlipFitGymCustomer;
import com.flipkart.model.FlipFitUser;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)

public class CustomerController {
    private final IFlipFitGymCustomer flipFitCustomerBusiness;
    private FlipFitGymCustomer flipFitGymCustomer;
    private FlipFitUser flipFitUser;

    @Inject
    public CustomerController(FlipFitGymCustomerBusiness flipFitGymCustomerService) {
        this.flipFitCustomerBusiness = flipFitGymCustomerService;
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public FlipFitGymCustomer login(FlipFitUser user) {
        FlipFitGymCustomer customer = flipFitCustomerBusiness.login(user);
        this.flipFitGymCustomer = customer;
        return flipFitGymCustomer;
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public FlipFitGymCustomer register(CustomerRegisterObject customerRegisterObject) {
        FlipFitGymCustomer customer = flipFitCustomerBusiness.registerCustomer(flipFitGymCustomer);
        this.flipFitGymCustomer = customer;
        return flipFitGymCustomer;
    }
    public Response login(@Valid LoginRequest loginRequest) {
        try {
            Person person = userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
            if (person != null) {
                return Response.ok("registered, successfully").build();
            }
            return Response.status(Response.Status.UNAUTHORIZED).entity("something went wrong!!").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }


    @GET
    @Path("/viewBookings")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<FlipFitBooking> viewBookings() {
        return flipFitCustomerBusiness.viewBookedSlots(flipFitGymCustomer.getUserId());
    }

    @GET
    @Path("/viewCentres")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<FlipFitGymCentre> viewCentres() {
        return flipFitCustomerBusiness.viewCentres();
    }

    @PUT
    @Path("/editDetails")
    @Consumes(MediaType.APPLICATION_JSON)
    public FlipFitGymCustomer editDetails(FlipFitGymCustomer flipFitGymCustomer) throws InvalidChoiceException {
        return flipFitCustomerBusiness.editDetails(flipFitGymCustomer);
    }

}
