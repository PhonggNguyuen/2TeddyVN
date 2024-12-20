import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  standalone: true, // Đảm bảo HomeComponent cũng là standalone
  imports: [HeaderComponent, FooterComponent] // Import các component ở đây
})
export class HomeComponent {}
