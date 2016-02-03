package restaurant.server.session;

import restaurant.server.entity.SystemMenager;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(SystemMenagerDaoLocal.class)
public class SystemMenagerDaoBean extends GenericDaoBean<SystemMenager, Integer>
implements SystemMenagerDaoLocal{

}
