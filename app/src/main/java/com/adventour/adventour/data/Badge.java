package com.adventour.adventour.data;

import android.graphics.Bitmap;

import java.util.HashMap;

/**
 * Created by FIXIT on 2019-02-09.
 */

public class Badge extends Document {

    public Badge() {
        put("badgeId", 0l);
        put("desc", "Empty Badge");
//        put("img", Bitmap.createBitmap(128, 128, Bitmap.Config.RGB_565));
    }

    public Badge(long badgeId, String description, Bitmap badge) {
        put("badgeId", badgeId);
        put("desc", description);
//        put("img", badge);
    }

    public Badge(HashMap<String, Object> map) {
        this();
        putAll(map);
    }

    public long getBadgeId() {
        return (long) get("badgeId");
    }
    public void setBadgeId(long badgeId) {
        put("badgeId", badgeId);
    }

    public String getDescription() {
        return (String) get("desc");
    }
    public void setDescription(String desc) {
        put("desc", desc);
    }

    public Bitmap getBitmap() {
        return (Bitmap) get("img");
    }
    public void setBitmap(Bitmap bm) {
        put("img", bm);
    }

}
