package restaurant.server.session;

import restaurant.server.entity.Visit;

public interface VisitDaoLocal  extends GenericDaoLocal<Visit, Integer>{
	public int findUserVisitsNo(Integer userId);
	public int findUserVisitsNoByRestaurant(Integer userId, Integer restaurantId);
}
