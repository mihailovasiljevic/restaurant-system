package restaurant.server.session;

import restaurant.server.entity.Menu;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
@Local(MenuDaoLocal.class)
public class MenuDaoBean extends GenericDaoBean<Menu, Integer>
implements MenuDaoLocal{

	@Override
	public List<Menu> findMenusByUserId(Integer userId) {
	    Query q = em.createNamedQuery("findMenusByUserId");
	    q.setParameter("userId", userId);
	    List<Menu> result = (List<Menu>)q.getResultList();
	    return result;
	}

}
