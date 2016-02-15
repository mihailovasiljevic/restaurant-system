package restaurant.server.session;

import java.util.List;

import restaurant.server.entity.TablesConfiguration;

public interface TablesConfigurationDaoLocal extends GenericDaoLocal<TablesConfiguration, Integer>{
	public List<TablesConfiguration> findTablesConfigurationsByUserId(Integer userId);
}
