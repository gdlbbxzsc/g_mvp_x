package c.g.a.x.lib_db.base.objectbox;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import c.g.a.x.lib_db.application.DBHelper;
import c.g.a.x.lib_db.base.BaseDaoModel;
import io.objectbox.Box;

public abstract class BaseBox<T extends BaseDaoModel> {

    protected Box<T> box;

    public BaseBox() {
        box = DBHelper.getBoxStore().boxFor((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public void insert(T vo) {
        box.put(vo);
    }

    public void insertAll(T... vos) {
        box.put(vos);
    }

    public void insertAll(List<T> vos) {
        box.put(vos);
    }

    public void delete(T vo) {
        box.remove(vo);
    }

    public void deleteAll() {
        box.removeAll();
    }

    public List<T> selectAll() {
        return box.query().build().find();
    }

    public abstract T select(String str);
}
