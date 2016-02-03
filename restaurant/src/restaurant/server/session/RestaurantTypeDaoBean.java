package restaurant.server.session;

import restaurant.server.entity.RestaurantType;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(RestaurantTypeDaoLocal.class)
public class RestaurantTypeDaoBean extends GenericDaoBean<RestaurantType, Integer>
implements RestaurantTypeDaoLocal{

}
