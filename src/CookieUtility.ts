import { Func1 } from "./FuncTypes";

namespace CookieUtility {
  export function set(key: string, value: string): void;
  export function set(key: string, value: string, expires: Date): void;
  export function set(key: string, value: string, maxAge: number): void;
  export function set(key: string, value: string, arg3?: Date | number): void {
    if (arg3 === undefined) {
      document.cookie = `${key}=${encodeURIComponent(value)}`;
    } else if (arg3 instanceof Date) {
      document.cookie = `${key}=${encodeURIComponent(value)};expires=${arg3.toUTCString()}`;
    } else {
      document.cookie = `${key}=${encodeURIComponent(value)};max-age=${arg3}`;
    }
  }

  export function getString(key: string): string | undefined;
  export function getString(key: string, decode: boolean): string | undefined;
  export function getString(key: string, decode = true): string | undefined {
    let result: string | undefined = undefined;

    const pattern = new RegExp(`(?:(?:^|.*;\\s*)${key}\\s*\\=\\s*([^;]*).*$)|^.*$`);
    const reResult = pattern.exec(document.cookie);
    if (reResult?.[1] !== undefined) {
      result = document.cookie.replace(pattern, "$1");

      if (decode) {
        result = decodeURIComponent(result);
      }
    }

    return result;
  }

  export function getInt(key: string): number | undefined;
  export function getInt(key: string, radix: number): number | undefined;
  export function getInt(key: string, radix: number, decode: boolean): number | undefined;
  export function getInt(key: string, radix = 10, decode = true): number | undefined {
    return get(key, str => Number.parseInt(str, radix), decode);
  }

  export function getFloat(key: string): number | undefined;
  export function getFloat(key: string, decode: boolean): number | undefined;
  export function getFloat(key: string, decode = true): number | undefined {
    return get(key, str => Number.parseFloat(str), decode);
  }

  export function get<TResult>(key: string, selector: Func1<string, TResult>): TResult | undefined;
  export function get<TResult>(key: string, selector: Func1<string, TResult>, decode: boolean): TResult | undefined;
  export function get<TResult>(key: string, selector: Func1<string, TResult>, decode = true): TResult | undefined {
    let result: TResult | undefined = undefined;

    const getResult = getString(key, decode);
    if (getResult !== undefined) {
      result = selector(getResult);
    }

    return result;
  }
}

export default CookieUtility;
