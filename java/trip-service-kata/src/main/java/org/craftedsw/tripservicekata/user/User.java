package org.craftedsw.tripservicekata.user;

import org.craftedsw.tripservicekata.trip.Trip;

import java.util.ArrayList;
import java.util.List;

public class User {

	private List<Trip> trips = new ArrayList<Trip>();
	private List<User> friends = new ArrayList<User>();
	
	public List<User> getFriends() {
		return friends;
	}
	
	public void addFriend(User user) {
		friends.add(user);
	}

	public void addTrip(Trip trip) {
		trips.add(trip);
	}
	
	public List<Trip> trips() {
		return trips;
	}

    public boolean isFriendOf(User loggedUser) {
        for (User friend : getFriends()) {
            if (friend.equals(loggedUser)) {
                return true;
            }
        }
        return false;
    }
}
