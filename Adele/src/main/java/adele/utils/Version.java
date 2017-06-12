package adele.utils;

import java.util.Objects;
import lombok.Getter;

/**
 * Immutable version object for easy version comparsion. Uses three version
 * levels with type, for example 1.0.0-SNAPSHOT.
 *
 * @author Ludek
 */
public class Version implements Comparable<Version> {

    private Integer cachedHash = null;
    
    // x.0.0
    @Getter
    private final int level_0;

    // 0.x.0
    @Getter
    private final int level_1;

    // 0.0.x
    @Getter
    private final int level_2;
    
    @Getter
    private final VersionType type;

    public enum VersionType {
        SNAPSHOT(0), ALPHA(1), BETA(2), RELEASE(3);
        
        private final int priority;
        
        private VersionType(int priority) {
            this.priority = priority;
        }
    }

    private Version(int l0, int l1, int l2, VersionType type) {
        level_0 = l0;
        level_1 = l1;
        level_2 = l2;
        this.type = type;
    }

    /**
     * Get immutable version object from string in format
     * int.int.int-VersionType. String can contain spaces. It uses internally
     * the public factory fromInt.
     *
     * Example strings: 0.3.5-SNAPSHOT, 1.3.2-BETA
     *
     * @param version string in parsable format
     * @return
     */
    public static Version fromString(String version) throws IllegalArgumentException {
        String withoutSpaces = version.replace(" ", "");
        String[] split = withoutSpaces.split("-");

        VersionType type;
        switch (split.length) {
            case 1:
                type = VersionType.RELEASE; // to support Maven version format
                break;
            case 2:
                try {
                    type = VersionType.valueOf(split[1]);
                } catch (IllegalArgumentException ex) {
                    throw new IllegalArgumentException("Cannot create Version. Unknown version type " + split[1]);
                }
                break;
            default:
                throw new IllegalArgumentException("Cannot create Version. Unknown format.");
        }

        String[] levelsSplit = split[0].split("\\.");
        if (levelsSplit.length != 3) {
            throw new IllegalArgumentException("Cannot create Version. Invalid number of levels.");
        }
        int level_0, level_1, level_2;
        try {
            level_0 = Integer.parseInt(levelsSplit[0]);
            level_1 = Integer.parseInt(levelsSplit[1]);
            level_2 = Integer.parseInt(levelsSplit[2]);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Cannot create Version. Invalid level number.");
        }
        return new Version(level_0, level_1, level_2, type);
    }

    @Override
    public String toString() {
        return level_0 + "." + level_1 + "." + level_2 + "-" + type.toString();
    }

    /**
     * Get immutable version object from integers and VersionType.
     *
     * @param l0 l0.int.int-VersionType
     * @param l1 int.l1.int-VersionType
     * @param l2 int.int.l2-VersionType
     * @param type int.int.int-type
     * @return
     */
    public static Version fromInt(int l0, int l1, int l2, VersionType type) {
        return new Version(l0, l1, l2, type);
    }

    // TODO - consult with effective java
    @Override
    public boolean equals(Object object) {
        if (object == null) {
            throw new NullPointerException();
        }
        if (!(object instanceof Version)) {
            throw new ClassCastException("Cannot compare equality against different class than Version.");
        }
        Version version = (Version) object;

        if (level_2 != version.level_2) {
            return false;
        }
        if (level_1 != version.level_1) {
            return false;
        }
        if (!type.equals(version.type)) {
            return false;
        }
        if (level_0 != version.level_0) {
            return false;
        }
        return true;
    }

    // TODO - consult with effective java
    @Override
    public int hashCode() {
        if (cachedHash == null) {
            int hash = 7;
            hash = 29 * hash + this.level_0;
            hash = 29 * hash + this.level_1;
            hash = 29 * hash + this.level_2;
            hash = 29 * hash + Objects.hashCode(this.type);
            cachedHash = hash;
        }
        return cachedHash;
    }

    @Override
    public int compareTo(Version o) {
        if (o == null) {
            throw new NullPointerException();
        }
        int levelDiff = level_0 - o.level_0;
        if (levelDiff != 0) {
            return levelDiff;
        }
        levelDiff = level_1 - o.level_1;
        if (levelDiff != 0) {
            return levelDiff;
        }
        levelDiff = level_2 - o.level_2;
        if (levelDiff != 0) {
            return levelDiff;
        }
        return type.priority - o.getType().priority;
    }

    

}
