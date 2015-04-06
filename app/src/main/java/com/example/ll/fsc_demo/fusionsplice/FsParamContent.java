package com.example.ll.fsc_demo.fusionsplice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ll on 4/6/15.
 */
public class FsParamContent {
    private static List<FsParamFile> ITEMS = new ArrayList<>();
    private static Map<Integer, FsParamFile> ITEM_MAP = new HashMap<>();
    private static int ACT_ID;

    static {
        // Add 3 sample items.
        addItem(new FsParamFile(2, "test", 3, 4));
        addItem(new FsParamFile(4, "hello", 5, 10));
        addItem(new FsParamFile(7, "haha", 9, 6));
        addItem(new FsParamFile(100, "no no no", 3, 7));
        ACT_ID = 7;
    }

    private static void addItem(FsParamFile item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static int getCount() {
        return ITEMS.size();
    }

    public static FsParamFile getItem(int position) {
        return ITEMS.get(position);
    }

    public static String getItemId(int position) {
        return String.valueOf(getItem(position).id);
    }

    public static boolean isActivated(int position) {
        return (getItem(position).id == ACT_ID);
    }
}
