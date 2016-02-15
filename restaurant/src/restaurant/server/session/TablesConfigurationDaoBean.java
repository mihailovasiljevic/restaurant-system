package restaurant.server.session;

import restaurant.server.entity.TablesConfiguration;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
@Local(TablesConfigurationDaoLocal.class)
public class TablesConfigurationDaoBean extends GenericDaoBean<TablesConfiguration, Integer>
implements TablesConfigurationDaoLocal{


	@Override
	public List<TablesConfiguration> findTablesConfigurationsByUserId(Integer userId) {
		// TODO Auto-generated method stub
	    Query q = em.createNamedQuery("findTablesConfigurationsByUserId");
	    q.setParameter("userId", userId);
	    List<TablesConfiguration> result = (List<TablesConfiguration>)q.getResultList();
	    return result;
	}

}
