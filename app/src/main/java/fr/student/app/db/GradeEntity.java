package fr.student.app.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "grades",
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
        indices = {
                @Index(value = {"semesterId", "courseId"}, unique = true)
        }
)
public class GradeEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public long semesterId;
    public long courseId;
    public float value;

    public GradeEntity(long semesterId, long courseId, float value) {
        this.semesterId = semesterId;
        this.courseId = courseId;
        this.value = value;
    }
}
