package restaurant.server.session;

import java.util.List;

import restaurant.server.entity.Menu;

public interface MenuDaoLocal extends GenericDaoLocal<Menu, Integer>{
	public List<Menu> findMenusByUserId(Integer userId);
}
