package restaurant.server.session;

import java.util.List;

import restaurant.server.entity.Vozilo;

public interface VoziloDaoLocal extends GenericDaoLocal<Vozilo, Integer> {

	public List<Vozilo> findVozilaSaKlimom();

}
