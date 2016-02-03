package restaurant.server.session;

import restaurant.server.entity.Menu;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(MenuDaoLocal.class)
public class MenuDaoBean extends GenericDaoBean<Menu, Integer>
implements MenuDaoLocal{

}
