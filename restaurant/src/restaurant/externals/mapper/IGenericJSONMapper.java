package restaurant.externals.mapper;
import java.io.Serializable;

public interface IGenericJSONMapper <T>{
	
	public Class<T> getObjectType();

	public String objectToJSON(T object);
	
	public T JSONToObject(String json);
}
