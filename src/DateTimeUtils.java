package jp.visgeek.utils;

public class DateTimeUtils {
	// コンストラクタ
	private DateTimeUtils() {
	}

	// フィールド

	// プロパティ相当

	// メソッド

	// スタティックフィールド

	// スタティックプロパティ相当

	// スタティックメソッド
	/**
	 * 残り時間を表示用にフォーマットした文字列を取得します。例: 21分
	 *
	 * @param dest
	 * @return
	 */
	public final String getStandardDifferenceTimeString(DateTime src, DateTime dest) {
		return getStandardDifferenceTimeString(src, dest, true);
	}

	/**
	 * 残り時間を表示用にフォーマットした文字列を取得します。例: 21分
	 * @param dest
	 * @param lowerVisible true にすると日時分秒において一つしたの桁まで表示する。例: 21分16秒
	 * @return
	 */
	public final String getStandardDifferenceTimeString(DateTime src, DateTime dest, boolean lowerVisible) {
		if (dest == null) {
			throw new NullPointerException("future");
		}

		TimeSpan diff = dest.subtract(src);

		if (diff.lessThan(0)) {
			return "0秒";

		} else if (diff.lessThan(TimeSpan.TICKS_PER_MINUTE)) {
			return String.format("%d秒", diff.seconds());

		} else if (diff.lessThan(TimeSpan.TICKS_PER_HOUR)) {
			return String.format(lowerVisible ? "%d分%d秒" : "%d分", diff.minutes(), diff.seconds());

		} else if (diff.lessThan(TimeSpan.TICKS_PER_DAY)) {
			return String.format(lowerVisible ? "%d時間%d分" : "%d時間", diff.hours(), diff.minutes());

		} else {
			return String.format("%d日", diff.days());
		}
	}

	/**
	 * 残り時間を表示用にフォーマットした文字列を取得します。例 09:21
	 *
	 * @param dest
	 * @return
	 */
	public final String getSimpleDifferenceTimeString(DateTime src, DateTime dest) {
		if (dest == null) {
			throw new NullPointerException("future");
		}

		TimeSpan diff = dest.subtract(src);

		if (diff.lessThan(0)) {
			return "00:00";

		} else if (diff.lessThan(TimeSpan.TICKS_PER_MINUTE)) {
			return String.format("00:%02d", diff.seconds());

		} else if (diff.lessThan(TimeSpan.TICKS_PER_HOUR)) {
			return String.format("%02d:%02d", diff.minutes(), diff.seconds());

		} else if (diff.lessThan(TimeSpan.TICKS_PER_DAY)) {
			return String.format("%02d:%02d", diff.hours(), diff.minutes());

		} else {
			return String.format("%02d", diff.days());
		}
	}
}
