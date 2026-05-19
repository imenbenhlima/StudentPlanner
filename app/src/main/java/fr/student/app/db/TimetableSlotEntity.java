package fr.student.app.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "timetable_slots",
        foreignKeys = {
                @ForeignKey(
                        entity = SemesterEntity.class,
                        parentColumns = "id",
                        childColumns = "semesterId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = CourseEntity.class,
                        parentColumns = "id",
                        childColumns = "courseId",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index("semesterId"), @Index("courseId")}
)
public class TimetableSlotEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public long semesterId;
    public long courseId;
    /** {@link java.util.Calendar#DAY_OF_WEEK} */
    public int dayOfWeek;
    /** Minutes from midnight */
    public int startMinuteOfDay;
    public int endMinuteOfDay;

    public TimetableSlotEntity(long semesterId, long courseId, int dayOfWeek, int startMinuteOfDay, int endMinuteOfDay) {
        this.semesterId = semesterId;
        this.courseId = courseId;
        this.dayOfWeek = dayOfWeek;
        this.startMinuteOfDay = startMinuteOfDay;
        this.endMinuteOfDay = endMinuteOfDay;
    }
}
