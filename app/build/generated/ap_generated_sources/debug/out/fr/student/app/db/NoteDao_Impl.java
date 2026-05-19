package fr.student.app.db;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class NoteDao_Impl implements NoteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CourseNoteEntity> __insertionAdapterOfCourseNoteEntity;

  private final EntityDeletionOrUpdateAdapter<CourseNoteEntity> __deletionAdapterOfCourseNoteEntity;

  private final EntityDeletionOrUpdateAdapter<CourseNoteEntity> __updateAdapterOfCourseNoteEntity;

  public NoteDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCourseNoteEntity = new EntityInsertionAdapter<CourseNoteEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `course_notes` (`id`,`semesterId`,`courseId`,`title`,`content`,`createdAtMillis`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final CourseNoteEntity entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.semesterId);
        if (entity.courseId == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.courseId);
        }
        if (entity.title == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.title);
        }
        if (entity.content == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.content);
        }
        statement.bindLong(6, entity.createdAtMillis);
      }
    };
    this.__deletionAdapterOfCourseNoteEntity = new EntityDeletionOrUpdateAdapter<CourseNoteEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `course_notes` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final CourseNoteEntity entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfCourseNoteEntity = new EntityDeletionOrUpdateAdapter<CourseNoteEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `course_notes` SET `id` = ?,`semesterId` = ?,`courseId` = ?,`title` = ?,`content` = ?,`createdAtMillis` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final CourseNoteEntity entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.semesterId);
        if (entity.courseId == null) {
          statement.bindNull(3);
        } else {
          statement.bindLong(3, entity.courseId);
        }
        if (entity.title == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.title);
        }
        if (entity.content == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.content);
        }
        statement.bindLong(6, entity.createdAtMillis);
        statement.bindLong(7, entity.id);
      }
    };
  }

  @Override
  public long insert(final CourseNoteEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfCourseNoteEntity.insertAndReturnId(entity);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final CourseNoteEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfCourseNoteEntity.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final CourseNoteEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfCourseNoteEntity.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<CourseNoteEntity> getForSemester(final long semesterId) {
    final String _sql = "SELECT * FROM course_notes WHERE semesterId = ? ORDER BY createdAtMillis DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, semesterId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfSemesterId = CursorUtil.getColumnIndexOrThrow(_cursor, "semesterId");
      final int _cursorIndexOfCourseId = CursorUtil.getColumnIndexOrThrow(_cursor, "courseId");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
      final int _cursorIndexOfCreatedAtMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAtMillis");
      final List<CourseNoteEntity> _result = new ArrayList<CourseNoteEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final CourseNoteEntity _item;
        final long _tmpSemesterId;
        _tmpSemesterId = _cursor.getLong(_cursorIndexOfSemesterId);
        final Long _tmpCourseId;
        if (_cursor.isNull(_cursorIndexOfCourseId)) {
          _tmpCourseId = null;
        } else {
          _tmpCourseId = _cursor.getLong(_cursorIndexOfCourseId);
        }
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        final String _tmpContent;
        if (_cursor.isNull(_cursorIndexOfContent)) {
          _tmpContent = null;
        } else {
          _tmpContent = _cursor.getString(_cursorIndexOfContent);
        }
        final long _tmpCreatedAtMillis;
        _tmpCreatedAtMillis = _cursor.getLong(_cursorIndexOfCreatedAtMillis);
        _item = new CourseNoteEntity(_tmpSemesterId,_tmpCourseId,_tmpTitle,_tmpContent,_tmpCreatedAtMillis);
        _item.id = _cursor.getLong(_cursorIndexOfId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
