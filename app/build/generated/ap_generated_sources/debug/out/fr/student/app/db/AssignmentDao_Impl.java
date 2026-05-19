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
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AssignmentDao_Impl implements AssignmentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AssignmentEntity> __insertionAdapterOfAssignmentEntity;

  private final EntityDeletionOrUpdateAdapter<AssignmentEntity> __deletionAdapterOfAssignmentEntity;

  private final EntityDeletionOrUpdateAdapter<AssignmentEntity> __updateAdapterOfAssignmentEntity;

  public AssignmentDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAssignmentEntity = new EntityInsertionAdapter<AssignmentEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `assignments` (`id`,`semesterId`,`courseId`,`title`,`description`,`dueDateMillis`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final AssignmentEntity entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.semesterId);
        statement.bindLong(3, entity.courseId);
        if (entity.title == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.title);
        }
        if (entity.description == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.description);
        }
        statement.bindLong(6, entity.dueDateMillis);
      }
    };
    this.__deletionAdapterOfAssignmentEntity = new EntityDeletionOrUpdateAdapter<AssignmentEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `assignments` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final AssignmentEntity entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfAssignmentEntity = new EntityDeletionOrUpdateAdapter<AssignmentEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `assignments` SET `id` = ?,`semesterId` = ?,`courseId` = ?,`title` = ?,`description` = ?,`dueDateMillis` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final AssignmentEntity entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.semesterId);
        statement.bindLong(3, entity.courseId);
        if (entity.title == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.title);
        }
        if (entity.description == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.description);
        }
        statement.bindLong(6, entity.dueDateMillis);
        statement.bindLong(7, entity.id);
      }
    };
  }

  @Override
  public long insert(final AssignmentEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfAssignmentEntity.insertAndReturnId(entity);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final AssignmentEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfAssignmentEntity.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final AssignmentEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfAssignmentEntity.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<AssignmentEntity> getForSemester(final long semesterId) {
    final String _sql = "SELECT * FROM assignments WHERE semesterId = ? ORDER BY dueDateMillis ASC";
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
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfDueDateMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDateMillis");
      final List<AssignmentEntity> _result = new ArrayList<AssignmentEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final AssignmentEntity _item;
        final long _tmpSemesterId;
        _tmpSemesterId = _cursor.getLong(_cursorIndexOfSemesterId);
        final long _tmpCourseId;
        _tmpCourseId = _cursor.getLong(_cursorIndexOfCourseId);
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        final String _tmpDescription;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmpDescription = null;
        } else {
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        }
        final long _tmpDueDateMillis;
        _tmpDueDateMillis = _cursor.getLong(_cursorIndexOfDueDateMillis);
        _item = new AssignmentEntity(_tmpSemesterId,_tmpCourseId,_tmpTitle,_tmpDescription,_tmpDueDateMillis);
        _item.id = _cursor.getLong(_cursorIndexOfId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public AssignmentEntity getNextDue(final long semesterId, final long fromMillis) {
    final String _sql = "SELECT * FROM assignments WHERE semesterId = ? AND dueDateMillis >= ? ORDER BY dueDateMillis ASC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, semesterId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, fromMillis);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfSemesterId = CursorUtil.getColumnIndexOrThrow(_cursor, "semesterId");
      final int _cursorIndexOfCourseId = CursorUtil.getColumnIndexOrThrow(_cursor, "courseId");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfDueDateMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "dueDateMillis");
      final AssignmentEntity _result;
      if (_cursor.moveToFirst()) {
        final long _tmpSemesterId;
        _tmpSemesterId = _cursor.getLong(_cursorIndexOfSemesterId);
        final long _tmpCourseId;
        _tmpCourseId = _cursor.getLong(_cursorIndexOfCourseId);
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        final String _tmpDescription;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmpDescription = null;
        } else {
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        }
        final long _tmpDueDateMillis;
        _tmpDueDateMillis = _cursor.getLong(_cursorIndexOfDueDateMillis);
        _result = new AssignmentEntity(_tmpSemesterId,_tmpCourseId,_tmpTitle,_tmpDescription,_tmpDueDateMillis);
        _result.id = _cursor.getLong(_cursorIndexOfId);
      } else {
        _result = null;
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
