package restaurant.server.session;

import restaurant.server.entity.City;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(CityDaoLocal.class)
public class CityDaoBean extends GenericDaoBean<City, Integer>
implements CityDaoLocal{

}
