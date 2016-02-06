package restaurant.server.session;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import restaurant.server.entity.Vozilo;

@Stateless
@Local(VoziloDaoLocal.class)
public class VoziloDaoBean extends GenericDaoBean<Vozilo, Integer> implements VoziloDaoLocal {
	
	public List<Vozilo> findVozilaSaKlimom() {
		Query q = em.createNamedQuery("findVozilaSaKlimom");
		@SuppressWarnings("unchecked")
		List<Vozilo> result = (List<Vozilo>) q.getResultList();
		return result;
	}

	public void remove(Vozilo v) {
		v = em.merge(v);

		v.getGorivo().getVozila().remove(v);
		v.getMenjac().getVozila().remove(v);

		em.remove(v);
	}

}
