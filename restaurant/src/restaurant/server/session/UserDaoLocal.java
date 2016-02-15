package restaurant.server.session;



import java.util.List;

import restaurant.server.entity.User;


public interface UserDaoLocal extends GenericDaoLocal<User, Integer>{
    public User findUserByEmail(String email);
    public List<User> findRestaurantMenagerBySystemMenagerId(Integer u);
}
