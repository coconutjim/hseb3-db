package domain;

import domain.realty_obj.RealtyObject;
import domain.realty_obj.RealtyObjectHibernateDao;

import java.util.List;

/***
 * Search
 */
public class SearchService {

    private static RealtyObjectHibernateDao roDao;

    public SearchService() {
        roDao = new RealtyObjectHibernateDao();
    }

    public List<RealtyObject> search(String city,
                                     Float minSquare,
                                     Float maxSquare,
                                     Float minPrice,
                                     Float maxPrice) {
        roDao.openCurrentSession();
        List<RealtyObject> list = roDao.findByCriteria(city,
                minSquare, maxSquare, minPrice, maxPrice);
        roDao.closeCurrentSession();
        return list;
    }
}
