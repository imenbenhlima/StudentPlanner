package fr.student.app.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "semesters")
public class SemesterEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;

    public SemesterEntity(String name) {
        this.name = name;
    }
}
