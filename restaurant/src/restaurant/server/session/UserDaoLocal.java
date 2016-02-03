package restaurant.server.session;

import restaurant.server.entity.User;


public interface UserDaoLocal extends GenericDaoLocal<User, Integer>{
    public User findUserByEmail(String email);
}
