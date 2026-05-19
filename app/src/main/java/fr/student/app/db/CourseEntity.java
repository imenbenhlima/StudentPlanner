package fr.student.app.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "courses",
        foreignKeys = @ForeignKey(
                entity = SemesterEntity.class,
                parentColumns = "id",
                childColumns = "semesterId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("semesterId")}
)
public class CourseEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public long semesterId;
    public String name;
    public float coefficient;
    public String description;
    /** One of {@link CourseType} */
    public String type;

    public CourseEntity(long semesterId, String name, float coefficient, String description, String type) {
        this.semesterId = semesterId;
        this.name = name;
        this.coefficient = coefficient;
        this.description = description;
        this.type = type;
    }
}
