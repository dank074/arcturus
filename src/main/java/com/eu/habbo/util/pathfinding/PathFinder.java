package com.eu.habbo.util.pathfinding;

import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomLayout;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.eu.habbo.habbohotel.rooms.RoomUnit;
import gnu.trove.set.hash.THashSet;

import java.awt.*;
import java.util.*;
import java.util.List;

public class PathFinder
{
    //Functions are moved to the RoomLayout class. These will be removed.

    @Deprecated()
    public static boolean squareInSquare(Rectangle outerSquare, Rectangle innerSquare)
    {
        if(outerSquare.x > innerSquare.x)
            return false;

        if(outerSquare.y > innerSquare.y)
            return false;

        if(outerSquare.x + outerSquare.width < innerSquare.x + innerSquare.width)
            return false;

        if(outerSquare.y + outerSquare.height < innerSquare.y + innerSquare.height)
            return false;

        return true;
    }
    @Deprecated()
    public static boolean pointInSquare(int x1, int y1, int x2, int y2, int pointX, int pointY)
    {
        return (pointX >= x1 && pointY >= y1) && (pointX <= x2 && pointY <= y2);
    }

    @Deprecated()
    public static boolean tilesAdjecent(RoomTile one, RoomTile two)
    {
        return tilesAdjecent(one.x, one.y, two.x, two.y);
    }

    @Deprecated()
    public static boolean tilesAdjecent(int x1, int y1, int x2, int y2)
    {
        return !(Math.abs(x1 - x2) > 1) && !(Math.abs(y1 - y2) > 1);
    }

    @Deprecated()
    public static RoomTile getSquareInFront(RoomLayout roomLayout, short x, short y, int rotation)
    {
        return getSquareInFront(roomLayout, x, y, rotation, (short)1);
    }

    @Deprecated()
    public static RoomTile getSquareInFront(RoomLayout roomLayout, short x, short y, int rotation, short offset)
    {
        rotation = rotation % 8;

        if(rotation == 0)
            return roomLayout.getTile(x, (short) (y - offset));
        else if(rotation == 1)
            return roomLayout.getTile((short) (x + offset), (short) (y - offset));
        else if(rotation == 2)
            return roomLayout.getTile((short) (x + offset), y);
        else if(rotation == 3)
            return roomLayout.getTile((short) (x + offset), (short) (y + offset));
        else if(rotation == 4)
            return roomLayout.getTile(x, (short) (y + offset));
        else if(rotation == 5)
            return roomLayout.getTile((short) (x - offset), (short) (y + offset));
        else if(rotation == 6)
            return roomLayout.getTile((short) (x - offset), y);
        else if(rotation == 7)
            return roomLayout.getTile((short) (x - offset), (short) (y - offset));
        else
            return roomLayout.getTile(x, y);
    }

    @Deprecated()
    public static List<RoomTile> getTilesAround(RoomLayout roomLayout, short x, short y)
    {
        List<RoomTile> tiles = new ArrayList<RoomTile>();

        for(int i = 0; i < 8; i++)
        {
            RoomTile t = getSquareInFront(roomLayout, x, y, i);
            if (t != null)
            {
                tiles.add(t);
            }
        }

        return tiles;
    }

    @Deprecated()
    public static Rectangle getSquare(int x, int y, int width, int length, int rotation)
    {
        rotation = (rotation % 8);

        if(rotation == 2 || rotation == 6)
        {
            return new Rectangle(x, y, length, width);
        }

        return new Rectangle(x, y, width, length);
    }

    @Deprecated()
    public static THashSet<RoomTile> getTilesAt(RoomLayout layout, short x, short y, int width, int length, int rotation)
    {
        THashSet<RoomTile> pointList = new THashSet<RoomTile>();

        if(rotation == 0 || rotation == 4)
        {
            for (short i = x; i <= (x + (width - 1)); i++)
            {
                for (short j = y; j <= (y + (length - 1)); j++)
                {
                    RoomTile t = layout.getTile(i, j);

                    if (t != null)
                    {
                        pointList.add(t);
                    }
                }
            }
        }
        else if(rotation == 2 || rotation == 6)
        {
            for (short i = x; i <= (x + (length - 1)); i++)
            {
                for (short j = y; j <= (y + (width - 1)); j++)
                {
                    RoomTile t = layout.getTile(i, j);

                    if (t != null)
                    {
                        pointList.add(t);
                    }
                }
            }
        }

        return pointList;
    }

    @Deprecated()
    public static boolean tilesAdjecent(RoomTile tile, RoomTile comparator, int width, int length, int rotation)
    {
        Rectangle rectangle = getSquare(comparator.x, comparator.y, width, length, rotation);
        rectangle = new Rectangle(rectangle.x - 1, rectangle.y -1, rectangle.width + 2, rectangle.height + 2);

        return rectangle.contains(tile.x, tile.y);
    }
}
