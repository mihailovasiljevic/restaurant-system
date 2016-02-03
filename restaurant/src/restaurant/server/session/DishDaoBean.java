package restaurant.server.session;

import restaurant.server.entity.Dish;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(DishDaoLocal.class)
public class DishDaoBean extends GenericDaoBean<Dish, Integer>
implements DishDaoLocal{

}
