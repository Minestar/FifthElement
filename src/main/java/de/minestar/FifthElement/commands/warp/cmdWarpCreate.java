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

package de.minestar.FifthElement.commands.warp;

import org.bukkit.entity.Player;

import com.bukkit.gemo.utils.UtilPermissions;

import de.minestar.FifthElement.core.Core;
import de.minestar.FifthElement.core.Settings;
import de.minestar.FifthElement.data.WarpCounter;
import de.minestar.FifthElement.statistics.warp.WarpCreateStat;
import de.minestar.minestarlibrary.stats.StatisticHandler;
import de.minestar.minestarlibrary.commands.AbstractCommand;
import de.minestar.minestarlibrary.utils.PlayerUtils;

public class cmdWarpCreate extends AbstractCommand {

    public cmdWarpCreate(String syntax, String arguments, String node) {
        super(Core.NAME, syntax, arguments, node);
    }

    @Override
    public void execute(String[] args, Player player) {

        // CHECK IF WARP CAN PLACED IN THIS WORLD
        if (!Core.warpManager.isWarpAllowedIn(player.getWorld()) && !UtilPermissions.playerCanUseCommand(player, "fifthelement.create.warps.goldgrube")) {
            PlayerUtils.sendError(player, pluginName, "Du kannst auf dieser Welt keine Warps erstellen!");
            return;
        }
        String warpName = args[0];

        if (!Core.warpManager.isValidName(warpName)) {
            PlayerUtils.sendError(player, pluginName, "Der Warpname '" + warpName + "' ist ung�ltig!");
            PlayerUtils.sendError(player, pluginName, "Der Warpname muss min. " + Settings.getMinWarpnameSize() + " Zeichen und maximal " + Settings.getMaxWarpnameSize() + " Zeichen lang sein.");
            return;
        }

        // IS KEY WORD ( SUB COMMAND OF WARP)
        if (Core.warpManager.isKeyWord(warpName)) {
            PlayerUtils.sendError(player, pluginName, "Der Warpname '" + warpName + "' ist ein Schlüsselwort und kann nicht als Warpname benutzt werden.");
            return;
        }

        // PLAYER HAS TOO MANY WARPS
        if (!Core.warpManager.canCreatePrivate(player.getName())) {
            PlayerUtils.sendError(player, pluginName, "Du kannst keinen weiteren privaten Warp erstellen!");
            return;
        }
        // NO DUPLICATE WARPS
        if (Core.warpManager.isWarpExisting(warpName)) {
            PlayerUtils.sendError(player, pluginName, "Es existiert bereits ein Warp names '" + warpName + "' !");
            return;
        }

        // CREATE THE NEW WARP
        Core.warpManager.createWarp(warpName, player);
        PlayerUtils.sendSuccess(player, pluginName, "Der Warp '" + warpName + "' wurde erstellt!");

        // PRINT OUT REST PRIVATE WARPS TO CREATE
        WarpCounter counter = Core.warpManager.getWarpCounter(player.getName());
        PlayerUtils.sendInfo(player, "Du kannst noch " + (Settings.getMaxPrivateWarps(player.getName()) - counter.getPrivateWarps()) + " private Warps erstellen.");

        // FIRE STATISTIC
        StatisticHandler.handleStatistic(new WarpCreateStat(player.getName(), warpName));
    }
}
