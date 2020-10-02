package club.mcmodding.salem.util;

public class Util {

    public static int clamp(int num, int min, int max) {
        if (num < min) num = min;
        if (num > max) num = max;

        return num;
    }

    public static float clamp(float num, float min, float max) {
        if (num < min) num = min;
        if (num > max) num = max;

        return num;
    }

}
