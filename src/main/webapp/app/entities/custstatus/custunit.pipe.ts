import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'custUnitPipe'})
export class CustUnitPipe implements PipeTransform {
  transform(value: number): number {
   return parseInt(value.toString().substr(1,2));
  }
}