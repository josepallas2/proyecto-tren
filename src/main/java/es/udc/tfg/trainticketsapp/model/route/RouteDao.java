package es.udc.tfg.trainticketsapp.model.route;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.tfg.trainticketsapp.model.route.Route.WeekDay;

public interface RouteDao extends GenericDao<Route, Long> {

	public Route findByName(String name) throws InstanceNotFoundException;

	public List<Route> findRoutesByDay(WeekDay day, List<Long> ids);
}
