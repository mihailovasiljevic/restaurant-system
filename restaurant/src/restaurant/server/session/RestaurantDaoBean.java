package restaurant.server.session;

import java.util.List;

import restaurant.server.entity.Restaurant;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
@Local(RestaurantDaoLocal.class)
public class RestaurantDaoBean extends GenericDaoBean<Restaurant, Integer>
implements RestaurantDaoLocal{

	@SuppressWarnings("unchecked")
	@Override
	public List<Restaurant> findRestaurantByUserId(Integer id) {
		// TODO Auto-generated method stub
        Query q = em.createNamedQuery("findRestaurantByUserId");
        q.setParameter("userId", id);

        List<Restaurant> resultList;
        resultList = (List<Restaurant>)q.getResultList();
        return resultList;
	}
}
