package restaurant.server.session;

import restaurant.server.entity.Friend;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(FriendDaoLocal.class)
public class FriendDaoBean extends GenericDaoBean<Friend, Integer>
implements FriendDaoLocal{

}
