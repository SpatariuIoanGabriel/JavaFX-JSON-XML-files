package Repository;

import Domain.Identifiable;
import Exception.*;
public abstract class FileRepo<T extends Identifiable<U>, U> extends MemoryRepository<T,U> {
    protected String fileName;

    public FileRepo(String filename) {
        this.fileName = filename;
//        this.readFromFile();
    }

    protected abstract void readFromFile();
    protected abstract void writeFile();

    @Override
    public void addEntity(T entity) throws NoIdenticalEntities {
        if (listWithElements.containsKey(entity.getId())) {
            throw new NoIdenticalEntities("Entity already exists!");
        }
        listWithElements.put(entity.getId(), entity);
        writeFile();
    }

    @Override
    public void removeEntityById(U id) throws NoEntityFound {
        super.removeEntityById(id);
        writeFile();
    }

    @Override
    public void updateEntityById(U id, T newItem) throws NoEntityFound {
        super.updateEntityById(id, newItem);
        writeFile();
    }
}
