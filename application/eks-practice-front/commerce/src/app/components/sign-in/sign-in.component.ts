import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent implements OnInit {

  showAlert: boolean = false;
  signInFormGroup: FormGroup = this.formBuilder.group({
    giriStoreWorkerSignIn: this.formBuilder.group({
      identifier: new FormControl('', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(35),
        this.notAllowBlank,
      ]),

      password: new FormControl('', [
        Validators.required,
        Validators.minLength(10),
        Validators.maxLength(50),
      ])
    })
  });

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit(): void {

  }

  get identifier(): any { return this.signInFormGroup.get('giriStoreWorkerSignIn.identifier'); }

  get password(): any { return this.signInFormGroup.get('giriStoreWorkerSignIn.password'); }

  notAllowBlank(control: FormControl) {
    const isWhiteSpace = (control.value || '').trim().length === 0;
    const isValid = !isWhiteSpace;
    return isValid ? null : {'whitespace': true};
  }

  signIn(): void {
    if (this.signInFormGroup.invalid) {
      this.signInFormGroup.markAllAsTouched();
      return;
    }

    // const email = this.loginFormGroup.get('giriStoreWorker').value.email;
    // const password = this.loginFormGroup.get('giriStoreWorker').value.password;

  }


}
