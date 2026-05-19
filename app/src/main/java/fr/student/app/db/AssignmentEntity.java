package fr.student.app.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "assignments",
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
public class AssignmentEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public long semesterId;
    public long courseId;
    public String title;
    public String description;
    public long dueDateMillis;

    public AssignmentEntity(long semesterId, long courseId, String title, String description, long dueDateMillis) {
        this.semesterId = semesterId;
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.dueDateMillis = dueDateMillis;
    }
}
