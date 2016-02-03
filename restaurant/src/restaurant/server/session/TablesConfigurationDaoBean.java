package restaurant.server.session;

import restaurant.server.entity.TablesConfiguration;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(TablesConfigurationDaoLocal.class)
public class TablesConfigurationDaoBean extends GenericDaoBean<TablesConfiguration, Integer>
implements TablesConfigurationDaoLocal{

}
