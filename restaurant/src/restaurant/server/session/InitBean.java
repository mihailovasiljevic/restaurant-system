package restaurant.server.session;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import restaurant.server.entity.City;
import restaurant.server.entity.Country;
import restaurant.server.entity.Street;


@Stateless
@Remote(Init.class)
public class InitBean implements Init{

	@PersistenceContext(unitName = "restaurant-system")
	EntityManager em;
	
	public void init() {
		Set<City> citySet = new HashSet<City>();
		Country cntr = new Country("Srbija", citySet);
		City city = new City("Novi Sad", cntr, new HashSet<Street>());
		cntr.add(city);
		
		CountryDaoLocal cdl = new CountryDaoBean();
		cdl.persist(cntr);
		
		CityDaoLocal cydl = new CityDaoBean();
		cydl.persist(city);

	}

}
