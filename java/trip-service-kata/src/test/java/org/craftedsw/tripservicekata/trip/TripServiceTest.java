package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TripServiceTest {

    private User loggedInUser;

    @BeforeEach
    void setUp() {
        loggedInUser = new User();
    }

    @Test
    void userNotLoggedIn_throwsException() {
        TripService tripService = new TestTripService(null);

        Executable executable = () -> tripService.getTripsByUser(new User());

        assertThrows(UserNotLoggedInException.class, executable);
    }

    @Test
    void notFriends_emptyList() {
        TripService tripService = new TestTripService(loggedInUser);

        List<Trip> tripsByUser = tripService.getTripsByUser(new User());

        assertThat(tripsByUser, is(empty()));
    }

    @Test
    void hasOtherFriends_emptyList() {
        User notFriend = new User();
        notFriend.addFriend(new User());
        TripService tripService = new TestTripService(loggedInUser);

        List<Trip> tripsByUser = tripService.getTripsByUser(notFriend);

        assertThat(tripsByUser, is(empty()));
    }

    @Test
    void returnsTripOfFriend() {
        User friendOfLoggedInUser = new User();
        friendOfLoggedInUser.addFriend(loggedInUser);
        Trip trip = new Trip();
        friendOfLoggedInUser.addTrip(trip);
        TripService tripService = new TestTripService(loggedInUser);

        List<Trip> tripsByUser = tripService.getTripsByUser(friendOfLoggedInUser);

        assertThat(tripsByUser, contains(trip));
    }

    public class TestTripService extends TripService {

        private User loggedInUser;

        public TestTripService(User loggedInUser) {
            this.loggedInUser = loggedInUser;
        }

        @Override
        protected User loggedInUser() {
            return loggedInUser;
        }

        @Override
        protected List<Trip> tripsOf(User user) {
            return user.trips();
        }
    }

}
