package restaurant.server.session;

import java.util.HashMap;

import restaurant.server.entity.User;

public interface Invitation {
	public void invitate(HashMap<Integer, User> reserve);
}
