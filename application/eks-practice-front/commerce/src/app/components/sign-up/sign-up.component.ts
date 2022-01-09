import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validator, Validators} from "@angular/forms";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  allTermsChecked = false;
  terms = [
    { id: 'required_chk_join_terms_fourteen', label: '[필수] 만 14세 이상입니다', checked: false },
    { id: 'required_chk_join_terms_service', label: '[필수] Giri Store Management 이용약관 동의', checked: false },
    { id: 'required_chk_join_terms_commerce', label: '[필수] 전자금융거래 이용약관 동의', checked: false },
    { id: 'required_chk_join_terms_privacy_collect_use', label: '[필수] 개인정보 수집 및 이용 동의', checked: false },
    { id: 'required_chk_join_terms_agree_to_collect_third_part_information', label: '[필수] 개인정보 제3자 제공 동의', checked: false },
    { id: 'chk_marketing_purpose_collect', label: '[선택] 마케팅 및 이벤트 목적의 개인정보 수집 및 이용동의', checked: false },
    { id: 'chk_agree_to_receive_ads', label: '[선택] 광고성 정보 수신 동의', checked: false },
    { id: 'check_agree_to_receive_email', label: '[선택] 이메일 수신 동의', checked: false },
  ];

  signUpFormGroup: FormGroup = this.formBuilder.group({
      giriStoreWorkerSignUp: this.formBuilder.group({
        identifier: new FormControl('', [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(35),
          this.notAllowBlank
        ]),

        password: new FormControl('', [
          Validators.required,
          Validators.minLength(10),
          Validators.maxLength(50),
        ]),

        required_chk_join_terms_fourteen: new FormControl(false, [Validators.requiredTrue]),
        required_chk_join_terms_service: new FormControl(false, [Validators.requiredTrue]),
        required_chk_join_terms_commerce: new FormControl(false, [Validators.requiredTrue]),
        required_chk_join_terms_privacy_collect_use: new FormControl(false, [Validators.requiredTrue]),
        required_chk_join_terms_agree_to_collect_third_part_information: new FormControl(false, [Validators.requiredTrue]),
        chk_marketing_purpose_collect: new FormControl(false),
        chk_agree_to_receive_ads: new FormControl(false),
        check_agree_to_receive_email: new FormControl(false),
      })
    }
  )
  constructor(private formBuilder: FormBuilder) { }

  ngOnInit(): void {
  }

  get identifier(): any {
    return this.signUpFormGroup.get('giriStoreWorkerSignUp.identifier');
  }

  get password(): any {
    return this.signUpFormGroup.get('giriStoreWorkerSignUp.password');
  }

  get termsFourteen(): any {
    return this.signUpFormGroup.get('giriStoreWorkerSignUp.required_chk_join_terms_fourteen')
  }

  get termsService(): any {
    return this.signUpFormGroup.get('giriStoreWorkerSignUp.required_chk_join_terms_service');
  }

  get termsCommerce(): any {
    return this.signUpFormGroup.get('giriStoreWorkerSignUp.required_chk_join_terms_commerce');
  }

  get termsPrivacyCollectUse(): any {
    return this.signUpFormGroup.get('giriStoreWorkerSignUp.required_chk_join_terms_privacy_collect_use');
  }

  get termsAgreeToCollectThirdPArtInformation(): any {
    return this.signUpFormGroup.get('giriStoreWorkerSignUp.required_chk_join_terms_agree_to_collect_third_part_information');
  }

  notAllowBlank(control: FormControl) {
    const isWhiteSpace = (control.value || '').trim().length === 0;
    const isValid = !isWhiteSpace;
    return isValid ? null : {'whitespace': true};
  }

  allAgree(event: any) {
    this.allTermsChecked = true;
    const checked = event.target.checked;
    this.terms.forEach(item => item.checked = checked);
  }

  signUp(): void {
    if (this.signUpFormGroup.invalid) {
      this.signUpFormGroup.markAllAsTouched();
      return;
    }
  }

  observe(event: any) {
    const checked = event.target.checked;
    let target = this.terms.find(item => item.id === event.target.id);
    // @ts-ignore
    target.checked = checked;
    this.allTermsChecked = this.terms.length === this.terms.filter(item => item.checked).length;
  }
}
