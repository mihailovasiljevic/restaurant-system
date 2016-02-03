package restaurant.server.session;

import restaurant.server.entity.RestaurantTable;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(RestaurantTableDaoLocal.class)
public class RestaurantTableDaoBean extends GenericDaoBean<RestaurantTable, Integer>
implements RestaurantTableDaoLocal{

}
