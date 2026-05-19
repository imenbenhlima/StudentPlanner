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
public final class TimetableDao_Impl implements TimetableDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TimetableSlotEntity> __insertionAdapterOfTimetableSlotEntity;

  private final EntityDeletionOrUpdateAdapter<TimetableSlotEntity> __deletionAdapterOfTimetableSlotEntity;

  private final EntityDeletionOrUpdateAdapter<TimetableSlotEntity> __updateAdapterOfTimetableSlotEntity;

  public TimetableDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTimetableSlotEntity = new EntityInsertionAdapter<TimetableSlotEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `timetable_slots` (`id`,`semesterId`,`courseId`,`dayOfWeek`,`startMinuteOfDay`,`endMinuteOfDay`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final TimetableSlotEntity entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.semesterId);
        statement.bindLong(3, entity.courseId);
        statement.bindLong(4, entity.dayOfWeek);
        statement.bindLong(5, entity.startMinuteOfDay);
        statement.bindLong(6, entity.endMinuteOfDay);
      }
    };
    this.__deletionAdapterOfTimetableSlotEntity = new EntityDeletionOrUpdateAdapter<TimetableSlotEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `timetable_slots` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final TimetableSlotEntity entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfTimetableSlotEntity = new EntityDeletionOrUpdateAdapter<TimetableSlotEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `timetable_slots` SET `id` = ?,`semesterId` = ?,`courseId` = ?,`dayOfWeek` = ?,`startMinuteOfDay` = ?,`endMinuteOfDay` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final TimetableSlotEntity entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.semesterId);
        statement.bindLong(3, entity.courseId);
        statement.bindLong(4, entity.dayOfWeek);
        statement.bindLong(5, entity.startMinuteOfDay);
        statement.bindLong(6, entity.endMinuteOfDay);
        statement.bindLong(7, entity.id);
      }
    };
  }

  @Override
  public long insert(final TimetableSlotEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfTimetableSlotEntity.insertAndReturnId(entity);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final TimetableSlotEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfTimetableSlotEntity.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final TimetableSlotEntity entity) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfTimetableSlotEntity.handle(entity);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<TimetableSlotEntity> getForSemester(final long semesterId) {
    final String _sql = "SELECT * FROM timetable_slots WHERE semesterId = ? ORDER BY dayOfWeek, startMinuteOfDay";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, semesterId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfSemesterId = CursorUtil.getColumnIndexOrThrow(_cursor, "semesterId");
      final int _cursorIndexOfCourseId = CursorUtil.getColumnIndexOrThrow(_cursor, "courseId");
      final int _cursorIndexOfDayOfWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "dayOfWeek");
      final int _cursorIndexOfStartMinuteOfDay = CursorUtil.getColumnIndexOrThrow(_cursor, "startMinuteOfDay");
      final int _cursorIndexOfEndMinuteOfDay = CursorUtil.getColumnIndexOrThrow(_cursor, "endMinuteOfDay");
      final List<TimetableSlotEntity> _result = new ArrayList<TimetableSlotEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final TimetableSlotEntity _item;
        final long _tmpSemesterId;
        _tmpSemesterId = _cursor.getLong(_cursorIndexOfSemesterId);
        final long _tmpCourseId;
        _tmpCourseId = _cursor.getLong(_cursorIndexOfCourseId);
        final int _tmpDayOfWeek;
        _tmpDayOfWeek = _cursor.getInt(_cursorIndexOfDayOfWeek);
        final int _tmpStartMinuteOfDay;
        _tmpStartMinuteOfDay = _cursor.getInt(_cursorIndexOfStartMinuteOfDay);
        final int _tmpEndMinuteOfDay;
        _tmpEndMinuteOfDay = _cursor.getInt(_cursorIndexOfEndMinuteOfDay);
        _item = new TimetableSlotEntity(_tmpSemesterId,_tmpCourseId,_tmpDayOfWeek,_tmpStartMinuteOfDay,_tmpEndMinuteOfDay);
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
  public List<TimetableSlotEntity> getForDay(final long semesterId, final int dayOfWeek) {
    final String _sql = "SELECT * FROM timetable_slots WHERE semesterId = ? AND dayOfWeek = ? ORDER BY startMinuteOfDay ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, semesterId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, dayOfWeek);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfSemesterId = CursorUtil.getColumnIndexOrThrow(_cursor, "semesterId");
      final int _cursorIndexOfCourseId = CursorUtil.getColumnIndexOrThrow(_cursor, "courseId");
      final int _cursorIndexOfDayOfWeek = CursorUtil.getColumnIndexOrThrow(_cursor, "dayOfWeek");
      final int _cursorIndexOfStartMinuteOfDay = CursorUtil.getColumnIndexOrThrow(_cursor, "startMinuteOfDay");
      final int _cursorIndexOfEndMinuteOfDay = CursorUtil.getColumnIndexOrThrow(_cursor, "endMinuteOfDay");
      final List<TimetableSlotEntity> _result = new ArrayList<TimetableSlotEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final TimetableSlotEntity _item;
        final long _tmpSemesterId;
        _tmpSemesterId = _cursor.getLong(_cursorIndexOfSemesterId);
        final long _tmpCourseId;
        _tmpCourseId = _cursor.getLong(_cursorIndexOfCourseId);
        final int _tmpDayOfWeek;
        _tmpDayOfWeek = _cursor.getInt(_cursorIndexOfDayOfWeek);
        final int _tmpStartMinuteOfDay;
        _tmpStartMinuteOfDay = _cursor.getInt(_cursorIndexOfStartMinuteOfDay);
        final int _tmpEndMinuteOfDay;
        _tmpEndMinuteOfDay = _cursor.getInt(_cursorIndexOfEndMinuteOfDay);
        _item = new TimetableSlotEntity(_tmpSemesterId,_tmpCourseId,_tmpDayOfWeek,_tmpStartMinuteOfDay,_tmpEndMinuteOfDay);
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
