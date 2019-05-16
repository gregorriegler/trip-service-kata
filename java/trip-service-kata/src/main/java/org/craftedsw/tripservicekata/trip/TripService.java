package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

import java.util.ArrayList;
import java.util.List;

public class TripService {

    public static final ArrayList<Trip> NO_TRIPS = new ArrayList<>();

    public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
        User loggedInUser = loggedInUser();
        assertUserLoggedIn(loggedInUser);

        return user.isFriendOf(loggedInUser)
            ? tripsOf(user)
            : NO_TRIPS;
    }

    private void assertUserLoggedIn(User loggedUser) {
        if (loggedUser == null) {
            throw new UserNotLoggedInException();
        }
    }

    protected List<Trip> tripsOf(User user) {
        return TripDAO.findTripsByUser(user);
    }

    protected User loggedInUser() {
        return UserSession.getInstance().getLoggedUser();
    }

}
