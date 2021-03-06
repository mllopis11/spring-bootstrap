package mike.bootstrap.utilities.helpers;

import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ByteUnit represents bit size at given unit granularity and provides utility
 * methods to manipulate bytes representations.
 * 
 * @author Mike (2021-02)
 *
 */
public enum ByteUnit {

	TERA(1099511627776L, "tb"),
    GIGA(1073741824L, "gb"),
    MEGA(1048576L, "mb"),
    KILO(1024L, "kb"),
    BYTE(1L, "b");
    
	public static final List<ByteUnit> UNITS = List.of(TERA, GIGA, MEGA, KILO, BYTE);
	
	private static final Pattern sizePattern = Pattern.compile("(\\d{1,18})((b|k|kb|m|mb|g|gb|t|tb))");
	
    private final long base;
    private final String unit;
    private final long maxSize;
    
    private ByteUnit(long base, String unit) {
        this.base = base;
        this.unit = unit;
        this.maxSize = Long.MAX_VALUE / base;
    }
    
    /**
     * @return base granularity value (ex.: 1024 for KILO)
     */
    public long base() {
        return base;
    }
    
    /**
     * @param bytes size in bytes
     * @return size at the unit granularity 
     */
    public double size(long bytes) {
        return bytes >= 0 ? bytes / Double.valueOf(base) : 0;
    }
    
    /**
     * Compute bytes corresponding to the given size pattern. An IllegalArgumentException
     * is thrown if the argument is invalid or if the value exceeds the max. value for the
     * given unit. The unit max. value is {@link java.lang.Long#MAX_VALUE} divide by the
     * unit bytes.
     *  
     * @param sizeWithUnit a string representation of the expected size (ex. 2k)
     * @return  computed bytes according to the granularity unit (ex.: 2k returns 2048)
     */
    public static long from(String sizeWithUnit) {
        String sizeUnit = Utils.trim(sizeWithUnit).toLowerCase();
        
        Matcher matcher = sizePattern.matcher(sizeUnit);
        
        if ( ! matcher.matches() ) {
            throw new IllegalArgumentException(
                    String.format("'%s' does not match pattern '%s'", sizeWithUnit, sizePattern));
        }
        
        final long sValue = Long.parseLong(matcher.group(1));
        String sUnit = matcher.group(2).endsWith("b") ? matcher.group(2) : matcher.group(2) + "b";
       
        return UNITS.stream()
                    .filter( v -> sUnit.equals(v.unit) && sValue <= v.maxSize)
                    .mapToLong( v -> sValue * v.base)
                    .findAny()
                    .orElseThrow( () -> new IllegalArgumentException("size exceeds the max. size for the unit") );
    }
    
    /**
     * @param bytes size in bytes
     * @return the best representation for the given size (ex.: 6.9kb for 7069 bytes) 
     */
    public static String format(long bytes) {
    	
    	return UNITS.stream()
    				.filter( v -> bytes >= v.base)
    				.map( v -> new DecimalFormat("#.##" + v.unit).format(v.size(bytes)))
    				.findAny()
    				.orElse("0b");
    }
}
