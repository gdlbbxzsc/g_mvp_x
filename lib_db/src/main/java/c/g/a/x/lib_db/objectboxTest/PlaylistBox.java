package c.g.a.x.lib_db.objectboxTest;

import c.g.a.x.lib_db.base.objectbox.BaseBox;

public class PlaylistBox extends BaseBox<Playlist> {

    @Override
    public Playlist select(String str) {
        return box.query().equal(Playlist_.name, str).build().findUnique();
    }
}
