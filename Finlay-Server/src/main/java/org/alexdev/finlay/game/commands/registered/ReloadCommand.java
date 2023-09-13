package org.alexdev.finlay.game.commands.registered;

import org.alexdev.finlay.game.catalogue.CatalogueManager;
import org.alexdev.finlay.game.commands.Command;
import org.alexdev.finlay.game.entity.Entity;
import org.alexdev.finlay.game.entity.EntityType;
import org.alexdev.finlay.game.fuserights.Fuseright;
import org.alexdev.finlay.game.item.ItemManager;
import org.alexdev.finlay.game.player.Player;
import org.alexdev.finlay.game.player.PlayerManager;
import org.alexdev.finlay.game.room.models.RoomModelManager;
import org.alexdev.finlay.game.texts.TextsManager;
import org.alexdev.finlay.messages.outgoing.catalogue.CATALOGUE_PAGES;
import org.alexdev.finlay.messages.outgoing.user.ALERT;
import org.alexdev.finlay.util.config.GameConfiguration;
import org.alexdev.finlay.util.config.writer.GameConfigWriter;

public class ReloadCommand extends Command {
    @Override
    public void addPermissions() {
        this.permissions.add(Fuseright.ADMINISTRATOR_ACCESS);
    }

    @Override
    public void addArguments() {
        this.arguments.add("component");
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;

        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        String component = args[0];
        String componentName = null;

        if (component.equalsIgnoreCase("catalogue")
                || component.equalsIgnoreCase("shop")
                || component.equalsIgnoreCase("items")) {
            ItemManager.reset();
            CatalogueManager.reset();

            // Regenerate collision map with proper height differences (if they changed).
            player.getRoomUser().getRoom().getMapping().regenerateCollisionMap();

            // Resend the catalogue index every 15 minutes to clear page cache
            for (Player p : PlayerManager.getInstance().getPlayers()) {
                p.send(new CATALOGUE_PAGES(
                        CatalogueManager.getInstance().getPagesForRank(p.getDetails().getRank(), p.getDetails().hasClubSubscription())
                ));
            }

            componentName = "Catalogue and item definitions";
        }

        if (component.equalsIgnoreCase("texts")) {
            TextsManager.reset();
            componentName = "Texts";
        }

        if (component.equalsIgnoreCase("models")) {
            RoomModelManager.reset();
            componentName = "Room models";
        }

        if (component.equalsIgnoreCase("settings") ||
                component.equalsIgnoreCase("config")) {

            GameConfiguration.reset(new GameConfigWriter());
            componentName = "Game settings";
        }

        if (componentName != null) {
            player.send(new ALERT(componentName + " has been reloaded."));
        } else {
            player.send(new ALERT("You did not specify which component to reload!<br>You may reload either the catalogue/shop/items, models, texts or game settings."));
        }
    }

    @Override
    public String getDescription() {
        return "Refresh the settings/items/texts";
    }
}