package restaurant.server.session;

import restaurant.server.entity.RestaurantMenager;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(RestaurantMenagerDaoLocal.class)
public class RestaurantMenagerDaoBean extends GenericDaoBean<RestaurantMenager, Integer>
implements RestaurantMenagerDaoLocal{

}
