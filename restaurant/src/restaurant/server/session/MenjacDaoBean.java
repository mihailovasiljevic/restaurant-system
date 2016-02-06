package restaurant.server.session;

import javax.ejb.Local;
import javax.ejb.Stateless;

import restaurant.server.entity.Menjac;

@Stateless
@Local(MenjacDaoLocal.class)
public class MenjacDaoBean extends GenericDaoBean<Menjac, Integer> implements MenjacDaoLocal {

}
