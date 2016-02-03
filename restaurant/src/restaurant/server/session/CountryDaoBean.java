package restaurant.server.session;

import restaurant.server.entity.Country;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(CountryDaoLocal.class)
public class CountryDaoBean extends GenericDaoBean<Country, Integer>
implements CountryDaoLocal{

}
