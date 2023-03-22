package com.example.swordfight.map;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.swordfight.graphics.SpriteSheet;

abstract class Tile {

    protected final Rect mapLocationRect;

    public Tile(Rect mapLocationRect) {
        this.mapLocationRect = mapLocationRect;
    }

    public enum TileType {
        GROUND_TILE,
        LAVA_TILE,
        LEFT_WALL_TILE,
        TOP_WALL_TILE,
        BOTTOM_WALL_TILE,
        RIGHT_WALL_TILE
    }

    public static Tile getTile(int idxTileType, SpriteSheet spriteSheet, Rect mapLocationRect) {

        switch(TileType.values()[idxTileType]) {
            case GROUND_TILE:
                return new GroundTile(spriteSheet, mapLocationRect);
            case LAVA_TILE:
                return new LavaTile(spriteSheet, mapLocationRect);
            case LEFT_WALL_TILE:
                return new LeftWallTile(spriteSheet, mapLocationRect);
            case TOP_WALL_TILE:
                return new TopWallTile(spriteSheet, mapLocationRect);
            case BOTTOM_WALL_TILE:
                return new BottomWallTile(spriteSheet, mapLocationRect);
            case RIGHT_WALL_TILE:
                return new RightWallTile(spriteSheet, mapLocationRect);
            default:
                return null;
        }

    }

    public abstract void draw(Canvas canvas);
}
