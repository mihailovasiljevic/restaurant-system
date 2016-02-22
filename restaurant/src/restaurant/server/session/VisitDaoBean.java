package restaurant.server.session;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import restaurant.server.entity.Visit;

@Stateless
@Local(VisitDaoLocal.class)
public class VisitDaoBean extends GenericDaoBean<Visit, Integer>
implements VisitDaoLocal{
	public int findUserVisitsNo(Integer userId){
        Query q = em.createNamedQuery("findUserVisitsNo");
        q.setParameter("userId", userId);

        int result = (int)q.getSingleResult();
        return result;
	}
	public int findUserVisitsNoByRestaurant(Integer userId, Integer restaurantId){
        Query q = em.createNamedQuery("findUserVisitsNoByRestaurant");
        q.setParameter("userId", userId);
        q.setParameter("restaurantId", restaurantId);
        
        int result = (int)q.getSingleResult();
        return result;
	}
}
