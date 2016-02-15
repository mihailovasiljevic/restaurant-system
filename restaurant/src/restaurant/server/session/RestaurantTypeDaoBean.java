package restaurant.server.session;

import java.util.List;

import restaurant.server.entity.Restaurant;
import restaurant.server.entity.RestaurantType;
import restaurant.server.entity.User;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

@Stateless
@Local(RestaurantTypeDaoLocal.class)
public class RestaurantTypeDaoBean extends GenericDaoBean<RestaurantType, Integer>
implements RestaurantTypeDaoLocal{
	@SuppressWarnings("unchecked")
	public List<RestaurantType>  findRestaurantTypeByUserId(Integer id){
        Query q = em.createNamedQuery("findRestaurantTypeByUserId");
        q.setParameter("userId", id);

        List<RestaurantType> resultList;
        resultList = (List<RestaurantType>)q.getResultList();
        return resultList;
	}

}
