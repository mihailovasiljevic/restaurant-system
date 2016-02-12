package restaurant.externals.mapper;

import java.lang.reflect.ParameterizedType;

import restaurant.server.entity.Address;
import restaurant.server.entity.Street;

public class TestMapper {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Address address = new Address("112", new Street(), null, null);
			AddressMapper am = new AddressMapper();
			System.out.println(am.objectToJSON(address));
			String addressO = "{\"id\":null,\"brojUUlici\":\"112\",\"street\":{\"id\":null,\"name\":null,\"city\":null,\"addresses\":[]},\"restaurants\":null,\"users\":null}";
			Address address2 = am.JSONToObject(addressO);
			
			if(am.objectToJSON(address).equals(am.objectToJSON(address2))){
				System.out.println("Uspesno!");
			}
	}

}
