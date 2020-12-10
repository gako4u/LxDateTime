import * as luxon from "luxon";
import { DateTime, TimeSpan } from "./dateTime";

const Console = {
  WriteLine: (obj?: unknown): void => {
    if (obj === undefined) {
      console.log("");
    } else if ((obj as any)["toString"] !== undefined) {
      console.log((obj as any).toString());
    } else {
      console.log(obj);
    }
  }
};

{

  //  luxon.Settings.defaultZoneName = "America/Chicago";
  const now = luxon.Duration.fromMillis

  {
    const dt = DateTime.now();
    Console.WriteLine(dt.toString());
    Console.WriteLine(dt.year);
    Console.WriteLine(dt.month);
    Console.WriteLine(dt.day);
    Console.WriteLine(dt.hour);
    Console.WriteLine(dt.minute);
    Console.WriteLine(dt.second);
    Console.WriteLine(dt.millisecond);
    Console.WriteLine(dt.date.toString());
    Console.WriteLine(dt.dayOfWeek);
    Console.WriteLine(dt.dayOfYear);
    Console.WriteLine(dt.timeOfDay);
  }

  {
    Console.WriteLine();
    Console.WriteLine(new TimeSpan(1, 2, 3));
    Console.WriteLine(new TimeSpan(1, 2, 3, 4));
    Console.WriteLine(new TimeSpan(1, 2, 3, 4, 5));

    Console.WriteLine();
    Console.WriteLine(new TimeSpan(1, 2, 3).milliseconds);
    Console.WriteLine(new TimeSpan(1, 2, 3, 4).milliseconds);
    Console.WriteLine(new TimeSpan(1, 2, 3, 4, 5).milliseconds);

    Console.WriteLine();
    Console.WriteLine(new TimeSpan(1, 2, 3).totalMilliseconds);
    Console.WriteLine(new TimeSpan(1, 2, 3, 4).totalMilliseconds);
    Console.WriteLine(new TimeSpan(1, 2, 3, 4, 5).totalMilliseconds);

    Console.WriteLine();
    Console.WriteLine(new DateTime(1888, 1, 1, 0, 0, 0, 0));
    Console.WriteLine(new DateTime(0));
    Console.WriteLine(new DateTime());
  }

  {
    Console.WriteLine();
    const dt = new DateTime(2021, 1, 1, 2, 3, 4, 5);
    Console.WriteLine(dt.toString("yyyy/MM/dd hh:mm:ss.SSS"));

    const dt2 = dt.add(new TimeSpan(0.5, 0, 0, 0, 0));
    Console.WriteLine(dt2.toString("yyyy/MM/dd hh:mm:ss.SSS"));

    const dt3 = dt.addDays(-1);
    Console.WriteLine(dt3.toString("yyyy/MM/dd HH:mm:ss.SSS"));
  }

  // const MILLIS_PER_SECOND = 1000;
  // const MILLIS_PER_MINUTE = MILLIS_PER_SECOND * 60;
  // const MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60;
  // const MILLIS_PER_DAY = MILLIS_PER_HOUR * 24;
  // const totalDays = totalMillisecondes / MILLIS_PER_DAY;
  // const totalHours = totalMillisecondes / MILLIS_PER_HOUR;
  // const totalMinutes = totalMillisecondes / MILLIS_PER_MINUTE;
  // const totalSeconds = totalMillisecondes / MILLIS_PER_SECOND;

  // const days = Math.floor(totalDays);
  // const hours = Math.floor(totalHours % 24);
  // const minutes = Math.floor(totalMinutes % 60);
  // const seconds = Math.floor(totalSeconds % 60);
  // const milliseconds = Math.floor(totalMillisecondes % 1000);

  // console.log(`${hours}:${minutes}:${seconds}:${milliseconds}`);
}
