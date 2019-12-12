package c.g.a.x.lib_db.base.objectbox;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import c.g.a.x.lib_db.application.DBHelper;
import c.g.a.x.lib_db.base.BaseDaoModel;
import io.objectbox.Box;
import io.objectbox.Property;

public abstract class BaseBox<T extends BaseDaoModel> {

    protected Box<T> box;

    public BaseBox() {
        box = DBHelper.getBoxStore().boxFor((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public void insert(T vos) {
        box.put(vos);
    }

    public void insert(T... vos) {
        box.put(vos);
    }

    public void insert(List<T> vos) {
        box.put(vos);
    }

    public void delete(T vos) {
        box.remove(vos);
    }

    public void delete(T... vos) {
        box.remove(vos);
    }

    public void delete(List<T> vos) {
        box.remove(vos);
    }

    public void deleteAll() {
        box.removeAll();
    }

    public List<T> select() {
        return box.query().build().find();
    }

    public T select(String value) {
        return box.query().equal(equalProperty4Select(), value).build().findUnique();
    }

    protected abstract Property<T> equalProperty4Select();
}
