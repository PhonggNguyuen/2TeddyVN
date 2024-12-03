import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrl: './order.component.scss',
  standalone: true, 
  imports: [HeaderComponent, FooterComponent] // Import các component ở đây
})
export class OrderComponent {

}
