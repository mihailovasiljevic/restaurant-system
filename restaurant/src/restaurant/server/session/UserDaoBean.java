package restaurant.server.session;

import restaurant.server.entity.User;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;


@Stateless
@Local(UserDaoLocal.class)
public class UserDaoBean extends GenericDaoBean<User, Integer>
        implements UserDaoLocal{

    public User findUserByEmail(String email){
        Query q = em.createNamedQuery("findUserByEmail");
        q.setParameter("userEmail", email);

        User result = (User)q.getSingleResult();
        return result;
    }
    
    public List<User> findRestaurantMenagerBySystemMenagerId(Integer userId){
        Query q = em.createNamedQuery("findRestaurantMenagerBySystemMenagerId");
        q.setParameter("userId", userId);
        
        @SuppressWarnings("unchecked")
		List<User> result = q.getResultList();
        return result;
    }
}
