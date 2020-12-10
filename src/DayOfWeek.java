package jp.visgeek.utils;

import java.util.Calendar;

import org.apache.commons.lang3.StringUtils;

/**
 * 曜日を表す列挙体です。
 */
public enum DayOfWeek {
	/**
	 * 日曜日
	 */
	SUNDAY(Calendar.SUNDAY, "Sunday", "日"),

	/**
	 * 月曜日
	 */
	MONDAY(Calendar.MONDAY, "Monday", "月"),

	/**
	 * 火曜日
	 */
	TUESDAY(Calendar.TUESDAY, "Tuesday", "火"),

	/**
	 * 水曜日
	 */
	WEDNESDAY(Calendar.WEDNESDAY, "Wednesday", "水"),

	/**
	 * 木曜日
	 */
	THURSDAY(Calendar.THURSDAY, "Thursday", "木"),

	/**
	 * 金曜日
	 */
	FRIDAY(Calendar.FRIDAY, "Friday", "金"),

	/**
	 * 土曜日
	 */
	SATURDAY(Calendar.SATURDAY, "Saturday", "土"),
	//
	;

	private DayOfWeek(int calendarDayOfWeek, String text, String shortTextJapanese) {
		this.calendarDayOfWeek = calendarDayOfWeek;
		this._text = text;
		this._shortText = StringUtils.left(text, 3);
		this._textJapanese = shortTextJapanese + "曜日";
		this._shortTextJapanese = shortTextJapanese;
	}

	private final int calendarDayOfWeek;

	private final String _text;

	private final String _shortText;

	private final String _textJapanese;

	private final String _shortTextJapanese;

	/**
	 * java.util.Calendar の曜日定数に変換します。
	 *
	 * @return
	 */
	public int toCalendarDayOfWeek() {
		return this.calendarDayOfWeek;
	}

	/**
	 * 英語の曜日名を取得します。
	 * @return
	 */
	public final String text() {
		return this._text;
	}

	public final String shortText() {
		return this._shortText;
	}

	public final String textJapanese() {
		return this._textJapanese;
	}

	public final String shortTextJapanese() {
		return this._shortTextJapanese;
	}

	@Override
	public String toString() {
		return this.text();
	}

	/**
	 * java.util.Calendar の曜日定数で初期化します。
	 * @param calendarDayOfWeek
	 * @return
	 */
	public static DayOfWeek fromCalendarDayOfWeek(int calendarDayOfWeek) {
		//@formatter:off
		switch (calendarDayOfWeek) {
			case Calendar.SUNDAY:    return DayOfWeek.SUNDAY;
			case Calendar.MONDAY:    return DayOfWeek.MONDAY;
			case Calendar.TUESDAY:   return DayOfWeek.TUESDAY;
			case Calendar.WEDNESDAY: return DayOfWeek.WEDNESDAY;
			case Calendar.THURSDAY:  return DayOfWeek.THURSDAY;
			case Calendar.FRIDAY:    return DayOfWeek.FRIDAY;
			case Calendar.SATURDAY:  return DayOfWeek.SATURDAY;
			default:
				throw new IllegalArgumentException("不正な値です。");
		}
		//@formatter:on
	}
}
