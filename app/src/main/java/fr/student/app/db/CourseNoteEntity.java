package fr.student.app.db;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "course_notes",
        foreignKeys = @ForeignKey(
                entity = SemesterEntity.class,
                parentColumns = "id",
                childColumns = "semesterId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("semesterId"), @Index("courseId")}
)
public class CourseNoteEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public long semesterId;
    @Nullable
    public Long courseId;
    public String title;
    public String content;
    public long createdAtMillis;

    public CourseNoteEntity(long semesterId, Long courseId, String title, String content, long createdAtMillis) {
        this.semesterId = semesterId;
        this.courseId = courseId;
        this.title = title;
        this.content = content;
        this.createdAtMillis = createdAtMillis;
    }
}
