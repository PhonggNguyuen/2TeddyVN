import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule,FormsModule , HttpClientModule], 
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  phone: string;
  password: string;
  retypePassword: string;
  lastName: string;
  firstName: string;
  isAccepted: boolean;

  constructor() {
    this.phone = '';
    this.password = '';
    this.retypePassword = '';
    this.firstName = '';
    this.lastName = '';
    this.isAccepted = false;
  }

  onPhoneChange() {
    console.log(`Phone typed: ${this.phone}`);
  }


  register() {
    const message = `firstName: ${this.firstName}, lastName: ${this.lastName}, phone: ${this.phone}, password: ${this.password}, retypePassword: ${this.retypePassword}, isAccepted: ${this.isAccepted}`;
    alert(message);
  }
}
