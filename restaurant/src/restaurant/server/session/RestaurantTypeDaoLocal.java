package restaurant.server.session;

import java.util.List;

import restaurant.server.entity.RestaurantType;

public interface RestaurantTypeDaoLocal extends GenericDaoLocal<RestaurantType, Integer>{
	public List<RestaurantType>  findRestaurantTypeByUserId(Integer id);
}
