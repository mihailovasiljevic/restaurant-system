package restaurant.server.session;

import restaurant.server.entity.User;

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
}
