package restaurant.server.session;

import restaurant.server.entity.Restaurant;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(RestaurantDaoLocal.class)
public class RestaurantDaoBean extends GenericDaoBean<Restaurant, Integer>
implements RestaurantDaoLocal{

}
