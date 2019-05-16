package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TripServiceTest {

    @Test
    void userNotLoggedIn() {
        TripService tripService = new TestTripService(null);

        Executable executable = () -> tripService.getTripsByUser(new User());

        assertThrows(UserNotLoggedInException.class, executable);
    }

    public class TestTripService extends TripService {

        private User loggedInUser;

        public TestTripService(User loggedInUser) {
        }

        @Override
        protected User loggedInUser() {
            return loggedInUser;
        }
    }

}
