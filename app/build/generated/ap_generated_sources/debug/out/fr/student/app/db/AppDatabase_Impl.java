package fr.student.app.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile SemesterDao _semesterDao;

  private volatile CourseDao _courseDao;

  private volatile TimetableDao _timetableDao;

  private volatile AssignmentDao _assignmentDao;

  private volatile NoteDao _noteDao;

  private volatile GradeDao _gradeDao;

  private volatile ReminderDao _reminderDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `semesters` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `courses` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `semesterId` INTEGER NOT NULL, `name` TEXT, `coefficient` REAL NOT NULL, `description` TEXT, `type` TEXT, FOREIGN KEY(`semesterId`) REFERENCES `semesters`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_courses_semesterId` ON `courses` (`semesterId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `timetable_slots` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `semesterId` INTEGER NOT NULL, `courseId` INTEGER NOT NULL, `dayOfWeek` INTEGER NOT NULL, `startMinuteOfDay` INTEGER NOT NULL, `endMinuteOfDay` INTEGER NOT NULL, FOREIGN KEY(`semesterId`) REFERENCES `semesters`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`courseId`) REFERENCES `courses`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_timetable_slots_semesterId` ON `timetable_slots` (`semesterId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_timetable_slots_courseId` ON `timetable_slots` (`courseId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `assignments` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `semesterId` INTEGER NOT NULL, `courseId` INTEGER NOT NULL, `title` TEXT, `description` TEXT, `dueDateMillis` INTEGER NOT NULL, FOREIGN KEY(`semesterId`) REFERENCES `semesters`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`courseId`) REFERENCES `courses`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_assignments_semesterId` ON `assignments` (`semesterId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_assignments_courseId` ON `assignments` (`courseId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `course_notes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `semesterId` INTEGER NOT NULL, `courseId` INTEGER, `title` TEXT, `content` TEXT, `createdAtMillis` INTEGER NOT NULL, FOREIGN KEY(`semesterId`) REFERENCES `semesters`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_course_notes_semesterId` ON `course_notes` (`semesterId`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_course_notes_courseId` ON `course_notes` (`courseId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `grades` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `semesterId` INTEGER NOT NULL, `courseId` INTEGER NOT NULL, `value` REAL NOT NULL, FOREIGN KEY(`semesterId`) REFERENCES `semesters`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`courseId`) REFERENCES `courses`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_grades_semesterId_courseId` ON `grades` (`semesterId`, `courseId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `reminders` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `text` TEXT, `triggerMillis` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'f5d436ba32ecd2c0f8ac6a002627bff5')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `semesters`");
        db.execSQL("DROP TABLE IF EXISTS `courses`");
        db.execSQL("DROP TABLE IF EXISTS `timetable_slots`");
        db.execSQL("DROP TABLE IF EXISTS `assignments`");
        db.execSQL("DROP TABLE IF EXISTS `course_notes`");
        db.execSQL("DROP TABLE IF EXISTS `grades`");
        db.execSQL("DROP TABLE IF EXISTS `reminders`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsSemesters = new HashMap<String, TableInfo.Column>(2);
        _columnsSemesters.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSemesters.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSemesters = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSemesters = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSemesters = new TableInfo("semesters", _columnsSemesters, _foreignKeysSemesters, _indicesSemesters);
        final TableInfo _existingSemesters = TableInfo.read(db, "semesters");
        if (!_infoSemesters.equals(_existingSemesters)) {
          return new RoomOpenHelper.ValidationResult(false, "semesters(fr.student.app.db.SemesterEntity).\n"
                  + " Expected:\n" + _infoSemesters + "\n"
                  + " Found:\n" + _existingSemesters);
        }
        final HashMap<String, TableInfo.Column> _columnsCourses = new HashMap<String, TableInfo.Column>(6);
        _columnsCourses.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("semesterId", new TableInfo.Column("semesterId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("coefficient", new TableInfo.Column("coefficient", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourses.put("type", new TableInfo.Column("type", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCourses = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysCourses.add(new TableInfo.ForeignKey("semesters", "CASCADE", "NO ACTION", Arrays.asList("semesterId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesCourses = new HashSet<TableInfo.Index>(1);
        _indicesCourses.add(new TableInfo.Index("index_courses_semesterId", false, Arrays.asList("semesterId"), Arrays.asList("ASC")));
        final TableInfo _infoCourses = new TableInfo("courses", _columnsCourses, _foreignKeysCourses, _indicesCourses);
        final TableInfo _existingCourses = TableInfo.read(db, "courses");
        if (!_infoCourses.equals(_existingCourses)) {
          return new RoomOpenHelper.ValidationResult(false, "courses(fr.student.app.db.CourseEntity).\n"
                  + " Expected:\n" + _infoCourses + "\n"
                  + " Found:\n" + _existingCourses);
        }
        final HashMap<String, TableInfo.Column> _columnsTimetableSlots = new HashMap<String, TableInfo.Column>(6);
        _columnsTimetableSlots.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimetableSlots.put("semesterId", new TableInfo.Column("semesterId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimetableSlots.put("courseId", new TableInfo.Column("courseId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimetableSlots.put("dayOfWeek", new TableInfo.Column("dayOfWeek", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimetableSlots.put("startMinuteOfDay", new TableInfo.Column("startMinuteOfDay", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTimetableSlots.put("endMinuteOfDay", new TableInfo.Column("endMinuteOfDay", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTimetableSlots = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysTimetableSlots.add(new TableInfo.ForeignKey("semesters", "CASCADE", "NO ACTION", Arrays.asList("semesterId"), Arrays.asList("id")));
        _foreignKeysTimetableSlots.add(new TableInfo.ForeignKey("courses", "CASCADE", "NO ACTION", Arrays.asList("courseId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesTimetableSlots = new HashSet<TableInfo.Index>(2);
        _indicesTimetableSlots.add(new TableInfo.Index("index_timetable_slots_semesterId", false, Arrays.asList("semesterId"), Arrays.asList("ASC")));
        _indicesTimetableSlots.add(new TableInfo.Index("index_timetable_slots_courseId", false, Arrays.asList("courseId"), Arrays.asList("ASC")));
        final TableInfo _infoTimetableSlots = new TableInfo("timetable_slots", _columnsTimetableSlots, _foreignKeysTimetableSlots, _indicesTimetableSlots);
        final TableInfo _existingTimetableSlots = TableInfo.read(db, "timetable_slots");
        if (!_infoTimetableSlots.equals(_existingTimetableSlots)) {
          return new RoomOpenHelper.ValidationResult(false, "timetable_slots(fr.student.app.db.TimetableSlotEntity).\n"
                  + " Expected:\n" + _infoTimetableSlots + "\n"
                  + " Found:\n" + _existingTimetableSlots);
        }
        final HashMap<String, TableInfo.Column> _columnsAssignments = new HashMap<String, TableInfo.Column>(6);
        _columnsAssignments.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAssignments.put("semesterId", new TableInfo.Column("semesterId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAssignments.put("courseId", new TableInfo.Column("courseId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAssignments.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAssignments.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAssignments.put("dueDateMillis", new TableInfo.Column("dueDateMillis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAssignments = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysAssignments.add(new TableInfo.ForeignKey("semesters", "CASCADE", "NO ACTION", Arrays.asList("semesterId"), Arrays.asList("id")));
        _foreignKeysAssignments.add(new TableInfo.ForeignKey("courses", "CASCADE", "NO ACTION", Arrays.asList("courseId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesAssignments = new HashSet<TableInfo.Index>(2);
        _indicesAssignments.add(new TableInfo.Index("index_assignments_semesterId", false, Arrays.asList("semesterId"), Arrays.asList("ASC")));
        _indicesAssignments.add(new TableInfo.Index("index_assignments_courseId", false, Arrays.asList("courseId"), Arrays.asList("ASC")));
        final TableInfo _infoAssignments = new TableInfo("assignments", _columnsAssignments, _foreignKeysAssignments, _indicesAssignments);
        final TableInfo _existingAssignments = TableInfo.read(db, "assignments");
        if (!_infoAssignments.equals(_existingAssignments)) {
          return new RoomOpenHelper.ValidationResult(false, "assignments(fr.student.app.db.AssignmentEntity).\n"
                  + " Expected:\n" + _infoAssignments + "\n"
                  + " Found:\n" + _existingAssignments);
        }
        final HashMap<String, TableInfo.Column> _columnsCourseNotes = new HashMap<String, TableInfo.Column>(6);
        _columnsCourseNotes.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourseNotes.put("semesterId", new TableInfo.Column("semesterId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourseNotes.put("courseId", new TableInfo.Column("courseId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourseNotes.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourseNotes.put("content", new TableInfo.Column("content", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCourseNotes.put("createdAtMillis", new TableInfo.Column("createdAtMillis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCourseNotes = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysCourseNotes.add(new TableInfo.ForeignKey("semesters", "CASCADE", "NO ACTION", Arrays.asList("semesterId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesCourseNotes = new HashSet<TableInfo.Index>(2);
        _indicesCourseNotes.add(new TableInfo.Index("index_course_notes_semesterId", false, Arrays.asList("semesterId"), Arrays.asList("ASC")));
        _indicesCourseNotes.add(new TableInfo.Index("index_course_notes_courseId", false, Arrays.asList("courseId"), Arrays.asList("ASC")));
        final TableInfo _infoCourseNotes = new TableInfo("course_notes", _columnsCourseNotes, _foreignKeysCourseNotes, _indicesCourseNotes);
        final TableInfo _existingCourseNotes = TableInfo.read(db, "course_notes");
        if (!_infoCourseNotes.equals(_existingCourseNotes)) {
          return new RoomOpenHelper.ValidationResult(false, "course_notes(fr.student.app.db.CourseNoteEntity).\n"
                  + " Expected:\n" + _infoCourseNotes + "\n"
                  + " Found:\n" + _existingCourseNotes);
        }
        final HashMap<String, TableInfo.Column> _columnsGrades = new HashMap<String, TableInfo.Column>(4);
        _columnsGrades.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGrades.put("semesterId", new TableInfo.Column("semesterId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGrades.put("courseId", new TableInfo.Column("courseId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsGrades.put("value", new TableInfo.Column("value", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysGrades = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysGrades.add(new TableInfo.ForeignKey("semesters", "CASCADE", "NO ACTION", Arrays.asList("semesterId"), Arrays.asList("id")));
        _foreignKeysGrades.add(new TableInfo.ForeignKey("courses", "CASCADE", "NO ACTION", Arrays.asList("courseId"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesGrades = new HashSet<TableInfo.Index>(1);
        _indicesGrades.add(new TableInfo.Index("index_grades_semesterId_courseId", true, Arrays.asList("semesterId", "courseId"), Arrays.asList("ASC", "ASC")));
        final TableInfo _infoGrades = new TableInfo("grades", _columnsGrades, _foreignKeysGrades, _indicesGrades);
        final TableInfo _existingGrades = TableInfo.read(db, "grades");
        if (!_infoGrades.equals(_existingGrades)) {
          return new RoomOpenHelper.ValidationResult(false, "grades(fr.student.app.db.GradeEntity).\n"
                  + " Expected:\n" + _infoGrades + "\n"
                  + " Found:\n" + _existingGrades);
        }
        final HashMap<String, TableInfo.Column> _columnsReminders = new HashMap<String, TableInfo.Column>(3);
        _columnsReminders.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("text", new TableInfo.Column("text", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminders.put("triggerMillis", new TableInfo.Column("triggerMillis", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysReminders = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesReminders = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoReminders = new TableInfo("reminders", _columnsReminders, _foreignKeysReminders, _indicesReminders);
        final TableInfo _existingReminders = TableInfo.read(db, "reminders");
        if (!_infoReminders.equals(_existingReminders)) {
          return new RoomOpenHelper.ValidationResult(false, "reminders(fr.student.app.db.ReminderEntity).\n"
                  + " Expected:\n" + _infoReminders + "\n"
                  + " Found:\n" + _existingReminders);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "f5d436ba32ecd2c0f8ac6a002627bff5", "9f10a0a520cfa0bf91a9731e6f816d55");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "semesters","courses","timetable_slots","assignments","course_notes","grades","reminders");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `semesters`");
      _db.execSQL("DELETE FROM `courses`");
      _db.execSQL("DELETE FROM `timetable_slots`");
      _db.execSQL("DELETE FROM `assignments`");
      _db.execSQL("DELETE FROM `course_notes`");
      _db.execSQL("DELETE FROM `grades`");
      _db.execSQL("DELETE FROM `reminders`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(SemesterDao.class, SemesterDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CourseDao.class, CourseDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TimetableDao.class, TimetableDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AssignmentDao.class, AssignmentDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(NoteDao.class, NoteDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(GradeDao.class, GradeDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ReminderDao.class, ReminderDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public SemesterDao semesterDao() {
    if (_semesterDao != null) {
      return _semesterDao;
    } else {
      synchronized(this) {
        if(_semesterDao == null) {
          _semesterDao = new SemesterDao_Impl(this);
        }
        return _semesterDao;
      }
    }
  }

  @Override
  public CourseDao courseDao() {
    if (_courseDao != null) {
      return _courseDao;
    } else {
      synchronized(this) {
        if(_courseDao == null) {
          _courseDao = new CourseDao_Impl(this);
        }
        return _courseDao;
      }
    }
  }

  @Override
  public TimetableDao timetableDao() {
    if (_timetableDao != null) {
      return _timetableDao;
    } else {
      synchronized(this) {
        if(_timetableDao == null) {
          _timetableDao = new TimetableDao_Impl(this);
        }
        return _timetableDao;
      }
    }
  }

  @Override
  public AssignmentDao assignmentDao() {
    if (_assignmentDao != null) {
      return _assignmentDao;
    } else {
      synchronized(this) {
        if(_assignmentDao == null) {
          _assignmentDao = new AssignmentDao_Impl(this);
        }
        return _assignmentDao;
      }
    }
  }

  @Override
  public NoteDao noteDao() {
    if (_noteDao != null) {
      return _noteDao;
    } else {
      synchronized(this) {
        if(_noteDao == null) {
          _noteDao = new NoteDao_Impl(this);
        }
        return _noteDao;
      }
    }
  }

  @Override
  public GradeDao gradeDao() {
    if (_gradeDao != null) {
      return _gradeDao;
    } else {
      synchronized(this) {
        if(_gradeDao == null) {
          _gradeDao = new GradeDao_Impl(this);
        }
        return _gradeDao;
      }
    }
  }

  @Override
  public ReminderDao reminderDao() {
    if (_reminderDao != null) {
      return _reminderDao;
    } else {
      synchronized(this) {
        if(_reminderDao == null) {
          _reminderDao = new ReminderDao_Impl(this);
        }
        return _reminderDao;
      }
    }
  }
}
