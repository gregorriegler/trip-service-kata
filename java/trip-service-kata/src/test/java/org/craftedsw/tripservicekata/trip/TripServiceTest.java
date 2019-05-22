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
        Executable executable = () -> tripService.getTripsByFriend(new User(), null);

        assertThrows(UserNotLoggedInException.class, executable);
    }

    @Test
    void notFriends_emptyList() {
        List<Trip> tripsByUser = tripService.getTripsByFriend(new User(), loggedInUser);

        assertThat(tripsByUser, is(empty()));
    }

    @Test
    void hasOtherFriends_emptyList() {
        User notFriend = new User(asList(new User()), Collections.emptyList());

        List<Trip> tripsByUser = tripService.getTripsByFriend(notFriend, loggedInUser);

        assertThat(tripsByUser, is(empty()));
    }

    @Test
    void returnsTripOfFriend() {
        User friendOfLoggedInUser = new User(asList(loggedInUser), asList(tripOfFriend));

        List<Trip> tripsByUser = tripService.getTripsByFriend(friendOfLoggedInUser, loggedInUser);

        assertThat(tripsByUser, contains(tripOfFriend));
    }

    private User loggedInUser = new User();
    private Trip tripOfFriend = new Trip();
    private TripDAO tripDAO = new TripDAO();
    private TripService tripService;

    @BeforeEach
    void setUp() {
        tripService = new TripService(tripDAO);
    }

}
