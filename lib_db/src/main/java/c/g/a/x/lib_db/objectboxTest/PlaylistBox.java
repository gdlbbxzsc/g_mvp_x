package c.g.a.x.lib_db.objectboxTest;

import c.g.a.x.lib_db.base.objectbox.BaseBox;
import io.objectbox.Property;

public class PlaylistBox extends BaseBox<Playlist> {

    @Override
    protected Property<Playlist> equalProperty4Select() {
        return Playlist_.name;
    }
}
