import { CommonModule } from '@angular/common';
import { Component, ViewChild } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { NgForm } from '@angular/forms';
import { HttpClient , HttpHeaders } from '@angular/common/http';
import { Route, Router } from '@angular/router';


@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule], 
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  @ViewChild('registerForm') registerForm!:NgForm;
  ngAfterViewInit() {
    if (!this.registerForm) {
      console.error('registerForm chưa được khởi tạo');
    }
  }
  fullName: string;
  phone: string;
  password: string;
  retypePassword: string;
  dateOfBirth: Date;
  address: string;  // Thêm trường địa chỉ
  isAccepted: boolean;

  constructor(private http: HttpClient , private router: Router) {
    this.fullName = 'Nguyễn Văn Nhất';
    this.phone = '0325467355';
    this.password = '123456';
    this.retypePassword = '123456';
    this.dateOfBirth = new Date();
    this.address = '51 Tôn Đản';  
    this.isAccepted = false;
    this.dateOfBirth.setFullYear(this.dateOfBirth.getFullYear()-18);
  }

  onPhoneChange() {
    console.log(`Phone typed: ${this.phone}`);
  }
  isPhoneValid(): boolean {
    const phonePattern = /^\d{10}$/; // Mẫu regex: số điện thoại phải gồm đúng 10 chữ số
    return phonePattern.test(this.phone);
  }
  
  checkPasswordsMatch() {
    if (this.password !== this.retypePassword) {
      this.registerForm.form.controls['retypePassword']?.setErrors({ passwordMismatch: true });
    } else {
      this.registerForm.form.controls['retypePassword']?.setErrors(null);
    }
  }
  
  checkAge() {
    if (!this.registerForm || !this.registerForm.form) {
      return; // Nếu registerForm chưa khởi tạo, thoát sớm
    }
    if (this.dateOfBirth) {
      const today = new Date();
      const birthDate = new Date(this.dateOfBirth);
      let age = today.getFullYear() - birthDate.getFullYear();
      const monthDiff = today.getMonth() - birthDate.getMonth();
      if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
        age--;
      }
      if (age < 3 || age > 120) {
        this.registerForm.form.controls['dateOfBirth']?.setErrors({ invalidAge: true });
      } else {
        this.registerForm.form.controls['dateOfBirth']?.setErrors(null);
      }
    }
  }
  
  
  
  register() {
   
    const apiUrl = 'http://localhost:8088/api/v1/users/register';
    const registerData = {
    "fullname": this.fullName,
    "phone_number": this.phone,
    "address": this.address,
    "password": this.password,
    "confirm_password": this.retypePassword,
    "date_of_birth": this.dateOfBirth,
    "facebook_account_id": 0,
    "google_account_id": 0,
    "role_id": 1
    };
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    this.http.post(apiUrl, registerData, { headers }).subscribe({
      next: (response: any) => {
        debugger
        this.router.navigate(['/login']);
      },
      complete: () => {
        debugger
      },
      error: (error: any) => {
       alert(`Không thể đăng kí ${error.error}`);
      }
    });
    
  }
}
