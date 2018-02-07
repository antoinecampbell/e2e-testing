import {$, by, element} from "protractor";
import * as fs from "fs";

export class E2EUtils {

  static login(username?: string = 'user', password?: string = 'user'): void {
    $('input[formControlName="username"]').sendKeys(username);
    $('input[formControlName="password"]').sendKeys(password);
    element(by.css('button')).click();
  }

  static writeScreenShot(data, imageName): void {
    const path = 'build/test-results/e2e/images';
    this.createDirectories(path.split('/'));

    const stream = fs.createWriteStream(`${path}/${imageName}.png`);
    stream.write(new Buffer(data, 'base64'));
    stream.end();
  }

  static createDirectories(pathList: string[]): void {
    pathList.forEach((value, index) => {
      const path = pathList.slice(0, index + 1).join('/');
      if (!fs.existsSync(path)) {
        fs.mkdirSync(path);
      }
    });

  }
}
