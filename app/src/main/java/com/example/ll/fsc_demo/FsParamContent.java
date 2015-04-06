package com.example.ll.fsc_demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ll on 4/6/15.
 */
public class FsParamContent {
    public static List<FsParamFile> ITEMS = new ArrayList<>();
    public static Map<String, FsParamFile> ITEM_MAP = new HashMap<>();
    public static String ACT_ID;

    static {
        // Add 3 sample items.
        addItem(new FsParamFile("2", "test", 3, 4));
        addItem(new FsParamFile("4", "hello", 5, 10));
        addItem(new FsParamFile("7", "haha", 9, 6));
        ACT_ID = "2";
    }

    private static void addItem(FsParamFile item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
}
