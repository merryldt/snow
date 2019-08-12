package java_base;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author liudongting
 * @date 2019/8/6 20:07
 */
public class javaAuto {
    static final Unsafe unsafe = getUnsafe();
    static final boolean is64bit = true;

    public static void main(String... args) {

        int i=1;
        int j=i++;
        System.out.println("-------------------------");
        System.out.println("----");
        printAddresses("i", i);

        System.gc();
        System.out.println("----");
        printAddresses("j", ++j);

        System.gc();
        System.out.println("----");
        printAddresses("i++", i++);
    }

    public static void printAddresses(String label, Object... objects) {
        System.out.print(label + ": 0x");
        long last = 0;
        int offset = unsafe.arrayBaseOffset(objects.getClass());
        int scale = unsafe.arrayIndexScale(objects.getClass());
        switch (scale) {
            case 4:
                long factor = is64bit ? 8 : 1;
                final long i1 = (unsafe.getInt(objects, offset) & 0xFFFFFFFFL) * factor;
                System.out.print(Long.toHexString(i1));
                last = i1;
                for (int i = 1; i < objects.length; i++) {
                    final long i2 = (unsafe.getInt(objects, offset + i * 4) & 0xFFFFFFFFL) * factor;
                    if (i2 > last)
                        System.out.print(", +" + Long.toHexString(i2 - last));
                    else
                        System.out.print(", -" + Long.toHexString( last - i2));
                    last = i2;
                }
                break;
            case 8:
                throw new AssertionError("Not supported");
        }
        System.out.println();
    }

    private static Unsafe getUnsafe() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

}
