namespace DateUtility {
  export function getCurrentTimezoneOffset(): number {
    const date = new Date();
    return (date.getHours() - date.getUTCHours() + 24) % 24;
  }

  export function AddFullYear(date: Date, value: number): Date {
    return getJST(date.getFullYear() + value, date.getMonth(), date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds(), date.getMilliseconds());
  }

  export function AddMonth(date: Date, value: number): Date {
    return getJST(date.getFullYear(), date.getMonth() + value, date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds(), date.getMilliseconds());
  }

  export function AddDate(date: Date, value: number): Date {
    return getJST(date.getFullYear(), date.getMonth(), date.getDate() + value, date.getHours(), date.getMinutes(), date.getSeconds(), date.getMilliseconds());
  }

  export function AddHours(date: Date, value: number): Date {
    return getJST(date.getFullYear(), date.getMonth(), date.getDate(), date.getHours() + value, date.getMinutes(), date.getSeconds(), date.getMilliseconds());
  }

  export function AddMinutes(date: Date, value: number): Date {
    return getJST(date.getFullYear(), date.getMonth(), date.getDate(), date.getHours(), date.getMinutes() + value, date.getSeconds(), date.getMilliseconds());
  }

  export function AddSeconds(date: Date, value: number): Date {
    return getJST(date.getFullYear(), date.getMonth(), date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds() + value, date.getMilliseconds());
  }

  export function AddMilliseconds(date: Date, value: number): Date {
    return getJST(date.getFullYear(), date.getMonth(), date.getDate(), date.getHours(), date.getMinutes(), date.getSeconds(), date.getMilliseconds() + value);
  }

  export function getJST(): Date;
  export function getJST(value: number): Date;
  export function getJST(value: string): Date;
  export function getJST(value: Date): Date;
  export function getJST(year: number, month: number, date: number): Date;
  export function getJST(year: number, month: number, date: number, hours: number, minutes: number, seconds: number): Date;
  export function getJST(year: number, month: number, date: number, hours: number, minutes: number, seconds: number, milliseconds: number): Date;
  export function getJST(arg0?: number | string | Date, month?: number, date = 1, hours = 0, minutes = 0, seconds = 0, milliseconds = 0): Date {
    let dateObj: Date;

    if (arg0 === undefined) {
      dateObj = new Date();
    } else if (typeof arg0 === "string") {
      dateObj = new Date(arg0);
    } else if (arg0 instanceof Date) {
      dateObj = new Date(arg0);
    } else if (month === undefined) {
      dateObj = new Date(arg0);
    } else {
      dateObj = new Date(arg0, month, date, hours, minutes, seconds, milliseconds);
    }

    return getLocal(dateObj, 9);
  }

  export function getLocal(date: Date, timeZoneOffset: number): Date {
    return new Date(date.getTime() + (date.getTimezoneOffset() + timeZoneOffset * 60) * 60 * 1000);
  }

  /**
   * 指定されたオブジェクトが表す日付の 0 時 0 分を取得します。
   * @param value 
   */
  export function getDatePart(value: Date): Date;

  /**
   * 指定されたオブジェクトが表す日付の 0 時 0 分に addendDates の 日数を足した日付を取得します。
   * @param value
   * @param addendDates 足す日数。1 を指定した場合は翌日の 0 時を取得します。
   */
  export function getDatePart(value: Date, addendDates: number): Date;
  export function getDatePart(value: Date, addendDates = 0): Date {
    return getJST(value.getFullYear(), value.getMonth(), value.getDate() + addendDates);
  }

  export function between(value: Date, begin: Date, end: Date): boolean {
    const val = value.getTime();
    return begin.getTime() <= val && val < end.getTime();
  }

  export function getDifferenceText(a: Date, b: Date): string {
    const diffMilliseconds = b.getDate() - a.getDate();

    const days = Math.floor(diffMilliseconds / (1000 * 60 * 60 * 24));
    if (1 <= days) { return days + "日"; }

    const hours = Math.floor(diffMilliseconds / (1000 * 60 * 60));
    if (1 <= hours) { return hours + "時間"; }

    const minutes = Math.floor(diffMilliseconds / (1000 * 60));
    if (1 <= minutes) { return minutes + "分"; }

    const seconds = Math.floor(diffMilliseconds / (1000));
    if (0 < seconds) { return minutes + "秒"; }

    return "0秒";
  }
}

export default DateUtility;
