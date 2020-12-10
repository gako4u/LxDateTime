package jp.visgeek.utils;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

import jp.visgeek.utils.Func1;
import jp.visgeek.utils.Func2;
import jp.visgeek.utils.collections.Enumerable;
import jp.visgeek.utils.collections.IEnumerable;

/**
 * 日時を表します。java.lang.String と同様に、このオブジェクトが表す値が変更されることはありません。
 */
public class DateTime implements Comparable<DateTime>, Cloneable, Serializable {
	// コンストラクタ
	/**
	 * 1 年 1 月 1 日 0 時 0 分 0 秒で初期化します。
	 */
	public DateTime() {
		this(1, 1, 1, 0, 0, 0, 0);
	}

	/**
	 * 年、月、日で初期化します。
	 *
	 * @param year 1 以上。
	 * @param month 1 以上。
	 * @param day 1 以上。
	 */
	public DateTime(int year, int month, int day) {
		this(year, month, day, 0, 0, 0, 0);
	}

	/**
	 * 年、月、日、時で初期化します。
	 *
	 * @param year 1 以上。
	 * @param month 1 以上。
	 * @param day 1 以上。
	 * @param hour 0 以上。
	 */
	public DateTime(int year, int month, int day, int hour) {
		this(year, month, day, hour, 0, 0, 0);
	}

	/**
	 * 年、月、日、時、分、秒で初期化します。
	 *
	 * @param year 1 以上。
	 * @param month 1 以上。
	 * @param day 1 以上。
	 * @param hour 0 以上。
	 * @param minute 0 以上。
	 * @param second 0 以上。
	 */
	public DateTime(int year, int month, int day, int hour, int minute, int second) {
		this(year, month, day, hour, minute, second, 0);
	}

	/**
	 * 年、月、日、時、分、秒、ミリ秒で初期化します。
	 *
	 * @param year 1 以上。
	 * @param month 1 以上。
	 * @param day 1 以上。
	 * @param hour 0 以上。
	 * @param minute 0 以上。
	 * @param second 0 以上。
	 * @param millisecond 0 以上。
	 */
	public DateTime(int year, int month, int day, int hour, int minute, int second, int millisecond) {
		this.calendar = Calendar.getInstance();
		this.calendar.set(year, month - 1, day, hour, minute, second);
		this.calendar.set(Calendar.MILLISECOND, millisecond);
	}

	/**
	 * java.util.Calendar で初期化します。
	 *
	 * @param calendar
	 */
	public DateTime(Calendar calendar) {
		if (calendar == null) {
			throw new NullPointerException("calendar");
		}
		this.calendar = (Calendar) calendar.clone();
	}

	/**
	 * java.util.Date で初期化します。
	 *
	 * @param date
	 */
	public DateTime(Date date) {
		if (date == null) {
			throw new NullPointerException("date");
		}
		this.calendar = Calendar.getInstance();
		this.calendar.setTime(date);
	}

	/**
	 * 1 年 1 月 1 日 0 時 0 分 0 秒からのミリ秒で初期化します。
	 *
	 * @param millisecondes
	 */
	public DateTime(long millisecondes) {
		this.calendar = Calendar.getInstance();
		this.calendar.setTimeInMillis(millisecondes - this.differnceOfEpoch());
	}

	/**
	 * 1 年 1 月 1 日 0 時 0 分 0 秒からの timeSpanで指定した時間間隔で初期化します。
	 *
	 * @param timeSpan
	 */
	public DateTime(TimeSpan timeSpan) {
		this((long) timeSpan.totalMilliseconds());
	}

	// フィールド
	/**
	 * インスタンスの内部値です。値が変更されることはありません。
	 */
	private final Calendar calendar;

	// プロパティ相当
	/**
	 * 年を表す数値(1 ～)を取得します。
	 *
	 * @return
	 */
	public final int year() {
		return this.calendar.get(Calendar.YEAR);
	}

	/**
	 * 月を表す数値(1 ～ 31)を取得します。
	 *
	 * @return
	 */
	public final int month() {
		return this.calendar.get(Calendar.MONTH) + 1;
	}

	/**
	 * 日を表す数値を取得します。
	 *
	 * @return
	 */
	public final int day() {
		return this.calendar.get(Calendar.DATE);
	}

	/**
	 * 時を表す数値(0 ～ 23)を取得します。
	 *
	 * @return
	 */
	public final int hour() {
		return this.calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 分を表す数値(0 ～ 59)を取得します。
	 *
	 * @return
	 */
	public final int minute() {
		return this.calendar.get(Calendar.MINUTE);
	}

	/**
	 * 秒を表す数値(0 ～ 59)を取得します。
	 *
	 * @return
	 */
	public final int second() {
		return this.calendar.get(Calendar.SECOND);
	}

	/**
	 * ミリ秒を表す数値(0 ～ 999)を取得します。
	 *
	 * @return
	 */
	public final int millisecond() {
		return this.calendar.get(Calendar.MILLISECOND);
	}

	/**
	 * 曜日を取得します。
	 *
	 * @return
	 */
	public final DayOfWeek dayOfWeek() {
		return DayOfWeek.fromCalendarDayOfWeek(this.calendar.get(Calendar.DAY_OF_WEEK));
	}

	/**
	 * 日付部分を取得します。
	 *
	 * @return
	 */
	public final DateTime date() {
		Calendar clone = this.cloneInternal();
		clone.set(Calendar.HOUR_OF_DAY, 0);
		clone.set(Calendar.MINUTE, 0);
		clone.set(Calendar.SECOND, 0);
		clone.set(Calendar.MILLISECOND, 0);
		return new DateTime(clone);
	}

	public final TimeSpan timeOfDay() {
		return new TimeSpan(0, this.hour(), this.minute(), this.second(), this.millisecond());
	}

	// メソッド
	public final DateTime add(TimeSpan value) {
		if (value == null) {
			throw new NullPointerException("value");
		}

		// この計算は DateTime と TimeSpan の内部値の仕様変更があれば変更する必要がある。
		return this.addMilliseconds((long) value.totalMilliseconds());
	}

	/**
	 * 指定した年数を足した新しいインスタンスを返します。負数も受け付けます。
	 *
	 * @param years
	 * @return
	 */
	public final DateTime addYears(int years) {
		Calendar clone = this.cloneInternal();
		clone.add(Calendar.YEAR, years);
		return new DateTime(clone);
	}

	/**
	 * 指定した月数を足した新しいインスタンスを返します。負数も受け付けます。
	 *
	 * @param months
	 * @return
	 */
	public final DateTime addMonths(int months) {
		Calendar clone = this.cloneInternal();
		clone.add(Calendar.MONTH, months);
		return new DateTime(clone);
	}

	/**
	 * 指定した日数を足した新しいインスタンスを返します。負数も受け付けます。
	 *
	 * @param days
	 * @return
	 */
	public final DateTime addDays(int days) {
		Calendar clone = this.cloneInternal();
		clone.add(Calendar.DATE, days);
		return new DateTime(clone);
	}

	/**
	 * 指定した時間数を足した新しいインスタンスを返します。負数も受け付けます。
	 *
	 * @param hours
	 * @return
	 */
	public final DateTime addHours(int hours) {
		Calendar clone = this.cloneInternal();
		clone.add(Calendar.HOUR, hours);
		return new DateTime(clone);
	}

	/**
	 * 指定した分数を足した新しいインスタンスを返します。負数も受け付けます。
	 *
	 * @param minutes
	 * @return
	 */
	public final DateTime addMinutes(int minutes) {
		Calendar clone = this.cloneInternal();
		clone.add(Calendar.MINUTE, minutes);
		return new DateTime(clone);
	}

	/**
	 * 指定した秒数を足した新しいインスタンスを返します。負数も受け付けます。
	 *
	 * @param seconds
	 * @return
	 */
	public final DateTime addSeconds(int seconds) {
		Calendar clone = this.cloneInternal();
		clone.add(Calendar.SECOND, seconds);
		return new DateTime(clone);
	}

	/**
	 * 指定したミリ秒数を足した新しいインスタンスを返します。負数も受け付けます。
	 *
	 * @param milliseconds
	 * @return
	 */
	public final DateTime addMilliseconds(long milliseconds) {
		Calendar clone = this.cloneInternal();
		clone.setTimeInMillis(clone.getTimeInMillis() + milliseconds);
		return new DateTime(clone);
	}

	/**
	 * 指定した日時との差を返します。
	 *
	 * @param value
	 * @return
	 */
	public final TimeSpan subtract(DateTime value) {
		if (value == null) {
			throw new NullPointerException("value");
		}

		return TimeSpan.fromMilliseconds(this.calendar.getTimeInMillis() - value.calendar.getTimeInMillis());
	}

	/**
	 * 指定した時間間隔を減算した値を返します。
	 * @param value
	 * @return
	 */
	public DateTime subtract(TimeSpan value) {
		if (value == null) {
			throw new NullPointerException("value");
		}

		// この計算は DateTime と TimeSpan の内部値の仕様変更があれば変更する必要がある。
		return this.addMilliseconds((long) -value.totalMilliseconds());
	}

	/**
	 * 指定した日時との差をミリ秒で返します。
	 *
	 * @deprecated 使用禁止
	 * @param value
	 * @return
	 */
	@Deprecated
	public final long subtract2(DateTime value) {
		if (value == null) {
			throw new NullPointerException("value");
		}
		return this.calendar.getTimeInMillis() - value.calendar.getTimeInMillis();
	}

	/**
	 * 指定した期間内に含まれているかどうかを判断します。
	 *
	 * @param start
	 * @param end
	 * @return
	 */
	public final boolean between(DateTime start, DateTime end) {
		if (start == null) {
			throw new NullPointerException("start");

		} else if (end == null) {
			throw new NullPointerException("end");

		} else if (end.compareTo(start) < 0) {
			throw new IllegalArgumentException("開始日時と終了日時の関係が不正です。");

		} else {
			return start.compareTo(this) <= 0 && this.compareTo(end) < 0;
		}
	}

	/**
	 * 指定した期間内に含まれているかどうかを判断します。
	 *
	 * @param period
	 * @return
	 */
	public final boolean between(Period period) {
		if (period == null) {
			throw new NullPointerException("period");
		}

		return this.between(period.start(), period.end());
	}

	/**
	 * 指定した日時よりも前であるかどうかを判断します。
	 *
	 * @param other
	 * @return
	 */
	public final boolean before(DateTime other) {
		if (other == null) {
			throw new NullPointerException("other");
		}
		return this.compareTo(other) < 0;
	}

	/**
	 * 指定した java.util.Calendar オブジェクトよりも前であるかどうかを判断します。
	 *
	 * @param other
	 * @return
	 */
	public final boolean before(Calendar other) {
		if (other == null) {
			throw new NullPointerException("value");
		}
		return this.calendar.before(other);
	}

	/**
	 * 指定した java.util.Date オブジェクトよりも前であるかどうかを判断します。
	 *
	 * @param other
	 * @return
	 */
	public final boolean before(Date other) {
		if (other == null) {
			throw new NullPointerException("value");
		}
		return this.toDate().before(other);
	}

	/**
	 * 指定した日時と同じまたは前であるかどうかを判断します。
	 *
	 * @param other
	 * @return
	 */
	public final boolean beforeOrEqual(DateTime other) {
		if (other == null) {
			throw new NullPointerException("other");
		}
		return this.compareTo(other) <= 0;
	}

	/**
	 * 指定した日時と同じまたは前であるかどうかを判断します。
	 *
	 * @param other
	 * @return
	 */
	public final boolean beforeOrEqual(Calendar other) {
		if (other == null) {
			throw new NullPointerException("other");
		}
		return this.calendar.compareTo(other) <= 0;
	}

	/**
	 * 指定した日時と同じまたは前であるかどうかを判断します。
	 *
	 * @param other
	 * @return
	 */
	public final boolean beforeOrEqual(Date other) {
		if (other == null) {
			throw new NullPointerException("other");
		}
		return this.toDate().compareTo(other) <= 0;
	}

	/**
	 * 内部値のクローンを作成します。
	 *
	 * @return
	 */
	private final Calendar cloneInternal() {
		Calendar clone = Calendar.getInstance();
		clone.setTimeInMillis(this.calendar.getTimeInMillis());
		return clone;
	}

	/**
	 * java.util.Calendar オブジェクトに変換します。
	 *
	 * @return
	 */
	public final Calendar toCalendar() {
		return this.cloneInternal();
	}

	/**
	 * java.util.Date オブジェクトに変換します。
	 *
	 * @return
	 */
	public final Date toDate() {
		return this.calendar.getTime();
	}

	/**
	 * オブジェクトが表す日時を文字列に変換します。
	 */
	@Override
	public final String toString() {
		return this.toString("yyyy/MM/dd HH:mm:ss.SSS");
	}

	/**
	 * オブジェクトが表す日時を指定したフォーマットで文字列に変換します。
	 *
	 * @param format
	 * @return
	 */
	public final String toString(String format) {
		if (format == null) {
			throw new NullPointerException("format");
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(this.toDate());
	}

	/**
	 * 1 年 1 月 1 日 00:00:00 からのミリ秒数を返します。
	 *
	 * @return
	 */
	public final long toTotalMilliseconds() {
		return this.calendar.getTimeInMillis() + this.differnceOfEpoch();
	}

	/**
	 * 1970 年 1 月 1 日 00:00:00 からのミリ秒数を返します。
	 *
	 * @return
	 */
	public final long toTotalUnixMilliseconds() {
		return this.calendar.getTimeInMillis();
	}

	/**
	 * このオブジェクトのハッシュコードを返します。
	 */
	@Override
	public final int hashCode() {
		return this.calendar.hashCode();
	}

	/**
	 * 指定されたオブジェクトと等しいかどうかを判断します。比較対象がこのオブジェクトと同じクラスかつ同じ値である場合、真となります。
	 */
	@Override
	@Deprecated
	public final boolean equals(Object obj) {
		boolean result = false;

		if (obj instanceof DateTime) {
			result = this.equals((DateTime) obj);
		}

		return result;
	}

	public final boolean equals(DateTime obj) {
		boolean result = false;

		if (obj != null) {
			if (this.calendar.equals(obj.calendar)) {
				result = true;
			}
		}

		return result;
	}

	/**
	 * 指定されたオブジェクトとの大小を比較します。
	 */
	@Override
	public final int compareTo(DateTime o) {
		return DateTime.compare(this, o);
	}

	@Override
	public final Object clone() {
		return new DateTime(this.cloneInternal());
	}

	/**
	 * UNIX エポックを表すミリ秒数です。
	 */
	private final long differnceOfEpoch() {
		return this.calendar.getTimeZone().getRawOffset() + 62135769600000L;
	}

	/**
	 * このオブジェクトを開始日時として24時間ごとの日時を指定した日時まで列挙します。
	 *
	 * @param endDateTime
	 * @return
	 */
	public final IEnumerable<DateTime> enumerateDates(final DateTime endDateTime) {
		final DateTime dt = this;

		Iterable<DateTime> iterable = new Iterable<DateTime>() {
			@Override
			public Iterator<DateTime> iterator() {
				return new Iterator<DateTime>() {
					private DateTime i = dt;

					@Override
					public boolean hasNext() {
						return this.i.beforeOrEqual(endDateTime);
					}

					@Override
					public DateTime next() {
						DateTime val = this.i;
						this.i = this.i.addDays(1);
						return val;
					}

					@Override
					public void remove() {
						throw new IllegalStateException();
					}
				};
			}
		};

		return Enumerable.of(iterable);
	}

	// スタティックフィールド
	/**
	 * シリアライズ用の ID です。
	 */
	private static final long serialVersionUID = 8103237295794957176L;

	/**
	 * 最小有効値を表します。
	 */
	public static final DateTime MIN_VALUE = new DateTime(1, 1, 1, 0, 0, 0, 0);

	/**
	 * 最大有効値を表します。
	 */
	public static final DateTime MAX_VALUE = new DateTime(9999, 12, 31, 23, 59, 59, 999);

	public static final Func1<String, DateTime> parse =
			new Func1<String, DateTime>() {
				@Override
				public DateTime func(String arg) {
					return DateTime.parse(arg);
				}
			};

	public static final DateTime EXCEL_MIN_VALUE = new DateTime(1900, 1, 1, 0, 0, 0);

	/**
	 * ゲームが始まる前の日時を表します。
	 */
	public static final DateTime BEFORE_GAME_START = new DateTime(2014, 1, 1, 0, 0, 0, 0);

	public static final Func2<String, DateTime, DateTime> tryParse =
			new Func2<String, DateTime, DateTime>() {
				@Override
				public DateTime func(String arg1, DateTime arg2) {
					return tryParseOrDefault(arg1, arg2);
				}
			};

	private static final String[] DATE_FORMATS =
			new String[] {
				"yyyy/MM/dd HH:mm:ss.SSS",
				"yyyy/MM/dd HH:mm:ss",
				"yyyy/MM/dd HH:mm",
				"yyyy/MM/dd",
				"yyyy-MM-dd'T'HH:mm:ssX",
				"yyyy-MM-dd'T'HH:mm:ss",
				"yyyy-MM-dd'T'HH:mm",
				"yyyy-MM-dd HH:mm:ss.SSS",
				"yyyy-MM-dd HH:mm:ss",
				"yyyy-MM-dd HH:mm",
				"yyyy-MM-dd"
			};

	// スタティックプロパティ相当
	/**
	 * 現在の日時を表す値を取得します。
	 *
	 * @return
	 */
	public static DateTime now() {
		return new DateTime(Calendar.getInstance());
	}

	/**
	 * 現在の日時を世界協定時刻(UTC)で表した値を取得します。
	 *
	 * @return
	 */
	public static DateTime utcNow() {
		return new DateTime(Calendar.getInstance(TimeZone.getTimeZone("UTC")));
	}

	// スタティックメソッド
	/**
	 * 1 つ目のインスタンスが 2 つ目のインスタンスよりも小さいか、等しいか、大きいかを表す整数を返します。null は null 以外の値より大きいと見なされます。null 同士の比較は等しいと見なされます。
	 *
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static int compare(DateTime t1, DateTime t2) {
		if (t1 == null) {
			return (t2 == null) ? 0 : -1;

		} else if (t2 == null) {
			return 1;

		} else {
			return t1.calendar.compareTo(t2.calendar);
		}
	}

	/**
	 * 2 つのインスタンスが等しいかどうかを判断します。一方だけが null の場合は false を返します。両方が null の場合は true を返します。
	 *
	 * @param t1
	 * @param t2
	 * @return
	 */
	public static boolean equals(DateTime t1, DateTime t2) {
		if (t1 == null) {
			return t2 == null;

		} else if (t2 == null) {
			return false;

		} else {
			return t1.calendar.equals(t2.calendar);
		}
	}

	/**
	 * 指定した月の最後の時刻を取得します。
	 * @param year
	 * @param month
	 * @return
	 */
	public static DateTime endOf(int year, int month) {
		return new DateTime(year, month + 1, 1);
	}

	/**
	 * 指定した年月日の最後の時刻を取得します。
	 * @param year
	 * @param month
	 * @return
	 */
	public static DateTime endOf(int year, int month, int day) {
		return new DateTime(year, month, day + 1);
	}

	/**
	 * 指定した月の日数を返します。
	 *
	 * @param year
	 * @param month
	 * @return
	 */
	public static int daysInMonth(int year, int month) {
		switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				return 31;

			case 4:
			case 6:
			case 9:
			case 11:
				return 30;

			case 2:
				return DateTime.isLeapYear(year) ? 29 : 28;

			default:
				throw new IllegalArgumentException("引数が不正です。");
		}
	}

	/**
	 * 指定した年が閏年かどうかを判断します。
	 *
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		if ((year % 4) != 0) {
			return false;

		} else if ((year % 100) == 0) {
			return ((year % 400) == 0);

		} else {
			return true;
		}
	}

	/**
	 * 指定した文字列が表す日時を DateTime オブジェクトに変換します。
	 *
	 * @param s
	 * @return
	 */
	public static DateTime parse(String s) {
		Date date = null;
		boolean isSuccess = false;

		for (String rule : DATE_FORMATS) {
			if (isSuccess) break;

			DateFormat df = new SimpleDateFormat(rule);
			ParsePosition pos = new ParsePosition(0);
			date = df.parse(s, pos);
			isSuccess = pos.getErrorIndex() == -1 ? true : false;
		}

		if (!isSuccess) {
			throw new IllegalArgumentException();
		}

		return new DateTime(date);
	}

	public static ParseResult tryParse(String s) {
		try {
			return new ParseResult(true, DateTime.parse(s));
		} catch (IllegalArgumentException e) {
			return new ParseResult(false, DateTime.MIN_VALUE);
		}
	}

	public static DateTime tryParseOrDefault(String s, DateTime defaultValue) {
		try {
			return DateTime.parse(s);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 今日の日付を表す値を返します。
	 *
	 * @return
	 */
	public static DateTime today() {
		return DateTime.now().date();
	}

	// スタティッククラス
	public static class ParseResult {
		private ParseResult(boolean isSuccess, DateTime value) {
			this.isSuccess = isSuccess;
			this.value = value;
		}

		private final boolean isSuccess;

		private final DateTime value;

		public final boolean isSuccess() {
			return this.isSuccess;
		}

		public final DateTime value() {
			return this.value;
		}
	}
}
