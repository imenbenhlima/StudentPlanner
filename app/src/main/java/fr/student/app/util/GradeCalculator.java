package fr.student.app.util;

import java.util.List;

import fr.student.app.db.CourseEntity;
import fr.student.app.db.GradeEntity;

public final class GradeCalculator {
    private GradeCalculator() {}

    /** Weighted average from stored grades and course coefficients. */
    public static float weightedAverage(List<GradeEntity> grades, java.util.Map<Long, CourseEntity> courseById) {
        if (grades == null || grades.isEmpty()) {
            return Float.NaN;
        }
        double sumWeighted = 0;
        double sumCoeff = 0;
        for (GradeEntity g : grades) {
            CourseEntity c = courseById.get(g.courseId);
            if (c == null || c.coefficient <= 0) continue;
            sumWeighted += g.value * c.coefficient;
            sumCoeff += c.coefficient;
        }
        if (sumCoeff <= 0) {
            return Float.NaN;
        }
        return (float) (sumWeighted / sumCoeff);
    }
}
