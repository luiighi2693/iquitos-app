export default class Util {
  // static checkNumbersOnly(event: any): boolean {
  //   const charCode = (event.which) ? event.which : event.keyCode;
  //   return !(charCode > 31 && (charCode < 48 || charCode > 57));
  // }

  static checkCharactersOnly(event: any): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;

    if (charCode === 8 || charCode === 32) {
      return true;
    }

    const patron = /[A-Za-z]/;
    return patron.test(String.fromCharCode(charCode));
  }

  static checkCharactersAndNumbersOnly(event: any): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;

    if (charCode === 8 || charCode === 32) {
      return true;
    }

    const patron = /[A-Za-z0-9]/;
    return patron.test(String.fromCharCode(charCode));
  }

  static checkNumbersOnly(event: any): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;

    if (charCode === 8) {
      return true;
    }

    const patron = /[0-9]/;
    return patron.test(String.fromCharCode(charCode));
  }

  static checkNumbersDecimalOnly(event: any): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode === 8 || (charCode === 46 && !event.target.value.includes('.'))) {
      return true;
    }

    const patron = /[0-9]/;
    return patron.test(String.fromCharCode(charCode));
  }
}
