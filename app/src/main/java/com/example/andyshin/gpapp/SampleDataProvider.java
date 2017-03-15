package com.example.andyshin.gpapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Andy Shin on 3/8/2017.
 */

public class SampleDataProvider {

    public static List<DataItem> dataItemList;
    public static Map<String, DataItem> dataItemMap;

    static{
        dataItemList = new ArrayList<>();
        dataItemMap = new HashMap<>();

        addItem(new DataItem(null, "Berserk and The Band of the Hawk Review (Playstation 4)", "Edible Herb",
                "It's not the Nocturne review but it is pretty much the next best thing right? Stay tuned in the coming weeks for some SMT goodness. \n",
                1, "Annual", "berserk_review.png"));

        addItem(new DataItem(null, "The Legend of Heroes Trails of Cold Steel 2 Review", "Herb",
                "Been way too long since I've posted any content, but fret not I will be back soon in the near future with some SMT videos!",
                2, "Annual", "cs2.png"));

        addItem(new DataItem(null, "Digimon Story Cyber Sleuth Review + Update", "Vegetable",
                "I'm back from Japan and with a brand new review of the recent Digimon Story Cyber Sleuth. And no, I haven't forgotten about the SMT retrospective, part 3 should be up within the next week.",
                3, "Perennial", "digital_cyber.png"));

        addItem(new DataItem(null, "Persona 2 Innocent Sin & Eternal Punishment Review (Shin Megami Tensei Retrospective Part 3)", "Vine",
                "The long overdue review of the entire Persona 2 game duology. The next few videos will be focusing on the recently released Fire Emblem Fates. Hopefully I'm back on track for a proper video release schedule.",
                4, "Perennial", "persona_2.png"));

        addItem(new DataItem(null, "Top 10 RPGs of 2016 (5-1)", "Vine",
                "My 5 favorite RPGs of 2016! I know missed quite a few, so be sure to let me know of your own RPG recommendations of 2016 in the comments section below!",
                5, "Perennial", "top5rpgs.png"));

    }

    private static void addItem(DataItem item) {
        dataItemList.add(item);
        dataItemMap.put(item.getItemId(), item);
    }

}
