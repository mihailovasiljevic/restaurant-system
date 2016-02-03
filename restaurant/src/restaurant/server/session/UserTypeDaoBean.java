package restaurant.server.session;

import restaurant.server.entity.UserType;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(UserTypeDaoLocal.class)
public class UserTypeDaoBean extends GenericDaoBean<UserType, Integer>
implements UserTypeDaoLocal {

}
