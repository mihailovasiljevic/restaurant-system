package restaurant.server.session;

import java.util.List;

import restaurant.server.entity.Restaurant;

public interface RestaurantDaoLocal extends GenericDaoLocal<Restaurant, Integer>{
	public List<Restaurant>  findRestaurantByUserId(Integer id);
}
