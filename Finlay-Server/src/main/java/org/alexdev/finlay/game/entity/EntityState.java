package org.alexdev.finlay.game.entity;

import org.alexdev.finlay.game.pathfinder.Position;
import org.alexdev.finlay.game.player.PlayerDetails;
import org.alexdev.finlay.game.room.Room;
import org.alexdev.finlay.game.room.RoomUserStatus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntityState {
    private int entityId;
    private int instanceId;
    private PlayerDetails details;
    private EntityType entityType;
    private Room room;
    private Position position;
    private Map<String, RoomUserStatus> statuses;

    public EntityState(int entityId, int instanceId, PlayerDetails details, EntityType entityType, Room room, Position position, Map<String, RoomUserStatus> statuses) {
        this.entityId = entityId;
        this.instanceId = instanceId;
        this.details = details;
        this.entityType = entityType;
        this.room = room;
        this.position = position;
        this.statuses = new ConcurrentHashMap<>(statuses);
    }

    public int getInstanceId() {
        return instanceId;
    }

    public Position getPosition() {
        return position;
    }

    public Map<String, RoomUserStatus> getStatuses() {
        return statuses;
    }

    public int getEntityId() {
        return entityId;
    }

    public PlayerDetails getDetails() {
        return details;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Room getRoom() {
        return room;
    }
}
