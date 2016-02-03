package restaurant.server.session;

import restaurant.server.entity.RestaurantGuest;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(RestaurantGuestDaoLocal.class)
public class RestaurantGuestDaoBean extends GenericDaoBean<RestaurantGuest, Integer>
implements RestaurantGuestDaoLocal{

}
