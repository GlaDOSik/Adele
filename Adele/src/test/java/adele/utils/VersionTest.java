package adele.utils;

import static org.junit.Assert.*;
import static adele.utils.Version.*;
import org.junit.Test;

public class VersionTest {
    
    @Test
    public void testFromInt() {
        Version fromIntVersion = Version.fromInt(1, 4, 5, VersionType.SNAPSHOT);
        assertEquals(1, fromIntVersion.getLevel_0());
        assertEquals(4, fromIntVersion.getLevel_1());
        assertEquals(5, fromIntVersion.getLevel_2());
        assertEquals(VersionType.SNAPSHOT, fromIntVersion.getType());
    }
    
    @Test
    public void testFromString() {
        Version fromStringVersion = Version.fromString("2.5.12-BETA");
        assertEquals(2, fromStringVersion.getLevel_0());
        assertEquals(5, fromStringVersion.getLevel_1());
        assertEquals(12, fromStringVersion.getLevel_2());
        assertEquals(VersionType.BETA, fromStringVersion.getType());
    }
    
    @Test
    public void testToString() {
        Version toStringVersion = Version.fromInt(1, 4, 3, VersionType.ALPHA);
        assertEquals("1.4.3-ALPHA", toStringVersion.toString());
    }
    
    @Test
    public void testCompareTo() {
        Version hv1 = Version.fromInt(2, 3, 5, VersionType.RELEASE);
        Version lv1 = Version.fromInt(2, 1, 5, VersionType.RELEASE);
        
        Version hv2 = Version.fromInt(1, 0, 0, VersionType.BETA);
        Version lv2 = Version.fromInt(1, 0, 0, VersionType.ALPHA);
        
        assertTrue("Level comparsion failed", hv1.compareTo(lv1) > 0);
        assertTrue("Level comparsion failed", lv1.compareTo(hv1) < 0);
        
        assertTrue("Type comparsion failed", hv2.compareTo(lv2) > 0);
        assertTrue("Type comparsion failed", lv2.compareTo(hv2) < 0);
        
        assertTrue("Equality comparsion failed", lv1.compareTo(lv1) == 0);
        assertTrue("Equality comparsion failed", hv2.compareTo(hv2) == 0);
        assertTrue("Ordering inconsistent with equals", hv2.equals(hv2));
    }
    
    @Test
    public void testHashCode() {
        Version v1 = Version.fromInt(3, 25, 8, VersionType.SNAPSHOT);
        Version v2 = Version.fromInt(3, 25, 8, VersionType.SNAPSHOT);
        
        assertEquals("Hash code mismatch", v1.hashCode(), v2.hashCode());
    }
    
}
