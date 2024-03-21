package Repository;

import Domain.Identifiable;
import Exception.*;

public interface IRepository<T extends Identifiable, U> {
    public void addEntity(T entity) throws NoIdenticalEntities;
    public void removeEntityById(U id) throws NoEntityFound;
    public T getEntityById(U id) throws NoEntityFound;
    public Iterable<T> getAllEntities();
    public void updateEntityById(U id, T newEntity) throws NoEntityFound;
}
