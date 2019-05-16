package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TripServiceTest {

    @Test
    void userNotLoggedIn_throwsException() {
        Executable executable = () -> tripService.getTripsByUser(new User(), null);

        assertThrows(UserNotLoggedInException.class, executable);
    }

    @Test
    void notFriends_emptyList() {
        List<Trip> tripsByUser = tripService.getTripsByUser(new User(), loggedInUser);

        assertThat(tripsByUser, is(empty()));
    }

    @Test
    void hasOtherFriends_emptyList() {
        User notFriend = new User(asList(new User()), Collections.emptyList());

        List<Trip> tripsByUser = tripService.getTripsByUser(notFriend, loggedInUser);

        assertThat(tripsByUser, is(empty()));
    }

    @Test
    void returnsTripOfFriend() {
        User friendOfLoggedInUser = new User(asList(loggedInUser), asList(tripOfFriend));

        List<Trip> tripsByUser = tripService.getTripsByUser(friendOfLoggedInUser, loggedInUser);

        assertThat(tripsByUser, contains(tripOfFriend));
    }

    private User loggedInUser;
    private Trip tripOfFriend;
    private TripService tripService;

    @BeforeEach
    void setUp() {
        loggedInUser = new User();
        tripOfFriend = new Trip();
        tripService = new TestTripService();
    }

    public class TestTripService extends TripService {

        @Override
        protected List<Trip> tripsOf(User user) {
            return user.trips();
        }
    }

}
