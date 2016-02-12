package restaurant.externals.mapper;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class GenericJSONMapper<T> implements IGenericJSONMapper<T> {

	private Class<T> object;
	private ObjectMapper mapper = new ObjectMapper();
	
	@SuppressWarnings("unchecked")
	public GenericJSONMapper() {
		// TODO Auto-generated constructor stub
		object = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	@Override
	public Class<T> getObjectType() {
		// TODO Auto-generated method stub
		return object;
	}

	@Override
	public String objectToJSON(T object) {
		// TODO Auto-generated method stub
		String jsonString;
		try {
			jsonString = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			jsonString = null;
		}
		return jsonString;
	}

	@Override
	public T JSONToObject(String json) {
		// TODO Auto-generated method stub
		T object;
		try {
			object = mapper.readValue(json, getObjectType());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			object = null;
		}
		return object;
	}

}
