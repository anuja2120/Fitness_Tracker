public class ActivityTracker {
    private static final double CALORIES_PER_STEP = 0.04;
    private static final double CALORIES_PER_DISTANCE_KM = 0.1;
    private static final double CALORIES_PER_DISTANCE_MI = 0.16;

    public static double calculateCaloriesBurned(int steps, double distance, String unit, String activity) {
        double caloriesPerDistance = unit.equals("Kilometers") ? CALORIES_PER_DISTANCE_KM : CALORIES_PER_DISTANCE_MI;
        double activityMultiplier = getActivityMultiplier(activity);
        return ((steps * CALORIES_PER_STEP) + (distance * caloriesPerDistance)) * activityMultiplier;
    }

    private static double getActivityMultiplier(String activity) {
        switch (activity) {
            case "Running":
                return 1.5;
            case "Cycling":
                return 1.2;
            case "Walking":
            default:
                return 1.0;
        }
    }
}



