import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-register',
  imports: [FormsModule ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  phone: string = "";
  password:string = "";
  retypePassword:string = "";
  constructor(){
    this.phone = '';
    this.password = "";
    this.retypePassword = "";
  }
  onPhoneChange(){
    console.log(`Phone typed:${this.phone}`);
  }
}
