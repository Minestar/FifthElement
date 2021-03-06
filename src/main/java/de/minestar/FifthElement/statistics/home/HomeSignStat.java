/*
 * Copyright (C) 2012 MineStar.de 
 * 
 * This file is part of FifthElement.
 * 
 * FifthElement is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 * 
 * FifthElement is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with FifthElement.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.minestar.FifthElement.statistics.home;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.Location;

import de.minestar.FifthElement.core.Core;
import de.minestar.minestarlibrary.stats.Statistic;
import de.minestar.minestarlibrary.stats.StatisticType;

public class HomeSignStat implements Statistic {

    private String playerName;
    private Location signLocation;
    private Timestamp date;

    public HomeSignStat() {
        // EMPTY CONSTRUCTOR FOR REFLECTION ACCESS
    }

    public HomeSignStat(String playerName, Location signLocation) {
        this.playerName = playerName;
        this.signLocation = signLocation;
        this.date = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public String getPluginName() {
        return Core.NAME;
    }

    @Override
    public String getName() {
        return "HomeSignUse";
    }

    @Override
    public LinkedHashMap<String, StatisticType> getHead() {

        LinkedHashMap<String, StatisticType> head = new LinkedHashMap<String, StatisticType>();

        head.put("player", StatisticType.STRING);
        head.put("x", StatisticType.INT);
        head.put("y", StatisticType.INT);
        head.put("z", StatisticType.INT);
        head.put("world", StatisticType.STRING);
        head.put("date", StatisticType.DATETIME);

        return head;

    }

    @Override
    public Queue<Object> getData() {

        Queue<Object> data = new LinkedList<Object>();

        data.add(playerName);
        data.add(signLocation.getBlockX());
        data.add(signLocation.getBlockY());
        data.add(signLocation.getBlockZ());
        data.add(signLocation.getWorld().getName());
        data.add(date);

        return data;

    }

}
