package restaurant.server.session;

import restaurant.server.entity.Reservation;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(ReservationDaoLocal.class)
public class ReservationDaoBean extends GenericDaoBean<Reservation, Integer>
implements ReservationDaoLocal{

}
