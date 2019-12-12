package c.g.a.x.lib_db.objectbox;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Playlist {
    @Id(assignable = true)
    public long id;

    public String name;
}
