import * as luxon from 'luxon';

type Integer = number;

type Float = number;

const MILLIS_PER_SECOND = 1000;

const MILLIS_PER_MINUTE = MILLIS_PER_SECOND * 60;

const MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60;

const MILLIS_PER_DAY = MILLIS_PER_HOUR * 24;

type ZoneArg = string | luxon.Zone | undefined;

function isTimeZoneType(arg: unknown): arg is ZoneArg {
  return arg === undefined || typeof arg === "string" || arg instanceof luxon.Zone;
}

const INTERNAL_OBJECT_KEY = "_internalObject";

function getInternal<T>(obj: unknown): T {
  return (obj as { INTERNAL_OBJECT_KEY: T }).INTERNAL_OBJECT_KEY;
}

export class DateTime {
  public constructor();
  public constructor(zone: string);
  public constructor(zone: luxon.Zone);
  public constructor(date: Date);
  public constructor(date: Date, zone: string);
  public constructor(date: Date, zone: luxon.Zone);
  public constructor(totalMilliseconds: Integer);
  public constructor(totalMilliseconds: Integer, zone: string);
  public constructor(totalMilliseconds: Integer, zone: luxon.Zone);
  public constructor(year: Integer, month: Integer, day: Integer);
  public constructor(year: Integer, month: Integer, day: Integer, zone: string);
  public constructor(year: Integer, month: Integer, day: Integer, zone: luxon.Zone);
  public constructor(year: Integer, month: Integer, day: Integer, hour: Integer, minute: Integer, second: Integer);
  public constructor(year: Integer, month: Integer, day: Integer, hour: Integer, minute: Integer, second: Integer, zone: string);
  public constructor(year: Integer, month: Integer, day: Integer, hour: Integer, minute: Integer, second: Integer, zone: luxon.Zone);
  public constructor(year: Integer, month: Integer, day: Integer, hour: Integer, minute: Integer, second: Integer, millisecond: Integer);
  public constructor(year: Integer, month: Integer, day: Integer, hour: Integer, minute: Integer, second: Integer, millisecond: Integer, zone: string);
  public constructor(year: Integer, month: Integer, day: Integer, hour: Integer, minute: Integer, second: Integer, millisecond: Integer, zone: luxon.Zone);
  public constructor(luxonDateTime: luxon.DateTime);
  public constructor(
    arg0?: ZoneArg | number | Date | luxon.DateTime,
    arg1?: ZoneArg | number,
    day = 1,
    arg3?: ZoneArg | number,
    minute = 0,
    second = 0,
    arg4?: ZoneArg | number,
    zone?: ZoneArg
  ) {
    if (arg0 === undefined) { this[INTERNAL_OBJECT_KEY] = luxon.DateTime.fromObject({ year: 1, month: 1, day: 1 }); }
    else if (arg0 instanceof luxon.DateTime) { this[INTERNAL_OBJECT_KEY] = arg0; }
    else if (isTimeZoneType(arg0)) { this[INTERNAL_OBJECT_KEY] = luxon.DateTime.fromObject({ year: 1, month: 1, day: 1, zone: arg0 }); }
    else if (arg0 instanceof Date) { this[INTERNAL_OBJECT_KEY] = luxon.DateTime.fromJSDate(arg0, { zone: arg1 as ZoneArg }); }
    else if (isTimeZoneType(arg1)) { this[INTERNAL_OBJECT_KEY] = luxon.DateTime.fromMillis(arg0, { zone: arg1 }); }
    else if (isTimeZoneType(arg3)) { this[INTERNAL_OBJECT_KEY] = luxon.DateTime.fromObject({ year: arg0, month: arg1, day: day, zone: arg3 }); }
    else if (isTimeZoneType(arg4)) { this[INTERNAL_OBJECT_KEY] = luxon.DateTime.fromObject({ year: arg0, month: arg1, day: day, hour: arg3, zone: arg4 }); }
    else /*                     */ { this[INTERNAL_OBJECT_KEY] = luxon.DateTime.fromObject({ year: arg0, month: arg1, day: day, hour: arg3, minute: minute, second: second, millisecond: arg4, zone: zone }); }
  }

  private readonly [INTERNAL_OBJECT_KEY]: luxon.DateTime;

  public get zone(): luxon.Zone { return this[INTERNAL_OBJECT_KEY].zone; }

  public get date(): DateTime { return new DateTime(this[INTERNAL_OBJECT_KEY].startOf("day")); }

  public get day(): Integer { return this[INTERNAL_OBJECT_KEY].day; }

  public get dayOfWeek(): DayOfWeek { return this[INTERNAL_OBJECT_KEY].weekday; }

  public get dayOfYear(): Integer { return this[INTERNAL_OBJECT_KEY].ordinal; }

  public get hour(): Integer { return this[INTERNAL_OBJECT_KEY].hour; }

  public get millisecond(): Integer { return this[INTERNAL_OBJECT_KEY].millisecond; }

  public get minute(): Integer { return this[INTERNAL_OBJECT_KEY].minute; }

  public get month(): Integer { return this[INTERNAL_OBJECT_KEY].month; }

  public get second(): Integer { return this[INTERNAL_OBJECT_KEY].second; }

  public get timeOfDay(): TimeSpan {
    return new TimeSpan(
      0,
      this[INTERNAL_OBJECT_KEY].hour,
      this[INTERNAL_OBJECT_KEY].minute,
      this[INTERNAL_OBJECT_KEY].second,
      this[INTERNAL_OBJECT_KEY].millisecond,
    );
  }

  public get year(): number { return this[INTERNAL_OBJECT_KEY].year; }

  public add(value: TimeSpan): DateTime {
    const result = this[INTERNAL_OBJECT_KEY].plus(value[INTERNAL_OBJECT_KEY]);
    return new DateTime(result);
  }

  public addDays(value: Float): DateTime {
    const result = this[INTERNAL_OBJECT_KEY].plus({ day: value });
    return new DateTime(result);
  }

  public addHours(value: Float): DateTime {
    const result = this[INTERNAL_OBJECT_KEY].plus({ hour: value });
    return new DateTime(result);
  }

  public addMilliseconds(value: Float): DateTime {
    const result = this[INTERNAL_OBJECT_KEY].plus({ millisecond: value });
    return new DateTime(result);
  }

  public addMinutes(value: Float): DateTime {
    const result = this[INTERNAL_OBJECT_KEY].plus({ minute: value });
    return new DateTime(result);
  }

  public addMonths(value: Integer): DateTime {
    const result = this[INTERNAL_OBJECT_KEY].plus({ month: value });
    return new DateTime(result);
  }

  public addSeconds(value: Float): DateTime {
    const result = this[INTERNAL_OBJECT_KEY].plus({ second: value });
    return new DateTime(result);
  }

  public addYears(value: Integer): DateTime {
    const result = this[INTERNAL_OBJECT_KEY].plus({ year: value });
    return new DateTime(result);
  }

  public compare(other: DateTime): number {

    return compareNumbers(this[INTERNAL_OBJECT_KEY].toMillis(), other[INTERNAL_OBJECT_KEY].toMillis());
  }

  public equals(other: DateTime): boolean {
    return this[INTERNAL_OBJECT_KEY].equals(other[INTERNAL_OBJECT_KEY]);
  }

  public toString(): string;
  public toString(format: string): string;
  public toString(format: string): string;
  public toString(format?: string, options?: luxon.LocaleOptions & luxon.DateTimeFormatOptions): string {
    return format
      ? this[INTERNAL_OBJECT_KEY].toFormat(format, options)
      : this[INTERNAL_OBJECT_KEY].toISO();
  }

  public static now(): DateTime;
  public static now(zone: string): DateTime;
  public static now(zone: luxon.Zone): DateTime;
  public static now(zone?: ZoneArg): DateTime {
    return new DateTime(luxon.DateTime.fromObject({ zone: zone }));
  }

  public static UtcNow(): DateTime {
    return new DateTime(luxon.DateTime.utc());
  }
}

export class TimeSpan {
  public constructor(luxonDuration: luxon.Duration);
  public constructor(totalMilliseconds: Integer);
  public constructor(hours: Integer, minutes: Integer, seconds: Integer);
  public constructor(days: Integer, hours: Integer, minutes: Integer, seconds: Integer);
  public constructor(days: Integer, hours: Integer, minutes: Integer, seconds: Integer, milliseconds: Integer);
  public constructor(arg0: Integer | luxon.Duration, arg1?: Integer, arg2?: Integer, arg3?: Integer, arg4?: Integer) {
    if (arg0 instanceof luxon.Duration) {
      this[INTERNAL_OBJECT_KEY] = arg0;

    } else {
      const dulationObject: luxon.DurationObject = {};
      if (arg1 === undefined) {
        dulationObject.days = 0;
        dulationObject.hours = 0;
        dulationObject.minutes = 0;
        dulationObject.seconds = 0;
        dulationObject.milliseconds = arg0;
      } else if (arg3 === undefined) {
        dulationObject.days = 0;
        dulationObject.hours = arg0;
        dulationObject.minutes = arg1;
        dulationObject.seconds = arg2!;
        dulationObject.milliseconds = 0;
      } else {
        dulationObject.days = arg0;
        dulationObject.hours = arg1;
        dulationObject.minutes = arg2!;
        dulationObject.seconds = arg3!;
        dulationObject.milliseconds = arg4!;
      }
      this[INTERNAL_OBJECT_KEY] = luxon.Duration.fromObject(dulationObject);
    }
  }

  private readonly [INTERNAL_OBJECT_KEY]: luxon.Duration;

  public get days(): Integer { return this[INTERNAL_OBJECT_KEY].days; }

  public get hours(): Integer { return this[INTERNAL_OBJECT_KEY].hours; }

  public get minutes(): Integer { return this[INTERNAL_OBJECT_KEY].minutes; }

  public get seconds(): Integer { return this[INTERNAL_OBJECT_KEY].seconds; }

  public get milliseconds(): Integer { return this[INTERNAL_OBJECT_KEY].milliseconds; }

  public get totalDays(): Float { return this[INTERNAL_OBJECT_KEY].as('days'); }

  public get totalHours(): Float { return this[INTERNAL_OBJECT_KEY].as('hours'); }

  public get totalMinutes(): Float { return this[INTERNAL_OBJECT_KEY].as('minutes'); }

  public get totalSeconds(): Float { return this[INTERNAL_OBJECT_KEY].as('seconds'); }

  public get totalMilliseconds(): Integer { return this[INTERNAL_OBJECT_KEY].as('milliseconds'); }

  public add(value: TimeSpan): TimeSpan {
    return new TimeSpan(this[INTERNAL_OBJECT_KEY].plus(value[INTERNAL_OBJECT_KEY]));
  }

  public compare(value: TimeSpan): number {
    return compareNumbers(this.totalMilliseconds, value.totalMilliseconds);
  }

  public duration(): TimeSpan {
    const totalMilliseconds = this.totalMilliseconds;
    return new TimeSpan(0, 0, 0, 0, this.totalMilliseconds >= 0 ? totalMilliseconds : -totalMilliseconds);
  }

  public equals(value: TimeSpan): boolean {
    return this[INTERNAL_OBJECT_KEY].equals(value[INTERNAL_OBJECT_KEY]);
  }

  public negate(): TimeSpan {
    return new TimeSpan(this[INTERNAL_OBJECT_KEY].negate());
  }

  public subtract(value: TimeSpan): TimeSpan {
    return new TimeSpan(this[INTERNAL_OBJECT_KEY].minus(value[INTERNAL_OBJECT_KEY]));
  }

  public toString(): string;
  public toString(format: string): string;
  public toString(format: string, options: luxon.DurationToFormatOptions): string;
  public toString(format?: string, options?: luxon.DurationToFormatOptions): string {
    return format
      ? this[INTERNAL_OBJECT_KEY].toFormat(format, options)
      : this[INTERNAL_OBJECT_KEY].toISO();
  }

  public static readonly ZERO = new TimeSpan(0);
}

/**
 * 曜日
 */
export enum DayOfWeek {
  Sunday = 0,
  Monday = 1,
  Tuesday = 2,
  Wednesday = 3,
  Thursday = 4,
  Friday = 5,
  Saturday = 6
}

function compareNumbers(a: number, b: number): number {
  if (a > b) {
    return 1;
  } else if (a < b) {
    return -1;
  } else {
    return 0;
  }
}
