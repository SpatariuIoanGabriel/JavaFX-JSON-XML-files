package Repository;

import Domain.Identifiable;
import Exception.*;
import java.util.HashMap;
import java.util.Map;

public class MemoryRepository<T extends Identifiable<?>, U> implements IRepository<T,U> {
    public Map<U, T> listWithElements = new HashMap<>();

    @Override
    public void addEntity(T entity) throws NoIdenticalEntities {
        if(listWithElements.containsKey(entity.getId()))
            throw new NoIdenticalEntities("There is another entity with the id = " + entity.getId() + ".");
        listWithElements.put((U) entity.getId(), entity);
    }

    @Override
    public void removeEntityById(U id) throws NoEntityFound {
        if (listWithElements.containsKey(id)) {
            listWithElements.remove(id);
        } else
            throw new NoEntityFound("The entity with the id = " + id + " does not exist.");
    }

    @Override
    public T getEntityById(U id) throws NoEntityFound {
        if (listWithElements.containsKey(id)) {
            return listWithElements.get(id);
        } else {
            throw new NoEntityFound("The entity with the id = " + id + " does not exist.");
        }
    }

    @Override
    public Iterable<T> getAllEntities() {
        return listWithElements.values();
    }

    @Override
    public void updateEntityById(U id, T newEntity) throws NoEntityFound{
        if(listWithElements.containsKey(id)) {
            listWithElements.replace(id, newEntity);
        } else {
            throw new NoEntityFound("The entity with the id = " + id + " does not exist.");
        }
    }
}
