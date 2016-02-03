package restaurant.server.session;

import restaurant.server.entity.Address;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(AddressDaoLocal.class)
public class AddressDaoBean extends GenericDaoBean<Address, Integer>
implements AddressDaoLocal{

}
