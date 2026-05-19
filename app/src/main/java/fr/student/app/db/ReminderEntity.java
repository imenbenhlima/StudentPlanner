package fr.student.app.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reminders")
public class ReminderEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String text;
    public long triggerMillis;

    public ReminderEntity(String text, long triggerMillis) {
        this.text = text;
        this.triggerMillis = triggerMillis;
    }
}
