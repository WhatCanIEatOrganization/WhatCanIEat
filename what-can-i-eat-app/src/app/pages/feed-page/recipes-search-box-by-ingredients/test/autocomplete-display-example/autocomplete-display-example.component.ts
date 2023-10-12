import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Observable, startWith, map } from 'rxjs';

export interface User {
  name: string;
}

@Component({
  selector: 'app-autocomplete-display-example',
  templateUrl: './autocomplete-display-example.component.html',
  styleUrls: ['./autocomplete-display-example.component.scss']
})
export class AutocompleteDisplayExampleComponent implements OnInit {
  myControl = new FormControl<string | User>('');
  options: User[] = [{name: 'Mary'}, {name: 'Shelley'}, {name: 'Igor'}];
  filteredOptions!: Observable<User[]>;
  formGroup!: FormGroup;

  ngOnInit() {
    // this.formGroup = new FormGroup({
    //   myControl: new FormControl<string | User>('')
    // });

    this.filteredOptions = this.myControl.valueChanges.pipe(
      startWith(''),
      map(value => {
        const name = typeof value === 'string' ? value : value?.name;
        return name ? this._filter(name as string) : this.options.slice();
      }),
    );
  }

  displayFn(user: User): string {
    return user && user.name ? user.name : '';
  }

  private _filter(name: string): User[] {
    const filterValue = name.toLowerCase();

    return this.options.filter(option => option.name.toLowerCase().includes(filterValue));
  }

  check(): void {
    console.log(typeof this.myControl.value === "object");
  }
}