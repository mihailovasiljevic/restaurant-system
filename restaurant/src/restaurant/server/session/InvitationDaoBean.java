package restaurant.server.session;

import restaurant.server.entity.Invitation;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(InvitationDaoLocal.class)
public class InvitationDaoBean extends GenericDaoBean<Invitation, Integer>
implements InvitationDaoLocal{

}
