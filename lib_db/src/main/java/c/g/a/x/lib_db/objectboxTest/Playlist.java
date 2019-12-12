package c.g.a.x.lib_db.objectboxTest;


import c.g.a.x.lib_db.base.BaseDaoModel;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Playlist extends BaseDaoModel {
    @Id(assignable = true)
    public long id;

    public String name;
}
