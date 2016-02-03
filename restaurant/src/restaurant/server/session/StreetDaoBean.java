package restaurant.server.session;

import restaurant.server.entity.Street;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(StreetDaoLocal.class)
public class StreetDaoBean extends GenericDaoBean<Street, Integer>
implements StreetDaoLocal{

}
