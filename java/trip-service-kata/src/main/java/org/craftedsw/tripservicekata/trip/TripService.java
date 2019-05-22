package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;

import java.util.ArrayList;
import java.util.List;

public class TripService {

    public static final ArrayList<Trip> NO_TRIPS = new ArrayList<>();

    private final TripDAO tripDAO;

    public TripService(TripDAO tripDAO) {
        this.tripDAO = tripDAO;
    }

    public List<Trip> getTripsByFriend(User friend, User loggedInUser) throws UserNotLoggedInException {
        assertUserLoggedIn(loggedInUser);

        return friend.isFriendOf(loggedInUser)
            ? tripsBy(friend)
            : NO_TRIPS;
    }

    private void assertUserLoggedIn(User loggedUser) {
        if (loggedUser == null) {
            throw new UserNotLoggedInException();
        }
    }

    private List<Trip> tripsBy(User user) {
        return tripDAO.tripsByUser(user);
    }

}
