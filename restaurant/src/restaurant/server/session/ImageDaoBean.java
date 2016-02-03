package restaurant.server.session;

import restaurant.server.entity.Image;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Stateless
@Local(ImageDaoLocal.class)
public class ImageDaoBean extends GenericDaoBean<Image, Integer>
implements ImageDaoLocal{

}
