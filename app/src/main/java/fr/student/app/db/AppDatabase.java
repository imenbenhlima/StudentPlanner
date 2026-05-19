package fr.student.app.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {
                SemesterEntity.class,
                CourseEntity.class,
                TimetableSlotEntity.class,
                AssignmentEntity.class,
                CourseNoteEntity.class,
                GradeEntity.class,
                ReminderEntity.class
        },
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract SemesterDao semesterDao();
    public abstract CourseDao courseDao();
    public abstract TimetableDao timetableDao();
    public abstract AssignmentDao assignmentDao();
    public abstract NoteDao noteDao();
    public abstract GradeDao gradeDao();
    public abstract ReminderDao reminderDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "student_app.db"
                            )
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
